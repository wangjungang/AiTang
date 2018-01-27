package com.example.administrator.aitang.ui.zhibo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.bean.zhibo.ClassDetailBean;
import com.example.administrator.aitang.zhibo.DemoCache;
import com.example.administrator.aitang.zhibo.base.ui.TActivity;
import com.example.administrator.aitang.zhibo.base.util.log.LogUtil;
import com.example.administrator.aitang.zhibo.education.doodle.Transaction;
import com.example.administrator.aitang.zhibo.education.doodle.TransactionCenter;
import com.example.administrator.aitang.zhibo.education.fragment.ChatRoomFragment;
import com.example.administrator.aitang.zhibo.education.fragment.ChatRoomMessageFragment;
import com.example.administrator.aitang.zhibo.education.fragment.ChatRoomRTSFragment;
import com.example.administrator.aitang.zhibo.education.fragment.OnlinePeopleFragment;
import com.example.administrator.aitang.zhibo.education.helper.ChatRoomMemberCache;
import com.example.administrator.aitang.zhibo.education.helper.VideoListener;
import com.example.administrator.aitang.zhibo.education.util.Preferences;
import com.example.administrator.aitang.zhibo.im.session.ModuleProxy;
import com.example.administrator.aitang.zhibo.im.ui.dialog.DialogMaker;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.chatroom.ChatRoomService;
import com.netease.nimlib.sdk.chatroom.ChatRoomServiceObserver;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomInfo;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomKickOutEvent;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomStatusChangeData;
import com.netease.nimlib.sdk.chatroom.model.EnterChatRoomData;
import com.netease.nimlib.sdk.chatroom.model.EnterChatRoomResultData;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.rts.RTSCallback;
import com.netease.nimlib.sdk.rts.RTSChannelStateObserver;
import com.netease.nimlib.sdk.rts.RTSManager2;
import com.netease.nimlib.sdk.rts.constant.RTSTunnelType;
import com.netease.nimlib.sdk.rts.model.RTSData;
import com.netease.nimlib.sdk.rts.model.RTSTunData;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hzxuwen on 2016/2/29.
 */
public class ZhiboActivity extends TActivity implements VideoListener {
    private static final String TAG = ZhiboActivity.class.getSimpleName();

    private final static String EXTRA_ROOM_ID = "ROOM_ID";
    private final static String EXTRA_MODE = "EXTRA_MODE";
    private final static String KEY_SHARE_URL = "webUrl";
    /**
     * 聊天室基本信息
     */
    private String roomId;
    private ChatRoomInfo roomInfo;

    private boolean isCreate; // true 主持人模式，false 观众模式
    private String shareUrl; // 分享地址
    private String sessionId; // 多人白板sessionid
    private String sessionName;

    /**
     * 子页面
     */
    private ChatRoomMessageFragment messageFragment;
    private ChatRoomFragment fragment;
    private ChatRoomRTSFragment rtsFragment;
    private OnlinePeopleFragment onlinePeopleFragment;

    private AbortableFuture<EnterChatRoomResultData> enterRequest;

    private boolean isFirstComing = true; // 主播是否首次进入房间


    private ClassDetailBean.DataBean data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhibo);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        parseIntent();

        // 注册监听
        registerObservers(true);
        // 登录聊天室
        enterRoom();
    }

    @Override
    protected void onDestroy() {
        endSession();
        registerObservers(false);
        if (!TextUtils.isEmpty(sessionName)) {
            logd("unregister rts observers");
            registerRTSObservers(sessionName, false);
        }

        if (fragment != null) {
            fragment.onKickOut();
        }

        super.onDestroy();
    }


    /**
     * @param context
     * @param roomId   聊天室ID
     * @param isCreate 是否是聊天室的创建者
     */
    public static void start(Context context, String roomId, boolean isCreate, ClassDetailBean.DataBean data) {
        Intent intent = new Intent();
        intent.setClass(context, ZhiboActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(EXTRA_ROOM_ID, roomId);
        intent.putExtra(EXTRA_MODE, isCreate);
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", data);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private void parseIntent() {
        roomId = getIntent().getStringExtra(EXTRA_ROOM_ID);
        isCreate = getIntent().getBooleanExtra(EXTRA_MODE, false);
        data = getIntent().getParcelableExtra("data");
    }

    @Override
    public void onBackPressed() {
        if (messageFragment == null || !messageFragment.onBackPressed()) {
            if (fragment != null) {
                fragment.onBackPressed();
            }
        }
    }

    private void registerObservers(boolean register) {
        NIMClient.getService(ChatRoomServiceObserver.class).observeOnlineStatus(onlineStatus, register);
        NIMClient.getService(ChatRoomServiceObserver.class).observeKickOutEvent(kickOutObserver, register);
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(userStatusObserver, register);
    }

    Observer<StatusCode> userStatusObserver = new Observer<StatusCode>() {
        @Override
        public void onEvent(StatusCode statusCode) {
            if (statusCode.wontAutoLogin()) {
                NIMClient.getService(ChatRoomService.class).exitChatRoom(roomId);
                if (fragment != null) {
                    fragment.onKickOut();
                }
            }
        }
    };

    protected void log(String flag, String str) {
        if (flag.equals("d"))
            Log.d("TAG", "来自" + ZhiboActivity.this.getClass().getSimpleName() + "的日志" + str);
        else if (flag.equals("i"))
            Log.i("TAG", "来自" + ZhiboActivity.this.getClass().getSimpleName() + "的日志" + str);
    }

    protected void logd(String str) {
        log("d", str);
    }

    protected void logi(String str) {
        log("i", str);
    }

    Observer<ChatRoomStatusChangeData> onlineStatus = new Observer<ChatRoomStatusChangeData>() {
        @Override
        public void onEvent(ChatRoomStatusChangeData chatRoomStatusChangeData) {
            if (chatRoomStatusChangeData.status == StatusCode.CONNECTING) {
                DialogMaker.updateLoadingMessage("连接中...");
            } else if (chatRoomStatusChangeData.status == StatusCode.UNLOGIN) {
                if (NIMClient.getService(ChatRoomService.class).getEnterErrorCode(roomId) == ResponseCode.RES_CHATROOM_STATUS_EXCEPTION) {
                    // 聊天室连接状态异常

                    NIMClient.getService(ChatRoomService.class).exitChatRoom(roomId);
                    if (fragment != null) {
                        fragment.onKickOut();
                    }
                } else {
                    if (fragment != null) {
                        fragment.onOnlineStatusChanged(false);
                    }
                }
            } else if (chatRoomStatusChangeData.status == StatusCode.LOGINING) {
                DialogMaker.updateLoadingMessage("登录中...");
            } else if (chatRoomStatusChangeData.status == StatusCode.LOGINED) {
                if (fragment != null) {
                    fragment.onOnlineStatusChanged(true);
                }
            } else if (chatRoomStatusChangeData.status.wontAutoLogin()) {
            } else if (chatRoomStatusChangeData.status == StatusCode.NET_BROKEN) {
                logd(getString(R.string.net_broken));
                if (fragment != null) {
                    fragment.onOnlineStatusChanged(false);
                }
            }
            Log.i("TAG", "Chat Room Online Status:" + chatRoomStatusChangeData.status.name());
        }
    };

    Observer<ChatRoomKickOutEvent> kickOutObserver = new Observer<ChatRoomKickOutEvent>() {
        @Override
        public void onEvent(ChatRoomKickOutEvent chatRoomKickOutEvent) {
            if (chatRoomKickOutEvent.getReason() == ChatRoomKickOutEvent.ChatRoomKickOutReason.CHAT_ROOM_INVALID) {
                if (!roomInfo.getCreator().equals(DemoCache.getAccount()))
                    Toast.makeText(ZhiboActivity.this, R.string.meeting_closed, Toast.LENGTH_SHORT).show();
            } else if (chatRoomKickOutEvent.getReason() == ChatRoomKickOutEvent.ChatRoomKickOutReason.KICK_OUT_BY_MANAGER) {
                Toast.makeText(ZhiboActivity.this, R.string.kick_out_by_master, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ZhiboActivity.this, "被踢出聊天室，reason:" + chatRoomKickOutEvent.getReason(), Toast.LENGTH_SHORT).show();
            }

            if (fragment != null) {
                fragment.onKickOut();
            }
        }
    };
    ProgressBar progressBar;

    public class refresh implements Runnable {
        private int width;
        private MediaPlayer mplay = null;

        public refresh(int width, MediaPlayer mplay) {
            this.width = width;
            this.mplay = mplay;
        }

        @Override
        public void run() {
            while (mplay.isPlaying()) {
                //获取当前播放位置
                int position = mplay.getCurrentPosition() / 1000;
                //计算进度条位置

            }
        }
    }

    private void enterRoom() {

        DialogMaker.showProgressDialog(this, null, "", true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (enterRequest != null) {
                    enterRequest.abort();
                    onLoginDone();
                    finish();
                }
            }
        }).setCanceledOnTouchOutside(false);
        EnterChatRoomData data = new EnterChatRoomData(roomId);
        enterRequest = NIMClient.getService(ChatRoomService.class).enterChatRoom(data);
        logd("进入聊天室");
        enterRequest.setCallback(new RequestCallback<EnterChatRoomResultData>() {
            @Override
            public void onSuccess(EnterChatRoomResultData result) {
                Log.i("TAG", "进入聊天室成功" + "--member:" + result.getMember() + "--creator:" + result.getRoomInfo().getCreator() + "--cre:" + DemoCache.getAccount());
                onLoginDone();
                roomInfo = result.getRoomInfo();
                ChatRoomMember member = result.getMember();
                member.setRoomId(roomInfo.getRoomId());
                ChatRoomMemberCache.getInstance().saveMyMember(member);
                if (roomInfo.getCreator().equals(DemoCache.getAccount())) {
                    isCreate = true;
                }
                if (roomInfo.getExtension() != null) {
                    shareUrl = (String) roomInfo.getExtension().get(KEY_SHARE_URL);
                }
                initChatRoomFragment(roomInfo.getName());
                initRTSFragment(roomInfo);
                initRTSSession();
                registerRTSObservers(roomInfo.getRoomId(), true);
            }


            @Override
            public void onFailed(int code) {
                onLoginDone();
                if (code == ResponseCode.RES_CHATROOM_BLACKLIST) {
                    Toast.makeText(ZhiboActivity.this, "你已被拉入黑名单，不能再进入", Toast.LENGTH_SHORT).show();
                } else if (code == ResponseCode.RES_ENONEXIST) {
                    Toast.makeText(ZhiboActivity.this, "该聊天室不存在", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("TAG", "进入聊天室失败, code=" + code);
                }
                finish();
            }

            @Override
            public void onException(Throwable exception) {
                onLoginDone();
                Log.i("TAG", "进入聊天室异常, e=" + exception.getMessage());
                finish();
            }
        });
    }


    private void onLoginDone() {
        enterRequest = null;
        DialogMaker.dismissProgressDialog();
    }

    private void initChatRoomFragment(final String roomName) {
        fragment = (ChatRoomFragment) getSupportFragmentManager().findFragmentById(R.id.chat_rooms_fragment);
        if (fragment != null) {
            fragment.initLiveVideo(roomInfo, roomName, isCreate, shareUrl, new ModuleProxy() {
                @Override
                public boolean sendMessage(IMMessage msg) {
                    return false;
                }

                @Override
                public void onInputPanelExpand() {

                }

                @Override
                public void shouldCollapseInputPanel() {
                    if (messageFragment != null) {
                        messageFragment.shouldCollapseInputPanel();
                    }
                }

                @Override
                public boolean isLongClickEnabled() {
                    return false;
                }
            });
        } else {
            // 如果Fragment还未Create完成，延迟初始化
            getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    initChatRoomFragment(roomName);
                }
            }, 50);
        }
    }

    private void initRTSFragment(final ChatRoomInfo roomInfo) {
        rtsFragment = (ChatRoomRTSFragment) getSupportFragmentManager().findFragmentById(R.id.chat_room_rts_fragment);
        if (rtsFragment != null) {
            rtsFragment.initRTSView(roomInfo.getRoomId(), roomInfo);
        } else {
            getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    initRTSFragment(roomInfo);
                }
            }, 50);
        }
    }

    private void updateRTSFragment() {
        rtsFragment = (ChatRoomRTSFragment) getSupportFragmentManager().findFragmentById(R.id.chat_room_rts_fragment);
        if (rtsFragment != null) {
            rtsFragment.initView();
        } else {
            getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateRTSFragment();
                }
            }, 50);
        }
    }


    // 初始化多人白板
    private void initRTSSession() {
        // 主播创建并进入多人白板session。观众进入多人白板session
        if (roomInfo.getCreator().equals(DemoCache.getAccount())) {
            logd("initrtssession");
            RTSManager2.getInstance().createSession(roomInfo.getRoomId(), "test", new RTSCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.i("TAG", "创建多人白板房间成功");
                    joinRTSSession();
                }

                @Override
                public void onFailed(int i) {
                    if (i == 417) {
                        Log.i("TAG", "多人白板房间已经被预定");
                        joinRTSSession();
                    }
                    Log.i("TAG", "create rts session failed, code:" + i);
                }

                @Override
                public void onException(Throwable throwable) {
                    Log.i("TAG", "create rts session failed, throwable:" + throwable.getMessage());
                }
            });
        } else

        {
            joinRTSSession();
        }

    }

    // 加入多人白板session
    private void joinRTSSession() {
        boolean isOpen = Preferences.getRTSRecord();
        //isOpen：是否开启服务器录制
//        final boolean finalIsOpen = isOpen;
        final boolean finalIsOpen = false;
        RTSManager2.getInstance().joinSession(roomInfo.getRoomId(), finalIsOpen, new RTSCallback<RTSData>() {
            @Override
            public void onSuccess(RTSData rtsData) {
                logd(rtsData.getAccount() + "-" + rtsData.getExtra() + "-" + rtsData.getLocalSessionId() + "-"
                        + rtsData.getTimeTag() + "-" + rtsData.getTunnelTypes() + "-" + rtsData.getChannelId());
//                null-test-19743991-1513062139066-[DATA]-193671953745537

                // 主播的白板默认为开启状态
                logd("roominfo.getcreator:" + roomInfo.getCreator() + "---account:" + DemoCache.getAccount());
                if (roomInfo.getCreator().equals(DemoCache.getAccount())) {
                    logd("设置了是主播");
                    ChatRoomMemberCache.getInstance().setRTSOpen(true);
                    updateRTSFragment();
                }
                Log.i("TAG", "加入多人白板房间成功");
            }

            @Override
            public void onFailed(int i) {
                Log.i("TAG", "加入白板房间失败, code:" + i);
            }

            @Override
            public void onException(Throwable throwable) {
                Log.i("TAG", "加入白板房间异常，throwable:" + throwable.getMessage());
            }
        });

        sessionId = roomInfo.getRoomId();
    }

    private void registerRTSObservers(String sessionName, boolean register) {
        this.sessionName = sessionName;
        RTSManager2.getInstance().observeChannelState(sessionName, channelStateObserver, register);//监听互动白板通道状态
        RTSManager2.getInstance().observeReceiveData(sessionName, receiveDataObserver, register);//监听数据收发
    }


    private void endSession() {
        RTSManager2.getInstance().leaveSession(sessionName, new RTSCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                LogUtil.i(TAG, "离开白板房间成功");
            }

            @Override
            public void onFailed(int code) {
                LogUtil.i(TAG, "离开白板房间失败, code:" + code);
            }

            @Override
            public void onException(Throwable exception) {

            }
        });
    }

    /**
     * 监听当前会话的状态
     */
    private RTSChannelStateObserver channelStateObserver = new RTSChannelStateObserver() {

        @Override
        public void onConnectResult(String localSessionId, RTSTunnelType tunType, long channelId, int code, String recordFile) {
            Log.d("TAG", "服务器录制的回调, tunType=" + tunType.toString() +
                    ", channelId=" + channelId +
                    ", code=" + code + ",recordFile=" + recordFile);
            if (code != 200) {
                RTSManager2.getInstance().leaveSession(sessionId, null);
                return;
            }

            // 主播进入，
            // 1、第一次进入，或者异常退出发送白板清空指令。
            // 2、网络变化，发送主播的同步数据。
            List<Transaction> cache = new ArrayList<>(1);
            if (roomInfo.getCreator().equals(DemoCache.getAccount())) {
                if (isFirstComing) {
                    isFirstComing = false;
                    cache.add(new Transaction().makeClearSelfTransaction());
                    cache.add(new Transaction().makeFlipTranscation("", 0, 0, 1));
                    TransactionCenter.getInstance().sendToRemote(sessionId, null, cache);
                } else {
                    TransactionCenter.getInstance().onNetWorkChange(sessionId, true);
                }
            } else {
                // 非主播进入房间，发送同步请求，请求主播向他同步之前的白板笔记
                Log.d("TAG", "发送同步请求");
                TransactionCenter.getInstance().onNetWorkChange(sessionId, false);
                cache.add(new Transaction().makeSyncRequestTransaction());
                TransactionCenter.getInstance().sendToRemote(sessionId, roomInfo.getCreator(), cache);
            }
        }

        @Override
        public void onChannelEstablished(String sessionId, RTSTunnelType tunType) {
            logd("onCallEstablished,tunType=" + tunType.toString());
        }

        @Override
        public void onUserJoin(String sessionId, RTSTunnelType tunType, String account) {
            LogUtil.i(TAG, "On User Join, account:" + account);
        }

        @Override
        public void onUserLeave(String sessionId, RTSTunnelType tunType, String account, int event) {
            LogUtil.i(TAG, "On User Leave, account:" + account);
        }

        @Override
        public void onDisconnectServer(String sessionId, RTSTunnelType tunType) {
            logd("onDisconnectServer, tunType=" + tunType.toString());
            if (tunType == RTSTunnelType.DATA) {
                // 如果数据通道断了，那么关闭会话
                Toast.makeText(ZhiboActivity.this, "TCP通道断开，自动结束会话", Toast.LENGTH_SHORT).show();
                RTSManager2.getInstance().leaveSession(sessionId, null);
            } else if (tunType == RTSTunnelType.AUDIO) {
            }
        }

        @Override
        public void onError(String sessionId, RTSTunnelType tunType, int code) {
            Log.i("TAG", "onError, tunType=" + tunType.toString() + ", error=" + code);
        }

        @Override
        public void onNetworkStatusChange(String sessionId, RTSTunnelType tunType, int value) {
            // 网络信号强弱
            LogUtil.i(TAG, "network status:" + value);
        }
    };

    /**
     * 监听收到对方发送的通道数据
     */
    private Observer<RTSTunData> receiveDataObserver = new Observer<RTSTunData>() {
        @Override
        public void onEvent(RTSTunData rtsTunData) {
            logd("收到白板数据");
            String data = "[parse bytes error]";
            try {
                data = new String(rtsTunData.getData(), 0, rtsTunData.getLength(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            TransactionCenter.getInstance().onReceive(sessionId, rtsTunData.getAccount(), data);
        }
    };

    public ChatRoomInfo getRoomInfo() {
        return roomInfo;
    }

    public void setRoomInfo(ChatRoomInfo roomInfo) {
        this.roomInfo = roomInfo;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (messageFragment != null) {
            messageFragment.onActivityResult(requestCode, resultCode, data);
        }

        if (rtsFragment != null) {
            rtsFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onVideoOn(String account) {
        if (fragment != null) {
            fragment.onVideoOn(account);
        }
    }

    @Override
    public void onVideoOff(String account) {
        if (fragment != null) {
            fragment.onVideoOff(account);
        }
    }

    @Override
    public void onTabChange(boolean notify) {
        if (fragment != null) {
            fragment.onTabChange(notify);
        }
    }

    @Override
    public void onKickOutSuccess(String account) {

    }

    @Override
    public void onUserLeave(String account) {
        if (account.equals(roomInfo.getCreator())) {
            Toast.makeText(this, "主播离开房间，课程结束", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onReportSpeaker(Map<String, Integer> map) {
        if (onlinePeopleFragment == null) {
            onlinePeopleFragment = (OnlinePeopleFragment) getSupportFragmentManager().findFragmentById(R.id.online_people_fragment);
        }

        if (onlinePeopleFragment != null) {
            onlinePeopleFragment.onReportSpeaker(map);
        }
    }

    @Override
    public void onAcceptConfirm() {
        if (onlinePeopleFragment == null) {
            onlinePeopleFragment = (OnlinePeopleFragment) getSupportFragmentManager().findFragmentById(R.id.online_people_fragment);
        }

        if (onlinePeopleFragment != null) {
            onlinePeopleFragment.onAcceptConfirm();
        }

        if (rtsFragment == null) {
            rtsFragment = (ChatRoomRTSFragment) getSupportFragmentManager().findFragmentById(R.id.chat_room_rts_fragment);
        }

        if (rtsFragment != null) {
            rtsFragment.onAcceptConfirm();
        }
    }

    public boolean isCreate() {
        return isCreate;
    }

    public String getSessionId() {
        return sessionId;
    }

}

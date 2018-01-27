package com.example.administrator.aitang.fragment.wode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.ui.mine.PlayBackActivity;
import com.example.administrator.aitang.zhibo.base.ui.TFragment;
import com.example.administrator.aitang.zhibo.base.util.ScreenUtil;
import com.example.administrator.aitang.zhibo.base.util.log.LogUtil;
import com.example.administrator.aitang.zhibo.education.activity.FileListActivity;
import com.example.administrator.aitang.zhibo.education.doodle.ActionTypeEnum;
import com.example.administrator.aitang.zhibo.education.doodle.DoodleView;
import com.example.administrator.aitang.zhibo.education.doodle.OnlineStatusObserver;
import com.example.administrator.aitang.zhibo.education.doodle.SupportActionType;
import com.example.administrator.aitang.zhibo.education.doodle.Transaction;
import com.example.administrator.aitang.zhibo.education.doodle.TransactionCenter;
import com.example.administrator.aitang.zhibo.education.doodle.action.MyPath;
import com.example.administrator.aitang.zhibo.education.helper.VideoListener;
import com.example.administrator.aitang.zhibo.education.model.Document;
import com.example.administrator.aitang.zhibo.education.model.FileDownloadStatusEnum;
import com.example.administrator.aitang.zhibo.im.ui.dialog.EasyAlertDialogHelper;
import com.example.administrator.aitang.zhibo.im.util.storage.StorageType;
import com.example.administrator.aitang.zhibo.im.util.storage.StorageUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomInfo;
import com.netease.nimlib.sdk.document.DocumentManager;
import com.netease.nimlib.sdk.document.model.DMData;
import com.netease.nimlib.sdk.document.model.DMDocTransQuality;
import com.netease.nimlib.sdk.nos.NosService;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.os.Looper.getMainLooper;

/**
 * Created by hzxuwen on 2016/10/27.
 */

public class RTSFragment extends TFragment implements View.OnClickListener, VideoListener, OnlineStatusObserver, DoodleView.FlipListener {
    private static final String TAG = RTSFragment.class.getSimpleName();
    @BindView(R.id.doodle_view)
    DoodleView doodleView; // 画板
    // 文档分享
    @BindView(R.id.file_loading_text)
    TextView fileLoadingText;
    @BindView(R.id.close_file_btn)
    TextView closeFileBtn;
    Unbinder unbinder;
    private View rootView;


    // data
    private String sessionId; // 白板sessionId
    private HashMap<Integer, Integer> colorChoosedMap = new HashMap<>();
    private HashMap<Integer, Integer> colorMap = new HashMap<>();
    private int choosedColor;

    private int currentPageNum = 0;
    private int totalPageNum = 0;
    private Document document;

    public DoodleView getDoodleView() {
        return doodleView;
    }

    private boolean isFileMode = false; // 是否是文档模式

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_rt, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListener();
    }

    public void onCurrent() {
    }

    @Override
    public void onResume() {
        super.onResume();
        doodleView.onResume();
    }

    public void onKickOut() {
        LogUtil.d(TAG, "chat room do kick out");
        getActivity().finish();
    }


    @Override
    public void onDestroy() {
        if (doodleView != null) {
            doodleView.end();
        }
        registerObservers(false);
        super.onDestroy();
    }

    private void registerObservers(boolean register) {
        TransactionCenter.getInstance().registerOnlineStatusObserver(sessionId, this);
    }


    private void setListener() {
        // 文档
        closeFileBtn.setOnClickListener(this);
        fileLoadingText.setOnClickListener(this);
    }

    public void initRTSView(String sessionId, ChatRoomInfo roomInfo) {
        this.sessionId = sessionId;
//        this.roomInfo = roomInfo;
        initView();
        initDoodleView(null);
        registerObservers(true);
    }

    public void initRTSView() {
        initView();
        initDoodleView(null);
        registerObservers(true);
    }

    // 初始化是否开启白板
    public void initView() {
        doodleView.setEnableView(false);
    }


    @Override
    public void onClick(View v) {
        if (v == closeFileBtn) {
            confirmCloseFileShare();
        } else if (v == fileLoadingText) {
            pageFlip(docTransaction);
        }
    }

    private Activity activity;

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        this.activity = (PlayBackActivity) activity;
    }

    /**
     * ***************************** 画板 ***********************************
     */

    private void initDoodleView(String account) {
        // add support ActionType
        SupportActionType.getInstance().addSupportActionType(ActionTypeEnum.Path.getValue(), MyPath.class);
        doodleView.init("123", account, DoodleView.Mode.BOTH, Color.WHITE, Color.BLACK, getContext(), this);
        doodleView.setPaintSize(3);
        doodleView.setPaintType(ActionTypeEnum.Path.getValue());

        // adjust paint offset
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Rect frame = new Rect();
                getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
                int statusBarHeight = frame.top;
                Log.i("Doodle", "statusBarHeight =" + statusBarHeight);

                int marginTop = doodleView.getTop();
                Log.i("Doodle", "doodleView marginTop =" + marginTop);

                int marginLeft = doodleView.getLeft();
                Log.i("Doodle", "doodleView marginLeft =" + marginLeft);

                float offsetX = marginLeft;
                float offsetY = statusBarHeight + marginTop + ScreenUtil.dip2px(220f) + ScreenUtil.dip2px(40);

                doodleView.setPaintOffset(offsetX, offsetY);
                Log.i("Doodle", "client1 offsetX = " + offsetX + ", offsetY = " + offsetY);
            }
        }, 50);
    }

    @Override
    public void onVideoOn(String account) {

    }

    @Override
    public void onVideoOff(String account) {

    }

    @Override
    public void onTabChange(boolean notify) {

    }

    @Override
    public void onKickOutSuccess(String account) {

    }

    @Override
    public void onUserLeave(String account) {
        logd("chatroomrtsfragment--onuserleave");
    }

    @Override
    public void onReportSpeaker(Map<String, Integer> map) {

    }

    @Override
    public void onAcceptConfirm() {
        initView();
    }

    /*************************** 网络状态 *********************/

    @Override
    public boolean onNetWorkChange(boolean isCreator) {
        // 断网重连。主播断网重连上来，给观众发自己的同步数据
        // 观众先清空本地
        if (isCreator) {
            doodleView.sendSyncPrepare();
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    doodleView.sendSyncData(null);
                }
            }, 50);
        } else {
            initView();
            doodleView.clearAll();
        }
        return true;
    }

    /******************** activity result **************/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == FileListActivity.REQUEST_CODE) {
            Document doc = (Document) data.getSerializableExtra(FileListActivity.EXTRA_DATA_DOC);
            this.document = doc;
            enterDocMode(doc);
        }
    }

    /********************* 文档共享 **********************/

    private void enterDocMode(Document document) {
        isFileMode = true;
        updatePagesUI(document, 1);
        closeFileBtn.setVisibility(View.VISIBLE);
        Map<Integer, String> pathMap = document.getPathMap();
        if (pathMap == null) {
            return;
        }
        String path = pathMap.get(1);

        // 把正在使用文档，通知给观众
        masterSendFlipData(document, 1);

        // 主播自己界面显示文档信息
        try {
            final Bitmap bitmap = BitmapFactory.decodeFile(path);
            if (bitmap != null) {
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doodleView.setImageView(bitmap);
                    }
                }, 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updatePagesUI(Document document, int currentPageNum) {
        if (isFileMode) {
            totalPageNum = document.getDmData().getPageNum();
            updatePages(currentPageNum, totalPageNum);
            updatePageBtnUI();
        } else {
        }
    }

    private void updatePages(int currentNum, int pagesNum) {
        this.currentPageNum = currentNum;
    }

    /**
     * 上下翻页
     */
    private void changePages(Document document) {
        if (currentPageNum < 1) {
            currentPageNum = 1;
            return;
        }
        if (currentPageNum > totalPageNum) {
            currentPageNum = totalPageNum;
            return;
        }
        // 主播翻页
        // 1、自己doodleview显示新的内容
        // 2、发送控制命令给观众
        Bitmap bitmap = BitmapFactory.decodeFile(document.getPathMap().get(currentPageNum));
        doodleView.setImageView(bitmap);
        updatePages(currentPageNum, totalPageNum);
        updatePageBtnUI();
        masterSendFlipData(document, currentPageNum);
    }

    private void updatePageBtnUI() {
        if (currentPageNum == 1) {
        } else {
        }
        if (currentPageNum == totalPageNum) {
        } else {
        }
    }

    private void masterSendFlipData(Document document, int currentPageNum) {
        doodleView.clear();
        doodleView.sendFlipData(document.getDmData().getDocId(), currentPageNum, document.getDmData().getPageNum(), 1);
    }

    /************************ FlipListener *******************/

    @Override
    public void onFlipPage(Transaction transaction) {
        pageFlip(transaction);
    }

    private DMData docData;
    private Transaction docTransaction;

    private void pageFlip(final Transaction transaction) {
        this.docTransaction = transaction;
        if (transaction == null) {
            return;
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showLoadingText();
            }
        });

        // 文档第0页，表示退出文档分享
        if (transaction.getCurrentPageNum() == 0) {
            isFileMode = false;
            closeFileShare();
            hideLoadingText();
            return;
        }
        // 如果文档信息已经下载过了，就不用载了。直接去载翻页图片
        isFileMode = true;

        if (docData != null && docData.getDocId().equals(transaction.getDocId())) {
            doDownloadPage(document, transaction.getCurrentPageNum());
            return;
        }
        LogUtil.i(TAG, "doc id:" + transaction.getDocId());
        DocumentManager.getInstance().querySingleDocumentData(transaction.getDocId(), new RequestCallback<DMData>() {
            @Override
            public void onSuccess(DMData dmData) {
                LogUtil.i(TAG, "query doc success");
                docData = dmData;
                document = new Document(dmData, new HashMap<Integer, String>(), FileDownloadStatusEnum.NotDownload);
                doDownloadPage(document, transaction.getCurrentPageNum());
            }

            @Override
            public void onFailed(int i) {
                LogUtil.i(TAG, "query doc failed, code:" + i);
                showRetryLoadingText();
            }

            @Override
            public void onException(Throwable throwable) {
                LogUtil.i(TAG, "query doc exception:" + throwable.toString());
            }
        });
    }

    private void doDownloadPage(final Document document, final int currentPage) {
        if (document == null || document.getDmData() == null) {
            return;
        }
        if (getDoodleView().isClick()) return;
        final String path = StorageUtil.getWritePath(document.getDmData().getDocName() + currentPage, StorageType.TYPE_FILE);
        String url = document.getDmData().getTransCodedUrl(currentPage, DMDocTransQuality.MEDIUM);
        Map<Integer, String> pathMap = document.getPathMap();
        pathMap.put(currentPage, path);
        document.setPathMap(pathMap);
        NIMClient.getService(NosService.class).download(url, null, path).setCallback(new RequestCallback() {
            @Override
            public void onSuccess(Object o) {
                hideLoadingText();
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                LogUtil.i(TAG, "Audience download success, set bitmap:" + bitmap);
                doodleView.setImageView(bitmap);
                updatePagesUI(document, currentPage);
            }

            @Override
            public void onFailed(int i) {
                LogUtil.i(TAG, "Audience download file failed, code:" + i);
                showRetryLoadingText();
            }

            @Override
            public void onException(Throwable throwable) {

            }
        });
    }

    private void confirmCloseFileShare() {
        EasyAlertDialogHelper.createOkCancelDiolag(getActivity(), getString(R.string.operation_confirm),
                getString(R.string.confirm_to_close_file_share), getString(R.string.close_file_share), getString(R.string.cancel), true,
                new EasyAlertDialogHelper.OnDialogActionListener() {
                    @Override
                    public void doCancelAction() {

                    }

                    @Override
                    public void doOkAction() {
                        closeFileShare();
                    }
                }).show();
    }


    // 退出文档分享
    private void closeFileShare() {
        isFileMode = false;
        doodleView.setBitmap(null);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                closeFileBtn.setVisibility(View.GONE);
                updatePagesUI(null, -1);
            }
        });
        // 主播退出文档分享，通知观众
//        if (DemoCache.getAccount().equals(roomInfo.getCreator())) {
//            doodleView.clear();
//            Log.d("TAG", "document=" + document);
//            doodleView.sendFlipData(document.getDmData().getDocId(), 0, 0, 1);
//        }
    }

    private void showLoadingText() {
        fileLoadingText.setVisibility(View.VISIBLE);
        fileLoadingText.setText(R.string.file_loading);
        fileLoadingText.setEnabled(false);
    }

    private void hideLoadingText() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fileLoadingText.setVisibility(View.GONE);
            }
        });
    }

    private void showRetryLoadingText() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fileLoadingText.setVisibility(View.VISIBLE);
                fileLoadingText.setText(R.string.failed_to_retry);//加载失败，点击重试
                fileLoadingText.setEnabled(true);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}

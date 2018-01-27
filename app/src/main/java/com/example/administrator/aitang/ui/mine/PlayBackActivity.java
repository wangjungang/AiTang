package com.example.administrator.aitang.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.SeekBar;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.bean.zhibo.DataRecordingBean;
import com.example.administrator.aitang.fragment.wode.RTSFragment;
import com.example.administrator.aitang.views.MyUniversalMediaController;
import com.example.administrator.aitang.views.MyUniversalVideoView;
import com.example.administrator.aitang.zhibo.base.ui.TActivity;
import com.example.administrator.aitang.zhibo.education.doodle.DoodleView;
import com.example.administrator.aitang.zhibo.education.doodle.TransactionCenter;
import com.example.administrator.aitang.zhibo.education.helper.VideoListener;
import com.universalvideoview.UniversalVideoView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Created by hzxuwen on 2016/2/29.
 */
public class PlayBackActivity extends TActivity implements VideoListener, UniversalVideoView.VideoViewCallback, SeekBar.OnSeekBarChangeListener {
    private static final String TAG = PlayBackActivity.class.getSimpleName();
    private LoadListener loadListener;
    private String VIDEO_URL;

    public interface LoadListener {
        void loadComplete();
    }

    public void setonLoadListener(LoadListener listener) {
        this.loadListener = listener;
    }

    /**
     * 子页面
     */
    private RTSFragment rtsFragment;

    private static final String SEEK_POSITION_KEY = "SEEK_POSITION_KEY";
    private static final String VIDEONAME = "videoName";
    private static final String WHITENAME = "whiteName";
    MyUniversalVideoView mVideoView;
    MyUniversalMediaController mMediaController;

    View mVideoLayout;

    private int mSeekPosition;
    private boolean isFullscreen;
    private int newPosition;
    String sdcard = Environment.getExternalStorageDirectory().getPath() + "/AITANG/download";

    private String videoName;//视频文件的名字
    private String whiteName;//回放文件的类型(1:白板)
    private int currentPosition;//播放器当前进度
    private int sjc;//白板的时间戳

    public PlayBackActivity() throws IOException {
    }

    private void parseIntent() {
        videoName = getIntent().getStringExtra(VIDEONAME);
        whiteName = getIntent().getStringExtra(WHITENAME);
    }

    /**
     * @param context
     */
    public static void start(Context context, String videoName, String whiteName) {
        Intent intent = new Intent();
        intent.setClass(context, PlayBackActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(VIDEONAME, videoName);
        intent.putExtra(WHITENAME, whiteName);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playback);
        parseIntent();

        mVideoLayout = findViewById(R.id.video_layout);
        mVideoView = (MyUniversalVideoView) findViewById(R.id.videoView);
        mMediaController = (MyUniversalMediaController) findViewById(R.id.media_controller);

        mVideoView.setMediaController(mMediaController);
        String vurl = sdcard + "/" + videoName;
//        String vurl = sdcard + "/" + "myvideo.mp4";
        Log.d("TAG", "播放的视频网址：" + vurl + "白板" + sdcard + "/" + whiteName);
        mVideoView.setVideoPath(vurl);
        mMediaController.setSetSeekLister(this);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                if (!TextUtils.isEmpty(whiteName)) {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    initRTSFragment();
                    playVideo();
                    try {
                        String s = sdcard + "/" + whiteName;
                        readFile(s);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    playVideo();
                }
            }
        });
    }

    /**
     * 读取文件的内容
     *
     * @param filename 文件名称
     * @return
     * @throws Exception
     */

    public void readFile(final String filename) throws Exception {
        data = gzip(filename);
        List<DataRecordingBean> recordings = new ArrayList<>();
        if (data == null) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        readFile(filename);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 100);
        } else
            handler.post(runnable);
    }

    byte[] data;

    private byte[] gzip(String mFileName) {
        GZIPInputStream in = null;
        try {
            in = new GZIPInputStream(new FileInputStream(mFileName));
            byte[] bb = new byte[in.available()];
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            byte[] by = new byte[(int) new File(mFileName).length()];
            int ch;
            ch = in.read(bb);
            while (ch != -1) {
                bytestream.write(bb, 0, ch);
                ch = in.read(bb);
            }
            return bytestream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void playVideo() {
        if (mSeekPosition > 0) {
            mVideoView.seekTo(mSeekPosition);
        }
        mVideoView.start();
        mMediaController.setTitle(whiteName);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SEEK_POSITION_KEY, mSeekPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        mSeekPosition = outState.getInt(SEEK_POSITION_KEY);
    }


    @Override
    public void onScaleChange(boolean isFullscreen) {
        this.isFullscreen = isFullscreen;
        if (isFullscreen) {
            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            mVideoLayout.setLayoutParams(layoutParams);

        } else {
            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            mVideoLayout.setLayoutParams(layoutParams);
        }

        switchTitleBar(!isFullscreen);
    }

    private void switchTitleBar(boolean show) {
        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            if (show) {
                supportActionBar.show();
            } else {
                supportActionBar.hide();
            }
        }
    }

    @Override
    public void onPause(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onPause UniversalVideoView callback");
    }

    @Override
    public void onStart(MediaPlayer mediaPlayer) {
    }

    @Override
    public void onBufferingStart(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onBufferingStart UniversalVideoView callback");
    }

    @Override
    public void onBufferingEnd(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onBufferingEnd UniversalVideoView callback");
    }

    @Override
    public void onBackPressed() {
        if (this.isFullscreen) {
            mVideoView.setFullscreen(false);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoView != null && mVideoView.isPlaying()) {
            mSeekPosition = mVideoView.getCurrentPosition();
            mVideoView.pause();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }


    /**
     * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序，和和intToBytes（）配套使用
     *
     * @param src    byte数组
     * @param offset 从数组的第offset位开始
     * @return int数值
     */
    public static int bytesToInt(byte[] src, int offset) {
        int value;
        value = (int) ((src[offset] & 0xFF)
                | ((src[offset + 1] & 0xFF) << 8)
                | ((src[offset + 2] & 0xFF) << 16)
                | ((src[offset + 3] & 0xFF) << 24));
        return value;
    }


    public byte[] getContent(String filePath) throws IOException {
//        File file = new File(filePath);
        InputStream is = getResources().getAssets().open(filePath);
        long fileSize = is.available();
        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("file too big...");
            return null;
        }
        byte[] buffer = new byte[(int) fileSize];
        int offset = 0;
        int numRead = 0;
        while (offset < buffer.length
                && (numRead = is.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += numRead;
        }
// 确保所有数据均被读取
        if (offset != buffer.length) {
            throw new IOException("Could not completely read file ");
        }
        is.close();
        return buffer;
    }

    /**
     * 截取字节数组
     *
     * @param bytes  原始数组
     * @param offset 偏移量
     * @param size   长度
     *               public static void (Object src,
     *               int srcPos,
     *               Object dest,
     *               int destPos,
     *               int length)
     *               src:源数组；	srcPos:源数组要复制的起始位置；
     *               dest:目的数组；	destPos:目的数组放置的起始位置；	length:复制的长度。
     * @return 新的字节数组
     */
    public static byte[] copyBytes(byte[] bytes, int offset, int size) {
        byte[] newBytes = new byte[size];
        System.arraycopy(bytes, offset, newBytes, 0, size);
        return newBytes;
    }


    private void initRTSFragment() {
        rtsFragment = (RTSFragment) getSupportFragmentManager().findFragmentById(R.id.rts_fragment);
        if (rtsFragment != null) {
            rtsFragment.initRTSView();
            rtsFragment.getDoodleView().setPlayBack(true);
        } else {
            getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    initRTSFragment();
                }
            }, 50);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (rtsFragment != null) {
            rtsFragment.onActivityResult(requestCode, resultCode, data);
        }
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
        logd("PlayBackActivity--onuserleave");
    }

    @Override
    public void onReportSpeaker(Map<String, Integer> map) {
    }

    @Override
    public void onAcceptConfirm() {
        if (rtsFragment == null) {
            rtsFragment = (RTSFragment) getSupportFragmentManager().findFragmentById(R.id.rts_fragment);
        }
        if (rtsFragment != null) {
            rtsFragment.onAcceptConfirm();
        }
    }

    public interface FragmentCallBack {
        public PlayBackActivity getMainActivity();
    }

    public PlayBackActivity getMainActivity() {
        return this;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        newPosition = mVideoView.getDuration() * progress / 1000;//时间
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mVideoView.pause();
        handler.removeCallbacks(runnable);
        runcount = 0;
        pacLength = 0;//以取出的包长
        bc = 0;//当前的包长
        timestamp = 0;//时间戳
        content = null;
        i = 0;
        if (rtsFragment == null) {
            rtsFragment = (RTSFragment) getSupportFragmentManager().findFragmentById(R.id.rts_fragment);
        } else
            rtsFragment.getDoodleView().setClick(true);
        handler.post(runnable);
    }

    int runcount = 0;//全局变量，用于判断是否是第一次执行
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                handler.removeCallbacks(runnable);
            }
        }
    };
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            List<DataRecordingBean> recordings = new ArrayList<>();
            aa(data, recordings);
            handler.postDelayed(this, 50);
        }
    };

    int pacLength = 0;//以取出的包长
    int bc = 0;//当前的包长
    int timestamp = 0;//时间戳
    String content = null;
    int i = 0;

    private void aa(final byte[] byt, final List<DataRecordingBean> recordings) {
        int pos = mMediaController.getmPlayer().getCurrentPosition();
        while (timestamp < pos && (pacLength + 8) <= byt.length) {
            if (content == null) {
            } else {
                recordings.add(new DataRecordingBean(bc, timestamp, content));
                TransactionCenter.getInstance().onReceive("123", null, content);
            }
            bc = bytesToInt(copyBytes(byt, pacLength, 4), 0);//一个包长
            timestamp = bytesToInt(copyBytes(byt, (pacLength + 4), 4), 0);//时间戳
            content = new String(copyBytes(byt, (pacLength + 8), (bc - 8)));
            Log.d("TAG", "数据：" + content+"总包长："+byt.length);
            pacLength += bc;
        }
        if (!mVideoView.isPlaying()) {
            if (rtsFragment == null) {
                rtsFragment = (RTSFragment) getSupportFragmentManager().findFragmentById(R.id.rts_fragment);
            } else {
                DoodleView dv = rtsFragment.getDoodleView();
                if (dv != null)
                    dv.setClick(false);
                TransactionCenter.getInstance().onReceive("123", null, content);
                mVideoView.start();
            }
        }
        if (data != null)
            if (pacLength + 8 > data.length) {
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            } else {
            }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

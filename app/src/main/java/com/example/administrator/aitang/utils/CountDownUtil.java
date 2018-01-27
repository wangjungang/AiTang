package com.example.administrator.aitang.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Button;


/**
 * Created by Administrator on 2017/6/27.
 * 倒计时util
 */

public  class CountDownUtil {

    int mMaxNum;
    int mCurrentNum;
    Button btn;
    Context mContext;
    Handler handler = new Handler();

    public CountDownUtil(Context context, int maxNum, Button v) {
        this.mMaxNum = maxNum;
        this.mCurrentNum=maxNum;
        this.btn = v;
        this.mContext = context;
    }

    public  void countDown() {
        handler.postDelayed(runnable, 1000);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mCurrentNum--;
            if (mCurrentNum > 0) {
                btn.setText(String.valueOf(mCurrentNum + "s后重新发送"));
                btn.setTextSize(12);
                handler.postDelayed(runnable, 1000);
                btn.setEnabled(false);
            } else {
                btn.setText(String.valueOf("获取验证码"));
                btn.setTextSize(16);
                btn.setEnabled(true);
                mCurrentNum=mMaxNum;
            }
        }
    };
}

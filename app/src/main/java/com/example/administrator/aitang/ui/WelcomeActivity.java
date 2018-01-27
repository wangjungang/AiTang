package com.example.administrator.aitang.ui;

import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.app.Myapp;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 武培培 on 2017/10/19.
 */

public class WelcomeActivity extends MyBaseActivity {
    Handler handler = new Handler();
    @BindView(R.id.ll_content)
    LinearLayout llContent;

    @Override
    public void setContentView() {
        setContentView(R.layout.welcome_layout);
    }

    @Override
    public void init() {
        super.init();//
        setImmerBarcolor(llContent);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Myapp.spUtil.isLogin())
                    jumpTo(MainActivity.class, true);
                else
                    jumpTo(AccountActivity.class, true);
            }
        }, 1000);//3秒后跳转到应用的首页
    }

}

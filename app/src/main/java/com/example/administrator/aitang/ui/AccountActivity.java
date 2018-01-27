package com.example.administrator.aitang.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.ui.account.LoginActivity;
import com.example.administrator.aitang.ui.account.RegistActivity;
import com.example.administrator.aitang.zhibo.im.ui.dialog.EasyAlertDialogHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 武培培 on 2017/10/20.
 */

public class AccountActivity extends MyBaseActivity implements View.OnClickListener {
    @BindView(R.id.bt_denglu)
    Button bt_denglu;
    @BindView(R.id.bt_zhuce)
    Button bt_zhucce;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    private static final String KICK_OUT = "KICK_OUT";

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_account);

    }

    public void setListener() {
        bt_denglu.setOnClickListener(this);
        bt_zhucce.setOnClickListener(this);
    }

    @Override
    public void init() {
        super.init();
        //添加到activity栈中，用于注册完成关闭页面
        Myapp.shitiTypeActivities.add(this);
        parseIntent();
        setImmerBarcolor(llContent);
        setListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Myapp.shitiTypeActivities.remove(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_denglu:
                jumpTo(LoginActivity.class, false);
                break;
            case R.id.bt_zhuce:
                jumpTo(RegistActivity.class, false);
                break;
            default:
                break;
        }
    }

    private void parseIntent() {
        boolean isKickOut = getIntent().getBooleanExtra(KICK_OUT, false);
        if (isKickOut) {
            EasyAlertDialogHelper.showOneButtonDiolag(AccountActivity.this,
                    getString(R.string.kickout_notify),
                    getString(R.string.kickout_content),
                    getString(R.string.ok), true, null);
        }
    }
    public static void start(Context context, boolean kickOut) {
        Intent intent = new Intent(context, AccountActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(KICK_OUT, kickOut);
        context.startActivity(intent);
        Myapp.clearActivitiesExceptMe(AccountActivity.class);
    }

}

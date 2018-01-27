package com.example.administrator.aitang.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.login.VerificationCodeBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.http.HttpManager;
import com.example.administrator.aitang.http.listener.ServiceBackObjectListener;
import com.example.administrator.aitang.http.result.ServiceResult;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.example.administrator.aitang.utils.CountDownUtil;
import com.example.administrator.aitang.utils.IsMobileUtil;
import com.example.administrator.aitang.utils.MessageDigestUtils;
import com.example.administrator.aitang.utils.basic.StringUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by 武培培 on 2017/10/20.
 */

public class RegistActivity extends MyBaseActivity implements View.OnClickListener {
    @BindView(R.id.headerview)
    LinearLayout llHeaderView;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_verify)
    EditText et_verify;
    @BindView(R.id.bt_code)
    Button bt_code;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.et_requery_psd)
    EditText et_requery_pwd;
    @BindView(R.id.bt_finish)
    Button bt_finfish;
    @BindView(R.id.tv_exame)
    TextView tv_exame;
    @BindView(R.id.iv_header_left)
    ImageView ivHeaderLeft;

    CountDownUtil countDown;
    int verifycode;

    @Override
    public void setContentView() {
        setContentView(R.layout.regist_layout);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Myapp.shitiTypeActivities.remove(this);
    }

    @Override
    public void init() {
        super.init();
        Myapp.shitiTypeActivities.add(this);

        setImmerBarcolor(llHeaderView);
        //解决软键盘遮挡输入框问题
        setKeyBoardPatch5497(this);
        setHeader();
        countDown = new CountDownUtil(this, 60, bt_code);
        bt_code.setOnClickListener(this);
        bt_finfish.setOnClickListener(this);
        tv_exame.setOnClickListener(this);
        ivHeaderLeft.setOnClickListener(this);
    }

    private void setHeader() {
        setHeaderBackground(R.color.color_white);
        setHeaderImage(MyConstant.Position.LEFT, R.drawable.back_icon_nav, false, this);
        setHeaderTitle("账号注册", MyConstant.Position.CENTER, R.color.color_323232);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                this.finish();
                break;

            case R.id.bt_code://获取验证码
                //获取验证码
                if (!IsMobileUtil.isMobileNo(et_phone.getText().toString())) {
                    toast("手机号格式不正确");
                    return;
                }

                Map paramsMap = new HashMap();
                paramsMap.put("phone", et_phone.getText().toString());
                paramsMap.put("type", "1");

                HttpManager.getInstance().post(MyConstant.VERFICATION, paramsMap, new ServiceBackObjectListener() {
                    @Override
                    public void onSuccess(ServiceResult result, String response) {
                        //发送成功进行倒计时
                        countDown.countDown();
                        VerificationCodeBean codeBean = new Gson().fromJson(response.toString(), VerificationCodeBean.class);
                        verifycode = codeBean.getData().getVerifycode();
                    }

                    @Override
                    public void onFailure(ServiceResult result, Object obj) {
                        toast(result.getDesc());
                    }
                });

                break;
            case R.id.bt_finish://注册
                if (TextUtils.isEmpty(et_name.getText().toString())) {
                    toast("请输入昵称");
                    return;
                }
                if (TextUtils.isEmpty(et_phone.getText().toString())) {
                    toast("请输入手机号");
                    return;
                }
                if (StringUtils.isEmpty(et_verify.getText().toString())) {
                    toast("请输入验证码");
                    return;
                }

                if (TextUtils.isEmpty(et_pwd.getText().toString())) {
                    toast("密码不能为空");
                    return;
                }
                if (et_pwd.getText().toString().length() < 6 || et_requery_pwd.getText().toString().length() < 6) {
                    toast("密码最少为6位");
                    return;
                }
                if (!et_pwd.getText().toString().equals(et_requery_pwd.getText().toString())) {
                    toast("两次输入密码有误，请重新输入");
                    return;
                }
                if (!String.valueOf(verifycode).equals(et_verify.getText().toString())) {
                    toast("验证码错误");
                    return;
                }


                //跳转选择考试类型界面
                Intent intent = new Intent(this, ExameTypeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("flag", "1");//跳转标志位
                bundle.putString("uname", et_name.getText().toString());
                bundle.putString("uname", et_name.getText().toString());
                bundle.putString("uphone", et_phone.getText().toString());
                bundle.putString("upwd", MessageDigestUtils.getMD5(et_pwd.getText().toString()));
                intent.putExtras(bundle);
                startActivity(intent);

                break;

            default:
                break;
        }
    }
}

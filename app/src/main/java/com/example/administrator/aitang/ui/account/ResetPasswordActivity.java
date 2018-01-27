package com.example.administrator.aitang.ui.account;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.aitang.R;
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
import butterknife.OnClick;

import static com.example.administrator.aitang.R.id.et_phone;

public class ResetPasswordActivity extends MyBaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_header_left)
    ImageView ivHeaderLeft;
    @BindView(et_phone)
    EditText etPhone;
    @BindView(R.id.et_verify)
    EditText etVerify;
    @BindView(R.id.bt_code)
    Button btCode;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_confirm_pwd)
    EditText etConfirmPwd;
    @BindView(R.id.bt_ok)
    Button btOk;
    @BindView(R.id.headerview)
    LinearLayout llHeaderView;

    CountDownUtil countDown;//倒计时按钮工具类
    int verifyCode;//验证码


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_reset_password);
    }

    @Override
    public void init() {
        super.init();
        setImmerBarcolor(llHeaderView);
        setHeader();
        //解决软键盘遮挡输入框问题
        setKeyBoardPatch5497(this);
        countDown = new CountDownUtil(this, 60, btCode);
    }

    private void setHeader() {
        setHeaderBackground(R.color.color_white);
        setHeaderImage(MyConstant.Position.LEFT, R.drawable.back_icon_nav, false, this);
        setHeaderTitle("账号信息", MyConstant.Position.CENTER, R.color.color_323232);
    }

    @OnClick({R.id.bt_code, R.id.bt_ok, R.id.iv_header_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                this.finish();
                break;
            case R.id.bt_code:
                startSendCode();
                break;
            case R.id.bt_ok:

                if (checkInfo()) {
                    submit();
                }

                break;
        }

    }

    /**
     * 提交信息
     */
    private void submit() {

        showProgressDialog();
        Map map = new HashMap();

        map.put("uname", etPhone.getText().toString());
        map.put("upwd", MessageDigestUtils.getMD5(etPwd.getText().toString()));

        HttpManager.getInstance().post(MyConstant.FORGETPWD, map, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {
                hideProgressDialog();
                toast("修改成功");
                ResetPasswordActivity.this.finish();
            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {
                hideProgressDialog();
                toast(result.getDesc());
            }
        });
    }

    /**
     * 检查信息，true表示可以提交 false表示信息不全
     */
    private boolean checkInfo() {

        if (StringUtils.isEmpty(etPhone.getText().toString())) {
            toast("请输入手机号");
            return false;
        }
        if (StringUtils.isEmpty(etVerify.getText().toString())) {
            toast("请输入验证码");
            return false;
        }
        if (StringUtils.isEmpty(etPwd.getText().toString())) {
            toast("请输入密码");
            return false;
        }

        if (!etPwd.getText().toString().equals(etConfirmPwd.getText().toString())) {
            toast("两次输入密码有误，请重新输入");
            return false;
        }

        if (etPwd.getText().toString().length() < 6 || etConfirmPwd.getText().toString().length() < 6) {
            toast("密码最少为6位");
            return false;
        }
        if (!etVerify.getText().toString().equals(String.valueOf(verifyCode))) {
            toast("验证码输入错误");
            return false;
        }

        return true;
    }

    /**
     * 获取验证码
     */
    private void startSendCode() {
        //获取验证码
        if (!IsMobileUtil.isMobileNo(etPhone.getText().toString())) {
            toast("手机号格式不正确");
            return;
        }


        Map paramsMap = new HashMap();
        paramsMap.put("phone", etPhone.getText().toString());
        paramsMap.put("type", "2");//1 注册 2 找回

        HttpManager.getInstance().post(MyConstant.VERFICATION, paramsMap, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {
                //进行倒计时
                countDown.countDown();
                VerificationCodeBean codeBean = new Gson().fromJson(response.toString(), VerificationCodeBean.class);

                verifyCode = codeBean.getData().getVerifycode();

            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {
                toast(result.getDesc());
            }
        });
    }

}

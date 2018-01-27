package com.example.administrator.aitang.ui.mine;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.login.VerificationCodeBean;
import com.example.administrator.aitang.bean.wode.UserInfoBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.http.HttpManager;
import com.example.administrator.aitang.http.error.ErrorCode;
import com.example.administrator.aitang.http.error.ErrorDes;
import com.example.administrator.aitang.http.listener.ServiceBackObjectListener;
import com.example.administrator.aitang.http.result.ServiceResult;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.example.administrator.aitang.utils.CountDownUtil;
import com.example.administrator.aitang.utils.IsMobileUtil;
import com.example.administrator.aitang.utils.MessageDigestUtils;
import com.example.administrator.aitang.utils.basic.ProgressUtil;
import com.example.administrator.aitang.utils.basic.StringUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class ChangePwdActivity extends MyBaseActivity implements View.OnClickListener {

    @BindView(R.id.headerview)
    LinearLayout llHeaderView;

    @BindView(R.id.iv_header_left)
    ImageView ivHeaderLeft;
    @BindView(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.bt_send_code)
    Button btSendCode;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_confirm_pwd)
    EditText etConfirmPwd;
    @BindView(R.id.btn_complete)
    Button btnComplete;

    private CountDownUtil countDown;//倒计时按钮工具类
    private int verifyCode = 0;//验证码

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_change_pwd);
    }

    @Override
    public void init() {
        super.init();
        setImmerBarcolor(llHeaderView);
        setKeyBoardPatch5497(this);
        setHeader();
        setOnclickListener();
        setPhoneNumber();
    }

    private void setHeader() {
        setHeaderBackground(R.color.color_white);
        setHeaderImage(MyConstant.Position.LEFT, R.drawable.back_icon_nav, false, this);
        setHeaderTitle("账号信息", MyConstant.Position.CENTER, R.color.color_323232);
    }

    private void setOnclickListener() {
        ivHeaderLeft.setOnClickListener(this);
        btSendCode.setOnClickListener(this);
        btnComplete.setOnClickListener(this);
    }

    /**
     * 设置显示的手机号
     */
    private void setPhoneNumber() {
        String phoneNumber = Myapp.spUtil.getPhone();
        if (!StringUtils.isEmpty(phoneNumber)) {
            tvPhoneNumber.setText(phoneNumber);
        }

        countDown = new CountDownUtil(this, 60, btSendCode);
        //        btSendCode.performClick();//自动触发发送验证码事件

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                this.finish();
                break;

            case R.id.bt_send_code:

                //发送验证码
                startSendCode();

                break;

            case R.id.btn_complete:

                //提交修改
                checkAndComplete();

                break;
            default:
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void startSendCode() {
        //获取验证码
        if (StringUtils.isEmpty(tvPhoneNumber.getText().toString()) || !IsMobileUtil.isMobileNo(tvPhoneNumber.getText().toString())) {
            toast("手机号格式不正确");
            return;
        }


        Map paramsMap = new HashMap();
        paramsMap.put("phone", tvPhoneNumber.getText().toString());
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


    /**
     * 校验验证码，请求接口
     */
    private void checkAndComplete() {
        String inputCode = etCode.getText().toString();

        if (StringUtils.isEmpty(String.valueOf(verifyCode)) || //获取的验证码为空
                StringUtils.isEmpty(inputCode) || //输入的验证码为空
                !inputCode.equals(String.valueOf(verifyCode)) //两个验证码不匹配
                ) {
            toast("验证码不正确");
            return;
        }

        String pwd = etPwd.getText().toString();
        String rePwd = etConfirmPwd.getText().toString();
        if (!StringUtils.isEmpty(pwd) && pwd.length() < 6) {

            toast("密码不少于6位");
            return;
        }


        if (StringUtils.isEmpty(String.valueOf(pwd)) || //获取的验证码为空
                StringUtils.isEmpty(rePwd) || //输入的验证码为空
                !pwd.equals(rePwd) //两个验证码不匹配
                ) {
            toast("密码不正确");
            return;

        }

        //验证码正确，调用接口
        if (!StringUtils.isEmpty(tvPhoneNumber.getText().toString())) {
            //TODO 请求接口
            requestService();
        } else {
            toast("手机号异常");
        }

    }

    /**
     * 请求接口，成功后关闭页面
     */
    private void requestService() {
        ProgressUtil.show(this);
        Map paramsMap = new HashMap();

        String uid = Myapp.spUtil.getUid();
        String token = Myapp.spUtil.getToken();


        paramsMap.put("uid", uid);
        paramsMap.put("token", token);


        Map data = new HashMap();
        final String pwdMd5=MessageDigestUtils.getMD5(etPwd.getText().toString());
        data.put("upwd", pwdMd5);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(data);
        paramsMap.put("param", jsonStr);

        HttpManager.getInstance().post(MyConstant.UPDATEINFO, paramsMap, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {
                ProgressUtil.hide();
                toast("修改成功");
                UserInfoBean userInfoBean = (UserInfoBean) Myapp.spUtil.getBean(ChangePwdActivity.this, MyConstant.USERINFO);
                userInfoBean.getData().setUpwd(pwdMd5);
                Myapp.spUtil.putBean(ChangePwdActivity.this, MyConstant.USERINFO, userInfoBean);

                ChangePwdActivity.this.finish();

            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {
                ProgressUtil.hide();
                if (result.getCode().equals(ErrorCode.ERROR_CODE_202)) {
                    toast("修改失败");
                } else {
                    toast(ErrorDes.ERROR_INFO);
                }
            }
        });


    }

}

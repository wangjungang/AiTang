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
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.http.HttpManager;
import com.example.administrator.aitang.http.listener.ServiceBackObjectListener;
import com.example.administrator.aitang.http.result.ServiceResult;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.example.administrator.aitang.utils.CountDownUtil;
import com.example.administrator.aitang.utils.IsMobileUtil;
import com.example.administrator.aitang.utils.basic.StringUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class ChangePhoneOneActivity extends MyBaseActivity implements View.OnClickListener {

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
    @BindView(R.id.btn_next)
    Button btnNext;

    private CountDownUtil countDown;//倒计时按钮工具类
    private int verifyCode = 0;//验证码

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_change_phone_one);

    }

    @Override
    public void init() {
        super.init();
        Myapp.phoneActivities.add(this);
        setHeader();
        setImmerBarcolor(llHeaderView);
        setKeyBoardPatch5497(this);
        setOnclickListener();
        setPhoneNumber();
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

    }

    private void setOnclickListener() {
        ivHeaderLeft.setOnClickListener(this);
        btSendCode.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    private void setHeader() {
        setHeaderBackground(R.color.color_white);
        setHeaderImage(MyConstant.Position.LEFT, R.drawable.back_icon_nav, false, this);
        setHeaderTitle("账号信息", MyConstant.Position.CENTER, R.color.color_323232);
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
            case R.id.btn_next:
                //下一步
                goNext();

//                //TODO 测试跳转逻辑
//                jumpTo(ChangePhoneTwoActivity.class, false);

                break;
            default:
                break;
        }

    }

    /**
     * 点击下一步，先确定验证码，后跳转
     */
    private void goNext() {

        String inputCode = etCode.getText().toString();

        if (StringUtils.isEmpty(String.valueOf(verifyCode)) || //获取的验证码为空
                StringUtils.isEmpty(inputCode) || //输入的验证码为空
                !inputCode.equals(String.valueOf(verifyCode)) //两个验证码不匹配
                ) {
            toast("验证码错误");
        } else {
            //验证码正确，跳转
            jumpTo(ChangePhoneTwoActivity.class, false);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Myapp.phoneActivities.remove(this);
    }
}

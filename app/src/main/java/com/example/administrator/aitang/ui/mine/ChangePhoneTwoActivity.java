package com.example.administrator.aitang.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.example.administrator.aitang.utils.IsMobileUtil;
import com.example.administrator.aitang.utils.basic.StringUtils;
import com.example.administrator.aitang.views.SendCodeDialog;

import butterknife.BindView;

public class ChangePhoneTwoActivity extends MyBaseActivity implements View.OnClickListener {

    @BindView(R.id.headerview)
    LinearLayout llHeaderView;

    @BindView(R.id.iv_header_left)
    ImageView ivHeaderLeft;
    @BindView(R.id.et_new_phone)
    EditText etNewPhone;
    @BindView(R.id.btn_next)
    Button btnNext;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_change_phone_two);

    }

    @Override
    public void init() {
        super.init();
        Myapp.phoneActivities.add(this);
        setImmerBarcolor(llHeaderView);
        setHeader();
        setOnclickListener();
    }

    private void setHeader() {
        setHeaderBackground(R.color.color_white);
        setHeaderImage(MyConstant.Position.LEFT, R.drawable.back_icon_nav, false, this);
        setHeaderTitle("账号信息", MyConstant.Position.CENTER, R.color.color_323232);
    }

    private void setOnclickListener() {
        ivHeaderLeft.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                this.finish();
                break;

            case R.id.btn_next://下一步,先弹出提示框，确定后跳转到新页面接收验证码

                if (StringUtils.isEmpty(etNewPhone.getText().toString()) ||
                        !IsMobileUtil.isMobileNo(etNewPhone.getText().toString())) {
                    //如果手机号是空或者格式不正确，提示
                    toast("请填写正确的手机号码");

                } else {
                    showDialog();
                }

                break;
            default:
                break;
        }
    }

    /**
     * 弹出提示框确认信息
     */
    private void showDialog() {

        SendCodeDialog dialog = new SendCodeDialog(this, "确认手机号码",etNewPhone.getText().toString(), new SendCodeDialog.OnConfirmBtnClickListener() {
            @Override
            public void click(View v) {
                Intent intent = new Intent(ChangePhoneTwoActivity.this, ChangePhoneThreeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("phoneNumber", etNewPhone.getText().toString());
                intent.putExtras(bundle);
                jumpTo(intent, false);
            }
        }, new SendCodeDialog.OnCancelBtnClickListener() {
            @Override
            public void click(View v) {

            }
        });
        dialog.show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Myapp.phoneActivities.remove(this);
    }

}

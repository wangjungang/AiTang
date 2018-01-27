package com.example.administrator.aitang.ui.mine;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.http.HttpManager;
import com.example.administrator.aitang.http.listener.ServiceBackObjectListener;
import com.example.administrator.aitang.http.result.ServiceResult;
import com.example.administrator.aitang.ui.AccountActivity;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.example.administrator.aitang.ui.account.LoginActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangeAccountInfoActivity extends MyBaseActivity {
    @BindView(R.id.headerview)
    LinearLayout llHeaderView;
    @BindView(R.id.ll_pwd_phone_content)
    LinearLayout llPwdPhoneContent;
    @BindView(R.id.iv_header_left)
    ImageView ivHeaderLeft;
    @BindView(R.id.rl_change_name)
    RelativeLayout rlChangeName;
    @BindView(R.id.rl_change_pwd)
    RelativeLayout rlChangePwd;
    @BindView(R.id.rl_change_phone)
    RelativeLayout rlChangePhone;
    @BindView(R.id.btn_logout)
    Button btnLogout;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_change_account_info);
    }

    @Override
    public void init() {
        super.init();
        //记录当前activity,当修改手机号成功用于跳转关闭，在onDestroy中关闭
        Myapp.phoneActivities.add(this);
        setImmerBarcolor(llHeaderView);
        setHeader();

        if (Myapp.spUtil.isThirdLogin()) {
            llPwdPhoneContent.setVisibility(View.GONE);
        }
    }

    private void setHeader() {
        setHeaderBackground(R.color.color_white);
        setHeaderImage(MyConstant.Position.LEFT, R.drawable.back_icon_nav, false, null);
        setHeaderTitle("账号信息", MyConstant.Position.CENTER, R.color.color_323232);
    }


    /**
     * 点击事件
     *
     * @param view
     */
    @OnClick({R.id.iv_header_left, R.id.rl_change_name, R.id.rl_change_pwd,
            R.id.rl_change_phone, R.id.btn_logout})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.iv_header_left://返回
                this.finish();
                break;
            case R.id.rl_change_name://修改昵称

                jumpTo(ChangeUserNameActivity.class, false);

                break;
            case R.id.rl_change_pwd://修改密码

                jumpTo(ChangePwdActivity.class, false);

                break;
            case R.id.rl_change_phone://修改手机号,一共有三个页面这里按one、two、three命名

                jumpTo(ChangePhoneOneActivity.class, false);

                break;
            case R.id.btn_logout:

                //TODO 清除登陆信息，跳转到登陆界面

                Myapp.clearActivitiesExceptMe(ChangeAccountInfoActivity.class);
                Myapp.spUtil.clear();
                jumpTo(AccountActivity.class, true);

                break;
            default:
                break;
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Myapp.phoneActivities.remove(this);
    }


    /**
     * 获取用户信息
     */
    private void requestService() {

        Map paramsMap = new HashMap();

        String uid = Myapp.spUtil.getUid();
        String token = Myapp.spUtil.getToken();


        paramsMap.put("uid", uid);
        paramsMap.put("token", token);

        HttpManager.getInstance().post(MyConstant.GETUSERINFO, paramsMap, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {
                toast(response);
            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {
                toast(result.getDesc());
            }
        });


    }

}

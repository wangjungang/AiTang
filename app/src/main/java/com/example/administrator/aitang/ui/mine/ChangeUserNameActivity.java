package com.example.administrator.aitang.ui.mine;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.wode.UserInfoBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.http.HttpManager;
import com.example.administrator.aitang.http.error.ErrorCode;
import com.example.administrator.aitang.http.error.ErrorDes;
import com.example.administrator.aitang.http.listener.ServiceBackObjectListener;
import com.example.administrator.aitang.http.result.ServiceResult;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.example.administrator.aitang.utils.basic.StringUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class ChangeUserNameActivity extends MyBaseActivity implements View.OnClickListener {

    @BindView(R.id.headerview)
    LinearLayout llHeaderView;

    @BindView(R.id.iv_header_left)
    ImageView ivHeaderLeft;
    @BindView(R.id.et_new_name)
    EditText etNewName;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_change_user_name);
    }

    @Override
    public void init() {
        super.init();
        setImmerBarcolor(llHeaderView);
        setHeader();
        setOnclickListener();
    }


    private void setOnclickListener() {
        ivHeaderLeft.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    private void setHeader() {
        setHeaderBackground(R.color.color_white);
        setHeaderImage(MyConstant.Position.LEFT, R.drawable.back_icon_nav, false, this);
        setHeaderTitle("账号信息", MyConstant.Position.CENTER, R.color.color_323232);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_header_left://返回
                this.finish();
                break;
            case R.id.btn_submit://提交修改

                if (!StringUtils.isEmpty(etNewName.getText().toString())) {
                    requestService();
                }

                break;

            default:
                break;
        }
    }


    /**
     * 请求接口，成功后关闭页面
     */
    private void requestService() {

        showProgressDialog();

        Map paramsMap = new HashMap();

        String uid = Myapp.spUtil.getUid();
        String token = Myapp.spUtil.getToken();


        paramsMap.put("uid", uid);
        paramsMap.put("token", token);


        Map data = new HashMap();
        data.put("uname", etNewName.getText().toString());
        Gson gson = new Gson();
        String jsonStr = gson.toJson(data);
        paramsMap.put("param", jsonStr);

        HttpManager.getInstance().post(MyConstant.UPDATEINFO, paramsMap, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {

                hideProgressDialog();
                toast("修改成功");
                //修改本地保存的昵称
                Myapp.spUtil.setNike(etNewName.getText().toString());
                UserInfoBean userInfoBean = (UserInfoBean) Myapp.spUtil.getBean(ChangeUserNameActivity.this, MyConstant.USERINFO);
                userInfoBean.getData().setUname(etNewName.getText().toString());
                Myapp.spUtil.putBean(ChangeUserNameActivity.this, MyConstant.USERINFO, userInfoBean);

                ChangeUserNameActivity.this.finish();


            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {
                hideProgressDialog();
                if (result.getCode().equals(ErrorCode.ERROR_CODE_202)) {
                    toast("修改失败");
                } else {
                    toast(ErrorDes.ERROR_INFO);
                }
            }
        });


    }
}

package com.example.administrator.aitang.ui.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.wode.ConfigBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.http.HttpManager;
import com.example.administrator.aitang.http.listener.ServiceBackObjectListener;
import com.example.administrator.aitang.http.result.ServiceResult;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.example.administrator.aitang.utils.basic.StringUtils;
import com.example.administrator.aitang.utils.glide.GlideUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactUsActivity extends MyBaseActivity {

    @BindView(R.id.headerview)
    LinearLayout llHeaderView;

    @BindView(R.id.iv_header_left)
    ImageView ivHeaderLeft;
    @BindView(R.id.tv_wechat_name)
    TextView tvWechatName;
    @BindView(R.id.tv_website)
    TextView tvWebsite;
    @BindView(R.id.tv_custom_service_name)
    TextView tvCustomServiceName;
    @BindView(R.id.tv_custom_service_phone)
    TextView tvCustomServicePhone;
    @BindView(R.id.tv_custom_service_wechat)
    TextView tvCustomServiceWechat;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.iv_wechat)
    ImageView ivWechat;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_contact_us);
    }

    @Override
    public void init() {
        super.init();
        setImmerBarcolor(llHeaderView);
        setHeader();

        requestData();
    }


    private void setHeader() {
        setHeaderBackground(R.color.color_white);
        setHeaderImage(MyConstant.Position.LEFT, R.drawable.back_icon_nav, false, null);
        setHeaderTitle("联系我们", MyConstant.Position.CENTER, R.color.color_323232);
    }


    /**
     * 请求数据
     */
    private void requestData() {

        Map paramsMap = new HashMap();

        String uid = Myapp.spUtil.getUid();
        String token = Myapp.spUtil.getToken();

        paramsMap.put("uid", uid);
        paramsMap.put("token", token);

        HttpManager.getInstance().get(MyConstant.CONFIG, paramsMap, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {

                ConfigBean configBean = new Gson().fromJson(response, ConfigBean.class);
                initData(configBean);

            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {}
        });

    }

    /**
     * 加载数据
     *
     * @param configBean
     */
    private void initData(ConfigBean configBean) {
        ConfigBean.DataBean dataBean = configBean.getData();

        //微信公众号名字
        if (!StringUtils.isEmpty(dataBean.getWechatname())) {
            tvWechatName.setText(dataBean.getWechatname());
        }

        //官网
        if (!StringUtils.isEmpty(dataBean.getWebsite())) {
            tvWebsite.setText(dataBean.getWebsite());
        }

        //客服
        if (!StringUtils.isEmpty(dataBean.getCustom_service())) {
            tvCustomServiceName.setText(dataBean.getCustom_service());
        }

        //客服电话
        if (!StringUtils.isEmpty(dataBean.getCustom_service_phone())) {
            tvCustomServicePhone.setText(dataBean.getCustom_service_phone());
        }

        //客服微信
        if (!StringUtils.isEmpty(dataBean.getCustom_service_wechat())) {
            tvCustomServiceWechat.setText(dataBean.getCustom_service_wechat());
        }

        //办公地址
        if (!StringUtils.isEmpty(dataBean.getAddress())) {
            tvAddress.setText(dataBean.getAddress());
        }

        //公众号二维码
        if (!StringUtils.isEmpty(dataBean.getWechat())) {
            GlideUtils.loadImg(dataBean.getWechat(), ivWechat);
        }
    }


    @OnClick({R.id.iv_header_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                ContactUsActivity.this.finish();
                break;
            default:
                break;
        }
    }
}

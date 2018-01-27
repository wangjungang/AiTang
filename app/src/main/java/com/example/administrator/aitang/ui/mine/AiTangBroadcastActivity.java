package com.example.administrator.aitang.ui.mine;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.wode.AiTangBroadcastAdapter;
import com.example.administrator.aitang.bean.wode.AiTangBroadcastBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.http.HttpManager;
import com.example.administrator.aitang.http.error.ErrorCode;
import com.example.administrator.aitang.http.listener.ServiceBackObjectListener;
import com.example.administrator.aitang.http.result.ServiceResult;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AiTangBroadcastActivity extends MyBaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    @BindView(R.id.headerview)
    LinearLayout llHeaderView;

    @BindView(R.id.iv_header_left)
    ImageView ivHeaderLeft;
    @BindView(R.id.lv_broadcast)
    ListView lvBroadcast;

    AiTangBroadcastAdapter broadcastAdapter = null;
//    private AiTangBroadcastBean aiTangBroadcastBean;//请求接口返回的数据

    private List<AiTangBroadcastBean.DataBean> broadcastList = new ArrayList<>();

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_ai_tang_broadcast);
    }

    @Override
    public void init() {
        super.init();
        setImmerBarcolor(llHeaderView);
        setHeader();
        lvBroadcast.setOnItemClickListener(this);
        broadcastAdapter = new AiTangBroadcastAdapter(AiTangBroadcastActivity.this, broadcastList);
        lvBroadcast.setAdapter(broadcastAdapter);
        setRefresh();
    }


    private void setHeader() {
        setHeaderBackground(R.color.color_white);
        setHeaderImage(MyConstant.Position.LEFT, R.drawable.back_icon_nav, false, this);
        setHeaderTitle("爱唐播报", MyConstant.Position.CENTER, R.color.color_323232);
    }

    /**
     * 设置刷新相关
     */
    private void setRefresh() {
        aboutRefreshView(true);

        xRefreshView.setEmptyView(R.layout.layout_xrefresh_emptyview);
        View v = xRefreshView.getEmptyView();
        TextView tvEmptyTip = v.findViewById(R.id.tv_empty_tip);
        tvEmptyTip.setText("当前没有播报，下拉刷新");

        xRefreshView.setPullLoadEnable(false);
        xRefreshView.startRefresh();
    }

    /**
     * 停止刷新
     */
    private void stopRefresh() {
        xRefreshView.stopRefresh();
        xRefreshView.stopLoadMore();
    }

    @Override
    public void refresh() {
        super.refresh();
        requestData();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_header_left:
                AiTangBroadcastActivity.this.finish();
                break;
            default:
                break;
        }

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        // 点击跳转webview页面？

    }


    /**
     * 请求数据
     */
    private void requestData() {


        HttpManager.getInstance().get(MyConstant.AITANGBROADCAST, null, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {
                xRefreshView.enableEmptyView(false);

                AiTangBroadcastBean aiTangBroadcastBean = new Gson().fromJson(response, AiTangBroadcastBean.class);

                if (null != aiTangBroadcastBean) {
                    broadcastList.clear();
                    broadcastList.addAll(aiTangBroadcastBean.getData());
                    broadcastAdapter.notifyDataSetChanged();
                }
                stopRefresh();
            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {
                xRefreshView.enableEmptyView(true);
                if (result.getCode().equals(ErrorCode.ERROR_CODE_201)) {

                } else {
                    toast("无法连接到服务器,请检查您的网络");
                }
                stopRefresh();
            }
        });
    }
}

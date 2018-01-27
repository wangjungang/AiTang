package com.example.administrator.aitang.ui.lianxi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.lianxi.CollectionListAdapter;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.lianxi.CollectionListBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.http.HttpManager;
import com.example.administrator.aitang.http.error.ErrorCode;
import com.example.administrator.aitang.http.error.ErrorDes;
import com.example.administrator.aitang.http.listener.ServiceBackObjectListener;
import com.example.administrator.aitang.http.result.ServiceResult;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by 武培培 on 2017/10/26.
 */

public class CollectionTiMuActivity extends MyBaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    @BindView(R.id.listview)
    ListView listView;
    @BindView(R.id.headerview)
    LinearLayout ll_header;
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    CollectionListAdapter adapter;
    List<CollectionListBean.DataBean> datas = new ArrayList<>();
    @BindView(R.id.iv_header_left)
    ImageView ivHeaderLeft;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_history_real_question);
    }

    @Override
    public void init() {
        super.init();
        setHeader();
        aboutRefreshView(true);

        xRefreshView.setEmptyView(R.layout.layout_xrefresh_emptyview);
        View v = xRefreshView.getEmptyView();
        TextView tvEmptyTip = v.findViewById(R.id.tv_empty_tip);
        tvEmptyTip.setText("当前没有收藏题目，下拉刷新");

        xRefreshView.startRefresh();
        adapter = new CollectionListAdapter(this, datas);
        listView.setAdapter(adapter);
        setImmerBarcolor(ll_header);
        listView.setOnItemClickListener(this);
    }

    private void setHeader() {
        setHeaderBackground(R.color.color_white);
        setHeaderImage(MyConstant.Position.LEFT, R.drawable.back_icon_nav, false, this);
        setHeaderTitle("收藏题目", MyConstant.Position.CENTER, R.color.color_323232);
        ivHeaderLeft.setOnClickListener(this);
    }

    @Override
    public void refresh() {
        super.refresh();
        requestRealQuestion();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                finish();
                break;
        }
    }

    private void requestRealQuestion() {

        Map paramsMap = new HashMap();

        String uid = Myapp.spUtil.getUid();
        String token = Myapp.spUtil.getToken();

        paramsMap.put("uid", uid);
        paramsMap.put("token", token);
        paramsMap.put("type", "2");

        HttpManager.getInstance().get(MyConstant.COLLECTION, paramsMap, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {
                xRefreshView.enableEmptyView(false);
                Gson gson = new Gson();
                CollectionListBean bean = gson.fromJson(response, CollectionListBean.class);
                datas.clear();
                datas.addAll(bean.getData());
                adapter.notifyDataSetChanged();
                xRefreshView.stopRefresh();
                xRefreshView.stopLoadMore();

            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {
                xRefreshView.enableEmptyView(true);
                if (result.getCode().equals(ErrorCode.ERROR_CODE_201)) {

                } else {
                    toast(ErrorDes.ERROR_INFO);
                }
                xRefreshView.stopRefresh();
                xRefreshView.stopLoadMore();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, CollectionDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", datas.get(i));
        intent.putExtras(bundle);
        startActivity(intent);
    }
}

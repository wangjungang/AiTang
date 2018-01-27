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
import com.example.administrator.aitang.adapter.lianxi.UserMessageAdapter;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.lianxi.UserMessageBean;
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
import butterknife.OnClick;

/********************************************************************
 @description: 用户消息页面
 @author: wangzexin
 @time: 2017/11/15 14:21
 @变更历史:
 ********************************************************************/
public class UserMessageActivity extends MyBaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.headerview)
    LinearLayout llHeaderView;

    @BindView(R.id.iv_header_left)
    ImageView ivHeaderLeft;
    @BindView(R.id.lv_message)
    ListView lvMessage;


    private UserMessageAdapter messageAdapter = null;
    private List<UserMessageBean.DataBean> messageList = new ArrayList<>();

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_user_message);
    }

    @Override
    public void init() {
        super.init();
        setImmerBarcolor(llHeaderView);
        setHeader();

        messageAdapter = new UserMessageAdapter(this, messageList);
        lvMessage.setAdapter(messageAdapter);
        setRefresh();
        lvMessage.setOnItemClickListener(this);
    }

    /**
     * 设置刷新相关
     */
    private void setRefresh() {
        aboutRefreshView(true);

        xRefreshView.setEmptyView(R.layout.layout_xrefresh_emptyview);
        View v = xRefreshView.getEmptyView();
        TextView tvEmptyTip = v.findViewById(R.id.tv_empty_tip);
        tvEmptyTip.setText("当前没有消息，下拉刷新");

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
        getData();
    }

    private void setHeader() {
        setHeaderBackground(R.color.color_white);
        setHeaderImage(MyConstant.Position.LEFT, R.drawable.back_icon_nav, false, null);
        setHeaderTitle("消息", MyConstant.Position.CENTER, R.color.color_323232);
    }


    /**
     * 获取消息数据
     */
    private void getData() {

        Map paramsMap = new HashMap();

        String uid = Myapp.spUtil.getUid();
        String token = Myapp.spUtil.getToken();


        paramsMap.put("uid", uid);
        paramsMap.put("token", token);

        HttpManager.getInstance().get(MyConstant.USERMESSAGE, paramsMap, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {
                xRefreshView.enableEmptyView(false);
                Gson gson = new Gson();
                UserMessageBean messageBean = gson.fromJson(response, UserMessageBean.class);
                messageList.clear();
                messageList.addAll(messageBean.getData());
                messageAdapter.notifyDataSetChanged();
                stopRefresh();
            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {
                stopRefresh();
                xRefreshView.enableEmptyView(true);
                if (result.getCode().equals(ErrorCode.ERROR_CODE_201)) {

                } else {
                    toast(ErrorDes.ERROR_INFO);
                }
            }
        });

    }


    @OnClick({R.id.iv_header_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                UserMessageActivity.this.finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (messageList.get(i).getType().equals("1")
                || messageList.get(i).getType().equals("2")) {//1和2是可以点击的
            Intent intent = new Intent(this, PiGaiDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("type", messageList.get(i).getType());
            bundle.putString("questionid", messageList.get(i).getQuestionid());
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}

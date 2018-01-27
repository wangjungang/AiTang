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
import com.example.administrator.aitang.adapter.lianxi.HistoryRealQuestionAdapter;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.lianxi.HistoryRealQuestiionBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.http.error.ErrorDes;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.example.administrator.aitang.utils.okhttp.OkHttpUtils;
import com.example.administrator.aitang.utils.okhttp.callback.StringCallback;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by 武培培 on 2017/10/26.
 */

public class HistoryRealQuestionACtivity extends MyBaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    @BindView(R.id.listview)
    ListView listView;
    @BindView(R.id.headerview)
    LinearLayout ll_header;
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    HistoryRealQuestionAdapter adapter;
    List<HistoryRealQuestiionBean.Data> datas = new ArrayList<>();
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
        tvEmptyTip.setText("当前没有真题，下拉刷新");

        xRefreshView.setPullLoadEnable(false);
        xRefreshView.startRefresh();
        adapter = new HistoryRealQuestionAdapter(this, datas);
        listView.setAdapter(adapter);
        setImmerBarcolor(ll_header);
        listView.setOnItemClickListener(this);
    }

    private void setHeader() {
        setHeaderBackground(R.color.color_white);
        setHeaderImage(MyConstant.Position.LEFT, R.drawable.back_icon_nav, false, this);
        setHeaderTitle("历年真题", MyConstant.Position.CENTER, R.color.color_323232);
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
        OkHttpUtils.get().url(MyConstant.REALQUESTION)
                .addParams("uid", Myapp.spUtil.getUid())
                .build().execute(requestCallback());
    }

    private StringCallback requestCallback() {
        return new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                xRefreshView.enableEmptyView(true);
//                toast(ErrorDes.ERROR_INFO);
            }

            @Override
            public void onResponse(String response, int id) throws IOException, JSONException {
                xRefreshView.enableEmptyView(false);
                HistoryRealQuestiionBean bean = new Gson().fromJson(response, HistoryRealQuestiionBean.class);
                String code = bean.getCode();
                if (code.equals("200")) {
                    datas = bean.getData();
                    if (datas.size() > 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //下拉刷新先清空原来的数据，再添加新数据
                                adapter.clear();
                                //添加新数据
                                adapter.addAll(datas, false);
                                adapter.notifyDataSetChanged();
                                xRefreshView.stopRefresh();
                                xRefreshView.stopLoadMore();
                            }
                        });
                    } else {
                        xRefreshView.stopRefresh();
                        xRefreshView.stopLoadMore();
                    }
                } else {
                    xRefreshView.stopRefresh();
                    xRefreshView.stopLoadMore();
                }
            }
        };
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //进入真题详情
        Intent intent = new Intent(this, KSZNLXActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(MyConstant.XITITYPEFLAG, MyConstant.LINIANZHENTI);
        bundle.putString(MyConstant.QCID, datas.get(i).getQcid());
        intent.putExtras(bundle);
        jumpTo(intent, false);
    }

}

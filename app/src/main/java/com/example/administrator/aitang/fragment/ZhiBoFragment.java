package com.example.administrator.aitang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.zhibo.ClassListAdapter;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.zhibo.ClassBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.ui.account.ExameTypeActivity;
import com.example.administrator.aitang.ui.zhibo.KechengXiangqingActivity;
import com.example.administrator.aitang.ui.zhibo.MyClassActivity;
import com.example.administrator.aitang.utils.RequestFailResult;
import com.example.administrator.aitang.utils.okhttp.OkHttpUtils;
import com.example.administrator.aitang.utils.okhttp.callback.StringCallback;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by 武培培 on 2017/10/24.
 */

public class ZhiBoFragment extends MyBaseFragment implements AdapterView.OnItemClickListener {
    @BindView(R.id.headerview)
    LinearLayout llHeaderView;
    @BindView(R.id.iv_header_left)
    ImageView iv_left;
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.lsv_zhibo)
    ListView lsv;
    static List<ClassBean.DataBean> datas;
    ClassListAdapter adapter;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_zhibo, container, false);
    }


    @Override
    public void init() {
        setImmerBarcolor(llHeaderView);
        iv_left.setImageResource(R.drawable.kechengxuanze_icon_nav);
        setHeaderBackground(R.color.color_white);
        iv_left.setVisibility(View.VISIBLE);
        tv_title.setText("直播课");

        aboutRefreshView(true);
        xRefreshView.setPullLoadEnable(false);
        datas = new ArrayList<>();
        adapter = new ClassListAdapter(getActivity(), datas, true);
        lsv.setAdapter(adapter);
        xRefreshView.startRefresh();
        lsv.setOnItemClickListener(this);

    }

    /**
     * 停止刷新
     */
    private void stopRefresh() {
        xRefreshView.stopRefresh();
        xRefreshView.stopLoadMore();
    }

    @Override
    public void addData() {
        forData("normal");
    }

    private void forData(final String str) {
        OkHttpUtils.get().url(MyConstant.CLASS_CLASSLIST)
                .addParams("uid", Myapp.spUtil.getUid())
                .addParams("token", Myapp.spUtil.getToken())
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                stopRefresh();
            }

            @Override
            public void onResponse(String response, int id) throws IOException, JSONException {
                Log.d("TAG", "直播课程列表response:" + response);
                ClassBean bean = new Gson().fromJson(response, ClassBean.class);
                if (bean.getCode().equals("200")) {
                    switch (str) {
                        case "normal"://普通的请求数据
                        case "refresh"://下拉
                            adapter.addAll(bean.getData(), true);
                            break;
                        case "loadmore"://上推
                            adapter.addAll(bean.getData(), false);
                            break;
                    }
                    stopRefresh();
                } else {
                    log("请求返回的状态码=" + bean.getCode() + ":" + RequestFailResult.requestFail(bean.getCode()));
                }
            }
        });
    }

    @OnClick({R.id.iv_header_left, R.id.tv_zhibofrg_myclass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                //考试类型
                Intent intent = new Intent(getActivity(), ExameTypeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("flag", "2");//跳转标志
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.tv_zhibofrg_myclass://我的课程
                jumpTo(MyClassActivity.class, false);
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ClassBean.DataBean bean = (ClassBean.DataBean) adapter.getItem(i);
        Intent intent = new Intent(getActivity(), KechengXiangqingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", bean);
        intent.putExtras(bundle);
        jumpTo(intent, false);
    }
}

package com.example.administrator.aitang.fragment.zhibo;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.zhibo.ClassDetailAdapter;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.zhibo.ClassDetailBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.fragment.MyBaseFragment;
import com.example.administrator.aitang.ui.zhibo.ZhiboActivity;
import com.example.administrator.aitang.ui.zhibo.ZhiboEndActivity;
import com.example.administrator.aitang.ui.zhibo.ZhiboNoStartActivity;
import com.example.administrator.aitang.utils.RequestFailResult;
import com.example.administrator.aitang.utils.okhttp.OkHttpUtils;
import com.example.administrator.aitang.utils.okhttp.callback.StringCallback;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;


public class KechengbiaoFragement extends MyBaseFragment implements AdapterView.OnItemClickListener {

    private String c_id;
    @BindView(R.id.lsv_kechengbiao)
    ListView lsv;
    ClassDetailAdapter adapter;
    static List<ClassDetailBean.DataBean> datas_classDetail;
    private int isBuy;//是否买过该课程 1：买过 2：没买过


    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();//从activity传过来的Bundle
        if (bundle != null) {
            c_id = bundle.getString("c_id");
            isBuy = bundle.getInt("isBuy");
        }
        return inflater.inflate(R.layout.fragment_kechengbiao_fragement, container, false);
    }

    @Override
    public void init() {

        aboutRefreshView(true);
        xRefreshView.setPullLoadEnable(false);
        datas_classDetail = new ArrayList<>();
        adapter = new ClassDetailAdapter(getActivity());
        lsv.setAdapter(adapter);
        xRefreshView.startRefresh();
        lsv.setOnItemClickListener(this);
    }

    @Override
    public void addData() {
        forData("normal");
    }

    private void forData(final String str) {
        OkHttpUtils.get().url(MyConstant.CLASS_LISTDETAIL)
                .addParams("uid", Myapp.spUtil.getUid())
                .addParams("token", Myapp.spUtil.getToken())
                .addParams("c_id", c_id)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                stopRefresh();
                Log.d("TAG", "课程表请求出错" + e);
            }

            @Override
            public void onResponse(String response, int id) throws IOException, JSONException {
                Log.d("TAG", "课程表response:" + response);
                ClassDetailBean bean = new Gson().fromJson(response, ClassDetailBean.class);
                if (bean.getCode().equals("200")) {
                    switch (str) {
                        case "normal"://普通的请求数据
                            adapter.addAll(bean.getData(), true);
                            break;
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

    /**
     * 停止刷新
     */
    private void stopRefresh() {
        xRefreshView.stopRefresh();
        xRefreshView.stopLoadMore();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (isBuy == 1) {//买过课程
            ClassDetailBean.DataBean bean = (ClassDetailBean.DataBean) adapter.getItem(i);
            String imgUrl = bean.getCdimg();
            String time = bean.getTime();
            String state = bean.getIsstart();//当前开播状态  0：未开播 1：正在开播 2：已经结束
            if (state.equals("0")) {
                Intent intent1 = new Intent(getActivity(), ZhiboNoStartActivity.class);
                intent1.putExtra("imgurl", bean.getCdimg());
                intent1.putExtra("time", bean.getTime());
                getActivity().startActivity(intent1);
            } else if (state.equals("1")) {
                ZhiboActivity.start(getActivity(), bean.getTeacher().getRoomid(), false, bean);
            } else if (state.equals("2")) {
                Intent intent3 = new Intent(getActivity(), ZhiboEndActivity.class);
                intent3.putExtra("imgurl", bean.getCdimg());
                Bundle bundle = new Bundle();
                bundle.putParcelable("data", bean);
                intent3.putExtras(bundle);
                getActivity().startActivity(intent3);
            }
        } else if (isBuy == 2) {//没买过课程
            Toast.makeText(getActivity(), "您需要先购买该课程", Toast.LENGTH_SHORT).show();
        }
    }
}

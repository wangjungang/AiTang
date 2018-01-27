package com.example.administrator.aitang.fragment.zhibo;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.zhibo.TeacherJieshaoAdapter;
import com.example.administrator.aitang.bean.zhibo.ClassBean;
import com.example.administrator.aitang.fragment.MyBaseFragment;
import com.example.administrator.aitang.ui.zhibo.KechengXiangqingActivity;
import com.example.administrator.aitang.ui.zhibo.MingshiXinxiActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LaoshiJieshaoFragemnt extends MyBaseFragment implements AdapterView.OnItemClickListener {
    TeacherJieshaoAdapter adapter_teacher;
    List<ClassBean.DataBean.TeacherBean> datas_teacher;
    List<ClassBean.DataBean.TeacherBean> datas;
    TeacherJieshaoAdapter adapter;
    @BindView(R.id.lsv_laoshijieshao)
    ListView lsv;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();//从activity传过来的Bundle
        datas_teacher = new ArrayList<>();
        datas = new ArrayList<>();
        return inflater.inflate(R.layout.fragment_laoshi_jieshao_fragemnt, container, false);
    }

    KechengXiangqingActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("TAG", "onattach()");
        activity = (KechengXiangqingActivity) context;
    }

    @Override
    public void init() {
        aboutRefreshView(true);
        xRefreshView.setPullLoadEnable(false);
        adapter = new TeacherJieshaoAdapter(getActivity(), datas);
        lsv.setAdapter(adapter);
        xRefreshView.startRefresh();
        lsv.setOnItemClickListener(this);
    }

    @Override
    public void addData() {
        forData();
    }

    private void forData() {
        datas = activity.getData();
        adapter.addAll(datas, true);
        stopRefresh();
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
        ClassBean.DataBean.TeacherBean bean = (ClassBean.DataBean.TeacherBean) adapter.getItem(i);
        Intent intent = new Intent(getActivity(), MingshiXinxiActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", bean);
        intent.putExtras(bundle);
        jumpTo(intent, false);
    }
}

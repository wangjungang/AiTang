package com.example.administrator.aitang.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.EndlessRecyclerOnScrollListener;
import com.example.administrator.aitang.adapter.LoadMoreWrapper;
import com.example.administrator.aitang.adapter.MyRecyclerBaseAdapter;
import com.example.administrator.aitang.ui.MyBaseActivity;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/8.
 */

public abstract class MyRecyclerBaseFragment<T> extends MyBaseFragment {
    protected SwipeRefreshLayout srl;
    RecyclerView rv;
    protected MyRecyclerBaseAdapter rvAdapter;
    protected LoadMoreWrapper loadMoreWrapper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        String clazzName = getClass().getSimpleName();//MessageFragment

        //调用抽象方法由子类手动赋值
        view = createView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);
        //要求，所有的Fragment布局文件include header时，必须指定同样的id：R.id.headerview
        if (view == null)
            return null;
        headerView = view.findViewById(R.id.headerview);
//        srl = view.findViewById(R.id.swipeRecreshLayout);
        rv = view.findViewById(R.id.recyclerview);
        baseActivity = (MyBaseActivity) getActivity();
//        //  设置刷新球颜色

        init();
        return view;
    }

    protected abstract T normalAdapter();

    protected void setRecycle(Context context) {

        //手动调用,通知系统去测量
        srl.setColorSchemeColors(Color.BLUE);
        srl.setBackgroundResource(R.color.touming);
        srl.measure(0, 0);
        srl.setRefreshing(true);

        rvAdapter = (MyRecyclerBaseAdapter) normalAdapter();
        loadMoreWrapper = new LoadMoreWrapper(rvAdapter);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setAdapter(loadMoreWrapper);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("TAG","刷新监听---");
                request("refresh");
            }
        });
        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                request("loadmore");
            }
        });
        request("normal");
    }

    /**
     * 分情况进行网络请求
     *
     * @param str
     */
    private void request(String str) {
        switch (str) {
            case "normal"://普通的请求数据
                requestData(str);
                Log.d("MyRecyclerBaseFragment", "普通请求");
                break;
            case "refresh"://下拉
                Log.d("TAG","刷新监听---停止刷新");
                srl.setRefreshing(false);
                Log.d("MyRecyclerBaseFragment", "下拉请求");
                refresh(str);
                break;
            case "loadmore"://上推
                Log.d("MyRecyclerBaseFragment", "上推请求");
                loadMore(str);
                break;
            default:
                break;
        }
        loadMoreWrapper.notifyDataSetChanged();
    }

    //第一次进行执行的网络请求
    protected abstract void requestData(String str);

    //下拉的时候执行
    protected abstract void refresh(String str);

    //滑动的时候执行
    protected abstract void loadMore(String str);

    /**
     * 有数据
     */
    protected void haveData() {
        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);
        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
    }

    /**
     * 没有数据
     */
    protected void noData() {
        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

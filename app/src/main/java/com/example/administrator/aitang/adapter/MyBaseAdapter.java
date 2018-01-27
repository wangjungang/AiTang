package com.example.administrator.aitang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/26.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {

    public Context context;
    public LayoutInflater inflater;
    List<T> datasource;

    public MyBaseAdapter(Context context) {
        super();
        this.context = context;
        this.datasource = new ArrayList<T>();
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public MyBaseAdapter(Context context, List<T> datasource) {

        super();
        this.context = context;
        this.datasource = datasource;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return datasource.size();
    }

    @Override
    public T getItem(int position) {
        return datasource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addAll(List<T> list, boolean isClear) {
        if (isClear) {
            datasource.clear();
        }
        datasource.addAll(list);
        notifyDataSetChanged();
    }

    public void add(T t) {
        datasource.add(t);
        notifyDataSetChanged();
    }

    public void remove(T t) {
        datasource.remove(t);
        notifyDataSetChanged();
    }

    public void clear() {
        datasource.clear();
        notifyDataSetChanged();
    }

    public void setViewGone(List<View> v) {
        for (View view : v
                ) {
            view.setVisibility(View.GONE);
        }
    }


}
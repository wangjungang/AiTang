package com.example.administrator.aitang.adapter.zhibo;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/5.
 */

public class AbsHolder extends RecyclerView.ViewHolder {
    View mView;
    private SparseArray<View> mViews = new SparseArray<>();

    public AbsHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(@IdRes int id) {
        View view = mViews.get(id);
        if (view == null) {
            view = mView.findViewById(id);
            mViews.put(id, view);
        }
        return (T) view;
    }

}
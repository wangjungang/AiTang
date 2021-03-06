package com.example.administrator.aitang.adapter.zhibo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/5.
 */

public  abstract class AbsRVAdapter<T, Holder extends AbsHolder>
        extends RecyclerView.Adapter<Holder> {
    protected String TAG;
    protected List<T> mData = new ArrayList<>();
    protected Context mContext;
    Holder holder;

    public AbsRVAdapter(Context context, List<T> data) {
        mData = data;
        mContext = context;
        String arrays[] = getClass().getName().split("\\.");
        TAG = arrays[arrays.length - 1];
    }

    @Override public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(setLayoutId(viewType), parent, false);
        //LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //VD binding = DataBindingUtil.inflate(inflater, setLayoutId(viewType), parent, false);
        //;
        holder = getViewHolder(view, viewType);
        return holder;
    }

    protected abstract Holder getViewHolder(View convertView, int viewType);

    @Override public void onBindViewHolder(Holder holder, int position) {
        bindData(holder, position, mData.get(position));
    }

    @Override public void onBindViewHolder(Holder holder, int position, List<Object> payloads) {
        if (payloads == null || payloads.isEmpty()) {
            bindData(holder, position, mData.get(position));
        } else {
            bindData(holder, position, mData.get(position), payloads);
        }
    }

    public Context getContext() {
        return mContext;
    }

    @Override public int getItemCount() {
        return mData.size();
    }

    /**
     * item 的type
     */
    protected abstract int setLayoutId(int type);

    protected abstract void bindData(Holder holder, int position, T item);

    protected void bindData(Holder holder, int position, T item, List<Object> payloads) {

    }
}
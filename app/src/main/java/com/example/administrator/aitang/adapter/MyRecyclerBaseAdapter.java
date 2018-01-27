package com.example.administrator.aitang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */

public abstract class MyRecyclerBaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    public LayoutInflater inflater;
    List<T> datasource;
    protected RecyclerViewOnItemClickListener mOnItemClickListener;//item的点击监听
    protected RecyclerViewOnItemLongClickListener mOnItemLongClickListener;//item的长按点击事件

    public MyRecyclerBaseAdapter(Context context) {
        super();
        this.context = context;
        this.datasource = new ArrayList<T>();
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public MyRecyclerBaseAdapter(Context context, List<T> datasource) {
        super();
        this.context = context;
        this.datasource = datasource;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setmOnItemClickListener(RecyclerViewOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setmOnItemLongClickListener(RecyclerViewOnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View v = getView();
        BaseViewHolder bvh = getViewHolder(v);
        if (mOnItemClickListener != null) {
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view, (Integer) view.getTag());
                }
            });
        }
        if (mOnItemLongClickListener != null) {
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemLongClickListener.onItemLongClick(view, (Integer) view.getTag());
                    //返回true表示消耗了事件，事件不会继续传递
                    return true;
                }
            });
        }
        return bvh;
    }

    @Override
    public abstract void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position);

    protected abstract View getView();

    protected abstract BaseViewHolder getViewHolder(View view);

    public T getItem(int position) {
        return datasource.get(position);
    }


    @Override
    public int getItemCount() {
        return datasource.size();
    }

    public void addAll(List<T> list, boolean isClear) {
        addAll(list, isClear, false);
    }

    /**
     *
     * @param list 添加的数据
     * @param isClear 是否需要清空之前的数据
     * @param isTop 是否需要把数据添加到顶部显示(通常在分页下拉刷新的时候会用到)
     */
    public void addAll(List<T> list, boolean isClear, boolean isTop) {
        if (isClear) {
            datasource.clear();
        }
        if (isTop)
            datasource.addAll(0, list);
        else
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


    protected static class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }
    }
}

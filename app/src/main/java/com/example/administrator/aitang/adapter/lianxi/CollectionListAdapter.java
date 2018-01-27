package com.example.administrator.aitang.adapter.lianxi;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.MyBaseAdapter;
import com.example.administrator.aitang.bean.lianxi.CollectionListBean;
import com.example.administrator.aitang.utils.basic.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 武培培 on 2017/10/26.
 */

public class CollectionListAdapter extends MyBaseAdapter<CollectionListBean.DataBean> {
    public CollectionListAdapter(Context context, List<CollectionListBean.DataBean> datasource) {
        super(context, datasource);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.inflate_lsvitem_collection_list, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        CollectionListBean.DataBean bean = getItem(i);
        if (!StringUtils.isEmpty(bean.getName())) {
            viewHolder.tvName.setText(bean.getName());
        }
        String uqid = bean.getUqid();
        if (!StringUtils.isEmpty(uqid)) {
            String[] uqidlist = uqid.split(",");
            if (uqidlist.length > 0) {
                viewHolder.tvCount.setText("(" + uqidlist.length + "道)");
            }
        } else {
            viewHolder.tvCount.setText("(0道)");
        }


        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_count)
        TextView tvCount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

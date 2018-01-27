package com.example.administrator.aitang.adapter.lianxi;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.MyBaseAdapter;
import com.example.administrator.aitang.bean.lianxi.ZhengquelvBean;
import com.example.administrator.aitang.utils.basic.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/25.
 */

public class ZhengquelvAdapter extends MyBaseAdapter<ZhengquelvBean> {
    public ZhengquelvAdapter(Context context, List<ZhengquelvBean> datasource) {
        super(context, datasource);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.inflate_lsvitem_lianxizhoubao_zhengquelv, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ZhengquelvBean bean = getItem(i);
        holder.tv_type.setText(bean.getType());
        holder.sb_sb.setIndeterminate(false);
        holder.sb_sb.setMax(100);
        holder.sb_sb.setProgress((int) Math.round(bean.getProbability()));
        holder.tv_num.setText((int) Math.round(bean.getProbability())+"");
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_inflate_lxzb_zql_type)
        TextView tv_type;
        @BindView(R.id.sb_inflate_lxzb_zql_sb)
        SeekBar sb_sb;
        @BindView(R.id.tv_inflate_lxzb_zql_num)
        TextView tv_num;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

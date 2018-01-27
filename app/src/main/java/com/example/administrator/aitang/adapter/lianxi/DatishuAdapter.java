package com.example.administrator.aitang.adapter.lianxi;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.MyBaseAdapter;
import com.example.administrator.aitang.bean.lianxi.DatishuBean;
import com.example.administrator.aitang.utils.basic.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/25.
 */

public class DatishuAdapter extends MyBaseAdapter<DatishuBean> {
    public DatishuAdapter(Context context, List<DatishuBean> datasource) {
        super(context, datasource);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.inflate_lsvitem_lianxizhoubao_datishu, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        DatishuBean bean = getItem(i);
        holder.tv_content.setText(bean.getType());
        holder.tv_datishu.setText(StringUtils.double2StrRound(bean.getTimunum()));
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_inflate_lsvitem_datishu_content)
        TextView tv_content;
        @BindView(R.id.tv_inflate_lsvitem_datishu)
        TextView tv_datishu;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

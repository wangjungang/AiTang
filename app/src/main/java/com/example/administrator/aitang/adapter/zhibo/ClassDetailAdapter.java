package com.example.administrator.aitang.adapter.zhibo;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.MyBaseAdapter;
import com.example.administrator.aitang.bean.zhibo.ClassDetailBean;
import com.example.administrator.aitang.utils.basic.DateUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/26.
 */

public class ClassDetailAdapter extends MyBaseAdapter<ClassDetailBean.DataBean> {

    public ClassDetailAdapter(Context context) {
        super(context);
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.inflate_lsvitem_kechengbiao, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ClassDetailBean.DataBean bean = getItem(i);
        String s = bean.getCdstart_time();
        String e = bean.getCdend_time();
        String startTime = DateUtil.toString(Long.parseLong(s), "yyyy-MM-dd");
        String endTime = DateUtil.toString(Long.parseLong(e), "yyyy-MM-dd");
        long timeDis = Long.parseLong(e) - Long.parseLong(s);//时间差 秒
        long result = timeDis / 60;
        holder.tvTime.setText(startTime + "-" + endTime + " (");
        holder.tvKeshi.setText("" + result);
        holder.tvName.setText(bean.getTname());
        holder.tvTimu.setText(" - " + bean.getCdintro());
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_inflatelsvitem_kcb_time)
        TextView tvTime;
        @BindView(R.id.tv_inflatelsvitem_kcb_keshi)
        TextView tvKeshi;
        @BindView(R.id.tv_inflatelsvitem_kcb_name)
        TextView tvName;
        @BindView(R.id.tv_inflatelsvitem_kcb_timu)
        TextView tvTimu;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

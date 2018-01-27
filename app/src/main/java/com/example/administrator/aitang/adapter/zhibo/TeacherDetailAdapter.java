package com.example.administrator.aitang.adapter.zhibo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.MyBaseAdapter;
import com.example.administrator.aitang.bean.zhibo.TeacherDetailBean;
import com.example.administrator.aitang.utils.basic.DateUtil;
import com.example.administrator.aitang.views.MyRatingBar;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/26.
 */

public class TeacherDetailAdapter extends MyBaseAdapter<TeacherDetailBean.DataBean> {
    public TeacherDetailAdapter(Context context, List<TeacherDetailBean.DataBean> datasource) {
        super(context, datasource);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.inflate_lsvitem_mingshixinxi, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        TeacherDetailBean.DataBean bean = getItem(i);
        holder.tvTitle.setText(bean.getTname() + "-" + bean.getCdintro() + "-" + bean.getFather());
        float num = (float) bean.getScore() / 20;
        DecimalFormat df = new DecimalFormat("0.0");
        holder.tvXingji.setStar(Float.parseFloat(df.format(num)));
        holder.tvFenshu.setText(bean.getScore()+"");
        String startTime = DateUtil.toString(Long.parseLong(bean.getCdstart_time()), "yyyy-MM-dd");
        String endTime = DateUtil.toString(Long.parseLong(bean.getCdend_time()), "yyyy-MM-dd");
        holder.tvTime.setText(startTime + "-" + endTime);
        holder.tvPn.setText(bean.getComment());
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_inflatelsvitem_msxx_title)
        TextView tvTitle;
        @BindView(R.id.tv_inflatelsvitem_msxx_xingji)
        MyRatingBar tvXingji;
        @BindView(R.id.tv_inflatelsvitem_msxx_fs)
        TextView tvFenshu;
        @BindView(R.id.tv_inflate_lsvitem_time)
        TextView tvTime;
        @BindView(R.id.tv_inflatelsvitem_msxx_pn)
        TextView tvPn;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

package com.example.administrator.aitang.adapter.zhibo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.MyBaseAdapter;
import com.example.administrator.aitang.bean.zhibo.ClassBean;
import com.example.administrator.aitang.utils.glide.GlideUtils;
import com.example.administrator.aitang.views.MyRatingBar;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/26.
 */

public class TeacherJieshaoAdapter extends MyBaseAdapter<ClassBean.DataBean.TeacherBean> {
    public TeacherJieshaoAdapter(Context context, List<ClassBean.DataBean.TeacherBean> datasource) {
        super(context, datasource);
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.inflate_lsvitem_laoshijieshao, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ClassBean.DataBean.TeacherBean bean = getItem(i);
        GlideUtils.setCircleAvatar(bean.getTpic(), holder.imgPic, R.drawable.touxiang_image_laoshijieshao, R.drawable.touxiang_image_laoshijieshao);
        holder.tvName.setText(bean.getTname());
        holder.tvDis.setText(bean.getTsimple());
        float num = (float) Float.parseFloat(bean.getTscore()) / 100;
        DecimalFormat df = new DecimalFormat("0.0");
        holder.tvXingji.setStar(Float.parseFloat(df.format(num)));
        holder.tvFenshu.setText(bean.getTscore());
        holder.tvContent.setText(bean.getTintro());
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_inflatelsvitem_lsjs_pic)
        ImageView imgPic;
        @BindView(R.id.tv_inflatelsvitem_lsjs_name)
        TextView tvName;
        @BindView(R.id.tv_inflatelsvitem_lsjs_dis)
        TextView tvDis;
        @BindView(R.id.tv_inflatelsvitem_lsjs_xingji)
        MyRatingBar tvXingji;
        @BindView(R.id.tv_inflatelsvitem_lsjs_fenshu)
        TextView tvFenshu;
        @BindView(R.id.tv_inflatelsvitem_lsjs_ckpf)
        TextView tvCkpf;
        @BindView(R.id.tv_inflatelsvitem_lsjs_content)
        TextView tvContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

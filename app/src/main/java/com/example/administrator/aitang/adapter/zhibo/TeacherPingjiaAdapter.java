package com.example.administrator.aitang.adapter.zhibo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.MyBaseAdapter;
import com.example.administrator.aitang.bean.zhibo.TeacherPingjia;
import com.example.administrator.aitang.utils.basic.DateUtil;
import com.example.administrator.aitang.views.MyRatingBar;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/27.
 */

public class TeacherPingjiaAdapter extends MyBaseAdapter<TeacherPingjia.DataBean.CommentBean> {
    public TeacherPingjiaAdapter(Context context, List<TeacherPingjia.DataBean.CommentBean> datasource) {
        super(context, datasource);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.inflate_lsvitem_laoshipingjia, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        TeacherPingjia.DataBean.CommentBean bean = getItem(i);
        holder.tvName.setText(bean.getUname());
        holder.tvTime.setText(DateUtil.toString(Long.parseLong(bean.getCtime()), "yyyy-MM-dd HH:mm"));
        holder.tvContent.setText(bean.getCcontent());
        float num = (float) Float.parseFloat(bean.getCscore()) / 20;
        DecimalFormat df = new DecimalFormat("0.0");
        holder.mrbPingjia.setStar(Float.parseFloat(df.format(num)));
        holder.tvFenshu.setText(bean.getCscore());
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_inflate_lsvitem_laoshipingjia_name)
        TextView tvName;
        @BindView(R.id.tv_inflate_lsvitem_laoshipingjia_time)
        TextView tvTime;
        @BindView(R.id.tv_inflate_lsvitem_laoshipingjia_content)
        TextView tvContent;
        @BindView(R.id.mrb_inflate_lsvitem_laoshipingjia_pingjia)
        MyRatingBar mrbPingjia;
        @BindView(R.id.tv_inflate_lsvitem_laoshipingjia_fenshu)
        TextView tvFenshu;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

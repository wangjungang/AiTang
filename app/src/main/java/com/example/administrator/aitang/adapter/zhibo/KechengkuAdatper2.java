package com.example.administrator.aitang.adapter.zhibo;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.MyBaseAdapter;
import com.example.administrator.aitang.bean.zhibo.KechengkuBean;
import com.example.administrator.aitang.utils.basic.DateUtil;
import com.example.administrator.aitang.utils.glide.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/31.
 */

public class KechengkuAdatper2 extends MyBaseAdapter<KechengkuBean.DataBean> {
    public KechengkuAdatper2(Context context) {
        super(context);
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.inflate_lsvitem_inflate_zhibofrg, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        KechengkuBean.DataBean bean = getItem(position);
        holder.tvBanji.setText(bean.getMtype());
        holder.tvKechengzhaiyao.setText("课程摘要：" + bean.getClassX().getC_name());
        String startTime = DateUtil.toString(Long.parseLong(bean.getClassX().getC_start_time()), "yyyy-MM-dd");
        String endTime = DateUtil.toString(Long.parseLong(bean.getClassX().getC_end_time()), "yyyy-MM-dd");
        holder.tvTime.setText(startTime + "-" + endTime);
        List<KechengkuBean.DataBean.TeacherBean> teacher = bean.getTeacher();
        ImageView[] iv = {holder.imgPic1, holder.imgPic2, holder.imgPic3};
        TextView[] tv = {holder.tvName1, holder.tvName2, holder.tvName3};
        for (int k = 0; k < iv.length; k++) {
            iv[k].setVisibility(View.INVISIBLE);
            tv[k].setVisibility(View.INVISIBLE);
        }
        for (int j = 0; j < teacher.size(); j++) {
            KechengkuBean.DataBean.TeacherBean d = teacher.get(j);
            Log.d("TAG" + position, d.toString());
            GlideUtils.setCircleAvatar(d.getTpic(), iv[j], R.drawable.touxiang_image_zhiboshouye, R.drawable.touxiang_image_zhiboshouye);
//            GlideUtils.setCircleAvatar("http://touxiang.vipyl.com/attached/image/20130426/20130426160342734273.jpg", iv[j], R.drawable.touxiang_image_zhiboshouye, R.drawable.touxiang_image_zhiboshouye);
            iv[j].setVisibility(View.VISIBLE);
            tv[j].setText(d.getTname());
            tv[j].setVisibility(View.VISIBLE);
            holder.rlPrice.setVisibility(View.INVISIBLE);
        }
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.tv_inflate_lsvitem_zhibofrg_banji)
        TextView tvBanji;
        @BindView(R.id.tv_inflate_lsvitem_zhibofrg_kechengzhaiyao)
        TextView tvKechengzhaiyao;
        @BindView(R.id.tv_inflate_lsvitem_zhibofrg_time)
        TextView tvTime;
        @BindView(R.id.img_inflate_lsvitem_zhibofrg_pic1)
        ImageView imgPic1;
        @BindView(R.id.tv_inflate_lsvitem_zhibofrg_name1)
        TextView tvName1;
        @BindView(R.id.img_inflate_lsvitem_zhibofrg_pic2)
        ImageView imgPic2;
        @BindView(R.id.tv_inflate_lsvitem_zhibofrg_name2)
        TextView tvName2;
        @BindView(R.id.img_inflate_lsvitem_zhibofrg_pic3)
        ImageView imgPic3;
        @BindView(R.id.tv_inflate_lsvitem_zhibofrg_name3)
        TextView tvName3;
        @BindView(R.id.rl_inflate_lsvitem_zhibofrg_price)
        RelativeLayout rlPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

package com.example.administrator.aitang.adapter.zhibo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.MyRecyclerBaseAdapter;
import com.example.administrator.aitang.bean.zhibo.ClassBean;
import com.example.administrator.aitang.utils.basic.DateUtil;
import com.example.administrator.aitang.utils.glide.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/31.
 */

public class KechengkuAdatper extends MyRecyclerBaseAdapter<ClassBean.DataBean> {
    public KechengkuAdatper(Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        ClassBean.DataBean bean = getItem(position);
        ViewHolder holder = (ViewHolder) holder1;
        holder.tvBanji.setText("1");
        holder.tvKechengzhaiyao.setText("课程摘要：" + bean.getC_name());
        String startTime = DateUtil.toString(Long.parseLong(bean.getC_start_time()), "yyyy-MM-dd");
        String endTime = DateUtil.toString(Long.parseLong(bean.getC_end_time()), "yyyy-MM-dd");
        holder.tvTime.setText(startTime + "-" + endTime);
        List<ClassBean.DataBean.TeacherBean> teacher = bean.getTeacher();
        ImageView[] iv = {holder.imgPic1, holder.imgPic2, holder.imgPic3};
        TextView[] tv = {holder.tvName1, holder.tvName2, holder.tvName3};
        for (int k = 0; k < iv.length; k++) {
            iv[k].setVisibility(View.INVISIBLE);
            tv[k].setVisibility(View.INVISIBLE);
        }
        for (int j = 0; j < teacher.size(); j++) {
            ClassBean.DataBean.TeacherBean d = teacher.get(j);
            Log.d("TAG" + position, d.toString());
            GlideUtils.setCircleAvatar(d.getTpic(), iv[j], R.drawable.touxiang_image_zhiboshouye, R.drawable.touxiang_image_zhiboshouye);
//            GlideUtils.setCircleAvatar("http://touxiang.vipyl.com/attached/image/20130426/20130426160342734273.jpg", iv[j], R.drawable.touxiang_image_zhiboshouye, R.drawable.touxiang_image_zhiboshouye);
            iv[j].setVisibility(View.VISIBLE);
            tv[j].setText(d.getTname());
            tv[j].setVisibility(View.VISIBLE);
            holder.rlPrice.setVisibility(View.INVISIBLE);
        }
        holder.root.setTag(position);
    }

    @Override
    protected View getView() {
        return inflater.inflate(R.layout.inflate_lsvitem_inflate_zhibofrg, null);
    }

    @Override
    protected BaseViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }


    static class ViewHolder extends BaseViewHolder {
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
        View root;

        ViewHolder(View view) {
            super(view);
            this.root = view;
            ButterKnife.bind(this, view);
        }
    }
}

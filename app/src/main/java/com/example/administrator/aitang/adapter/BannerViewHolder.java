package com.example.administrator.aitang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;


import com.example.administrator.aitang.R;
import com.example.administrator.aitang.bean.lianxi.IndexBean;
import com.example.administrator.aitang.bean.lianxi.IndexBean2;
import com.example.administrator.aitang.bean.lianxi.ShouYeBean;
import com.example.administrator.aitang.utils.glide.GlideUtils;
import com.zhouwei.mzbanner.holder.MZViewHolder;

/**
 * Created by Administrator on 2017/8/30.
 */

public class BannerViewHolder implements MZViewHolder<ShouYeBean.DataBean.SlideBean> {
    private ImageView mImageView;

    @Override
    public View createView(Context context) {
        // 返回页面布局
        View view = LayoutInflater.from(context).inflate(R.layout.videoplay_fragment_banneritem, null);
        mImageView = (ImageView) view.findViewById(R.id.img_videoplay_frg_banneritem);
        return view;
    }

    @Override
    public void onBind(Context context, int position, ShouYeBean.DataBean.SlideBean data) {
        // 数据绑
        GlideUtils.setAvatar(data.getPosterimg(), mImageView);
    }
}

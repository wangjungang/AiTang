package com.example.administrator.aitang.fragment.zhibo;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.administrator.aitang.R;
import com.example.administrator.aitang.fragment.MyBaseFragment;
import com.example.administrator.aitang.utils.basic.DensityUtils;

import butterknife.BindView;

public class KechengJieshaoFragemtn extends MyBaseFragment {

    String pic;
    @BindView(R.id.img_kechengjieshaoFrg)
    ImageView img;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();//从activity传过来的Bundle
        if (bundle != null) {
            pic = bundle.getString("pic");
        }
        return inflater.inflate(R.layout.fragment_kecheng_jieshao_fragemtn, container, false);
    }

    @Override
    public void init() {
        Glide.with(getActivity()).load(pic).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                int bwidth = bitmap.getWidth();
                int mheight = bitmap.getHeight();
                int width = DensityUtils.getDisplay(getActivity()).getWidth();
                int height = width * mheight / bwidth;
                ViewGroup.LayoutParams params = img.getLayoutParams();
                params.height = height;
                img.setImageBitmap(bitmap);
                img.setLayoutParams(params);
            }
        });
    }

}

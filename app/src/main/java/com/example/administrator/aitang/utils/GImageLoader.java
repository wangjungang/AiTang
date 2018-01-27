package com.example.administrator.aitang.utils;

import android.content.Context;
import android.widget.ImageView;

import com.example.administrator.aitang.utils.glide.GlideUtils;
import com.youth.banner.loader.ImageLoader;


/**
 * Created by Eric on 2016/11/27.
 */

public class GImageLoader extends ImageLoader {


    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        GlideUtils.setAvatar((String) path, imageView);
    }
}

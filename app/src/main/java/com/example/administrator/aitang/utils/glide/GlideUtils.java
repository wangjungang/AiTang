package com.example.administrator.aitang.utils.glide;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.example.administrator.aitang.R;

import java.util.concurrent.ExecutionException;

import static com.example.administrator.aitang.app.Myapp.context;

/**
 * Created by Administrator on 2017/7/11.
 */

public class GlideUtils {
    public static void setAvatar(String avatar, ImageView iv, int placeholder, int error) {
        setAvatar(avatar, iv, placeholder, error, 0, 0);
    }

    public static void setAvatar(String avatar, ImageView iv) {
        setAvatar(avatar, iv, 0, 0, 0, 0);
    }

    public static void setAvatar(String avatar, ImageView iv, int placeholder, int error, int width, int height) {
        if (TextUtils.isEmpty(avatar)) {
            if (error == 0)
                iv.setImageResource(R.drawable.logo_img_qidongye);
            else
                iv.setImageResource(error);
        } else {
            DrawableTypeRequest<String> pic = Glide.with(context)
                    .load(avatar);
            if (placeholder != 0)
                pic.placeholder(placeholder);
            if (error != 0)
                pic.error(error);
            if (width != 0 && height != 0) {
                pic.override(width, height);
            }
            pic.fitCenter()
//                    .crossFade(1000)// 可设置时长，默认“300ms” 淡入
                    .dontAnimate()
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            Log.d("TAG", "图片加载出错了：" + "错误原因: " + e + "图片路径: " + model);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .skipMemoryCache(true)//跳过内存缓存，默认是有内存缓存的
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)//磁盘缓存有关，有四种模式：DiskCacheStrategy.NONE:什么都不缓存； DiskCacheStrategy.SOURCE:仅缓存原图(全分辨率的图片)； DiskCacheStrategy.RESULT:仅缓存最终的图片,即修改了尺寸或者转换后的图片； DiskCacheStrategy.ALL:缓存所有版本的图片, 默认模式
                    .into(iv);


        }
    }

    //设置圆角图片
    public static void setRoundAvatar(String avatar, ImageView iv, int count) {
        if (TextUtils.isEmpty(avatar)) {
            iv.setImageResource(R.drawable.logo_img_qidongye);
        } else {
            Glide.with(context).load(avatar).transform(new GlideLoadPicUtils.GlideRoundTransform(context, count))
//                    .placeholder(R.drawable.loading)//占位符 也就是加载中的图片，可放个gif
                    .error(0)//失败图片
                    .dontAnimate()
//                    .crossFade(1000)// 可设置时长，默认“300ms”
//                    .override(80, 80)//设置最终显示的图片像素为80*80,注意:这个是像素,而不是控件的宽高
                    .skipMemoryCache(true)//跳过内存缓存，默认是有内存缓存的
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)//磁盘缓存有关，有四种模式：DiskCacheStrategy.NONE:什么都不缓存； DiskCacheStrategy.SOURCE:仅缓存原图(全分辨率的图片)； DiskCacheStrategy.RESULT:仅缓存最终的图片,即修改了尺寸或者转换后的图片； DiskCacheStrategy.ALL:缓存所有版本的图片, 默认模式
                    .thumbnail(0.1f)//缩略图
                    .into(iv);
        }
    }

    //设置圆形图片
    public static void setCircleAvatar(String avatar, ImageView iv) {
        setCircleAvatar(avatar, iv, 0, 0);
    }

    public static void setCircleAvatar(String avatar, ImageView iv, int error, int placeholder) {
        if (TextUtils.isEmpty(avatar)) {
            iv.setImageResource(R.drawable.logo_img_qidongye);
        } else {
            DrawableRequestBuilder<String> a = Glide.with(context).load(avatar).transform(new GlideLoadPicUtils.GlideCircleTransform(context));
            if (placeholder != 0)
                a.placeholder(placeholder);
            if (error != 0)
                a.error(error);
            a.dontAnimate()
//                    .crossFade(1000)// 可设置时长，默认“300ms”
//                    .override(80, 80)//设置最终显示的图片像素为80*80,注意:这个是像素,而不是控件的宽高
                    .skipMemoryCache(true)//跳过内存缓存，默认是有内存缓存的
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)//磁盘缓存有关，有四种模式：DiskCacheStrategy.NONE:什么都不缓存； DiskCacheStrategy.SOURCE:仅缓存原图(全分辨率的图片)； DiskCacheStrategy.RESULT:仅缓存最终的图片,即修改了尺寸或者转换后的图片； DiskCacheStrategy.ALL:缓存所有版本的图片, 默认模式
                    .thumbnail(0.1f)//缩略图
                    .into(iv);
        }
    }

    //设置高斯图片
    public static void setBlurAvatar(String avatar, ImageView iv) {
        if (TextUtils.isEmpty(avatar)) {
            iv.setImageResource(0);
        } else {
            Glide.with(context).load(avatar).bitmapTransform(new GlideLoadPicUtils.BlurTransformation(context))
//                    .placeholder(R.drawable.loading)//占位符 也就是加载中的图片，可放个gif
                    .error(0)//失败图片
                    .dontAnimate()
//                    .crossFade(1000)// 可设置时长，默认“300ms”
//                    .override(80, 80)//设置最终显示的图片像素为80*80,注意:这个是像素,而不是控件的宽高
                    .skipMemoryCache(true)//跳过内存缓存，默认是有内存缓存的
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)//磁盘缓存有关，有四种模式：DiskCacheStrategy.NONE:什么都不缓存； DiskCacheStrategy.SOURCE:仅缓存原图(全分辨率的图片)； DiskCacheStrategy.RESULT:仅缓存最终的图片,即修改了尺寸或者转换后的图片； DiskCacheStrategy.ALL:缓存所有版本的图片, 默认模式
                    .thumbnail(0.1f)//缩略图
                    .into(iv);
        }
    }

    public static void setBlurAvatar(String avatar, ImageView iv, int radius) {
        if (TextUtils.isEmpty(avatar)) {
            iv.setImageResource(0);
        } else {
            Glide.with(context).load(avatar).bitmapTransform(new GlideLoadPicUtils.BlurTransformation(context, radius))
//                    .placeholder(R.drawable.loading)//占位符 也就是加载中的图片，可放个gif
                    .error(0)//失败图片
                    .dontAnimate()
//                    .crossFade(1000)// 可设置时长，默认“300ms”
//                    .override(80, 80)//设置最终显示的图片像素为80*80,注意:这个是像素,而不是控件的宽高
                    .skipMemoryCache(true)//跳过内存缓存，默认是有内存缓存的
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)//磁盘缓存有关，有四种模式：DiskCacheStrategy.NONE:什么都不缓存； DiskCacheStrategy.SOURCE:仅缓存原图(全分辨率的图片)； DiskCacheStrategy.RESULT:仅缓存最终的图片,即修改了尺寸或者转换后的图片； DiskCacheStrategy.ALL:缓存所有版本的图片, 默认模式
                    .thumbnail(0.1f)//缩略图
                    .into(iv);
        }
    }

    //图片做灰度处理
    public static void setImgGrayProcessing(String avatar, ImageView iv) {
        if (TextUtils.isEmpty(avatar)) {
            iv.setImageResource(0);
        } else {
            Glide.with(context).load(avatar).bitmapTransform(new GlideLoadPicUtils.GrayscaleTransformation(context))
//                    .placeholder(R.drawable.loading)//占位符 也就是加载中的图片，可放个gif
                    .error(0)//失败图片
                    .dontAnimate()
//                    .crossFade(1000)// 可设置时长，默认“300ms”
//                    .override(80, 80)//设置最终显示的图片像素为80*80,注意:这个是像素,而不是控件的宽高
                    .skipMemoryCache(true)//跳过内存缓存，默认是有内存缓存的
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)//磁盘缓存有关，有四种模式：DiskCacheStrategy.NONE:什么都不缓存； DiskCacheStrategy.SOURCE:仅缓存原图(全分辨率的图片)； DiskCacheStrategy.RESULT:仅缓存最终的图片,即修改了尺寸或者转换后的图片； DiskCacheStrategy.ALL:缓存所有版本的图片, 默认模式
                    .thumbnail(0.1f)//缩略图
                    .into(iv);
        }
    }

    public static Bitmap mBitmap;

    public static Drawable getDrawable(String avatar) {
        if (TextUtils.isEmpty(avatar)) {
            return null;
        } else {
            Glide.with(context).load(avatar)
                    .asBitmap()
                    .dontAnimate()
                    .error(0)//失败图片
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                            mBitmap = bitmap;
                        }
                    });
        }
        return (Drawable) new BitmapDrawable(mBitmap);
    }

    public static Bitmap getmBitmap(String avatar) {
        if (TextUtils.isEmpty(avatar)) {
            return null;
        } else {

            try {
                return Glide.with(context).load(avatar).asBitmap().centerCrop().into(500, 500).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
        return null;
    }
//    Glide可以加载视频的缩略图：
//
//    String filePath = "/storage/emulated/0/Pictures/example_video.mp4";
//
//Glide
//        .with( context )
//            .load(Uri.fromFile( new File( filePath ) ) )
//            .into( imageViewGifAsBitmap );



    public static void loadImg(Uri uri,ImageView imageView){
        Glide.with( context).load( uri).into(imageView );
    }
    public static void loadImg(String path,ImageView imageView){
        Glide.with( context).load( path).into(imageView );
    }
}

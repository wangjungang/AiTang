package com.example.administrator.aitang.utils.glide;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.renderscript.RSRuntimeException;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by Administrator on 2017/6/29.
 * 使用glide加载网络图片成圆形或圆角图片
 */

public class GlideLoadPicUtils {
    /**
     * 下载的图片转圆形
     * 使用方法：
     * Glide.with(this).load("https://www.baidu.com/img/bdlogo.png").transform(new GlideCircleTransform(context)).into(imageView);
     */
    public static class GlideCircleTransform extends BitmapTransformation {
        public GlideCircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }

    /**
     * 下载的图片转圆角
     * 使用方法：
     * Glide.with(this).load("https://www.baidu.com/img/bdlogo.png").transform(new GlideRoundTransform(context, 10)).into(imageView);
     */
    public static class GlideRoundTransform extends BitmapTransformation {
        private static float radius = 0f;

        public GlideRoundTransform(Context context) {
            this(context, 4);
        }

        public GlideRoundTransform(Context context, int dp) {
            super(context);
            this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return roundCrop(pool, toTransform);
        }

        private static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName() + Math.round(radius);
        }
    }

    /**
     * 下载的图片做高斯处理
     * 使用方法：
     * Glide.with(this).load("https://www.baidu.com/img/bdlogo.png").transform(new GlideRoundTransform(context, 10)).into(imageView);
     */
    public static class BlurTransformation implements Transformation<Bitmap> {

        private static int MAX_RADIUS = 25;
        private static int DEFAULT_DOWN_SAMPLING = 1;

        private Context mContext;
        private BitmapPool mBitmapPool;

        private int mRadius;
        private int mSampling;

        public BlurTransformation(Context context) {
            this(context, Glide.get(context).getBitmapPool(), MAX_RADIUS, DEFAULT_DOWN_SAMPLING);
        }

        public BlurTransformation(Context context, BitmapPool pool) {
            this(context, pool, MAX_RADIUS, DEFAULT_DOWN_SAMPLING);
        }

        public BlurTransformation(Context context, BitmapPool pool, int radius) {
            this(context, pool, radius, DEFAULT_DOWN_SAMPLING);
        }

        public BlurTransformation(Context context, int radius) {
            this(context, Glide.get(context).getBitmapPool(), radius, DEFAULT_DOWN_SAMPLING);
        }

        public BlurTransformation(Context context, BitmapPool pool, int radius, int sampling) {
            mContext = context;
            mBitmapPool = pool;
            mRadius = radius;
            mSampling = sampling;
        }

        /**
         * @param context
         * @param radius   设置模糊度(在0.0到25.0之间)
         * @param sampling 图片缩放比例,默认“1”
         */
        public BlurTransformation(Context context, int radius, int sampling) {
            mContext = context;
            mBitmapPool = Glide.get(context).getBitmapPool();
            mRadius = radius;
            mSampling = sampling;
        }

        @Override
        public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
            Bitmap source = resource.get();

            int width = source.getWidth();
            int height = source.getHeight();
            int scaledWidth = width / mSampling;
            int scaledHeight = height / mSampling;

            Bitmap bitmap = mBitmapPool.get(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);
            if (bitmap == null) {
                bitmap = Bitmap.createBitmap(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(bitmap);
            canvas.scale(1 / (float) mSampling, 1 / (float) mSampling);
            Paint paint = new Paint();
            paint.setFlags(Paint.FILTER_BITMAP_FLAG);
            canvas.drawBitmap(source, 0, 0, paint);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                try {
                    bitmap = RSBlur.blur(mContext, bitmap, mRadius);
                } catch (RSRuntimeException e) {
                    bitmap = FastBlur.blur(bitmap, mRadius, true);
                }
            } else {
                bitmap = FastBlur.blur(bitmap, mRadius, true);
            }

            return BitmapResource.obtain(bitmap, mBitmapPool);
        }

        @Override
        public String getId() {
            return "BlurTransformation(radius=" + mRadius + ", sampling=" + mSampling + ")";
        }
    }

    /**
     * 下载的图片做旋转处理
     * 使用方法：
     * Glide.with(this).load("https://www.baidu.com/img/bdlogo.png").transform(new GlideRoundTransform(context, 10)).into(imageView);
     */
    public static class RotateTransformation extends BitmapTransformation {

        private float rotateRotationAngle = 0f;

        public RotateTransformation(Context context, float rotateRotationAngle) {
            super(context);

            this.rotateRotationAngle = rotateRotationAngle;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            Matrix matrix = new Matrix();

            matrix.postRotate(rotateRotationAngle);

            return Bitmap.createBitmap(toTransform, 0, 0, toTransform.getWidth(), toTransform.getHeight(), matrix, true);
        }

        @Override
        public String getId() {
            return "rotate" + rotateRotationAngle;
        }
    }

    /**
     * /**
     * 下载的图片做灰度处理
     * 使用方法：
     * Glide.with(this).load("https://www.baidu.com/img/bdlogo.png").transform(new GlideRoundTransform(context, 10)).into(imageView);
     */
    public static class GrayscaleTransformation implements Transformation<Bitmap> {

        private BitmapPool mBitmapPool;

        public GrayscaleTransformation(Context context) {
            this(Glide.get(context).getBitmapPool());
        }

        public GrayscaleTransformation(BitmapPool pool) {
            mBitmapPool = pool;
        }

        @Override
        public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
            Bitmap source = resource.get();

            int width = source.getWidth();
            int height = source.getHeight();

            Bitmap.Config config =
                    source.getConfig() != null ? source.getConfig() : Bitmap.Config.ARGB_8888;
            Bitmap bitmap = mBitmapPool.get(width, height, config);
            if (bitmap == null) {
                bitmap = Bitmap.createBitmap(width, height, config);
            }

            Canvas canvas = new Canvas(bitmap);
            ColorMatrix saturation = new ColorMatrix();
            saturation.setSaturation(0f);
            Paint paint = new Paint();
            paint.setColorFilter(new ColorMatrixColorFilter(saturation));
            canvas.drawBitmap(source, 0, 0, paint);

            return BitmapResource.obtain(bitmap, mBitmapPool);
        }

        @Override
        public String getId() {
            return "GrayscaleTransformation()";
        }
    }
}

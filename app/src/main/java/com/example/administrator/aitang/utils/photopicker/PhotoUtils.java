package com.example.administrator.aitang.utils.photopicker;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Author : wangzexin
 * Date : 2017/11/28
 * Describe :
 */

public class PhotoUtils {
    public static String photoPath = "";

    /***********************************uri获取图片相关******************************************/
    /**
     * 从图片uri获取path
     *
     * @param context 上下文
     * @param uri     图片uri
     */
    public static String getPathFromUri(Context context, Uri uri) {
        String outPath = "";
        Cursor cursor = context.getContentResolver()
                .query(uri, null, null, null, null);
        if (cursor == null) {
            // miui 2.3 有可能为null
            return uri.getPath();
        } else {
            if (uri.toString().contains("content://com.android.providers.media.documents/document/image")) { // htc 某些手机
                // 获取图片地址
                String _id = null;
                String uridecode = uri.decode(uri.toString());
                int id_index = uridecode.lastIndexOf(":");
                _id = uridecode.substring(id_index + 1);
                Cursor mcursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, " _id = " + _id,
                        null, null);
                mcursor.moveToFirst();
                int column_index = mcursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                outPath = mcursor.getString(column_index);
                if (!mcursor.isClosed()) {
                    mcursor.close();
                }
                if (!cursor.isClosed()) {
                    cursor.close();
                }
                return outPath;
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    if (DocumentsContract.isDocumentUri(context, uri)) {
                        String docId = DocumentsContract.getDocumentId(uri);
                        if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                            //Log.d(TAG, uri.toString());
                            String id = docId.split(":")[1];
                            String selection = MediaStore.Images.Media._ID + "=" + id;
                            outPath = getImagePath(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                        } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                            //Log.d(TAG, uri.toString());
                            Uri contentUri = ContentUris.withAppendedId(
                                    Uri.parse("content://downloads/public_downloads"),
                                    Long.valueOf(docId));
                            outPath = getImagePath(context, contentUri, null);
                        }
                        return outPath;
                    }
                }
                if ("content".equalsIgnoreCase(uri.getScheme())) {
                    String auth = uri.getAuthority();
                    if (auth.equals("media")) {
                        outPath = getImagePath(context, uri, null);
                    } else if (auth.equals("com.example.administrator.aitang.fileprovider")) {
                        //参看file_paths_public配置
                        outPath = Environment.getExternalStorageDirectory() + "/Pictures/" + uri.getLastPathSegment();
                    }
                    return outPath;
                }
            }
            return outPath;
        }

    }


    /**
     * 从uri中取查询path路径
     *
     * @param context   上下文
     * @param uri
     * @param selection
     */
    private static String getImagePath(Context context, Uri uri, String selection) {
        String path = null;
        Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }


    /**
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     *
     * @param path
     * @return
     */
    public static String imageToBase64(String path) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try {

            InputStream in = new FileInputStream(path);

            data = new byte[in.available()];

            in.read(data);

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 返回Base64编码过的字节数组字符串
        return Base64.encodeToString(data, Base64.DEFAULT);

    }


/***********************************图片存储相关******************************************/

    /**
     * 将view转换为bitmap
     *
     * @param v
     * @return
     */
    public static Bitmap loadBitmapFromView(View v) {
        if (v == null) {
            return null;
        }
        v.setDrawingCacheEnabled(true);
        Bitmap screenshot;
//        v.measure(View.MeasureSpec.makeMeasureSpec(v.getWidth(),
//                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(
//                v.getHeight(), View.MeasureSpec.UNSPECIFIED));
        v.measure(View.MeasureSpec.makeMeasureSpec(v.getWidth(),
                View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(
                v.getHeight(), View.MeasureSpec.EXACTLY));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        v.buildDrawingCache();
        screenshot = v.getDrawingCache();
        if (screenshot == null) {
            v.setDrawingCacheEnabled(true);
//            screenshot = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
//                    Bitmap.Config.ARGB_4444);

            screenshot = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                    Bitmap.Config.ARGB_4444);
            Canvas c = new Canvas(screenshot);
            c.translate(-v.getScrollX(), -v.getScrollY());
            v.draw(c);
            return screenshot;
        }
        return screenshot;
    }


    /**
     * 保存图片到本地
     */
    public static boolean saveBitmap(Bitmap bm, String picName) {
        isHaveSDCard();
        File filePath;
        if (isHaveSDCard()) {
            filePath = Environment.getExternalStorageDirectory();
        } else {
            filePath = Environment.getDataDirectory();
        }
        File file = new File(filePath.getPath() + "/AiTang/img/");
        if (!file.isDirectory()) {
            file.delete();
            file.mkdirs();
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        boolean isOk = writeBitmap(filePath.getPath() + "/AiTang/img/", picName, bm);
        if (isOk) {
            return true;
        }
        return false;
    }

    /**
     * 保存图片
     *
     * @param path
     * @param name
     * @param bitmap
     */
    public static boolean writeBitmap(String path, String name, Bitmap bitmap) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        File _file = new File(path + name);
        if (_file.exists()) {
            _file.delete();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(_file);
            if (name != null && !"".equals(name)) {
                int index = name.lastIndexOf(".");
                if (index != -1 && (index + 1) < name.length()) {
                    String extension = name.substring(index + 1).toLowerCase();
                    if ("png".equals(extension)) {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    } else if ("jpg".equals(extension)
                            || "jpeg".equals(extension)) {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, fos);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public static boolean isHaveSDCard() {
        String SDState = android.os.Environment.getExternalStorageState();
        if (SDState.equals(android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    public static String getPhotoFolderPath() {
        File filePath;
        if (isHaveSDCard()) {
            filePath = Environment.getExternalStorageDirectory();
        } else {
            filePath = Environment.getDataDirectory();
        }
        return filePath.getPath() + "/AiTang/img/";
    }
}

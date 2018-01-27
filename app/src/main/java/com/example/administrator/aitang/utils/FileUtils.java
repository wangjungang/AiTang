package com.example.administrator.aitang.utils;

import android.os.Environment;

import java.io.File;

/**
 * @author wangzexin
 * @date 2018/1/7
 * @describe
 */

public class FileUtils {
    public static String sdcard = Environment.getExternalStorageDirectory().getPath() + "/AITANG/download";


    //判断文件是否存在
    public static boolean fileIsExists(String strFile) {
        String name = sdcard + "/" + strFile;
        try {
            File f = new File(name);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }


    /**
     * 删除文件
     * @param file
     */
    public static void deleteFile(File file) {
        if (file.isFile()) {
            deleteFileSafely(file);
            return;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                deleteFileSafely(file);
                return;
            }
            for (int i = 0; i < childFiles.length; i++) {
                deleteFile(childFiles[i]);
            }
            deleteFileSafely(file);
        }
    }



    /**
     * 安全删除文件.
     * @param file
     * @return
     */
    public static boolean deleteFileSafely(File file) {
        if (file != null) {
            String tmpPath = file.getParent() + File.separator + System.currentTimeMillis();
            File tmp = new File(tmpPath);
            file.renameTo(tmp);
            return tmp.delete();
        }
        return false;
    }
}

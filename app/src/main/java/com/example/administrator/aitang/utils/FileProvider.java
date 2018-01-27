package com.example.administrator.aitang.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;


public class FileProvider {
    private DBHelper dbHelper;

    public FileProvider(Context context, int version) {
        dbHelper = new DBHelper(context, version);
    }
    //=======================================

    /**
     * 向表中写入数据 ContentValues 对象封装key/value,key为表中的列
     */
    public long insert(String table, ContentValues values) {
        // 1.打开或创建数据库
        SQLiteDatabase sdb = dbHelper.getWritableDatabase();
        // 2.将数据写入到数据库
        long id = sdb.insert(table, null, values);
        // 3.释放资源
        sdb.close();
        return id;
    }

    public int delete(String table, String whereClause, String[] whereArgs) {
        SQLiteDatabase sdb =
                dbHelper.getWritableDatabase();
        //String sql="delete from notetab where _id=?";
        //sdb.execSQL(sql,new String[]{...});
        //如下方法底层会自动拼接SQL
        int num = sdb.delete(table,
                whereClause, whereArgs);
        sdb.close();
        return num;//返回值表示删除的记录的行
    }

    /**
     * 更新数据
     */
    public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase sdb =
                dbHelper.getWritableDatabase();
        int num = sdb.update(table,
                values, whereClause, whereArgs);
        sdb.close();
        return num;
    }
    //==========================================

    /**
     * 从表中查询数据
     */
    public Cursor query(String sql, String[] selectionArgs) {
        SQLiteDatabase sdb =
                dbHelper.getReadableDatabase();
        return sdb.rawQuery(sql, selectionArgs);
    }
    //=======================================

    private static final String sdcard = Environment.getExternalStorageDirectory().getPath() + "/AITANG/database";
    private static final String name = sdcard+"/filetab.db"; //数据库路径及名称
    /**
     * 工具类
     */
    private class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context, int version) {
            super(context, "filetab.db", null, version);
        }

        /**
         * 创建数据库时执行(只执行一次)
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.e("TAG-table", "onCreate");
            String sql = "create table filetab ("
                    + "_id integer primary key autoincrement,"
                    + "flagKey text not null," //在缓存界面本地数据与下载任务匹配的唯一标识
                    + "downLoadUrl text not null," //视屏下载的链接前缀，
                    + "type integer not null," //下载的类型 1：白板 2：视频
                    + "className text not null," //课程名字
                    + "zhaiyao text not null," //课程摘要
                    + "time text not null," //时间
                    + "photo text not null," //图片网址
                    + "name text not null," //老师的姓名
                    + "dis text not null," //课程描述
                    + "whiteName text not null," //白板名字
                    + "videoName text not null," //视频名字
                    + " state integer not null)"; //状态 1：下载完 2：未下载完 暂时没有用
            db.execSQL(sql);
            Log.e("TAG-table", "table create ok");
        }

        /**
         * 数据库的版本发生变化执行
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("TAG", "onUpgrade");
            String sql = "drop table filetab";
            db.execSQL(sql);
            onCreate(db);
        }
    }

}


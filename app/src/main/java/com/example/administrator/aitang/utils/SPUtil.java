package com.example.administrator.aitang.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Base64;

import com.example.administrator.aitang.constant.MyConstant;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SPUtil {
    private static String imAccount;//im account
    private static String imToken;//im token

    //设置imAccount
    public void setImAccount(String uid) {
        saveString(MyConstant.IMACCOUNT, uid);
    }

    public String getImAccount() {
        return getString(MyConstant.IMACCOUNT);
    }

    //设置imToken
    public void setImToken(String uid) {
        saveString(MyConstant.IMTOKEN, uid);
    }

    public String getImToken() {
        return getString(MyConstant.IMTOKEN);
    }

    private SharedPreferences sp;

    private static Editor editor;

    public SPUtil(Context context, String name) {

        sp = context.getSharedPreferences(
                name, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);

        editor = sp.edit();

    }

    public SPUtil(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sp.edit();
    }

    //判断当前是否有用户登录
    public boolean isLogin() {
        return getBoolean(MyConstant.ISLOGIN);
    }

    //设置当前已有用户登录
    public void setLogin(boolean flg) {
        saveBoolean(MyConstant.ISLOGIN, flg);
    }

    //判断当前是否是第三方登录
    public boolean isThirdLogin() {
        return getBoolean(MyConstant.ISTHIRDLOGIN);
    }

    //设置当前已有用户登录
    public void setThirdLogin(boolean flg) {
        saveBoolean(MyConstant.ISTHIRDLOGIN, flg);
    }


    //判断是否是第一次运行
    public boolean isFirstRun() {
        return getBoolean(MyConstant.FIRSTRUN);
    }

    //设置第一次登录过期
    public void setFistRun(boolean flag) {
        saveBoolean(MyConstant.FIRSTRUN, flag);
    }


    //设置uid
    public void setUid(String uid) {
        saveString(MyConstant.UID, uid);
    }

    public String getUid() {
        return getString(MyConstant.UID);
    }

    //设置用户昵称
    public void setNike(String nike) {
        saveString(MyConstant.NICK, nike);
    }

    //获取用户昵称
    public String getNike() {
        return getString(MyConstant.NICK);
    }

    //设置token
    public void setToken(String token) {
        saveString(MyConstant.TOKEN, token);
    }

    //获取token
    public String getToken() {
        return getString(MyConstant.TOKEN);
    }

    //设置用户手机号
    public void setPhone(String phone) {
        saveString(MyConstant.PHONE, phone);
    }

    //获取用户手机号
    public String getPhone() {
        return getString(MyConstant.PHONE);
    }


    //设置sessionId
    public void setSessionId(String sessionId) {
        saveString(MyConstant.SESSIONID, sessionId);
    }

    //获取sessionId
    public String getSessionId() {
        return getString(MyConstant.SESSIONID);
    }


    private void saveString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    private String getString(String key) {
        return sp.getString(key, null);
    }

    private void saveBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    private boolean getBoolean(String key) {
        return sp.getBoolean(key, false);
    }

    /**
     * 存放实体类以及任意类型
     *
     * @param context 上下文对象
     * @param key
     * @param obj
     */
    public static void putBean(Context context, String key, Object obj) {
        if (obj instanceof Serializable) {// obj必须实现Serializable接口，否则会出问题
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(obj);
                String string64 = new String(Base64.encode(baos.toByteArray(),
                        0));
                Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
                editor.putString(key, string64);
                editor.commit();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            throw new IllegalArgumentException(
                    "the obj must implement Serializble");
        }

    }

    public static Object getBean(Context context, String key) {
        Object obj = null;
        try {
            String base64 = PreferenceManager.getDefaultSharedPreferences(context).getString(key, "");
            if (base64.equals("")) {
                return null;
            }
            byte[] base64Bytes = Base64.decode(base64.getBytes(), 1);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            obj = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public void clear() {
        editor.clear().commit();
    }

    public void remove(String key) {
        editor.remove(key).commit();
    }
}

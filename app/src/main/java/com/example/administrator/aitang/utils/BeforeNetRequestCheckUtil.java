package com.example.administrator.aitang.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;

import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.utils.basic.NetWorkUtils;

/**
 * Created by Administrator on 2017/8/21.
 */

public class BeforeNetRequestCheckUtil {
    public static boolean isNet(Context context) {
        if (NetWorkUtils.isMobileNetworkOpen(Myapp.context)) {
            return true;
        } else {
            Toast.makeText(context, "无网络", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public static boolean isNetAndLogin(Context context) {
        if (isNet(context)) {
            if (Myapp.spUtil.isLogin()) {
                return true;
            } else {
                Toast.makeText(context, "请登录", Toast.LENGTH_SHORT).show();
//                context.startActivity(new Intent(context, LoginActivity.class));
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isNetAndLogin(Context context, Dialog dialog) {
        if (isNet(context)) {
            if (Myapp.spUtil.isLogin()) {
                return true;
            } else {
                Toast.makeText(context, "请登录", Toast.LENGTH_SHORT).show();
//                context.startActivity(new Intent(context, LoginActivity.class));
                dialog.dismiss();
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isNetAndLogin(Activity context, int code) {
        if (isNet(context)) {
            if (Myapp.spUtil.isLogin()) {
                return true;
            } else {
                Toast.makeText(context, "请登录", Toast.LENGTH_SHORT).show();
//                context.startActivityForResult(new Intent(context, LoginActivity.class), code);
                return false;
            }
        } else {
            return false;
        }
    }


}

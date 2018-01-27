package com.example.administrator.aitang.zhibo.im.business;

import android.app.Activity;
import android.content.Context;

import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.zhibo.DemoCache;
import com.example.administrator.aitang.zhibo.im.config.AuthPreferences;
import com.example.administrator.aitang.zhibo.inject.FlavorDependent;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;

/**
 * 注销帮助类
 * Created by huangjun on 2015/10/8.
 */
public class LogoutHelper {
    public static void logout(Context context, boolean isKickOut) {
        AuthPreferences.saveUserToken("");
        // 清理缓存&注销监听&清除状态
        DemoCache.getImageLoaderKit().clear();
        // flavor do logout
        FlavorDependent.getInstance().onLogout();
        DemoCache.clear();

        NIMClient.getService(AuthService.class).logout();
        Myapp.spUtil.clear();
        // 启动登录
        com.example.administrator.aitang.ui.AccountActivity.start(context, isKickOut);
        ((Activity) context).finish();
    }
}

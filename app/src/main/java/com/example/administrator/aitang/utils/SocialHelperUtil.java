package com.example.administrator.aitang.utils;

import com.example.administrator.aitang.constant.MyConstant;

import net.arvin.socialhelper.SocialHelper;

/**
 * Author : wangzexin
 * Date : 2017/12/7
 * Describe : 三方登陆分享的工具类
 *
 * 目前用到的位置：KSZNLXActivity、CuoTiFenXiActivity、ShenLunActivity、LoginActivity
 *
 * 在activity的onDestory中调用 SocialHelperUtil.getInstance().socialHelper().clear();取消广播绑定
 */

public class SocialHelperUtil {
    private static SocialHelperUtil sInstance = new SocialHelperUtil();

    private SocialHelper socialHelper;

    private SocialHelperUtil() {
        socialHelper = new SocialHelper.Builder()
                .setQqAppId(MyConstant.QQAPPID)
                .setWxAppId(MyConstant.WXAPPID)
                .setWxAppSecret(MyConstant.WXAPPSECRET)
//                .setWbAppId("wbAppId")
//                .setWbRedirectUrl("wbRedirectUrl")
                .build();
    }

    public static SocialHelperUtil getInstance() {
        return sInstance;
    }

    public SocialHelper socialHelper() {
        return socialHelper;
    }
}

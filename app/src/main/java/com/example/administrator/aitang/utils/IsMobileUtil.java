package com.example.administrator.aitang.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/6/21.
 * 判断是否是合法手机号
 */

public class IsMobileUtil {
    /**
     * 使用单例模式创建Md5加密
     */
    public static IsMobileUtil instance;

    public static IsMobileUtil getInstance() {
        if (instance == null) {
            synchronized (IsMobileUtil.class) {
                if (instance == null) {
                    instance = new IsMobileUtil();
                }
            }
        }
        return instance;
    }

    private IsMobileUtil() {
    }
    //对手机号进行验证，判断是否是合法的手机号
    public static boolean isMobileNo(String mobiles) {
        Pattern p = Pattern.compile("^((17[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

}

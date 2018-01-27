package com.example.administrator.aitang.utils;

/**
 * Created by Administrator on 2017/10/25.
 */

public class RequestFailResult {
    /**
     * 网络请求的时候返回的状态码非200
     *
     * @param code
     * @return
     */
    public static String requestFail(String code) {
        switch (code) {
            case "201":
                return "请求成功没有数据";
            case "202":
                return "登陆失败/注册失败/修改信息失败";
            case "203":
                return "不可点击的轮播详情";
            case "204":
                return "题数不够";
            case "205":
                return "图片过多";
            case "206":
                return "图片不合法";
            case "207":
                return "图片大于2M";
            case "001":
                return "缺少必填的参数";
            case "002":
                return "手机号已存在";
            case "003":
                return "请勿频繁发送验证码";
            case "004":
                return "短信发送失败";
            case "005":
                return "手机号不存在";
            case "006":
                return "账号或密码不一致";
            case "007":
                return "请登录";
            case "008":
                return "请完善考试信息";
            case "009":
                return "请勿重复注册";
            case "010":
                return "优惠券领取已满";
            case "011":
                return "数据传入有误";
        }
        return "";
    }
}

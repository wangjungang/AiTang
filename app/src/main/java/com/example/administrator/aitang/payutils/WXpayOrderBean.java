package com.example.administrator.aitang.payutils;

import com.google.gson.annotations.SerializedName;

/**
 * Author : wangzexin
 * Date : 2017/12/10
 * Describe : 微信支付提交订单的数据类
 */

public class WXpayOrderBean {


    /**
     * code : 200
     * data : {"appid":"wx426b3015555a46be","partnerid":"1900009851","prepayid":"wx20171027113738c3b53201840626923710","package":"Sign=WXPay","noncestr":"1AfvvhRClW9UwTnE","timestamp":1509075458,"out_trade_no":"020171027113738","sign":"A791C6CD8941075078D5AD9CF55F9747"}
     */

    private String code;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * appid : wx426b3015555a46be
         * partnerid : 1900009851
         * prepayid : wx20171027113738c3b53201840626923710
         * package : Sign=WXPay
         * noncestr : 1AfvvhRClW9UwTnE
         * timestamp : 1509075458
         * out_trade_no : 020171027113738
         * sign : A791C6CD8941075078D5AD9CF55F9747
         */

        private String appid;
        private String partnerid;
        private String prepayid;
        @SerializedName("package")
        private String packageX;
        private String noncestr;
        private int timestamp;
        private String out_trade_no;
        private String sign;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}

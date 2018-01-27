package com.example.administrator.aitang.bean.lianxi;

/**
 * Author : wangzexin
 * Date : 2017/12/11
 * Describe : 申论提交答案返回的数据类
 */

public class ShenLunDingDanResultBean {


    /**
     * code : 200
     * data : {"orderid":"10","ucid":"0","uid":"52","c_id":"0","ordersn":"83379487","orderprice":"33","couponprice":"0","classcoupon":"0","time":"1512983755","ordertype":"1","ordertotalprice":"33"}
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
         * orderid : 10
         * ucid : 0
         * uid : 52
         * c_id : 0
         * ordersn : 83379487
         * orderprice : 33
         * couponprice : 0
         * classcoupon : 0
         * time : 1512983755
         * ordertype : 1
         * ordertotalprice : 33
         */

        private String orderid;
        private String ucid;
        private String uid;
        private String c_id;
        private String ordersn;
        private String orderprice;
        private String couponprice;
        private String classcoupon;
        private String time;
        private String ordertype;
        private String ordertotalprice;

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getUcid() {
            return ucid;
        }

        public void setUcid(String ucid) {
            this.ucid = ucid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getC_id() {
            return c_id;
        }

        public void setC_id(String c_id) {
            this.c_id = c_id;
        }

        public String getOrdersn() {
            return ordersn;
        }

        public void setOrdersn(String ordersn) {
            this.ordersn = ordersn;
        }

        public String getOrderprice() {
            return orderprice;
        }

        public void setOrderprice(String orderprice) {
            this.orderprice = orderprice;
        }

        public String getCouponprice() {
            return couponprice;
        }

        public void setCouponprice(String couponprice) {
            this.couponprice = couponprice;
        }

        public String getClasscoupon() {
            return classcoupon;
        }

        public void setClasscoupon(String classcoupon) {
            this.classcoupon = classcoupon;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getOrdertype() {
            return ordertype;
        }

        public void setOrdertype(String ordertype) {
            this.ordertype = ordertype;
        }

        public String getOrdertotalprice() {
            return ordertotalprice;
        }

        public void setOrdertotalprice(String ordertotalprice) {
            this.ordertotalprice = ordertotalprice;
        }
    }
}

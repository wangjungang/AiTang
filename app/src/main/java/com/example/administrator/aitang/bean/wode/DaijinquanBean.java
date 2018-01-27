package com.example.administrator.aitang.bean.wode;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/10/27.
 * 我的代金券
 */

public class DaijinquanBean {

    /**
     * code : 200
     * data : {"class":[{"ucid":"1","uid":"15","couponid":"3","ucprice":"100","status":"2","uctype":"1","time":"1508902515","number":"1"},{"ucid":"2","uid":"15","couponid":"2","ucprice":"200","status":"2","uctype":"1","time":"1508902890","number":"1"}],"all":{"price":"2","days":"2"}}
     */

    private String code;
    private DataBean data;

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public DataBean getData() {
        return data;
    }

    public static class DataBean {
        /**
         * class : [{"ucid":"1","uid":"15","couponid":"3","ucprice":"100","status":"2","uctype":"1","time":"1508902515","number":"1"},{"ucid":"2","uid":"15","couponid":"2","ucprice":"200","status":"2","uctype":"1","time":"1508902890","number":"1"}]
         * all : {"price":"2","days":"2"}
         */

        private AllBean all;
        @SerializedName("class")
        private List<ClassBean> classX;

        public void setAll(AllBean all) {
            this.all = all;
        }

        public void setClassX(List<ClassBean> classX) {
            this.classX = classX;
        }

        public AllBean getAll() {
            return all;
        }

        public List<ClassBean> getClassX() {
            return classX;
        }

        public static class AllBean {
            /**
             * price : 2
             * days : 2
             */

            private String price;
            private String days;

            public void setPrice(String price) {
                this.price = price;
            }

            public void setDays(String days) {
                this.days = days;
            }

            public String getPrice() {
                return price;
            }

            public String getDays() {
                return days;
            }
        }

        public static class ClassBean {
            /**
             * ucid : 1
             * uid : 15
             * couponid : 3
             * ucprice : 100
             * status : 2
             * uctype : 1
             * time : 1508902515
             * number : 1
             */

            private String ucid;
            private String uid;
            private String couponid;
            private String ucprice;
            private String status;
            private String uctype;
            private String time;
            private String number;

            public void setUcid(String ucid) {
                this.ucid = ucid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public void setCouponid(String couponid) {
                this.couponid = couponid;
            }

            public void setUcprice(String ucprice) {
                this.ucprice = ucprice;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public void setUctype(String uctype) {
                this.uctype = uctype;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getUcid() {
                return ucid;
            }

            public String getUid() {
                return uid;
            }

            public String getCouponid() {
                return couponid;
            }

            public String getUcprice() {
                return ucprice;
            }

            public String getStatus() {
                return status;
            }

            public String getUctype() {
                return uctype;
            }

            public String getTime() {
                return time;
            }

            public String getNumber() {
                return number;
            }
        }
    }
}

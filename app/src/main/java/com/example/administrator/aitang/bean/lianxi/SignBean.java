package com.example.administrator.aitang.bean.lianxi;

/**
 * Author : wangzexin
 * Date : 2017/12/6
 * Describe : 签到返回的数据
 */

public class SignBean {

    /**
     * code : 200
     * data : {"uid":"52","depottype":"1","depotnum":"1","depottime":"1512546860","time":"1512546860","day":"1","tip":"zxtc"}
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
         * uid : 52
         * depottype : 1
         * depotnum : 1
         * depottime : 1512546860
         * time : 1512546860
         * day : 1
         * tip : zxtc
         */

        private String uid;
        private String depottype;
        private String depotnum;
        private String depottime;
        private String time;
        private String day;
        private String tip;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getDepottype() {
            return depottype;
        }

        public void setDepottype(String depottype) {
            this.depottype = depottype;
        }

        public String getDepotnum() {
            return depotnum;
        }

        public void setDepotnum(String depotnum) {
            this.depotnum = depotnum;
        }

        public String getDepottime() {
            return depottime;
        }

        public void setDepottime(String depottime) {
            this.depottime = depottime;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getTip() {
            return tip;
        }

        public void setTip(String tip) {
            this.tip = tip;
        }
    }
}

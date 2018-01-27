package com.example.administrator.aitang.bean.login;

/**
 * Created by wangzexin on 2017/11/7.
 *
 * {"code":"200","data":{"phone":"15633843852","verifycode":122347,"time":1510040671}}
 */

public class VerificationCodeBean {


    /**
     * code : 200
     * data : {"phone":"15633843852","verifycode":122347,"time":1510040671}
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
         * phone : 15633843852
         * verifycode : 122347
         * time : 1510040671
         */

        private String phone;
        private int verifycode;
        private int time;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getVerifycode() {
            return verifycode;
        }

        public void setVerifycode(int verifycode) {
            this.verifycode = verifycode;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }
    }
}

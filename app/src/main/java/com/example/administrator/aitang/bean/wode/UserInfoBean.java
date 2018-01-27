package com.example.administrator.aitang.bean.wode;

import java.io.Serializable;
import java.util.List;

/**
 * Author : wangzexin
 * Date : 2017/11/17
 * Describe : 用户信息
 */

public class UserInfoBean implements Serializable{
    /**
     * code : 200
     * data : {"uid":"46","uname":"wangwu","upic":"zxtc","uphone":"15633843852","third":"","upwd":"e10adc3949ba59abbe56e057f20f883e","utest_type":[["公务员考试","国考行测"]],"testid":"2","uintro":"zxtc","ustatus":"1","time":"1510207536"}
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

    public static class DataBean  implements Serializable{
        /**
         * uid : 46
         * uname : wangwu
         * upic : zxtc
         * uphone : 15633843852
         * third :
         * upwd : e10adc3949ba59abbe56e057f20f883e
         * utest_type : [["公务员考试","国考行测"]]
         * testid : 2
         * uintro : zxtc
         * ustatus : 1
         * time : 1510207536
         */

        private String uid;
        private String uname;
        private String upic;
        private String uphone;
        private String third;
        private String upwd;
        private String testid;
        private String uintro;
        private String ustatus;
        private String time;
        private List<List<String>> utest_type;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public String getUpic() {
            return upic;
        }

        public void setUpic(String upic) {
            this.upic = upic;
        }

        public String getUphone() {
            return uphone;
        }

        public void setUphone(String uphone) {
            this.uphone = uphone;
        }

        public String getThird() {
            return third;
        }

        public void setThird(String third) {
            this.third = third;
        }

        public String getUpwd() {
            return upwd;
        }

        public void setUpwd(String upwd) {
            this.upwd = upwd;
        }

        public String getTestid() {
            return testid;
        }

        public void setTestid(String testid) {
            this.testid = testid;
        }

        public String getUintro() {
            return uintro;
        }

        public void setUintro(String uintro) {
            this.uintro = uintro;
        }

        public String getUstatus() {
            return ustatus;
        }

        public void setUstatus(String ustatus) {
            this.ustatus = ustatus;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public List<List<String>> getUtest_type() {
            return utest_type;
        }

        public void setUtest_type(List<List<String>> utest_type) {
            this.utest_type = utest_type;
        }
    }
}

package com.example.administrator.aitang.bean.wode;

import java.util.List;

/**
 * Author : wangzexin
 * Date : 2017/11/9
 * Describe :
 */

public class TeacherRankBean {


    /**
     * code : 200
     * data : [{"tid":"1","tname":"妮娜.杜波夫","tpic":"http://localhost/lovet/Public/upload/300599a9044b6ecb.jpg","tphone":"13121222222","tpwd":"202cb962ac59075b964b07152d234b70","tintro":"妮娜·杜波夫，1989年1月9日出生于保加利亚索菲亚，后加入加拿大国籍，加拿大前奥运会体操选手，模特，演员，和业余歌手","tsimple":"最漂亮的女人","tscore":"100","tstatus":"1","ttime":"2017-08-21 14:38:30","time":"1503370347"}]
     */

    private String code;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * tid : 1
         * tname : 妮娜.杜波夫
         * tpic : http://localhost/lovet/Public/upload/300599a9044b6ecb.jpg
         * tphone : 13121222222
         * tpwd : 202cb962ac59075b964b07152d234b70
         * tintro : 妮娜·杜波夫，1989年1月9日出生于保加利亚索菲亚，后加入加拿大国籍，加拿大前奥运会体操选手，模特，演员，和业余歌手
         * tsimple : 最漂亮的女人
         * tscore : 100
         * tstatus : 1
         * ttime : 2017-08-21 14:38:30
         * time : 1503370347
         */

        private String tid;
        private String tname;
        private String tpic;
        private String tphone;
        private String tpwd;
        private String tintro;
        private String tsimple;
        private String tscore;
        private String tstatus;
        private String ttime;
        private String time;

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public String getTpic() {
            return tpic;
        }

        public void setTpic(String tpic) {
            this.tpic = tpic;
        }

        public String getTphone() {
            return tphone;
        }

        public void setTphone(String tphone) {
            this.tphone = tphone;
        }

        public String getTpwd() {
            return tpwd;
        }

        public void setTpwd(String tpwd) {
            this.tpwd = tpwd;
        }

        public String getTintro() {
            return tintro;
        }

        public void setTintro(String tintro) {
            this.tintro = tintro;
        }

        public String getTsimple() {
            return tsimple;
        }

        public void setTsimple(String tsimple) {
            this.tsimple = tsimple;
        }

        public String getTscore() {
            return tscore;
        }

        public void setTscore(String tscore) {
            this.tscore = tscore;
        }

        public String getTstatus() {
            return tstatus;
        }

        public void setTstatus(String tstatus) {
            this.tstatus = tstatus;
        }

        public String getTtime() {
            return ttime;
        }

        public void setTtime(String ttime) {
            this.ttime = ttime;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}

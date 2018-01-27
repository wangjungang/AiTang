package com.example.administrator.aitang.bean.wode;

import java.util.List;

/**
 * Author : wangzexin
 * Date : 2017/11/9
 * Describe : 爱唐播报 数据模型
 */

public class AiTangBroadcastBean {


    /**
     * code : 200
     * data : [{"pdid":"2","posterid":"2","tid":"0","pdurl":"http://localhost/lovet/Public/upload/30059cb4dfe7e1b0.jpg","pdtitle":"123","pdintro":"21321312321123213","time":"0"}]
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
         * pdid : 2
         * posterid : 2
         * tid : 0
         * pdurl : http://localhost/lovet/Public/upload/30059cb4dfe7e1b0.jpg
         * pdtitle : 123
         * pdintro : 21321312321123213
         * time : 0
         */

        private String pdid;
        private String posterid;
        private String tid;
        private String pdurl;
        private String pdtitle;
        private String pdintro;
        private String time;

        public String getPdid() {
            return pdid;
        }

        public void setPdid(String pdid) {
            this.pdid = pdid;
        }

        public String getPosterid() {
            return posterid;
        }

        public void setPosterid(String posterid) {
            this.posterid = posterid;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getPdurl() {
            return pdurl;
        }

        public void setPdurl(String pdurl) {
            this.pdurl = pdurl;
        }

        public String getPdtitle() {
            return pdtitle;
        }

        public void setPdtitle(String pdtitle) {
            this.pdtitle = pdtitle;
        }

        public String getPdintro() {
            return pdintro;
        }

        public void setPdintro(String pdintro) {
            this.pdintro = pdintro;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}

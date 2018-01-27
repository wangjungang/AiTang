package com.example.administrator.aitang.bean.zhibo;

import java.util.List;

/**
 * Created by Administrator on 2017/10/27.
 */

public class TeacherPingjia {

    /**
     * code : 200
     * data : {"comment":[{"cid":"1","tid":"1","uid":"2","cdid":"1","cscore":"80","ccontent":"吸血鬼日记太好看了","ctime":"2147483647","uname":"悟天"},{"cid":"2","tid":"1","uid":"1","cdid":"1","cscore":"60","ccontent":"吸血鬼日记","ctime":"2147483647","uname":null}],"start":[{"numbers":"1"},{"numbers":"1"}],"num":2}
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
         * comment : [{"cid":"1","tid":"1","uid":"2","cdid":"1","cscore":"80","ccontent":"吸血鬼日记太好看了","ctime":"2147483647","uname":"悟天"},{"cid":"2","tid":"1","uid":"1","cdid":"1","cscore":"60","ccontent":"吸血鬼日记","ctime":"2147483647","uname":null}]
         * start : [{"numbers":"1"},{"numbers":"1"}]
         * num : 2
         */

        private String iscomment;
        private int num;
        private List<CommentBean> comment;
        private List<StartBean> start;

        public String getIscomment() {
            return iscomment;
        }

        public void setIscomment(String iscomment) {
            this.iscomment = iscomment;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public void setComment(List<CommentBean> comment) {
            this.comment = comment;
        }

        public void setStart(List<StartBean> start) {
            this.start = start;
        }

        public int getNum() {
            return num;
        }

        public List<CommentBean> getComment() {
            return comment;
        }

        public List<StartBean> getStart() {
            return start;
        }

        public static class CommentBean {
            /**
             * cid : 1
             * tid : 1
             * uid : 2
             * cdid : 1
             * cscore : 80
             * ccontent : 吸血鬼日记太好看了
             * ctime : 2147483647
             * uname : 悟天
             */

            private String cid;
            private String tid;
            private String uid;
            private String cdid;
            private String cscore;
            private String ccontent;
            private String ctime;
            private String uname;

            public void setCid(String cid) {
                this.cid = cid;
            }

            public void setTid(String tid) {
                this.tid = tid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public void setCdid(String cdid) {
                this.cdid = cdid;
            }

            public void setCscore(String cscore) {
                this.cscore = cscore;
            }

            public void setCcontent(String ccontent) {
                this.ccontent = ccontent;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            public void setUname(String uname) {
                this.uname = uname;
            }

            public String getCid() {
                return cid;
            }

            public String getTid() {
                return tid;
            }

            public String getUid() {
                return uid;
            }

            public String getCdid() {
                return cdid;
            }

            public String getCscore() {
                return cscore;
            }

            public String getCcontent() {
                return ccontent;
            }

            public String getCtime() {
                return ctime;
            }

            public String getUname() {
                return uname;
            }
        }

        public static class StartBean {
            /**
             * numbers : 1
             */

            private String numbers;

            public void setNumbers(String numbers) {
                this.numbers = numbers;
            }

            public String getNumbers() {
                return numbers;
            }
        }
    }
}

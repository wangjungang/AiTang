package com.example.administrator.aitang.bean.lianxi;

import java.util.List;

/********************************************************************
 @version: 2.0.0
 @description: 数据报告实体类
 @author: wangzexin
 @time: 2017/12/11 19:06
 @变更历史:
 ********************************************************************/

public class ShujubaogaoBean {

    /**
     * code : 200
     * data : {"users":"9","start":"2017/10/23","end":"2017/12/11","maxranking":"66","scoreranking":"2","userList":[{"upid":"8","uid":"52","iid":"1","qcid":"0","upquestion":"42,96,155,176,320","upyes":"B,A,C,A,D","upno":"A,A,A,A,A","uptimes":"1708182","upscore":"0","uplist":"[]","uplevel":"0","uptime":"1512978923","upenum":"1","uptype":"1","time":"1512978923"},{"upid":"9","uid":"52","iid":"1","qcid":"0","upquestion":"107,160,292,338,350","upyes":"A,B,C,C,A","upno":"A,,A,,A","uptimes":"23905","upscore":"0","uplist":"[]","uplevel":"0","uptime":"1512979243","upenum":"1","uptype":"1","time":"1512979243"}],"practicenum":"2","score":"0.0000","userpnum":"10","minute":"1","questionranking":"1","practicedays":"49"}
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
         * users : 9
         * start : 2017/10/23
         * end : 2017/12/11
         * maxranking : 66
         * scoreranking : 2
         * userList : [{"upid":"8","uid":"52","iid":"1","qcid":"0","upquestion":"42,96,155,176,320","upyes":"B,A,C,A,D","upno":"A,A,A,A,A","uptimes":"1708182","upscore":"0","uplist":"[]","uplevel":"0","uptime":"1512978923","upenum":"1","uptype":"1","time":"1512978923"},{"upid":"9","uid":"52","iid":"1","qcid":"0","upquestion":"107,160,292,338,350","upyes":"A,B,C,C,A","upno":"A,,A,,A","uptimes":"23905","upscore":"0","uplist":"[]","uplevel":"0","uptime":"1512979243","upenum":"1","uptype":"1","time":"1512979243"}]
         * practicenum : 2
         * score : 0.0000
         * userpnum : 10
         * minute : 1
         * questionranking : 1
         * practicedays : 49
         */

        private String users;
        private String start;//开始时间
        private String end;//结束时间
        private String maxranking;//最高分
        private String scoreranking;//分数排名
        private String best;//全站最高答题量
        private String practicenum;//练习次数
        private String score;//平均分
        private String userpnum;//练习题数
        private String minute;//时间
        private String questionranking;//题数排名
        private String practicedays;//练习题天数
        private List<UserListBean> userList;

        public String getUsers() {
            return users;
        }

        public void setUsers(String users) {
            this.users = users;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getMaxranking() {
            return maxranking;
        }

        public void setMaxranking(String maxranking) {
            this.maxranking = maxranking;
        }

        public String getScoreranking() {
            return scoreranking;
        }

        public void setScoreranking(String scoreranking) {
            this.scoreranking = scoreranking;
        }

        public String getPracticenum() {
            return practicenum;
        }

        public void setPracticenum(String practicenum) {
            this.practicenum = practicenum;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getUserpnum() {
            return userpnum;
        }

        public void setUserpnum(String userpnum) {
            this.userpnum = userpnum;
        }

        public String getMinute() {
            return minute;
        }

        public void setMinute(String minute) {
            this.minute = minute;
        }

        public String getQuestionranking() {
            return questionranking;
        }

        public void setQuestionranking(String questionranking) {
            this.questionranking = questionranking;
        }

        public String getBest() {
            return best;
        }

        public void setBest(String best) {
            this.best = best;
        }

        public String getPracticedays() {
            return practicedays;
        }

        public void setPracticedays(String practicedays) {
            this.practicedays = practicedays;
        }

        public List<UserListBean> getUserList() {
            return userList;
        }

        public void setUserList(List<UserListBean> userList) {
            this.userList = userList;
        }

        public static class UserListBean {
            /**
             * upid : 8
             * uid : 52
             * iid : 1
             * qcid : 0
             * upquestion : 42,96,155,176,320
             * upyes : B,A,C,A,D
             * upno : A,A,A,A,A
             * uptimes : 1708182
             * upscore : 0
             * uplist : []
             * uplevel : 0
             * uptime : 1512978923
             * upenum : 1
             * uptype : 1
             * time : 1512978923
             */

            private String upid;
            private String uid;
            private String iid;
            private String qcid;
            private String upquestion;
            private String upyes;
            private String upno;
            private String uptimes;
            private String upscore;//分数
            private String uplist;
            private String uplevel;
            private String uptime;
            private String upenum;
            private String uptype;
            private String time;//时间

            public String getUpid() {
                return upid;
            }

            public void setUpid(String upid) {
                this.upid = upid;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getIid() {
                return iid;
            }

            public void setIid(String iid) {
                this.iid = iid;
            }

            public String getQcid() {
                return qcid;
            }

            public void setQcid(String qcid) {
                this.qcid = qcid;
            }

            public String getUpquestion() {
                return upquestion;
            }

            public void setUpquestion(String upquestion) {
                this.upquestion = upquestion;
            }

            public String getUpyes() {
                return upyes;
            }

            public void setUpyes(String upyes) {
                this.upyes = upyes;
            }

            public String getUpno() {
                return upno;
            }

            public void setUpno(String upno) {
                this.upno = upno;
            }

            public String getUptimes() {
                return uptimes;
            }

            public void setUptimes(String uptimes) {
                this.uptimes = uptimes;
            }

            public String getUpscore() {
                return upscore;
            }

            public void setUpscore(String upscore) {
                this.upscore = upscore;
            }

            public String getUplist() {
                return uplist;
            }

            public void setUplist(String uplist) {
                this.uplist = uplist;
            }

            public String getUplevel() {
                return uplevel;
            }

            public void setUplevel(String uplevel) {
                this.uplevel = uplevel;
            }

            public String getUptime() {
                return uptime;
            }

            public void setUptime(String uptime) {
                this.uptime = uptime;
            }

            public String getUpenum() {
                return upenum;
            }

            public void setUpenum(String upenum) {
                this.upenum = upenum;
            }

            public String getUptype() {
                return uptype;
            }

            public void setUptype(String uptype) {
                this.uptype = uptype;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
}

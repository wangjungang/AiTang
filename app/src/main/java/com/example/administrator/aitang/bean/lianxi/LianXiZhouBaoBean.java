package com.example.administrator.aitang.bean.lianxi;

import java.util.List;

/**
 * Author : wangzexin
 * Date : 2017/12/12
 * Describe : 练习周报的数据类
 */

public class LianXiZhouBaoBean {

    /**
     * code : 200
     * data : {"yuce":0,"stilltime":"2017/12/04-2017/12/10","userminuteplay":0,"up":0,"weeklist":[55,100,70,10,66,77,88],"alluser":"9","rankingup":9,"ranking":null,"allminuteplay":false,"userpractic":null,"allpractice":0,"questionlist":[{"all":70,"num":14,"name":"言语理解与表达","baifenbi":100,"zhenquelv":20}]}
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
         * yuce : 0
         * stilltime : 2017/12/04-2017/12/10
         * userminuteplay : 0
         * up : 0
         * weeklist : [55,100,70,10,66,77,88]
         * alluser : 9
         * rankingup : 9
         * ranking : null
         * allminuteplay : false
         * userpractic : null
         * allpractice : 0
         * questionlist : [{"all":70,"num":14,"name":"言语理解与表达","baifenbi":100,"zhenquelv":20}]
         */

        private int yuce;
        private String stilltime;
        private double userminuteplay;//用户平均练习题数
        private int up;//分数上升
        private String alluser;
        private int rankingup;//排名上升
        private String ranking;//排名
        private double allminuteplay;//所有人平均联系题数
        private String userpractic;//所有人平均联系题素
        private double allpractice;//所有人平均题数
        private List<Integer> weeklist;
        private List<QuestionlistBean> questionlist;

        public int getYuce() {
            return yuce;
        }

        public void setYuce(int yuce) {
            this.yuce = yuce;
        }

        public String getStilltime() {
            return stilltime;
        }

        public void setStilltime(String stilltime) {
            this.stilltime = stilltime;
        }

        public double getUserminuteplay() {
            return userminuteplay;
        }

        public void setUserminuteplay(double userminuteplay) {
            this.userminuteplay = userminuteplay;
        }

        public int getUp() {
            return up;
        }

        public void setUp(int up) {
            this.up = up;
        }

        public String getAlluser() {
            return alluser;
        }

        public void setAlluser(String alluser) {
            this.alluser = alluser;
        }

        public int getRankingup() {
            return rankingup;
        }

        public void setRankingup(int rankingup) {
            this.rankingup = rankingup;
        }

        public String getRanking() {
            return ranking;
        }

        public void setRanking(String ranking) {
            this.ranking = ranking;
        }

        public double getAllminuteplay() {
            return allminuteplay;
        }

        public void setAllminuteplay(double allminuteplay) {
            this.allminuteplay = allminuteplay;
        }

        public String getUserpractic() {
            return userpractic;
        }

        public void setUserpractic(String userpractic) {
            this.userpractic = userpractic;
        }

        public double getAllpractice() {
            return allpractice;
        }

        public void setAllpractice(double allpractice) {
            this.allpractice = allpractice;
        }

        public List<Integer> getWeeklist() {
            return weeklist;
        }

        public void setWeeklist(List<Integer> weeklist) {
            this.weeklist = weeklist;
        }

        public List<QuestionlistBean> getQuestionlist() {
            return questionlist;
        }

        public void setQuestionlist(List<QuestionlistBean> questionlist) {
            this.questionlist = questionlist;
        }

        public static class QuestionlistBean {
            /**
             * all : 70
             * num : 14
             * name : 言语理解与表达
             * baifenbi : 100
             * zhenquelv : 20
             */

            private int all;
            private double num;
            private String name;
            private double baifenbi;
            private double zhenquelv;

            public int getAll() {
                return all;
            }

            public void setAll(int all) {
                this.all = all;
            }

            public double getNum() {
                return num;
            }

            public void setNum(double num) {
                this.num = num;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public double getBaifenbi() {
                return baifenbi;
            }

            public void setBaifenbi(double baifenbi) {
                this.baifenbi = baifenbi;
            }

            public double getZhenquelv() {
                return zhenquelv;
            }

            public void setZhenquelv(double zhenquelv) {
                this.zhenquelv = zhenquelv;
            }
        }
    }
}

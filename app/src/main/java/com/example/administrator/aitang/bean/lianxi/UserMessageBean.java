package com.example.administrator.aitang.bean.lianxi;

import java.util.List;

/**
 * Author : wangzexin
 * Date : 2017/11/15
 * Describe : 用户消息数据类
 */

public class UserMessageBean {


    /**
     * code : 200
     * data : [{"messageid":"4","uid":"52","messagetitle":"试题批改通知","messageintro":"您与2017年09月26日提交的试题批改已完成","questionid":"","type":"3","time":"1506418631"},{"messageid":"5","uid":"52","messagetitle":"试题批改通知","messageintro":"您与2017年09月26日提交的试题批改已完成","questionid":"3","type":"1","time":"1506418659"},{"messageid":"6","uid":"52","messagetitle":"试题批改通知","messageintro":"您与2017年09月26日提交的试题批改已完成","questionid":"92408854","type":"2","time":"1506418681"}]
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
         * messageid : 4
         * uid : 52
         * messagetitle : 试题批改通知
         * messageintro : 您与2017年09月26日提交的试题批改已完成
         * questionid :
         * type : 3
         * time : 1506418631
         */

        private String messageid;
        private String uid;
        private String messagetitle;
        private String messageintro;
        private String questionid;
        private String type;
        private String time;

        public String getMessageid() {
            return messageid;
        }

        public void setMessageid(String messageid) {
            this.messageid = messageid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getMessagetitle() {
            return messagetitle;
        }

        public void setMessagetitle(String messagetitle) {
            this.messagetitle = messagetitle;
        }

        public String getMessageintro() {
            return messageintro;
        }

        public void setMessageintro(String messageintro) {
            this.messageintro = messageintro;
        }

        public String getQuestionid() {
            return questionid;
        }

        public void setQuestionid(String questionid) {
            this.questionid = questionid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}

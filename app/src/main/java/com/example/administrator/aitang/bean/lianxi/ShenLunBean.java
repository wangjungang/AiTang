package com.example.administrator.aitang.bean.lianxi;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : wangzexin
 * Date : 2017/12/4
 * Describe : 申论题的数据模型
 */

public class ShenLunBean {

    /**
     * code : 200
     * data : [{"mqid":"1","qcid":"1","areaid":"1","qtid":"15","qtpath":"0-10-15","mqtitle":"概括资料所列发的社会风险","mqintro":[["当前，我国农村经济发展迅速，新农村建设取得令人瞩目的成就。但调查显示，一些地方农村的管理以及思想道德建设等方面仍然存在不容忽视的问题。"]],"dateflag":"1","price":"20","time":"1508996873"}]
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

    public static class DataBean implements Parcelable {
        /**
         * mqid : 1
         * qcid : 1
         * areaid : 1
         * qtid : 15
         * qtpath : 0-10-15
         * mqtitle : 概括资料所列发的社会风险
         * mqintro : [["当前，我国农村经济发展迅速，新农村建设取得令人瞩目的成就。但调查显示，一些地方农村的管理以及思想道德建设等方面仍然存在不容忽视的问题。"]]
         * dateflag : 1
         * price : 20
         * time : 1508996873
         */

        private String mqid;
        private String qcid;
        private String areaid;
        private String qtid;
        private String qtpath;
        private String mqtitle;
        private String dateflag;
        private String price;
        private String time;
        private List<List<String>> mqintro;

        //填写的答案--不是解析返回串得到的，所以以后如果后台加字段，不能为这两个关键词
        private String answerContent = "";//主观题的答案
        private List<String> imgPathList;//照片的list


        protected DataBean(Parcel in) {
            mqid = in.readString();
            qcid = in.readString();
            areaid = in.readString();
            qtid = in.readString();
            qtpath = in.readString();
            mqtitle = in.readString();
            dateflag = in.readString();
            price = in.readString();
            time = in.readString();
            answerContent = in.readString();
            imgPathList = in.createStringArrayList();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public String getMqid() {
            return mqid;
        }

        public void setMqid(String mqid) {
            this.mqid = mqid;
        }

        public String getQcid() {
            return qcid;
        }

        public void setQcid(String qcid) {
            this.qcid = qcid;
        }

        public String getAreaid() {
            return areaid;
        }

        public void setAreaid(String areaid) {
            this.areaid = areaid;
        }

        public String getQtid() {
            return qtid;
        }

        public void setQtid(String qtid) {
            this.qtid = qtid;
        }

        public String getQtpath() {
            return qtpath;
        }

        public void setQtpath(String qtpath) {
            this.qtpath = qtpath;
        }

        public String getMqtitle() {
            return mqtitle;
        }

        public void setMqtitle(String mqtitle) {
            this.mqtitle = mqtitle;
        }

        public String getDateflag() {
            return dateflag;
        }

        public void setDateflag(String dateflag) {
            this.dateflag = dateflag;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public List<List<String>> getMqintro() {
            return mqintro;
        }

        public void setMqintro(List<List<String>> mqintro) {
            this.mqintro = mqintro;
        }

        public String getAnswerContent() {
            if (null == answerContent) {
                answerContent = "";
            }
            return answerContent;
        }

        public void setAnswerContent(String content) {
            this.answerContent = content;
        }

        public List<String> getImgPathList() {
            if (null == imgPathList) {
                imgPathList = new ArrayList<>();
            }
            return imgPathList;
        }

        public void setImgPathList(List<String> imgPathList) {
            this.imgPathList = imgPathList;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(mqid);
            parcel.writeString(qcid);
            parcel.writeString(areaid);
            parcel.writeString(qtid);
            parcel.writeString(qtpath);
            parcel.writeString(mqtitle);
            parcel.writeString(dateflag);
            parcel.writeString(price);
            parcel.writeString(time);
            parcel.writeString(answerContent);
            parcel.writeStringList(imgPathList);
        }
    }
}

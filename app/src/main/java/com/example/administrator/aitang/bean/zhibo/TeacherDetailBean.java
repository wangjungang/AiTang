package com.example.administrator.aitang.bean.zhibo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2017/10/26.
 */

public class TeacherDetailBean {

    /**
     * code : 200
     * data : [{"cdid":"1","c_id":"1","tid":"1","cdintro":"论持久战","tname":"妮娜.杜波夫","cdstart_time":"1503417600","cdend_time":"1503567411","time":"1503567413","comment":"1","score":90,"father":"高分夜班车(第1期)"}]
     */

    private String code;
    private List<DataBean> data;

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public static class DataBean implements Parcelable {
        /**
         * cdid : 1
         * c_id : 1
         * tid : 1
         * cdintro : 论持久战
         * tname : 妮娜.杜波夫
         * cdstart_time : 1503417600
         * cdend_time : 1503567411
         * time : 1503567413
         * comment : 1
         * score : 90
         * father : 高分夜班车(第1期)
         */

        private String cdid;
        private String c_id;
        private String tid;
        private String cdintro;
        private String tname;
        private String cdstart_time;
        private String cdend_time;
        private String time;
        private String comment;
        private int score;
        private String father;

        public void setCdid(String cdid) {
            this.cdid = cdid;
        }

        public void setC_id(String c_id) {
            this.c_id = c_id;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public void setCdintro(String cdintro) {
            this.cdintro = cdintro;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public void setCdstart_time(String cdstart_time) {
            this.cdstart_time = cdstart_time;
        }

        public void setCdend_time(String cdend_time) {
            this.cdend_time = cdend_time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public void setFather(String father) {
            this.father = father;
        }

        public String getCdid() {
            return cdid;
        }

        public String getC_id() {
            return c_id;
        }

        public String getTid() {
            return tid;
        }

        public String getCdintro() {
            return cdintro;
        }

        public String getTname() {
            return tname;
        }

        public String getCdstart_time() {
            return cdstart_time;
        }

        public String getCdend_time() {
            return cdend_time;
        }

        public String getTime() {
            return time;
        }

        public String getComment() {
            return comment;
        }

        public int getScore() {
            return score;
        }

        public String getFather() {
            return father;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.cdid);
            dest.writeString(this.c_id);
            dest.writeString(this.tid);
            dest.writeString(this.cdintro);
            dest.writeString(this.tname);
            dest.writeString(this.cdstart_time);
            dest.writeString(this.cdend_time);
            dest.writeString(this.time);
            dest.writeString(this.comment);
            dest.writeInt(this.score);
            dest.writeString(this.father);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.cdid = in.readString();
            this.c_id = in.readString();
            this.tid = in.readString();
            this.cdintro = in.readString();
            this.tname = in.readString();
            this.cdstart_time = in.readString();
            this.cdend_time = in.readString();
            this.time = in.readString();
            this.comment = in.readString();
            this.score = in.readInt();
            this.father = in.readString();
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }
}

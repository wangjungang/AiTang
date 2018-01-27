package com.example.administrator.aitang.bean.zhibo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 * 直播课程列表
 */

public class ClassBean {


    /**
     * code : 200
     * data : [{"c_id":"1","ccid":"2","c_name":"高分夜班车(第1期)","c_price":"10","c_pay_num":"0","c_start_time":"1501524183","c_end_time":"1503482246","c_end_pay":"1503395505","c_intro_img":"http://55.irapidtech.net/Public/upload/599c087c1d9df.jpg","c_intro":"12321312312312312321","c_qq":"7758458","c_type":"2","time":"1509354673","teacher":[{"tid":"9","tname":"黑崎一护","tpic":"http://55.irapidtech.net/Public/upload/3005a263dd2ee525.jpg","tintro":"黑崎一护（ Kurosaki Ichigo）是日本动漫《死神》中的男主角，是灭却师与死神结合生下的后代，体内同时拥有死神、灭却师以及虚的多种力量。在家人遭到虚的袭击时，为了救下家人，从死神朽木露琪亚那里得到了死神之力，从而开启了死神代理的工作。后来分别在尸魂界篇和虚圈篇拯救同伴于险境，并打败了想要破坏尸魂界安定的蓝染惣右介，被瀞灵廷授予\u201c尸魂界的英雄\u201d的称呼。打败蓝染后，一护失去了死神之力，后在完现术篇中从朽木露琪亚那里再次获得。\r\n在千年血战篇中一护觉醒了自己真正的斩魂刀，靠着同伴们的帮助最终打败了最强敌人友哈巴赫，解救了尸魂界、现世与虚圈。大决战约十年后，黑崎一护与井上织姬婚后育有一子，名叫黑崎一勇。","tsimple":"死神死神死神死神死神","tscore":"100","roomid":"19645919"}],"classnum":3,"isbuy":1},{"c_id":"2","ccid":"2","c_name":"高分夜班车(第2期)","c_price":"12","c_pay_num":"0","c_start_time":"1503565850","c_end_time":"1503565852","c_end_pay":"1503565854","c_intro_img":"http://55.irapidtech.net/Public/upload/599e98216cd2f.jpg","c_intro":"1231243214123123213","c_qq":"213213","c_type":"2","time":"1509354656","teacher":[{"tid":"9","tname":"黑崎一护","tpic":"http://55.irapidtech.net/Public/upload/3005a263dd2ee525.jpg","tintro":"黑崎一护（ Kurosaki Ichigo）是日本动漫《死神》中的男主角，是灭却师与死神结合生下的后代，体内同时拥有死神、灭却师以及虚的多种力量。在家人遭到虚的袭击时，为了救下家人，从死神朽木露琪亚那里得到了死神之力，从而开启了死神代理的工作。后来分别在尸魂界篇和虚圈篇拯救同伴于险境，并打败了想要破坏尸魂界安定的蓝染惣右介，被瀞灵廷授予\u201c尸魂界的英雄\u201d的称呼。打败蓝染后，一护失去了死神之力，后在完现术篇中从朽木露琪亚那里再次获得。\r\n在千年血战篇中一护觉醒了自己真正的斩魂刀，靠着同伴们的帮助最终打败了最强敌人友哈巴赫，解救了尸魂界、现世与虚圈。大决战约十年后，黑崎一护与井上织姬婚后育有一子，名叫黑崎一勇。","tsimple":"死神死神死神死神死神","tscore":"100","roomid":"19645919"}],"classnum":1,"isbuy":1}]
     */

    private String code;
    private List<DataBean> data;

    public ClassBean(String code, List<DataBean> data) {
        this.code = code;
        this.data = data;
    }

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
         * c_id : 1
         * ccid : 2
         * c_name : 高分夜班车(第1期)
         * c_price : 10
         * c_pay_num : 0
         * c_start_time : 1501524183
         * c_end_time : 1503482246
         * c_end_pay : 1503395505
         * c_intro_img : http://55.irapidtech.net/Public/upload/599c087c1d9df.jpg
         * c_intro : 12321312312312312321
         * c_qq : 7758458
         * c_type : 2
         * time : 1509354673
         * teacher : [{"tid":"9","tname":"黑崎一护","tpic":"http://55.irapidtech.net/Public/upload/3005a263dd2ee525.jpg","tintro":"黑崎一护（ Kurosaki Ichigo）是日本动漫《死神》中的男主角，是灭却师与死神结合生下的后代，体内同时拥有死神、灭却师以及虚的多种力量。在家人遭到虚的袭击时，为了救下家人，从死神朽木露琪亚那里得到了死神之力，从而开启了死神代理的工作。后来分别在尸魂界篇和虚圈篇拯救同伴于险境，并打败了想要破坏尸魂界安定的蓝染惣右介，被瀞灵廷授予\u201c尸魂界的英雄\u201d的称呼。打败蓝染后，一护失去了死神之力，后在完现术篇中从朽木露琪亚那里再次获得。\r\n在千年血战篇中一护觉醒了自己真正的斩魂刀，靠着同伴们的帮助最终打败了最强敌人友哈巴赫，解救了尸魂界、现世与虚圈。大决战约十年后，黑崎一护与井上织姬婚后育有一子，名叫黑崎一勇。","tsimple":"死神死神死神死神死神","tscore":"100","roomid":"19645919"}]
         * classnum : 3
         * isbuy : 1
         */

        private String c_id;
        private String ccid;
        private String c_name;
        private String c_price;
        private String c_pay_num;
        private String c_start_time;
        private String c_end_time;
        private String c_end_pay;
        private String c_intro_img;
        private String c_intro;
        private String c_qq;
        private String c_type;
        private String time;
        private int classnum;
        private int isbuy;
        private ArrayList<TeacherBean> teacher;
        public DataBean(String c_id, String ccid, String c_name, String c_price, String c_pay_num, String c_start_time, String c_end_time, String c_end_pay, String c_intro_img, String c_intro, String c_qq, String c_type, String time, int classnum, int isbuy, ArrayList<TeacherBean> teacher) {
            this.c_id = c_id;
            this.ccid = ccid;
            this.c_name = c_name;
            this.c_price = c_price;
            this.c_pay_num = c_pay_num;
            this.c_start_time = c_start_time;
            this.c_end_time = c_end_time;
            this.c_end_pay = c_end_pay;
            this.c_intro_img = c_intro_img;
            this.c_intro = c_intro;
            this.c_qq = c_qq;
            this.c_type = c_type;
            this.time = time;
            this.classnum = classnum;
            this.isbuy = isbuy;
            this.teacher = teacher;
        }

        public String getC_id() {
            return c_id;
        }

        public void setC_id(String c_id) {
            this.c_id = c_id;
        }

        public String getCcid() {
            return ccid;
        }

        public void setCcid(String ccid) {
            this.ccid = ccid;
        }

        public String getC_name() {
            return c_name;
        }

        public void setC_name(String c_name) {
            this.c_name = c_name;
        }

        public String getC_price() {
            return c_price;
        }

        public void setC_price(String c_price) {
            this.c_price = c_price;
        }

        public String getC_pay_num() {
            return c_pay_num;
        }

        public void setC_pay_num(String c_pay_num) {
            this.c_pay_num = c_pay_num;
        }

        public String getC_start_time() {
            return c_start_time;
        }

        public void setC_start_time(String c_start_time) {
            this.c_start_time = c_start_time;
        }

        public String getC_end_time() {
            return c_end_time;
        }

        public void setC_end_time(String c_end_time) {
            this.c_end_time = c_end_time;
        }

        public String getC_end_pay() {
            return c_end_pay;
        }

        public void setC_end_pay(String c_end_pay) {
            this.c_end_pay = c_end_pay;
        }

        public String getC_intro_img() {
            return c_intro_img;
        }

        public void setC_intro_img(String c_intro_img) {
            this.c_intro_img = c_intro_img;
        }

        public String getC_intro() {
            return c_intro;
        }

        public void setC_intro(String c_intro) {
            this.c_intro = c_intro;
        }

        public String getC_qq() {
            return c_qq;
        }

        public void setC_qq(String c_qq) {
            this.c_qq = c_qq;
        }

        public String getC_type() {
            return c_type;
        }

        public void setC_type(String c_type) {
            this.c_type = c_type;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getClassnum() {
            return classnum;
        }

        public void setClassnum(int classnum) {
            this.classnum = classnum;
        }

        public int getIsbuy() {
            return isbuy;
        }

        public void setIsbuy(int isbuy) {
            this.isbuy = isbuy;
        }

        public ArrayList<TeacherBean> getTeacher() {
            return teacher;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "c_id='" + c_id + '\'' +
                    ", ccid='" + ccid + '\'' +
                    ", c_name='" + c_name + '\'' +
                    ", c_price='" + c_price + '\'' +
                    ", c_pay_num='" + c_pay_num + '\'' +
                    ", c_start_time='" + c_start_time + '\'' +
                    ", c_end_time='" + c_end_time + '\'' +
                    ", c_end_pay='" + c_end_pay + '\'' +
                    ", c_intro_img='" + c_intro_img + '\'' +
                    ", c_intro='" + c_intro + '\'' +
                    ", c_qq='" + c_qq + '\'' +
                    ", c_type='" + c_type + '\'' +
                    ", time='" + time + '\'' +
                    ", classnum=" + classnum +
                    ", isbuy=" + isbuy +
                    ", teacher=" + teacher +
                    '}';
        }

        public void setTeacher(ArrayList<TeacherBean> teacher) {
            this.teacher = teacher;
        }

        public static class TeacherBean implements Parcelable {
            /**
             * tid : 9
             * tname : 黑崎一护
             * tpic : http://55.irapidtech.net/Public/upload/3005a263dd2ee525.jpg
             * tintro : 黑崎一护（ Kurosaki Ichigo）是日本动漫《死神》中的男主角，是灭却师与死神结合生下的后代，体内同时拥有死神、灭却师以及虚的多种力量。在家人遭到虚的袭击时，为了救下家人，从死神朽木露琪亚那里得到了死神之力，从而开启了死神代理的工作。后来分别在尸魂界篇和虚圈篇拯救同伴于险境，并打败了想要破坏尸魂界安定的蓝染惣右介，被瀞灵廷授予“尸魂界的英雄”的称呼。打败蓝染后，一护失去了死神之力，后在完现术篇中从朽木露琪亚那里再次获得。
             * 在千年血战篇中一护觉醒了自己真正的斩魂刀，靠着同伴们的帮助最终打败了最强敌人友哈巴赫，解救了尸魂界、现世与虚圈。大决战约十年后，黑崎一护与井上织姬婚后育有一子，名叫黑崎一勇。
             * tsimple : 死神死神死神死神死神
             * tscore : 100
             * roomid : 19645919
             */

            private String tid;
            private String tname;
            private String tpic;
            private String tintro;
            private String tsimple;
            private String tscore;
            private String roomid;

            public TeacherBean(String tid, String tname, String tpic, String tintro, String tsimple, String tscore, String roomid) {
                this.tid = tid;
                this.tname = tname;
                this.tpic = tpic;
                this.tintro = tintro;
                this.tsimple = tsimple;
                this.tscore = tscore;
                this.roomid = roomid;
            }

            @Override
            public String toString() {
                return "TeacherBean{" +
                        "tid='" + tid + '\'' +
                        ", tname='" + tname + '\'' +
                        ", tpic='" + tpic + '\'' +
                        ", tintro='" + tintro + '\'' +
                        ", tsimple='" + tsimple + '\'' +
                        ", tscore='" + tscore + '\'' +
                        ", roomid='" + roomid + '\'' +
                        '}';
            }

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

            public String getRoomid() {
                return roomid;
            }

            public void setRoomid(String roomid) {
                this.roomid = roomid;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.tid);
                dest.writeString(this.tname);
                dest.writeString(this.tpic);
                dest.writeString(this.tintro);
                dest.writeString(this.tsimple);
                dest.writeString(this.tscore);
                dest.writeString(this.roomid);
            }

            public TeacherBean() {
            }

            protected TeacherBean(Parcel in) {
                this.tid = in.readString();
                this.tname = in.readString();
                this.tpic = in.readString();
                this.tintro = in.readString();
                this.tsimple = in.readString();
                this.tscore = in.readString();
                this.roomid = in.readString();
            }

            public static final Creator<TeacherBean> CREATOR = new Creator<TeacherBean>() {
                @Override
                public TeacherBean createFromParcel(Parcel source) {
                    return new TeacherBean(source);
                }

                @Override
                public TeacherBean[] newArray(int size) {
                    return new TeacherBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.c_id);
            dest.writeString(this.ccid);
            dest.writeString(this.c_name);
            dest.writeString(this.c_price);
            dest.writeString(this.c_pay_num);
            dest.writeString(this.c_start_time);
            dest.writeString(this.c_end_time);
            dest.writeString(this.c_end_pay);
            dest.writeString(this.c_intro_img);
            dest.writeString(this.c_intro);
            dest.writeString(this.c_qq);
            dest.writeString(this.c_type);
            dest.writeString(this.time);
            dest.writeInt(this.classnum);
            dest.writeInt(this.isbuy);
            dest.writeList(this.teacher);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.c_id = in.readString();
            this.ccid = in.readString();
            this.c_name = in.readString();
            this.c_price = in.readString();
            this.c_pay_num = in.readString();
            this.c_start_time = in.readString();
            this.c_end_time = in.readString();
            this.c_end_pay = in.readString();
            this.c_intro_img = in.readString();
            this.c_intro = in.readString();
            this.c_qq = in.readString();
            this.c_type = in.readString();
            this.time = in.readString();
            this.classnum = in.readInt();
            this.isbuy = in.readInt();
            this.teacher = new ArrayList<TeacherBean>();
            in.readList(this.teacher, TeacherBean.class.getClassLoader());
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }
}

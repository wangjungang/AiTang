package com.example.administrator.aitang.bean.zhibo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2017/10/26.
 */

public class ClassDetailBean {


    /**
     * code : 200
     * data : [{"cdid":"1","c_id":"1","tid":"9","cdintro":"论持久战","tname":"黑崎一护","cdstart_time":"1503417600","cdend_time":"1503567411","isstart":"2","cdimg":"http://55.irapidtech.net/Public/upload/5a0e8ae4c08b9.png","time":"1514883007","teacher":{"tid":"9","tname":"黑崎一护","tpic":"http://55.irapidtech.net/Public/upload/3005a263dd2ee525.jpg","tphone":"13523432432","tpwd":"202cb962ac59075b964b07152d234b70","tintro":"黑崎一护（ Kurosaki Ichigo）是日本动漫《死神》中的男主角，是灭却师与死神结合生下的后代，体内同时拥有死神、灭却师以及虚的多种力量。在家人遭到虚的袭击时，为了救下家人，从死神朽木露琪亚那里得到了死神之力，从而开启了死神代理的工作。后来分别在尸魂界篇和虚圈篇拯救同伴于险境，并打败了想要破坏尸魂界安定的蓝染惣右介，被瀞灵廷授予\u201c尸魂界的英雄\u201d的称呼。打败蓝染后，一护失去了死神之力，后在完现术篇中从朽木露琪亚那里再次获得。\r\n在千年血战篇中一护觉醒了自己真正的斩魂刀，靠着同伴们的帮助最终打败了最强敌人友哈巴赫，解救了尸魂界、现世与虚圈。大决战约十年后，黑崎一护与井上织姬婚后育有一子，名叫黑崎一勇。","tsimple":"死神死神死神死神死神","tscore":"100","tstatus":"1","ttime":"0000-00-00 00:00:00","token":"2bbe2b5bace35e4da94975a4ca2474d2","roomid":"19645919","time":"1512455636"}},{"cdid":"2","c_id":"1","tid":"9","cdintro":"人的必修素养","tname":"黑崎一护","cdstart_time":"1503417600","cdend_time":"1503567411","isstart":"2","cdimg":"http://55.irapidtech.net/Public/upload/5a0e8ad65d5cf.jpg","time":"1514882897","teacher":{"tid":"9","tname":"黑崎一护","tpic":"http://55.irapidtech.net/Public/upload/3005a263dd2ee525.jpg","tphone":"13523432432","tpwd":"202cb962ac59075b964b07152d234b70","tintro":"黑崎一护（ Kurosaki Ichigo）是日本动漫《死神》中的男主角，是灭却师与死神结合生下的后代，体内同时拥有死神、灭却师以及虚的多种力量。在家人遭到虚的袭击时，为了救下家人，从死神朽木露琪亚那里得到了死神之力，从而开启了死神代理的工作。后来分别在尸魂界篇和虚圈篇拯救同伴于险境，并打败了想要破坏尸魂界安定的蓝染惣右介，被瀞灵廷授予\u201c尸魂界的英雄\u201d的称呼。打败蓝染后，一护失去了死神之力，后在完现术篇中从朽木露琪亚那里再次获得。\r\n在千年血战篇中一护觉醒了自己真正的斩魂刀，靠着同伴们的帮助最终打败了最强敌人友哈巴赫，解救了尸魂界、现世与虚圈。大决战约十年后，黑崎一护与井上织姬婚后育有一子，名叫黑崎一勇。","tsimple":"死神死神死神死神死神","tscore":"100","tstatus":"1","ttime":"0000-00-00 00:00:00","token":"2bbe2b5bace35e4da94975a4ca2474d2","roomid":"19645919","time":"1512455636"}},{"cdid":"4","c_id":"1","tid":"10","cdintro":"啦啦啦啦啦","tname":"黑崎一护","cdstart_time":"1511463724","cdend_time":"1511452800","isstart":"0","cdimg":"http://55.irapidtech.net/Public/upload/5a0e8b94e6b13.png","time":"1512471235","teacher":{"tid":"10","tname":"llll","tpic":"http://55.irapidtech.net/Public/upload/3005a3ccd0b69c23.png","tphone":"13565651111","tpwd":"202cb962ac59075b964b07152d234b70","tintro":"123123123123123123123123123123123123123123123123123123","tsimple":"123123123123123123","tscore":"100","tstatus":"1","ttime":"0000-00-00 00:00:00","token":"b39313bde5157a79197b0a69769cbd02","roomid":"20872314","time":"1513934092"}}]
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
         * cdid : 1
         * c_id : 1
         * tid : 9
         * cdintro : 论持久战
         * tname : 黑崎一护
         * cdstart_time : 1503417600
         * cdend_time : 1503567411
         * isstart : 2
         * cdimg : http://55.irapidtech.net/Public/upload/5a0e8ae4c08b9.png
         * time : 1514883007
         * teacher : {"tid":"9","tname":"黑崎一护","tpic":"http://55.irapidtech.net/Public/upload/3005a263dd2ee525.jpg","tphone":"13523432432","tpwd":"202cb962ac59075b964b07152d234b70","tintro":"黑崎一护（ Kurosaki Ichigo）是日本动漫《死神》中的男主角，是灭却师与死神结合生下的后代，体内同时拥有死神、灭却师以及虚的多种力量。在家人遭到虚的袭击时，为了救下家人，从死神朽木露琪亚那里得到了死神之力，从而开启了死神代理的工作。后来分别在尸魂界篇和虚圈篇拯救同伴于险境，并打败了想要破坏尸魂界安定的蓝染惣右介，被瀞灵廷授予\u201c尸魂界的英雄\u201d的称呼。打败蓝染后，一护失去了死神之力，后在完现术篇中从朽木露琪亚那里再次获得。\r\n在千年血战篇中一护觉醒了自己真正的斩魂刀，靠着同伴们的帮助最终打败了最强敌人友哈巴赫，解救了尸魂界、现世与虚圈。大决战约十年后，黑崎一护与井上织姬婚后育有一子，名叫黑崎一勇。","tsimple":"死神死神死神死神死神","tscore":"100","tstatus":"1","ttime":"0000-00-00 00:00:00","token":"2bbe2b5bace35e4da94975a4ca2474d2","roomid":"19645919","time":"1512455636"}
         */

        private String cdid;
        private String c_id;
        private String tid;
        private String cdintro;
        private String tname;
        private String cdstart_time;
        private String cdend_time;
        private String isstart;
        private String cdimg;
        private String time;
        private TeacherBean teacher;

        public String getCdid() {
            return cdid;
        }

        public void setCdid(String cdid) {
            this.cdid = cdid;
        }

        public String getC_id() {
            return c_id;
        }

        public void setC_id(String c_id) {
            this.c_id = c_id;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getCdintro() {
            return cdintro;
        }

        public void setCdintro(String cdintro) {
            this.cdintro = cdintro;
        }

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public String getCdstart_time() {
            return cdstart_time;
        }

        public void setCdstart_time(String cdstart_time) {
            this.cdstart_time = cdstart_time;
        }

        public String getCdend_time() {
            return cdend_time;
        }

        public void setCdend_time(String cdend_time) {
            this.cdend_time = cdend_time;
        }

        public String getIsstart() {
            return isstart;
        }

        public void setIsstart(String isstart) {
            this.isstart = isstart;
        }

        public String getCdimg() {
            return cdimg;
        }

        public void setCdimg(String cdimg) {
            this.cdimg = cdimg;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public TeacherBean getTeacher() {
            return teacher;
        }

        public void setTeacher(TeacherBean teacher) {
            this.teacher = teacher;
        }

        public static class TeacherBean implements Parcelable {
            /**
             * tid : 9
             * tname : 黑崎一护
             * tpic : http://55.irapidtech.net/Public/upload/3005a263dd2ee525.jpg
             * tphone : 13523432432
             * tpwd : 202cb962ac59075b964b07152d234b70
             * tintro : 黑崎一护（ Kurosaki Ichigo）是日本动漫《死神》中的男主角，是灭却师与死神结合生下的后代，体内同时拥有死神、灭却师以及虚的多种力量。在家人遭到虚的袭击时，为了救下家人，从死神朽木露琪亚那里得到了死神之力，从而开启了死神代理的工作。后来分别在尸魂界篇和虚圈篇拯救同伴于险境，并打败了想要破坏尸魂界安定的蓝染惣右介，被瀞灵廷授予“尸魂界的英雄”的称呼。打败蓝染后，一护失去了死神之力，后在完现术篇中从朽木露琪亚那里再次获得。
             在千年血战篇中一护觉醒了自己真正的斩魂刀，靠着同伴们的帮助最终打败了最强敌人友哈巴赫，解救了尸魂界、现世与虚圈。大决战约十年后，黑崎一护与井上织姬婚后育有一子，名叫黑崎一勇。
             * tsimple : 死神死神死神死神死神
             * tscore : 100
             * tstatus : 1
             * ttime : 0000-00-00 00:00:00
             * token : 2bbe2b5bace35e4da94975a4ca2474d2
             * roomid : 19645919
             * time : 1512455636
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
            private String token;
            private String roomid;
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

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public String getRoomid() {
                return roomid;
            }

            public void setRoomid(String roomid) {
                this.roomid = roomid;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
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
                dest.writeString(this.tphone);
                dest.writeString(this.tpwd);
                dest.writeString(this.tintro);
                dest.writeString(this.tsimple);
                dest.writeString(this.tscore);
                dest.writeString(this.tstatus);
                dest.writeString(this.ttime);
                dest.writeString(this.token);
                dest.writeString(this.roomid);
                dest.writeString(this.time);
            }

            public TeacherBean() {
            }

            protected TeacherBean(Parcel in) {
                this.tid = in.readString();
                this.tname = in.readString();
                this.tpic = in.readString();
                this.tphone = in.readString();
                this.tpwd = in.readString();
                this.tintro = in.readString();
                this.tsimple = in.readString();
                this.tscore = in.readString();
                this.tstatus = in.readString();
                this.ttime = in.readString();
                this.token = in.readString();
                this.roomid = in.readString();
                this.time = in.readString();
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
            dest.writeString(this.cdid);
            dest.writeString(this.c_id);
            dest.writeString(this.tid);
            dest.writeString(this.cdintro);
            dest.writeString(this.tname);
            dest.writeString(this.cdstart_time);
            dest.writeString(this.cdend_time);
            dest.writeString(this.isstart);
            dest.writeString(this.cdimg);
            dest.writeString(this.time);
            dest.writeParcelable(this.teacher, flags);
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
            this.isstart = in.readString();
            this.cdimg = in.readString();
            this.time = in.readString();
            this.teacher = in.readParcelable(TeacherBean.class.getClassLoader());
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

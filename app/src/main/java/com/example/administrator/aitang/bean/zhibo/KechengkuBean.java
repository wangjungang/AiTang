package com.example.administrator.aitang.bean.zhibo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/31.
 */

public class KechengkuBean {

    /**
     * code : 200
     * data : [{"mid":"1","uid":"2","c_id":"2","testid":"2","mtype":"2","mtime":"1505202090","class":{"c_id":"2","ccid":"3","c_name":"高分夜班车(第2期)","c_price":"12","c_pay_num":"0","c_start_time":"1511784061","c_end_time":"1512129661","c_end_pay":"1503565854","c_intro_img":"http://55.irapidtech.net/Public/upload/599e98216cd2f.jpg","c_qq":"213213","c_type":"2","time":"1505127739"},"teacher":[{"tid":"2","tname":"卡卡罗特","tpic":"http://55.irapidtech.net/Public/upload/300599aa9b8a5c25.jpg","tintro":"贝吉塔行星的赛亚人。他在小时候以\u201c下级战士\u201d的身份被送到地球。被武道家孙悟饭收养并取名为\u201c孙悟空\u201d。小时候失控变成大猩猩踩死悟饭后独自生活在深山。一日结识少女布玛，从而踏上收集龙珠的大冒险。","tsimple":"赛亚人","tscore":"100"}],"ymdhis":["1503565478"],"classnum":1},{"mid":"2","uid":"2","c_id":"1","testid":"2","mtype":"2","mtime":"0","class":{"c_id":"1","ccid":"2","c_name":"高分夜班车(第1期)","c_price":"10","c_pay_num":"0","c_start_time":"1501524183","c_end_time":"1503482246","c_end_pay":"1503395505","c_intro_img":"http://55.irapidtech.net/Public/upload/599c087c1d9df.jpg","c_qq":"7758458","c_type":"2","time":"1505127723"},"teacher":[{"tid":"1","tname":"妮娜.杜波夫","tpic":"http://55.irapidtech.net/Public/upload/300599a9044b6ecb.jpg","tintro":"妮娜·杜波夫，1989年1月9日出生于保加利亚索菲亚，后加入加拿大国籍，加拿大前奥运会体操选手，模特，演员，和业余歌手","tsimple":"最漂亮的女人","tscore":"100"},{"tid":"3","tname":"蒙奇·D·路飞","tpic":"http://55.irapidtech.net/Public/upload/300599b9cf421bea.jpg","tintro":"草帽海贼团、草帽大船团船长，极恶的世代之一。橡胶果实能力者，悬赏金5亿贝里。梦想是找到传说中的One Piece，成为海贼王。\r\n他性格积极乐观，爱憎分明且十分重视伙伴，不甘屈居于他人之下，对任何危险的事物都超感兴趣。和其他传统的海贼所不同的是，他并不会为了追求财富而无故杀戮，而是享受着身为海贼的冒险和自由。从忘年交香克斯手里继承了海贼王罗杰的草帽[1] 。","tsimple":"海贼王","tscore":"100"}],"ymdhis":["1503417600","1503417600"],"classnum":2}]
     */

    private String code;
    private ArrayList<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "KechengkuBean{" +
                "code='" + code + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean implements Parcelable {
        /**
         * mid : 1
         * uid : 2
         * c_id : 2
         * testid : 2
         * mtype : 2
         * mtime : 1505202090
         * class : {"c_id":"2","ccid":"3","c_name":"高分夜班车(第2期)","c_price":"12","c_pay_num":"0","c_start_time":"1511784061","c_end_time":"1512129661","c_end_pay":"1503565854","c_intro_img":"http://55.irapidtech.net/Public/upload/599e98216cd2f.jpg","c_qq":"213213","c_type":"2","time":"1505127739"}
         * teacher : [{"tid":"2","tname":"卡卡罗特","tpic":"http://55.irapidtech.net/Public/upload/300599aa9b8a5c25.jpg","tintro":"贝吉塔行星的赛亚人。他在小时候以\u201c下级战士\u201d的身份被送到地球。被武道家孙悟饭收养并取名为\u201c孙悟空\u201d。小时候失控变成大猩猩踩死悟饭后独自生活在深山。一日结识少女布玛，从而踏上收集龙珠的大冒险。","tsimple":"赛亚人","tscore":"100"}]
         * ymdhis : ["1503565478"]
         * classnum : 1
         */

        private String mid;
        private String uid;
        private String c_id;
        private String testid;
        private String mtype;
        private String mtime;
        @SerializedName("class")
        private ClassBean classX;
        private int classnum;
        private List<TeacherBean> teacher;
        private List<String> ymdhis;

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getC_id() {
            return c_id;
        }

        public void setC_id(String c_id) {
            this.c_id = c_id;
        }

        public String getTestid() {
            return testid;
        }

        public void setTestid(String testid) {
            this.testid = testid;
        }

        public String getMtype() {
            return mtype;
        }

        public void setMtype(String mtype) {
            this.mtype = mtype;
        }

        public String getMtime() {
            return mtime;
        }

        public void setMtime(String mtime) {
            this.mtime = mtime;
        }

        public ClassBean getClassX() {
            return classX;
        }

        public void setClassX(ClassBean classX) {
            this.classX = classX;
        }

        public int getClassnum() {
            return classnum;
        }

        public void setClassnum(int classnum) {
            this.classnum = classnum;
        }

        public List<TeacherBean> getTeacher() {
            return teacher;
        }

        public void setTeacher(List<TeacherBean> teacher) {
            this.teacher = teacher;
        }

        public List<String> getYmdhis() {
            return ymdhis;
        }

        public void setYmdhis(List<String> ymdhis) {
            this.ymdhis = ymdhis;
        }

        public static class ClassBean implements Parcelable {
            /**
             * c_id : 2
             * ccid : 3
             * c_name : 高分夜班车(第2期)
             * c_price : 12
             * c_pay_num : 0
             * c_start_time : 1511784061
             * c_end_time : 1512129661
             * c_end_pay : 1503565854
             * c_intro_img : http://55.irapidtech.net/Public/upload/599e98216cd2f.jpg
             * c_qq : 213213
             * c_type : 2
             * time : 1505127739
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
            private String c_qq;
            private String c_type;
            private String time;

            @Override
            public String toString() {
                return "ClassBean{" +
                        "c_id='" + c_id + '\'' +
                        ", ccid='" + ccid + '\'' +
                        ", c_name='" + c_name + '\'' +
                        ", c_price='" + c_price + '\'' +
                        ", c_pay_num='" + c_pay_num + '\'' +
                        ", c_start_time='" + c_start_time + '\'' +
                        ", c_end_time='" + c_end_time + '\'' +
                        ", c_end_pay='" + c_end_pay + '\'' +
                        ", c_intro_img='" + c_intro_img + '\'' +
                        ", c_qq='" + c_qq + '\'' +
                        ", c_type='" + c_type + '\'' +
                        ", time='" + time + '\'' +
                        '}';
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
                dest.writeString(this.c_qq);
                dest.writeString(this.c_type);
                dest.writeString(this.time);
            }

            public ClassBean() {
            }

            protected ClassBean(Parcel in) {
                this.c_id = in.readString();
                this.ccid = in.readString();
                this.c_name = in.readString();
                this.c_price = in.readString();
                this.c_pay_num = in.readString();
                this.c_start_time = in.readString();
                this.c_end_time = in.readString();
                this.c_end_pay = in.readString();
                this.c_intro_img = in.readString();
                this.c_qq = in.readString();
                this.c_type = in.readString();
                this.time = in.readString();
            }

            public static final Creator<ClassBean> CREATOR = new Creator<ClassBean>() {
                @Override
                public ClassBean createFromParcel(Parcel source) {
                    return new ClassBean(source);
                }

                @Override
                public ClassBean[] newArray(int size) {
                    return new ClassBean[size];
                }
            };
        }

        public static class TeacherBean {
            /**
             * tid : 2
             * tname : 卡卡罗特
             * tpic : http://55.irapidtech.net/Public/upload/300599aa9b8a5c25.jpg
             * tintro : 贝吉塔行星的赛亚人。他在小时候以“下级战士”的身份被送到地球。被武道家孙悟饭收养并取名为“孙悟空”。小时候失控变成大猩猩踩死悟饭后独自生活在深山。一日结识少女布玛，从而踏上收集龙珠的大冒险。
             * tsimple : 赛亚人
             * tscore : 100
             */

            private String tid;
            private String tname;
            private String tpic;
            private String tintro;
            private String tsimple;
            private String tscore;

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
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "mid='" + mid + '\'' +
                    ", uid='" + uid + '\'' +
                    ", c_id='" + c_id + '\'' +
                    ", testid='" + testid + '\'' +
                    ", mtype='" + mtype + '\'' +
                    ", mtime='" + mtime + '\'' +
                    ", classX=" + classX +
                    ", classnum=" + classnum +
                    ", teacher=" + teacher +
                    ", ymdhis=" + ymdhis +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.mid);
            dest.writeString(this.uid);
            dest.writeString(this.c_id);
            dest.writeString(this.testid);
            dest.writeString(this.mtype);
            dest.writeString(this.mtime);
            dest.writeParcelable(this.classX, flags);
            dest.writeInt(this.classnum);
            dest.writeList(this.teacher);
            dest.writeStringList(this.ymdhis);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.mid = in.readString();
            this.uid = in.readString();
            this.c_id = in.readString();
            this.testid = in.readString();
            this.mtype = in.readString();
            this.mtime = in.readString();
            this.classX = in.readParcelable(ClassBean.class.getClassLoader());
            this.classnum = in.readInt();
            this.teacher = new ArrayList<TeacherBean>();
            in.readList(this.teacher, TeacherBean.class.getClassLoader());
            this.ymdhis = in.createStringArrayList();
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

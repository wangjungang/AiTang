package com.example.administrator.aitang.bean.lianxi;

import com.example.administrator.aitang.bean.ExameTypeBean;
import com.example.administrator.aitang.fragment.LianxiFragment;

import java.util.List;

/**
 * Created by 武培培 on 2017/10/24.
 */

public class IndexBean {
    public String code;
    public Data data;


    public IndexBean(String code, Data data) {
        this.code = code;
        this.data = data;

    }



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }



    public static class Data{
        public List<Question> quetions;
public List<Slide>  slide;
        public User user;
        public String isdeport;
        public List<Question> getQuetions() {
            return quetions;
        }

        public String getIsdeport() {
            return isdeport;
        }

        public void setIsdeport(String isdeport) {
            this.isdeport = isdeport;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "quetions=" + quetions +
                    ", slide=" + slide +
                    ", user=" + user +
                    ", isdeport='" + isdeport + '\'' +
                    '}';
        }

        public void setQuetions(List<Question> quetions) {
            this.quetions = quetions;
        }

        public List<Slide> getSlide() {
            return slide;
        }

        public void setSlide(List<Slide> slide) {
            this.slide = slide;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public static class  Question{
            public String img;
            public String qtid;
            public String qtname;
            public List<Child> child;
            public String num;

            @Override
            public String toString() {
                return "Question{" +
                        "img='" + img + '\'' +
                        ", qtid='" + qtid + '\'' +
                        ", qtname='" + qtname + '\'' +
                        ", child=" + child +
                        ", num='" + num + '\'' +
                        '}';
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getQtid() {
                return qtid;
            }

            public void setQtid(String qtid) {
                this.qtid = qtid;
            }

            public String getQtname() {
                return qtname;
            }

            public void setQtname(String qtname) {
                this.qtname = qtname;
            }

            public List<Child> getChild() {
                return child;
            }

            public void setChild(List<Child> child) {
                this.child = child;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public static  class  Child{
                public String img;
                public String qtid;
                public String qtname;
                public List<String> child;

                @Override
                public String toString() {
                    return "Child{" +
                            "img='" + img + '\'' +
                            ", qtid='" + qtid + '\'' +
                            ", qtname='" + qtname + '\'' +
                            ", child=" + child +
                            '}';
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getQtid() {
                    return qtid;
                }

                public void setQtid(String qtid) {
                    this.qtid = qtid;
                }

                public String getQtname() {
                    return qtname;
                }

                public void setQtname(String qtname) {
                    this.qtname = qtname;
                }

                public List<String> getChild() {
                    return child;
                }

                public void setChild(List<String> child) {
                    this.child = child;
                }
            }
        }

        public static class Slide{
public String posterid;
            public String postername;
            public String posterimg;
            public String postertype;
            public String posterintro;
            public String posterstatus;
            public String time;

            @Override
            public String toString() {
                return "Slide{" +
                        "posterid='" + posterid + '\'' +
                        ", postername='" + postername + '\'' +
                        ", posterimg='" + posterimg + '\'' +
                        ", postertype='" + postertype + '\'' +
                        ", posterintro='" + posterintro + '\'' +
                        ", posterstatus='" + posterstatus + '\'' +
                        ", time='" + time + '\'' +
                        '}';
            }

            public String getPosterid() {
                return posterid;
            }

            public void setPosterid(String posterid) {
                this.posterid = posterid;
            }

            public String getPostername() {
                return postername;
            }

            public void setPostername(String postername) {
                this.postername = postername;
            }

            public String getPosterimg() {
                return posterimg;
            }

            public void setPosterimg(String posterimg) {
                this.posterimg = posterimg;
            }

            public String getPostertype() {
                return postertype;
            }

            public void setPostertype(String postertype) {
                this.postertype = postertype;
            }

            public String getPosterintro() {
                return posterintro;
            }

            public void setPosterintro(String posterintro) {
                this.posterintro = posterintro;
            }

            public String getPosterstatus() {
                return posterstatus;
            }

            public void setPosterstatus(String posterstatus) {
                this.posterstatus = posterstatus;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class  User{
public String uid;
            public String uname;
            public String upic;
            public String uphone;
            public  String third;
            public String upwd;
            public String utest_type;
            public String testid;
            public String uintro;
            public  String ustatus;
            public String time;

            @Override
            public String toString() {
                return "User{" +
                        "uid='" + uid + '\'' +
                        ", uname='" + uname + '\'' +
                        ", upic='" + upic + '\'' +
                        ", uphone='" + uphone + '\'' +
                        ", third='" + third + '\'' +
                        ", upwd='" + upwd + '\'' +
                        ", utest_type='" + utest_type + '\'' +
                        ", testid='" + testid + '\'' +
                        ", uintro='" + uintro + '\'' +
                        ", ustatus='" + ustatus + '\'' +
                        ", time='" + time + '\'' +
                        '}';
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getUname() {
                return uname;
            }

            public void setUname(String uname) {
                this.uname = uname;
            }

            public String getUpic() {
                return upic;
            }

            public void setUpic(String upic) {
                this.upic = upic;
            }

            public String getUphone() {
                return uphone;
            }

            public void setUphone(String uphone) {
                this.uphone = uphone;
            }

            public String getThird() {
                return third;
            }

            public void setThird(String third) {
                this.third = third;
            }

            public String getUpwd() {
                return upwd;
            }

            public void setUpwd(String upwd) {
                this.upwd = upwd;
            }

            public String getUtest_type() {
                return utest_type;
            }

            public void setUtest_type(String utest_type) {
                this.utest_type = utest_type;
            }

            public String getTestid() {
                return testid;
            }

            public void setTestid(String testid) {
                this.testid = testid;
            }

            public String getUintro() {
                return uintro;
            }

            public void setUintro(String uintro) {
                this.uintro = uintro;
            }

            public String getUstatus() {
                return ustatus;
            }

            public void setUstatus(String ustatus) {
                this.ustatus = ustatus;
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

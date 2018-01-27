package com.example.administrator.aitang.bean.lianxi;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 武培培 on 2017/10/25.
 */

public class IndexBean2 implements Serializable{
    public String code;
    public Data data;

    public String getCode() {
        return code;
    }

    public Data getData() {
        return data;
    }

    public IndexBean2(String code, Data data) {
        this.code = code;
        this.data = data;
    }

    public static class Data {
        public List<Question> quetions;
        public List<Slide> slide;
        public User user;
        public String isdeport;

        public Data(List<Question> quetions, List<Slide> slide, User user, String isdeport) {
            this.quetions = quetions;
            this.slide = slide;
            this.user = user;
            this.isdeport = isdeport;
        }

        public List<Question> getQuetions() {
            return quetions;
        }

        public List<Slide> getSlide() {
            return slide;
        }

        public User getUser() {
            return user;
        }

        public String getIsdeport() {
            return isdeport;
        }

        public static class Question implements Serializable {
            public String img;
            public String qtid;
            public String qtname;
            public List<Child> child;
            public String num;

            public Question(String qtid, String qtname, List<Child> child, String num) {
                this.qtid = qtid;
                this.qtname = qtname;
                this.child = child;
                this.num = num;
            }

            public String getImg() {
                return img;
            }

            public String getQtid() {
                return qtid;
            }

            public String getQtname() {
                return qtname;
            }

            public List<Child> getChild() {
                return child;
            }

            public String getNum() {
                return num;
            }

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


            public static  class Child implements Serializable{
                public String img;
                public String qtid;
                public String qtname;
                public List<Child2> child;
                public static class Child2 implements Serializable{
                    public String img;
                    public String qtid;
                    public String qtname;
                    public List<Child2> child;

                    public Child2(String img, String qtid, String qtname, List<Child2> child) {
                        this.img = img;
                        this.qtid = qtid;
                        this.qtname = qtname;
                        this.child = child;
                    }

                    public String getImg() {
                        return img;
                    }

                    public String getQtid() {
                        return qtid;
                    }

                    public String getQtname() {
                        return qtname;
                    }


                }
                public Child(String img, String qtid, String qtname, List<Child2> child) {
                    this.img = img;
                    this.qtid = qtid;
                    this.qtname = qtname;
                    this.child = child;
                }
                public String getImg() {
                    return img;
                }

                public String getQtid() {
                    return qtid;
                }

                public String getQtname() {
                    return qtname;
                }

                public List<Child2> getChild() {
                    return child;
                }

                @Override
                public String toString() {
                    return "Child{" +
                            "img='" + img + '\'' +
                            ", qtid='" + qtid + '\'' +
                            ", qtname='" + qtname + '\'' +
                            ", child=" + child +
                            '}';
                }


            }

        }

        public static class Slide {
            public String posterid;
            public String postername;
            public String posterimg;
            public String postertype;
            public String posterintro;
            public String posterstatus;
            public String time;

            public Slide(String posterid, String postername, String posterimg, String postertype, String posterintro, String posterstatus, String time) {
                this.posterid = posterid;
                this.postername = postername;
                this.posterimg = posterimg;
                this.postertype = postertype;
                this.posterintro = posterintro;
                this.posterstatus = posterstatus;
                this.time = time;
            }

            public String getPosterid() {
                return posterid;
            }

            public String getPostername() {
                return postername;
            }

            public String getPosterimg() {
                return posterimg;
            }

            public String getPostertype() {
                return postertype;
            }

            public String getPosterintro() {
                return posterintro;
            }

            public String getPosterstatus() {
                return posterstatus;
            }

            public String getTime() {
                return time;
            }
        }

        public static class User {
            public String uid;
            public String uname;
            public String upic;
            public String uphone;
            public String third;
            public String upwd;
            public String utest_type;
            public String testid;
            public String uintro;
            public String ustatus;
            public String time;

            public User(String uid, String uname, String upic, String uphone, String third, String upwd, String utest_type, String testid, String uintro, String ustatus, String time) {
                this.uid = uid;
                this.uname = uname;
                this.upic = upic;
                this.uphone = uphone;
                this.third = third;
                this.upwd = upwd;
                this.utest_type = utest_type;
                this.testid = testid;
                this.uintro = uintro;
                this.ustatus = ustatus;
                this.time = time;
            }

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

            public String getUname() {
                return uname;
            }

            public String getUpic() {
                return upic;
            }

            public String getUphone() {
                return uphone;
            }

            public String getThird() {
                return third;
            }

            public String getUpwd() {
                return upwd;
            }

            public String getUtest_type() {
                return utest_type;
            }

            public String getTestid() {
                return testid;
            }

            public String getUintro() {
                return uintro;
            }

            public String getUstatus() {
                return ustatus;
            }

            public String getTime() {
                return time;
            }
        }
    }
}

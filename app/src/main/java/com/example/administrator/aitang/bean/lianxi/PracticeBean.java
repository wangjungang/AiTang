package com.example.administrator.aitang.bean.lianxi;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;





/**
 * Author : wangzexin
 * Date : 2017/11/17
 * Describe : 自己定义的习题返回的Bean
 */

public class PracticeBean implements Parcelable {

    /**
     * qid : 14
     * qcid : 1
     * qctypeid : 1
     * qtpath : 0-4-5-82
     * qtitle : zxtc
     * qtid : 82
     * areaid : 1
     * qtgroup : -1
     * qtype : 1
     * qcontent : [["解决城市交通可以尝试一种新机制：公交因为承担着为低收入者、环保人士等提供社会公益性服务的职能，由政府购买公交服务，低价提供给公众；个体自行车出行或纳入到政府购买体系，或推行自行车优先措施；出租车因为价格的高低并不会影响需求者对出租车这种出行方式的选择，所以自然由开放的竞争市场来提供相应服务；对小汽车的使用者可以征收道路使用费，使交通拥堵的外部性内化。这种机制的实施，还要求有配套措施：比如完善公交优先基础设施、鼓励拼车行为、恢复或建设自行车道、制定针对绿色出行的政策法规，引导和鼓励绿色出行方式等。"],["对文中的","\u201c","新机制","\u201d","最恰当的概括是："]]
     * qanswer : [["A.","\u201c主体","\u2014","配套\u201d机制"],["B.","\u201c多元","\u2014","环保\u201d机制"],["C.","\u201c保障","\u2014","市场\u201d机制"],["D.","\u201c公平","\u2014","高效\u201d机制"]]
     * qerror : A
     * qsuccess : C
     * playnum : 0
     * successnum : 0
     * qdes : [["【思路点拨】本题是一道词语理解题。解题时首先锁定要解释词语在原文的具体位置，结合就近原则查找有无对其解释说明的语句。其次，为保证准确率，建议应通读全文，一方面防止文章其他部分再次出现解释说明的句子，致使自己不够全面，另一方面会得到更多提示信息，使自己更加确信。本题考查反向思维和通读全文概括的能力。通过分析文章可知，\u201c"],["【思路点拨】本题是一道词语理解题。解题时首先锁定要解释词语在原文的具体位置，结合就近原则查找有无对其解释说明的语句。其次，为保证准确率，建议应通读全文，一方面防止文章其他部分再次出现解释说明的句子，致使自己不够全面，另一方面会得到更多提示信息，使自己更加确信。本题考查反向思维和通读全文概括的能力。通过分析文章可知，\u201c","新机制\u201d后标点符号为冒号表示解释说明，那么需要对冒号后面的整句话进行理解，也就是对后面四分句进行全面概括。这四个小分句分别谈了公交、个体自行车、小汽车、出租车的问题，对应选项很难快速选择，其实本题考察到了反向思维能力，如果再把最后一句读完就很容易得出选项了。"],["【选项解析】最"],["后一句说"],["\u201c"],["这种机制的实施需要配套措施"],["\u201d"],["，根据这一句说明"],["A"],["选项明显不对，因为\u201c配套措施\u201d和\u201c新机制\u201d是并列关系不是包含关系，排除"],["A"],["；文章最后一句配套措施里提及\u201c制定针对绿色出行的政策法规，引导和鼓励绿色出行方式\u201d说明"],["B"],["选项的\u201c环保\u201d也是配套措施里面的，排除"],["B"],["；"],["D"],["选项\u201c公平"],["\u2014"],["高效\u201d文中未提及。在\u201c新机制\u201d解释说明的四句中，\u201c政府购买公交服务低价提供给公众、纳入到政府购买体系、开放的竞争市场来提供相应服务、征收道路使用费使交通拥堵的外部性内化\u201d等，第一、二、四点体现出了\u201c保障\u201d，第三点体现出了\u201c市场\u201d，所以综合起来对应\u201c保障\u2014市场\u201d机制。故本题正确答案为"],["C"],["选项。"]]
     * qdegree : 50
     * time : 1507615464
     * come : 历年真题
     * kaodian : 词语理解题
     * accuracy : 100
     */

    private String qid;
    private String qcid;
    private String qctypeid;
    private String qtpath;
    private String qtitle;
    private String qtid;
    private String areaid;
    private String qtgroup;
    private String qtype;
    private String qerror;
    private String qsuccess;
    private String playnum;
    private String successnum;
    private String qdegree;
    private String time;
    private String come;
    private String kaodian;
    private String accuracy;
//    private List<List<String>> qcontent;
//    private List<List<String>> qanswer;
//    private List<List<String>> qdes;

    private List<String> qcontent;
//    private List<List<PicUrl>> qcontentPicUrl;

    private List<String> qanswer;
//    private List<List<PicUrl>> qanswerPicUrl;

    private List<String> qdes;
//    private List<List<PicUrl>> qdesPicUrl;


    public PracticeBean(){

    }
    protected PracticeBean(Parcel in) {
        qid = in.readString();
        qcid = in.readString();
        qctypeid = in.readString();
        qtpath = in.readString();
        qtitle = in.readString();
        qtid = in.readString();
        areaid = in.readString();
        qtgroup = in.readString();
        qtype = in.readString();
        qerror = in.readString();
        qsuccess = in.readString();
        playnum = in.readString();
        successnum = in.readString();
        qdegree = in.readString();
        time = in.readString();
        come = in.readString();
        kaodian = in.readString();
        accuracy = in.readString();
        qcontent = in.createStringArrayList();
        qanswer = in.createStringArrayList();
        qdes = in.createStringArrayList();
    }

    public static final Creator<PracticeBean> CREATOR = new Creator<PracticeBean>() {
        @Override
        public PracticeBean createFromParcel(Parcel in) {
            return new PracticeBean(in);
        }

        @Override
        public PracticeBean[] newArray(int size) {
            return new PracticeBean[size];
        }
    };

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getQcid() {
        return qcid;
    }

    public void setQcid(String qcid) {
        this.qcid = qcid;
    }

    public String getQctypeid() {
        return qctypeid;
    }

    public void setQctypeid(String qctypeid) {
        this.qctypeid = qctypeid;
    }

    public String getQtpath() {
        return qtpath;
    }

    public void setQtpath(String qtpath) {
        this.qtpath = qtpath;
    }

    public String getQtitle() {
        return qtitle;
    }

    public void setQtitle(String qtitle) {
        this.qtitle = qtitle;
    }

    public String getQtid() {
        return qtid;
    }

    public void setQtid(String qtid) {
        this.qtid = qtid;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getQtgroup() {
        return qtgroup;
    }

    public void setQtgroup(String qtgroup) {
        this.qtgroup = qtgroup;
    }

    public String getQtype() {
        return qtype;
    }

    public void setQtype(String qtype) {
        this.qtype = qtype;
    }

    public String getQerror() {
        return qerror;
    }

    public void setQerror(String qerror) {
        this.qerror = qerror;
    }

    public String getQsuccess() {
        return qsuccess;
    }

    public void setQsuccess(String qsuccess) {
        this.qsuccess = qsuccess;
    }

    public String getPlaynum() {
        return playnum;
    }

    public void setPlaynum(String playnum) {
        this.playnum = playnum;
    }

    public String getSuccessnum() {
        return successnum;
    }

    public void setSuccessnum(String successnum) {
        this.successnum = successnum;
    }

    public String getQdegree() {
        return qdegree;
    }

    public void setQdegree(String qdegree) {
        this.qdegree = qdegree;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCome() {
        return come;
    }

    public void setCome(String come) {
        this.come = come;
    }

    public String getKaodian() {
        return kaodian;
    }

    public void setKaodian(String kaodian) {
        this.kaodian = kaodian;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public List<String> getQcontent() {
        return qcontent;
    }

//    public void setQcontent(List<List<String>> qcontent) {
//        this.qcontent = qcontent;
//    }

    public void setQcontent(List<String> qcontent) {
        this.qcontent = qcontent;
    }

    public List<String> getQanswer() {
        return qanswer;
    }

    public void setQanswer(List<String> qanswer) {
        this.qanswer = qanswer;
    }

    public List<String> getQdes() {
        return qdes;
    }

    public void setQdes(List<String> qdes) {
        this.qdes = qdes;
    }


//    public List<List<PicUrl>> getQcontentPicUrl() {
//        return qcontentPicUrl;
//    }
//
//    public void setQcontentPicUrl(List<List<PicUrl>> qcontentPicUrl) {
//        this.qcontentPicUrl = qcontentPicUrl;
//    }
//
//    public List<List<PicUrl>> getQanswerPicUrl() {
//        return qanswerPicUrl;
//    }
//
//    public void setQanswerPicUrl(List<List<PicUrl>> qanswerPicUrl) {
//        this.qanswerPicUrl = qanswerPicUrl;
//    }
//
//    public List<List<PicUrl>> getQdesPicUrl() {
//        return qdesPicUrl;
//    }
//
//    public void setQdesPicUrl(List<List<PicUrl>> qdesPicUrl) {
//        this.qdesPicUrl = qdesPicUrl;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(qid);
        parcel.writeString(qcid);
        parcel.writeString(qctypeid);
        parcel.writeString(qtpath);
        parcel.writeString(qtitle);
        parcel.writeString(qtid);
        parcel.writeString(areaid);
        parcel.writeString(qtgroup);
        parcel.writeString(qtype);
        parcel.writeString(qerror);
        parcel.writeString(qsuccess);
        parcel.writeString(playnum);
        parcel.writeString(successnum);
        parcel.writeString(qdegree);
        parcel.writeString(time);
        parcel.writeString(come);
        parcel.writeString(kaodian);
        parcel.writeString(accuracy);
        parcel.writeStringList(qcontent);
        parcel.writeStringList(qanswer);
        parcel.writeStringList(qdes);
    }

//    public static class PicUrl implements Parcelable{
//        public int indexStartOne;
//        public int indexEndOne;
//        public int indexStartTwo;
//        public int indexEndTwo;
//        public String picUrl;
//
//        public int getIndexStartOne() {
//            return indexStartOne;
//        }
//
//        public void setIndexStartOne(int indexStartOne) {
//            this.indexStartOne = indexStartOne;
//        }
//
//        public int getIndexEndOne() {
//            return indexEndOne;
//        }
//
//        public void setIndexEndOne(int indexEndOne) {
//            this.indexEndOne = indexEndOne;
//        }
//
//        public int getIndexStartTwo() {
//            return indexStartTwo;
//        }
//
//        public void setIndexStartTwo(int indexStartTwo) {
//            this.indexStartTwo = indexStartTwo;
//        }
//
//        public int getIndexEndTwo() {
//            return indexEndTwo;
//        }
//
//        public void setIndexEndTwo(int indexEndTwo) {
//            this.indexEndTwo = indexEndTwo;
//        }
//
//        public String getPicUrl() {
//            return picUrl;
//        }
//
//        public void setPicUrl(String picUrl) {
//            this.picUrl = picUrl;
//        }
//
//        public PicUrl() {
//        }
//
//
//        protected PicUrl(Parcel in) {
//            indexStartOne = in.readInt();
//            indexEndOne = in.readInt();
//            indexStartTwo = in.readInt();
//            indexEndTwo = in.readInt();
//            picUrl = in.readString();
//        }
//
//        public static final Creator<PicUrl> CREATOR = new Creator<PicUrl>() {
//            @Override
//            public PicUrl createFromParcel(Parcel in) {
//                return new PicUrl(in);
//            }
//
//            @Override
//            public PicUrl[] newArray(int size) {
//                return new PicUrl[size];
//            }
//        };
//
//        @Override
//        public int describeContents() {
//            return 0;
//        }
//
//        @Override
//        public void writeToParcel(Parcel parcel, int i) {
//            parcel.writeInt(indexStartOne);
//            parcel.writeInt(indexEndOne);
//            parcel.writeInt(indexStartTwo);
//            parcel.writeInt(indexEndTwo);
//            parcel.writeString(picUrl);
//        }
//    }
}

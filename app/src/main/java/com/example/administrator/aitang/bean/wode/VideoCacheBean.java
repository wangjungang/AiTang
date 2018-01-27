package com.example.administrator.aitang.bean.wode;

import com.arialyy.aria.core.inf.AbsEntity;

/**
 * @author wangzexin
 * @date 2018/1/5
 * @describe
 */

public class VideoCacheBean {
    String flagKey;
    String title;
    String zhaiYao;
    String time;
    String teacherName;
    String teacherIntro;
    String teacherPicUrl;
    String videoName;
    String whiteName;
    AbsEntity absEntity;


    public AbsEntity getAbsEntity() {
        return absEntity;
    }

    public void setAbsEntity(AbsEntity absEntity) {
        this.absEntity = absEntity;
    }

    public String getFlagKey() {
        return flagKey;
    }

    public void setFlagKey(String flagKey) {
        this.flagKey = flagKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getZhaiYao() {
        return zhaiYao;
    }

    public void setZhaiYao(String zhaiYao) {
        this.zhaiYao = zhaiYao;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherIntro() {
        return teacherIntro;
    }

    public void setTeacherIntro(String teacherIntro) {
        this.teacherIntro = teacherIntro;
    }

    public String getTeacherPicUrl() {
        return teacherPicUrl;
    }

    public void setTeacherPicUrl(String teacherPicUrl) {
        this.teacherPicUrl = teacherPicUrl;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getWhiteName() {
        return whiteName;
    }

    public void setWhiteName(String whiteName) {
        this.whiteName = whiteName;
    }
}

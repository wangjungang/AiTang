package com.example.administrator.aitang.utils;

import com.example.administrator.aitang.bean.lianxi.PracticeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : wangzexin
 * Date : 2017/11/21
 * Describe : 练习模块拼接html串的工具类
 */

public class HtmlStrUtils {
    private HtmlStrUtils() {

    }

    public static HtmlStrUtils getInstance() {
        return SingetonHolder.instance;
    }

    private static class SingetonHolder {
        private static HtmlStrUtils instance = new HtmlStrUtils();
    }

//    /**
//     * 将获取的数据拼接成html文本展示
//     * <p>
//     * <p>
//     * KSZNLXFragment、CTFXFragment、DaanAdapter这三个类中有使用
//     *
//     * @param strList
//     * @param picList
//     * @return
//     */
//    public String getHtmlStr(List<String> strList, List<List<PracticeBean.PicUrl>> picList) {
//
//        if (null == strList) {
//            strList = new ArrayList<>();
//        }
//        if (null == picList) {
//            picList = new ArrayList<>();
//        }
//
//        String html = "";
//        int index = 0;
//        for (int i = 0; i < strList.size() + picList.size(); i++) {
//            PracticeBean.PicUrl picUrl = null;
//            if (null != picList && picList.size() > 0) {
//                for (int j = 0; j < picList.size(); j++) {
//
//                    //如果正好循环到有图片的位置，将图片拼接进去
//                    if (i == picList.get(j).get(0).getIndexStartTwo()) {
//                        //图片集合中的一个元素可能有多张图片，循环拼接
//                        for (int k = 0; k < picList.get(j).size(); k++) {
//                            picUrl = picList.get(j).get(k);
//                            html += "<img src='" + picUrl.getPicUrl() + "'/><br/>";
//                        }
//
//                        index = i - 1;
//                        break;
//                    } else {
//                        html += strList.get(index) + "<br/>";
//                    }
//
//                }
//                index++;
//            } else {
//                html += strList.get(i) + "<br/>";
//            }
//
//        }
//        return html;
//    }


    /**
     * 将获取的数据拼接成html文本展示
     * <p>
     * <p>
     * KSZNLXFragment、CTFXFragment、DaanAdapter这三个类中有使用
     *
     * @param strList
     * @return
     */
    public String getHtmlString(List<String> strList) {

        if (null == strList) {
            strList = new ArrayList<>();
        }
        StringBuilder htmlBuilder = new StringBuilder("");
//        String html = "";
        for (int i = 0; i < strList.size(); i++) {
            if (i == (strList.size() - 1)) {
                htmlBuilder.append(strList.get(i));
//                html += strList.get(i);
            } else {
                htmlBuilder.append(strList.get(i) + "<br/>");
//                html += strList.get(i) + "<br/>";
            }
        }
        return htmlBuilder.toString();
    }

}




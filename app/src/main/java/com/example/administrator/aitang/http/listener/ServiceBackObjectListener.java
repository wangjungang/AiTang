package com.example.administrator.aitang.http.listener;


import com.example.administrator.aitang.http.result.ServiceResult;

/**
 * Created by wangzexin on 2017/2/14.
 */
public interface ServiceBackObjectListener {

    /**
     * 回调
     *
     * @param result 返回码以及描述
     * @param response  返回的Json数据
     */
    void onSuccess(ServiceResult result, String response);

    void onFailure(ServiceResult result, Object obj);
}

package com.example.administrator.aitang.http.listener;


import com.example.administrator.aitang.http.result.CommonResult;

/**
 * Created by wangzexin on 2017/2/14.
 */
public interface NetworkListener {
    void onResponse(CommonResult result, Object data);

}

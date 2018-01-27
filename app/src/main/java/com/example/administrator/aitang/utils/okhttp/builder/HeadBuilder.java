package com.example.administrator.aitang.utils.okhttp.builder;


import com.example.administrator.aitang.utils.okhttp.OkHttpUtils;
import com.example.administrator.aitang.utils.okhttp.request.OtherRequest;
import com.example.administrator.aitang.utils.okhttp.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}

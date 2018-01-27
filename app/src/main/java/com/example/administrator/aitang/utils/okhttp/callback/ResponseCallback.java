package com.example.administrator.aitang.utils.okhttp.callback;


import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/7/25.
 */

public abstract class ResponseCallback extends Callback<Response> {
    @Override
    public Response parseNetworkResponse(Response response, int id) throws Exception {
        //解决okhttp报java.lang.IllegalStateException: closed,java.lang.IllegalStateException: closed错误
        MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        return response.newBuilder().body(ResponseBody.create(mediaType, content))
                .build();
    }
}

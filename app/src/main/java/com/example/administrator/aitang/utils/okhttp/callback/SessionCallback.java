package com.example.administrator.aitang.utils.okhttp.callback;


import java.util.List;

import okhttp3.Headers;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/7/10.
 * 获取服务器传来的session
 * 传递cookie:
 * Request request = new Request.Builder()
 * .addHeader("cookie",s)//s为服务器传来的session
 * .url(App.url_login)
 * .post(body)
 * .build();
 */

public abstract class SessionCallback extends Callback<String> {
    @Override
    public String parseNetworkResponse(Response response, int id) throws Exception {
        Headers headers = response.headers();
        List<String> cookies = headers.values("Set-Cookie");
        String session = cookies.get(0);
        return session.substring(0, session.indexOf(";"));
    }
}

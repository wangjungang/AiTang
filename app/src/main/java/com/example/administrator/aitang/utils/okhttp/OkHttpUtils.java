package com.example.administrator.aitang.utils.okhttp;


import com.example.administrator.aitang.utils.okhttp.builder.GetBuilder;
import com.example.administrator.aitang.utils.okhttp.builder.HeadBuilder;
import com.example.administrator.aitang.utils.okhttp.builder.OtherRequestBuilder;
import com.example.administrator.aitang.utils.okhttp.builder.PostFileBuilder;
import com.example.administrator.aitang.utils.okhttp.builder.PostFormBuilder;
import com.example.administrator.aitang.utils.okhttp.builder.PostStringBuilder;
import com.example.administrator.aitang.utils.okhttp.callback.Callback;
import com.example.administrator.aitang.utils.okhttp.request.RequestCall;
import com.example.administrator.aitang.utils.okhttp.utils.Platform;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.Executor;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * 网络访问工具类：
 * <p>
 * 解析：
 * 1.一个object
 * OkHttpUtils.post().url(MyConstant.URL)
 * .addParams("username", et_phone.getText().toString())
 * .addParams("password", et_pwd.getText().toString())
 * .build().execute(new StringCallback()
 * <p>
 * 2.多个object
 * JSONArray jsonArray = new JSONArray(jsonData);
 * for (int i=0; i < jsonArray.length(); i++)    {
 * JSONObject jsonObject = jsonArray.getJSONObject(i);
 * String id = jsonObject.getString("id");
 * String name = jsonObject.getString("name");
 * }
 * <p>
 * 3.使用gson
 * Gson gson = new Gson();
 * MinSuBean bean = gson.fromJson(response, MinSuBean.class);
 * String name = bean.getName();
 * String picUrl = bean.getPicUrl();
 */
public class OkHttpUtils {
    public static final long DEFAULT_MILLISECONDS = 50000;//网络请求超时时间
    private volatile static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Platform mPlatform;

    public OkHttpUtils(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        } else {
            mOkHttpClient = okHttpClient;
        }

        mPlatform = Platform.get();
    }


    public static OkHttpUtils initClient(OkHttpClient okHttpClient) {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtils(okHttpClient);
                }
            }
        }
        return mInstance;
    }

    public static OkHttpUtils getInstance() {
        return initClient(null);
    }


    public Executor getDelivery() {
        return mPlatform.defaultCallbackExecutor();
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    /**
     * Get请求
     * String url = "http://www.csdn.net/";
     * OkHttpUtils
     * .get()
     * .url(url)
     * .addParams("username", "hyman")
     * .addParams("password", "123")
     * .build()
     * .execute(new StringCallback());
     */
    public static GetBuilder get() {
        return new GetBuilder();
    }


    /**
     * Post Json:提交一个Gson字符串到服务器端，注意：传递JSON的时候，不要通过addHeader去设置contentType，而使用.mediaType(MediaType.parse("application/json; charset=utf-8")).。
     * OkHttpUtils
     * .postString()
     * .url(url)
     * .content(new Gson().toJson(new User("zhy", "123")))
     * .mediaType(MediaType.parse("application/json; charset=utf-8"))
     * .build()
     * .execute(new MyStringCallback());
     *
     * @return
     */
    public static PostStringBuilder postString() {
        return new PostStringBuilder();
    }

    /**
     * Post文件:将文件作为请求体，发送到服务器。
     * OkHttpUtils
     * .postFile()
     * .url(url)
     * .file(file)
     * .build()
     * .execute(new MyStringCallback());
     */
    public static PostFileBuilder postFile() {
        return new PostFileBuilder();
    }


    /**
     * Post 表单
     * OkHttpUtils
     * .post()
     * .url(url)
     * .addParams("username", "hyman")
     * .addParams("password", "123")
     * .build()
     * .execute(callback);
     * <p>
     * Post表单形式上传多个文件：支持单个多个文件，addFile的第一个参数为文件的key，即类别表单中<input type="file" name="mFile"/>的name属性。
     * OkHttpUtils.post()//
     * .addFile("mFile", "messenger_01.png", file)//
     * .addFile("mFile", "test1.txt", file2)//
     * .url(url)
     * .params(params)//
     * .headers(headers)//
     * .build()//
     * .execute(new MyStringCallback());
     */
    public static PostFormBuilder post() {
        return new PostFormBuilder();
    }

    public static OtherRequestBuilder put() {
        return new OtherRequestBuilder(METHOD.PUT);
    }

    public static HeadBuilder head() {
        return new HeadBuilder();
    }

    public static OtherRequestBuilder delete() {
        return new OtherRequestBuilder(METHOD.DELETE);
    }

    public static OtherRequestBuilder patch() {
        return new OtherRequestBuilder(METHOD.PATCH);
    }

    public void execute(final RequestCall requestCall, Callback callback) {
        if (callback == null)
            callback = Callback.CALLBACK_DEFAULT;
        final Callback finalCallback = callback;
        final int id = requestCall.getOkHttpRequest().getId();

        requestCall.getCall().enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                sendFailResultCallback(call, e, finalCallback, id);
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                try {
                    if (call.isCanceled()) {
                        sendFailResultCallback(call, new IOException("Canceled!"), finalCallback, id);
                        return;
                    }

                    if (!finalCallback.validateReponse(response, id)) {
                        sendFailResultCallback(call, new IOException("request failed , reponse's code is : " + response.code()), finalCallback, id);
                        return;
                    }

                    Object o = finalCallback.parseNetworkResponse(response, id);
                    sendSuccessResultCallback(o, finalCallback, id);
                } catch (Exception e) {
                    sendFailResultCallback(call, e, finalCallback, id);
                } finally {
                    if (response.body() != null)
                        response.body().close();
                }

            }
        });
    }


    public void sendFailResultCallback(final Call call, final Exception e, final Callback callback, final int id) {
        if (callback == null) return;

        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onError(call, e, id);
                callback.onAfter(id);
            }
        });
    }

    public void sendSuccessResultCallback(final Object object, final Callback callback, final int id) {
        if (callback == null) return;
        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onResponse(object, id);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onAfter(id);
            }
        });
    }

    public void cancelTag(Object tag) {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    public static class METHOD {
        public static final String HEAD = "HEAD";
        public static final String DELETE = "DELETE";
        public static final String PUT = "PUT";
        public static final String PATCH = "PATCH";
    }
}


package com.example.administrator.aitang.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.http.error.ErrorCode;
import com.example.administrator.aitang.http.listener.NetworkListener;
import com.example.administrator.aitang.http.result.CommonResult;
import com.example.administrator.aitang.utils.okhttp.OkHttpUtils;
import com.example.administrator.aitang.utils.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.Call;


/**
 * Created by wangzexin on 2017/2/14.
 */
public class RequestManager {
    // 协议ID
    public static int HTTP = 100;
    private static final String TAG = RequestManager.class.getSimpleName();
    private static volatile RequestManager mInstance;

    /**
     * 获取单例引用
     *
     * @return
     */
    public static RequestManager getInstance() {
        if (null == mInstance) {
            mInstance = new RequestManager();
        }
        return mInstance;
    }

    /**
     * okHttp异步请求统一入口
     *
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     **/
    public void requestPost(List<String> mImgUrls, String keyInterface, Map<String, String> paramsMap, Object tag, NetworkListener callBack) {
        if (mImgUrls != null) {
            requestPostJsonAndFile(mImgUrls, keyInterface, paramsMap, callBack);
        } else {

            if (null == tag) {
                requestPostJson(keyInterface, paramsMap, callBack);
            } else {
                requestPostJsonWithTag(keyInterface, paramsMap, tag, callBack);
            }


        }

    }


    /**
     * okHttp异步请求统一入口
     *
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     **/
    public void requestGet(List<String> mImgUrls, String keyInterface, Map<String, String> paramsMap, Object tag, NetworkListener callBack) {
        if (mImgUrls != null) {
//            requestGetJsonAndFile(mImgUrls, paramsMap, callBack);
        } else {
            if (null == tag) {
                requestGetJson(keyInterface, paramsMap, callBack);
            } else {
                requestGetJsonWithTag(keyInterface, paramsMap, tag, callBack);
            }

        }

    }

    /**
     * 上传多张图片和文字
     *
     * @param mImgUrls  多张照片的路径list
     * @param paramsMap 参数
     * @param callBack  回调
     */
    private void requestPostJsonAndFile(List<String> mImgUrls, String keyInterface, Map<String, String> paramsMap, final NetworkListener callBack) {
        if (!isNetworkConnected(Myapp.getAppContext())) {
            failureCallBack(callBack);
            return;
        }

        for (int i = 0; i < mImgUrls.size(); i++) {
            File file = new File(mImgUrls.get(i));
            OkHttpUtils.post().addFile("img", file.getName(), file)
                    .url(keyInterface)
                    .params(paramsMap)
                    .id(HTTP)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            failureCallBack(callBack);
                            Log.e(TAG, e.toString());
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            successCallBack(response, callBack);
                        }
                    });
        }
    }

    /**
     * okHttp post异步请求
     *
     * @param keyInterface 接口名
     * @param paramsMap    参数
     * @param callBack     回调
     */
    private void requestPostJson(String keyInterface, Map<String, String> paramsMap, final NetworkListener callBack) {
        if (!isNetworkConnected(Myapp.getAppContext())) {
//                callBack.onResponse(CommonResult.resultWithCode(ErrorCode.ERROR_CODE_NETWORK_UNAVAILABLE), null);
            failureCallBack(callBack);
            return;

        }
        OkHttpUtils
                .post()
                .url(keyInterface)
                .params(paramsMap)
                .id(HTTP)
                .build().execute(new StringCallback() {

            @Override
            public void onError(Call call, Exception e, int id) {
                failureCallBack(callBack);
                Log.e(TAG, e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                successCallBack(response, callBack);
            }

        });
    }

    /**
     * okHttp 带有tag的post异步请求，可以取消请求
     *
     * @param keyInterface 接口
     * @param paramsMap    参数
     * @param tag          tag标志
     * @param callBack     回调
     */
    private void requestPostJsonWithTag(String keyInterface, Map<String, String> paramsMap, Object tag, final NetworkListener callBack) {
        if (!isNetworkConnected(Myapp.getAppContext())) {
//                callBack.onResponse(CommonResult.resultWithCode(ErrorCode.ERROR_CODE_NETWORK_UNAVAILABLE), null);
            failureCallBack(callBack);
            return;

        }
        OkHttpUtils
                .post()
                .tag(tag)
                .url(keyInterface)
                .params(paramsMap)
                .id(HTTP)
                .build().execute(new StringCallback() {

            @Override
            public void onError(Call call, Exception e, int id) {
                if (e.toString().contains("closed")) {
                    //主动取消
                    Log.e(TAG, e.toString());
                } else {
                    //其他
                    failureCallBack(callBack);
                }
                Log.e(TAG, e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                successCallBack(response, callBack);
            }

        });
    }


    /**
     * okHttp get异步请求
     *
     * @param keyInterface 接口
     * @param paramsMap    参数
     * @param callBack     回调
     */
    private void requestGetJson(String keyInterface, Map<String, String> paramsMap, final NetworkListener callBack) {
        if (!isNetworkConnected(Myapp.getAppContext())) {
//                callBack.onResponse(CommonResult.resultWithCode(ErrorCode.ERROR_CODE_NETWORK_UNAVAILABLE), null);
            failureCallBack(callBack);
            return;

        }
        OkHttpUtils
                .get()
                .url(keyInterface)
                .params(paramsMap)
                .id(HTTP)
                .build().execute(new StringCallback() {

            @Override
            public void onError(Call call, Exception e, int id) {
                failureCallBack(callBack);
                Log.e(TAG, e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                successCallBack(response, callBack);
            }

        });
    }


    /**
     * okHttp 带有tag的get异步请求，可以取消请求
     *
     * @param keyInterface 接口
     * @param paramsMap    参数
     * @param tag          tag标志
     * @param callBack     回调
     */
    private void requestGetJsonWithTag(String keyInterface, Map<String, String> paramsMap, Object tag, final NetworkListener callBack) {
        if (!isNetworkConnected(Myapp.getAppContext())) {
//                callBack.onResponse(CommonResult.resultWithCode(ErrorCode.ERROR_CODE_NETWORK_UNAVAILABLE), null);
            failureCallBack(callBack);
            return;

        }
        OkHttpUtils
                .get()
                .tag(tag)
                .url(keyInterface)
                .params(paramsMap)
                .id(HTTP)
                .build().execute(new StringCallback() {

            @Override
            public void onError(Call call, Exception e, int id) {
                if (e.toString().contains("closed")) {
                    //主动取消
                    Log.e(TAG, e.toString());
                } else {
                    //其他
                    failureCallBack(callBack);
                }

                Log.e(TAG, e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                successCallBack(response, callBack);
            }

        });
    }


    /**
     * 统一同意处理成功信息
     */
    private void successCallBack(String response, NetworkListener callBack) {
        JSONObject jsonObject = null;
        String code = "";
        if (callBack != null) {
            try {
                jsonObject = new JSONObject(response);
//                JSONObject codeJson = jsonObject.optJSONObject("status");
                code = jsonObject.optString("code");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            callBack.onResponse(CommonResult.resultWithCode(code), response);
        }
    }

    /**
     * 统一处理失败信息
     */
    private void failureCallBack(final NetworkListener callBack) {

        if (callBack != null) {
            callBack.onResponse(CommonResult.resultWithCode(ErrorCode.ERROR_CODE_NETWORK_UNAVAILABLE), null);
        }
    }


//    /**
//     * 上传单张图片和文字
//     *
//     * @param mImgUrl
//     * @param paramsMap
//     * @param callBack
//     */
//    private void requestPostJsonAndFile(String mImgUrl, Map<String, String> paramsMap, final NetworkListener callBack) {
//        if (!isNetworkConnected(Myapp.getAppContext())) {
//            failureCallBack(callBack);
//            return;
//        }
//        File file = new File(mImgUrl);
//        OkHttpUtils.post().addFile("img", file.getName(), file)
//                .url(ServerConfig.API)
//                .params(paramsMap)
//                .id(ServerConfig.HTTP)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        failureCallBack(callBack);
//                        Log.e(TAG, e.toString());
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        successCallBack(response, callBack);
//                    }
//                });
//    }

    /**
     * 判断是否有网络连接
     *
     * @param context
     * @return
     */
    public boolean isNetworkConnected(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {   //判断网络连接是否打开
            return mNetworkInfo.isConnected();
        }
        return false;
    }


//    /**
//     * 统一为请求添加头信息
//     *
//     * @return
//     */
//    private Request.Builder addHeaders() {
//        Request.Builder builder = new Request.Builder()
//                .addHeader("Connection", "keep-alive")
//                .addHeader("platform", "2")
//                .addHeader("phoneModel", Build.MODEL)
//                .addHeader("systemVersion", Build.VERSION.RELEASE)
//                .addHeader("appVersion", "3.2.0");
//        return builder;
//    }
//
//    public void setCertificates(InputStream... certificates) {
//        try {
//            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
//            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//            keyStore.load(null);
//            int index = 0;
//            for (InputStream certificate : certificates) {
//                String certificateAlias = Integer.toString(index++);
//                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
//
//                try {
//                    if (certificate != null)
//                        certificate.close();
//                } catch (IOException e) {
//                }
//            }
//
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//
//            TrustManagerFactory trustManagerFactory =
//                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//
//            trustManagerFactory.init(keyStore);
//            sslContext.init
//                    (
//                            null,
//                            trustManagerFactory.getTrustManagers(),
//                            new SecureRandom()
//                    );
////            HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
////            mOkHttpClient.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//    /**
//     *  okHttp同步请求统一入口
//     * @param actionUrl  接口地址
//     * @param requestType 请求类型
//     * @param paramsMap   请求参数
//     */
//    public void requestSyn(String actionUrl, int requestType, HashMap<String, String> paramsMap) {
//        switch (requestType) {
//            case TYPE_GET:
//                requestGetBySyn(actionUrl, paramsMap);
//                break;
//            case TYPE_POST_JSON:
//                requestPostBySyn(actionUrl, paramsMap);
//                break;
//            case TYPE_POST_FORM:
//                requestPostBySynWithForm(actionUrl, paramsMap);
//                break;
//        }
//    }

}

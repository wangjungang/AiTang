package com.example.administrator.aitang.http;


import com.example.administrator.aitang.http.listener.NetworkListener;
import com.example.administrator.aitang.http.listener.ServiceBackObjectListener;
import com.example.administrator.aitang.http.result.CommonResult;
import com.example.administrator.aitang.http.result.ServiceResult;
import com.example.administrator.aitang.utils.okhttp.OkHttpUtils;

import java.util.Map;

/**
 * Created by wangzexin on 2017/2/14.
 */
public class HttpManager {
    private static HttpManager instance;
//    private LoginService loginService = null;//登录模块service
//    private HttpManager() {
//        loginService = new LoginServiceImpl();
//    }
//public LoginService getLoginService() {
//    return loginService;
//}

    public static HttpManager getInstance() {
        if (null == instance) {
            instance = new HttpManager();
        }
        return instance;
    }


    /**
     * post请求
     *
     * @param keyInterface 接口名称
     * @param paramsMap    参数
     * @param serviceBack  回调
     */
    public void post(String keyInterface, Map paramsMap, final ServiceBackObjectListener serviceBack) {
        RequestManager.getInstance().requestPost(null, keyInterface, paramsMap, null,
                new NetworkListener() {
                    @Override
                    public void onResponse(CommonResult result, Object data) {
                        if (null != serviceBack) {

                            //成功
                            if (result.isSuccess()) {
//                                LoginInfo loginInfo = new Gson().fromJson(data.toString(), LoginInfo.class);
                                serviceBack.onSuccess(new ServiceResult(result.getCode(), result.getDesc()),
                                        data.toString());

                            } else {//失败
                                serviceBack.onFailure(new ServiceResult(result.getCode(), result.getDesc()),
                                        null);
                            }
                        }
                    }
                });
    }


    /**
     * post请求
     *
     * @param keyInterface 接口名称
     * @param paramsMap    参数
     * @param serviceBack  回调
     */
    public void postWithTag(String keyInterface, Map paramsMap, Object tag, final ServiceBackObjectListener serviceBack) {
        RequestManager.getInstance().requestPost(null, keyInterface, paramsMap, tag,
                new NetworkListener() {
                    @Override
                    public void onResponse(CommonResult result, Object data) {
                        if (null != serviceBack) {

                            //成功
                            if (result.isSuccess()) {
//                                LoginInfo loginInfo = new Gson().fromJson(data.toString(), LoginInfo.class);
                                serviceBack.onSuccess(new ServiceResult(result.getCode(), result.getDesc()),
                                        data.toString());

                            } else {//失败
                                serviceBack.onFailure(new ServiceResult(result.getCode(), result.getDesc()),
                                        null);
                            }
                        }
                    }
                });
    }

    /**
     * get请求
     *
     * @param keyInterface 接口名称
     * @param paramsMap    参数
     * @param serviceBack  回调
     */
    public void get(String keyInterface, Map paramsMap, final ServiceBackObjectListener serviceBack) {
        RequestManager.getInstance().requestGet(null, keyInterface, paramsMap, null,
                new NetworkListener() {
                    @Override
                    public void onResponse(CommonResult result, Object data) {
                        if (null != serviceBack) {

                            //成功
                            if (result.isSuccess()) {
//                                LoginInfo loginInfo = new Gson().fromJson(data.toString(), LoginInfo.class);
                                serviceBack.onSuccess(new ServiceResult(result.getCode(), result.getDesc()),
                                        data.toString());

                            } else {//失败
                                serviceBack.onFailure(new ServiceResult(result.getCode(), result.getDesc()),
                                        null);
                            }
                        }
                    }
                });
    }

    /**
     * 有tag的get请求，可以根据tag取消请求
     *
     * @param keyInterface 接口名称
     * @param paramsMap    参数
     * @param tag          tag标志
     * @param serviceBack  回调
     */
    public void getWithTag(String keyInterface, Map paramsMap, Object tag, final ServiceBackObjectListener serviceBack) {
        RequestManager.getInstance().requestGet(null, keyInterface, paramsMap, tag,
                new NetworkListener() {
                    @Override
                    public void onResponse(CommonResult result, Object data) {
                        if (null != serviceBack) {

                            //成功
                            if (result.isSuccess()) {
//                                LoginInfo loginInfo = new Gson().fromJson(data.toString(), LoginInfo.class);
                                serviceBack.onSuccess(new ServiceResult(result.getCode(), result.getDesc()),
                                        data.toString());

                            } else {//失败
                                serviceBack.onFailure(new ServiceResult(result.getCode(), result.getDesc()),
                                        null);
                            }
                        }
                    }
                });
    }


    /**
     * 根据tag取消请求
     *
     * @param tag
     */
    public void cancleRequest(Object tag) {
        OkHttpUtils.getInstance().cancelTag(tag);//取消以Activity.this作为tag的请求
    }
}

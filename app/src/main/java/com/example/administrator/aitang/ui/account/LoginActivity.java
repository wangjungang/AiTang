package com.example.administrator.aitang.ui.account;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.wode.UserInfoBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.http.HttpManager;
import com.example.administrator.aitang.http.error.ErrorCode;
import com.example.administrator.aitang.http.listener.ServiceBackObjectListener;
import com.example.administrator.aitang.http.result.ServiceResult;
import com.example.administrator.aitang.ui.MainActivity;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.example.administrator.aitang.utils.MessageDigestUtils;
import com.example.administrator.aitang.utils.NetUtils;
import com.example.administrator.aitang.utils.SocialHelperUtil;
import com.example.administrator.aitang.utils.basic.StringUtils;
import com.example.administrator.aitang.zhibo.DemoCache;
import com.example.administrator.aitang.zhibo.im.config.AuthPreferences;
import com.example.administrator.aitang.zhibo.im.config.UserPreferences;
import com.example.administrator.aitang.zhibo.im.ui.dialog.EasyAlertDialogHelper;
import com.google.gson.Gson;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

import net.arvin.socialhelper.callback.SocialLoginCallback;
import net.arvin.socialhelper.entities.ThirdInfoEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by 武培培 on 2017/10/24.
 */

public class LoginActivity extends MyBaseActivity implements View.OnClickListener {
    @BindView(R.id.et_account)
    EditText et_account;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.bt_login)
    Button bt_login;
    @BindView(R.id.tv_forget_pwd)
    TextView tv_forget_pwd;
    @BindView(R.id.iv_qq)
    ImageView iv_qq;
    @BindView(R.id.iv_weixin)
    ImageView iv_weixin;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    private AbortableFuture<LoginInfo> loginRequest;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void init() {
        super.init();
        setImmerBarcolor(llContent);

        setClickListener();
    }



    private void setClickListener() {
        bt_login.setOnClickListener(this);
        iv_qq.setOnClickListener(this);
        iv_weixin.setOnClickListener(this);
        tv_forget_pwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_qq:
                SocialHelperUtil.getInstance().socialHelper().loginQQ(this, new SocialLoginCallback() {
                    @Override
                    public void loginSuccess(ThirdInfoEntity info) {

                        String opeanId = info.getOpenId();
                        String name = info.getNickname();
                        if (!StringUtils.isEmpty(opeanId)) {
                            toLogin(opeanId, name);
                        }
                    }

                    @Override
                    public void socialError(String msg) {
//                        toast(msg);
                    }
                });
                break;
            case R.id.iv_weixin:
                SocialHelperUtil.getInstance().socialHelper().loginWX(this, new SocialLoginCallback() {
                    @Override
                    public void loginSuccess(ThirdInfoEntity info) {

                        String opeanId = info.getUnionId();
                        String name = info.getNickname();
                        if (!StringUtils.isEmpty(opeanId)) {
                            toLogin(opeanId, name);
                        }
                    }

                    @Override
                    public void socialError(String msg) {
                        toast(msg);
                    }
                });
                break;
            case R.id.bt_login:
                login();
                break;
            case R.id.tv_forget_pwd:
                jumpTo(ResetPasswordActivity.class, false);
                break;
            default:
                break;
        }
    }

    private void login() {
        if (!NetUtils.isNetworkConnected(this)) {
            toast("当前无可用网络，请检查后重试");
            return;
        } else {
            if (StringUtils.isEmpty(et_account.getText().toString())) {
                toast("请输入账号");
                return;
            }
            if (et_account.getText().toString().length() < 6) {
                toast("登录账号至少6位");
                return;
            }
            if (StringUtils.isEmpty(et_pwd.getText().toString())) {
                toast("密码不能为空");
                return;
            }

            toLogin("","");
        }
    }

    /**
     * 登陆接口，登陆成功单独保存uid、token、手机号，之后调用用户信息接口保存成实体类
     *
     * @param opeanID 第三方登录的id
     * @param name 第三方的昵称
     */
    private void toLogin(String opeanID, String name) {

        showProgressDialog();
        Map map = new HashMap();

        //如果opeanID不为空说明是第三方登录，否则为密码登录
        if (StringUtils.isEmpty(opeanID)) {
            map.put("uname", et_account.getText().toString());
            map.put("upwd", MessageDigestUtils.getMD5(et_pwd.getText().toString()));
            Myapp.spUtil.setThirdLogin(false);
        } else {
            map.put("uname", opeanID);
            map.put("name", name);
            Myapp.spUtil.setThirdLogin(true);
        }


        HttpManager.getInstance().post(MyConstant.LOGIN, map, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {
                Log.i("==========>", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String code = jsonObject.getString("code");
                    if (code.equals("200")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        String uid = jsonObject1.getString("uid");
                        String token = jsonObject1.getString("token");
                        String acctoken = jsonObject1.getString("acctoken");//IM的登录密码

                        Myapp.spUtil.setLogin(true);
                        Myapp.spUtil.setUid(uid);
                        Myapp.spUtil.setToken(token);
                        Myapp.spUtil.setPhone(et_account.getText().toString());

//                        //获取用户信息
//                        getUserInfo(uid, token, acctoken);

                        //IM登录
                        doLogin(acctoken, uid, token);
                    }
                } catch (JSONException e) {
                    hideProgressDialog();
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {
                if (result.getCode().equals(ErrorCode.ERROR_CODE_006)) {
                    toast("账号或密码错误");
                } else {
                    toast("登录失败");
                }

                hideProgressDialog();
            }
        });
    }

//    /**
//     * 获取用户信息
//     *
//     * @param uid
//     * @param token
//     * @param imToken IM的登录密码
//     */
//    private void getUserInfo(final String uid, final String token, final String imToken) {
//
//        Map paramsMap = new HashMap();
//
//        paramsMap.put("uid", uid);
//        paramsMap.put("token", token);
//
//        HttpManager.getInstance().get(MyConstant.GETUSERINFO, paramsMap, new ServiceBackObjectListener() {
//            @Override
//            public void onSuccess(ServiceResult result, String response) {
//
//                UserInfoBean userInfoBean = new Gson().fromJson(response, UserInfoBean.class);
//
//                if (null != userInfoBean) {
//                    //保存用户信息实体类
//                    Myapp.spUtil.putBean(LoginActivity.this, MyConstant.USERINFO, userInfoBean);
//
//                    Myapp.spUtil.setLogin(true);
//                    Myapp.spUtil.setUid(uid);
//                    Myapp.spUtil.setToken(token);
//                    Myapp.spUtil.setPhone(et_account.getText().toString());
//                }
//                //IM登录
//                doLogin(imToken, uid, token);
//            }
//
//            @Override
//            public void onFailure(ServiceResult result, Object obj) {
//                hideProgressDialog();
//                toast(result.getDesc());
//            }
//        });
//    }

    /**
     * IM 登录
     *
     * @param imToken IM的密码
     * @param uid     IM的账号
     * @param token   普通登录token
     */
    private void doLogin(final String imToken, final String uid, final String token) {
        loginRequest = NIMClient.getService(AuthService.class).login(new LoginInfo(uid, imToken));
        loginRequest.setCallback(new RequestCallback() {
            @Override
            public void onSuccess(Object param) {
                onLoginDone();
                String account = et_account.getText().toString();
                DemoCache.setAccount(uid);
                saveLoginInfo(uid, imToken);
//                Myapp.spUtil.setLogin(true);
//                Myapp.spUtil.setUid(uid);
//                Myapp.spUtil.setToken(token);
//                Myapp.spUtil.setPhone(et_account.getText().toString());
                // 初始化消息提醒
                NIMClient.toggleNotification(UserPreferences.getNotificationToggle());

                // 初始化免打扰
                NIMClient.updateStatusBarNotificationConfig(UserPreferences.getStatusConfig());
                hideProgressDialog();
                Myapp.clearActivitiesExceptMe(LoginActivity.class);
                jumpTo(MainActivity.class, true);
            }

            @Override
            public void onFailed(int code) {
                onLoginDone();
                if (code == 302) {
                    Log.d("TAG", "onFailed: " + "IM账号或密码错误");
                } else if (code == 404) {
                    Log.d("TAG", "onFailed: " + "IM账号不存在");
                } else {
                    Log.d("TAG", "onFailed: " + "IM登录失败");
                }
            }

            @Override
            public void onException(Throwable exception) {
                onLoginDone();
            }
        });
    }

    private void onLoginDone() {
        loginRequest = null;
        hideProgressDialog();
    }

    private void saveLoginInfo(final String account, final String token) {
        AuthPreferences.saveUserAccount(account);
        AuthPreferences.saveUserToken(token);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && SocialHelperUtil.getInstance().socialHelper() != null) {//qq分享如果选择留在qq，通过home键退出，再进入app则不会有回调
            SocialHelperUtil.getInstance().socialHelper().onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消三方登录的回调绑定
        SocialHelperUtil.getInstance().socialHelper().clear();
    }
}

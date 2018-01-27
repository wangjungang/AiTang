package com.example.administrator.aitang.ui.account;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.account.ExameTypeAdapter;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.ExameTypeBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.fragment.LianxiFragment;
import com.example.administrator.aitang.http.HttpManager;
import com.example.administrator.aitang.http.error.ErrorCode;
import com.example.administrator.aitang.http.error.ErrorDes;
import com.example.administrator.aitang.http.listener.ServiceBackObjectListener;
import com.example.administrator.aitang.http.result.ServiceResult;
import com.example.administrator.aitang.ui.MainActivity;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.example.administrator.aitang.utils.basic.StringUtils;
import com.example.administrator.aitang.zhibo.DemoCache;
import com.example.administrator.aitang.zhibo.im.config.AuthPreferences;
import com.example.administrator.aitang.zhibo.im.config.UserPreferences;
import com.google.gson.Gson;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by 武培培 on 2017/10/23.
 * 要参加的考试
 */

public class ExameTypeAddressActivity extends MyBaseActivity implements View.OnClickListener {
    @BindView(R.id.headerview)
    LinearLayout llHeaderView;
    @BindView(R.id.lsv_exame)
    ListView listView;
    @BindView(R.id.iv_header_left)
    ImageView iv_left;
    @BindView(R.id.tv_header_right)
    TextView tv_right;
    ExameTypeAdapter adapter;
    String parentid;
    List<ExameTypeBean.Data> datas = new ArrayList<>();
    String testid = "";
    String testtype = "";
    private String flag = "";//跳转过来的标志，1为注册界面跳转 2为首页练习和个人设置

    private Bundle bundle;

    private AbortableFuture<LoginInfo> loginRequest;//网易云信的登录

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Myapp.shitiTypeActivities.remove(this);
    }

    @Override
    public void init() {
        super.init();
        Myapp.shitiTypeActivities.add(this);
        setImmerBarcolor(llHeaderView);
        aboutRefreshView(true);
        xRefreshView.setPullLoadEnable(false);
        iv_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        xRefreshView.startRefresh();
        adapter = new ExameTypeAdapter(this, datas);
        listView.setAdapter(adapter);

        bundle = getIntent().getExtras();
        parentid = bundle.getString("prentid");
        flag = bundle.getString("flag", "");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                testid = datas.get(i).getTestid();
                String testids = datas.get(i).getTestid();
                String testpath = datas.get(i).getTestpath();
                testtype = testpath + "-" + testids;
                adapter.changCheckItemn(String.valueOf(i));
            }
        });

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.exam_type_address_layout);
    }

    @Override
    public void refresh() {
        super.refresh();
        requestExameType();
    }


    private void requestExameType() {

        Map paramsMap = new HashMap();
        paramsMap.put("parentid", parentid);
        HttpManager.getInstance().get(MyConstant.EXAMETYPE, paramsMap, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {
                ExameTypeBean bean = new Gson().fromJson(response, ExameTypeBean.class);
                datas = bean.getData();
                if (datas.size() > 0) {
                    adapter.addAll(datas, true);
                    adapter.notifyDataSetChanged();
                }
                xRefreshView.stopRefresh();
                xRefreshView.stopLoadMore();
            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {
                if (result.getCode().equals(ErrorCode.ERROR_CODE_201)) {
                    toast("没有考试信息");
                } else {
                    toast(ErrorDes.ERROR_INFO);
                }
                xRefreshView.stopRefresh();
                xRefreshView.stopLoadMore();
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                this.finish();
                break;
            case R.id.tv_header_right:
                if (TextUtils.isEmpty(testtype)) {
                    toast("请选择要参加的考试");
                } else {
                    if (flag.equals("1")) {//注册界面
                        requestRegist();
                    } else {//首页或者个人设置，要调用接口修改考试信息，并关闭界面
                        requestChange();
                    }

                }
                break;
            default:
                break;

        }
    }

    /**
     * 注册
     */
    private void requestRegist() {
        if (StringUtils.isEmpty(testtype)) {
            toast("请选择考试类型");
            return;
        }
        if (StringUtils.isEmpty(testid)) {
            toast("请选择要参加的考试");
            return;
        }


        Map paramsMap = new HashMap();
        final String phoneStr= bundle.getString("uphone");

        paramsMap.put("uname", bundle.getString("uname"));
        paramsMap.put("uphone", bundle.getString("uphone"));
        paramsMap.put("upwd", bundle.getString("upwd"));
        paramsMap.put("uphone", bundle.getString("uphone"));
        paramsMap.put("utest_type", testtype);
        paramsMap.put("testid", testid);

        HttpManager.getInstance().post(MyConstant.REGIST, paramsMap, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {

//                //返回首界面
//                Myapp.clearShiTiTypeActivities();
//                jumpTo(LoginActivity.class, true);

                //注册成功直接跳转到首页
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("code");
                    if (code.equals("200")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        String uid = jsonObject1.getString("uid");
                        String token = jsonObject1.getString("token");
                        String acctoken = jsonObject1.getString("acctoken");//IM的登录密码

                        Myapp.spUtil.setThirdLogin(false);
                        Myapp.spUtil.setLogin(true);
                        Myapp.spUtil.setUid(uid);
                        Myapp.spUtil.setToken(token);
                        Myapp.spUtil.setPhone(phoneStr);

                        //IM登录
                        doLogin(acctoken, uid, token);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {
                if (result.getCode().equals(ErrorCode.ERROR_CODE_202)) {
                    toast("注册失败");
                } else {
                    toast(ErrorDes.ERROR_INFO);
                }
            }
        });
    }

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

                DemoCache.setAccount(uid);
                saveLoginInfo(uid, imToken);
                // 初始化消息提醒
                NIMClient.toggleNotification(UserPreferences.getNotificationToggle());

                // 初始化免打扰
                NIMClient.updateStatusBarNotificationConfig(UserPreferences.getStatusConfig());

                Myapp.clearActivitiesExceptMe(ExameTypeAddressActivity.class);
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
                toast("网易云信登录失败");
            }

            @Override
            public void onException(Throwable exception) {
                onLoginDone();
            }
        });
    }

    private void onLoginDone() {
        loginRequest = null;
    }

    private void saveLoginInfo(final String account, final String token) {
        AuthPreferences.saveUserAccount(account);
        AuthPreferences.saveUserToken(token);
    }

    /**
     * 修改考试类型
     */
    private void requestChange() {

        Map paramsMap = new HashMap();

        String uid = Myapp.spUtil.getUid();
        String token = Myapp.spUtil.getToken();

        paramsMap.put("uid", uid);
        paramsMap.put("token", token);

        Map data = new HashMap();
        data.put("testid", testid);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(data);
        paramsMap.put("param", jsonStr);

        HttpManager.getInstance().post(MyConstant.UPDATEINFO, paramsMap, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {
                //练习界面onresume时刷新界面
                LianxiFragment.isRefreshIndex=true;
                Myapp.clearShiTiTypeActivities();
                ExameTypeAddressActivity.this.finish();

            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {
                toast(result.getDesc());
            }
        });
    }
}

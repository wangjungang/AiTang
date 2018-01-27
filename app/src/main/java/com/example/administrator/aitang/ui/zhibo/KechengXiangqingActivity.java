package com.example.administrator.aitang.ui.zhibo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.zhibo.ClassBean;
import com.example.administrator.aitang.bean.zhibo.ClassDetailBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.fragment.zhibo.KechengJieshaoFragemtn;
import com.example.administrator.aitang.fragment.zhibo.KechengbiaoFragement;
import com.example.administrator.aitang.fragment.zhibo.LaoshiJieshaoFragemnt;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.example.administrator.aitang.utils.basic.DateUtil;
import com.example.administrator.aitang.utils.okhttp.OkHttpUtils;
import com.example.administrator.aitang.utils.okhttp.callback.StringCallback;
import com.example.administrator.aitang.zhibo.im.business.LogoutHelper;
import com.google.gson.Gson;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.example.administrator.aitang.R.id.rb_kechengxiangqingAct_kcjs;

public class KechengXiangqingActivity extends MyBaseActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.headerview)
    LinearLayout llHeader;
    @BindView(R.id.iv_header_left)
    ImageView ivHeaderLeft;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_kechengxiangqingAct_title)
    TextView tvTitle;
    @BindView(R.id.btn_kechengxiangqingAct_ljgm)
    Button btnLjgm;
    @BindView(R.id.tv_kechengxiangqingAct_time)
    TextView tvTime;
    @BindView(R.id.rg_kechengxiangqingAct)
    RadioGroup rg;
    ClassBean.DataBean data;
    public static String FRAGMENT1 = "fragment1";
    public static String FRAGMENT2 = "fragment2";
    public static String FRAGMENT3 = "fragment3";
    @BindView(R.id.iv_header_right)
    ImageView ivHeaderRight;
    @BindView(R.id.textView5)
    ImageView textView5;
    @BindView(R.id.rb_kechengxiangqingAct_kcjs)
    RadioButton rbKechengxiangqingActKcjs;
    @BindView(R.id.rb_kechengxiangqingAct_kcb)
    RadioButton rbKechengxiangqingActKcb;
    @BindView(R.id.rb_kechengxiangqingAct_lsjs)
    RadioButton rbKechengxiangqingActLsjs;
    @BindView(R.id.frame_content)
    FrameLayout frameContent;
    private String roomid;//聊天室id
    private String isStart;//课程是否正在直播 0：未开播  1：正在直播  2：已直播完毕需要下载
    private boolean isClick = false;//在显示的内容是"进入课程"的时候，是否可以点击（true:点击跳转 false:点击提示没有内容）
    private ClassDetailBean.DataBean sendData;//传送到下个页面的bean
    private int isbuy;//是否买过该课程 1：买过 2：没买过
    int noPlay = 0;
    int noEnd = 0;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_kecheng_xiangqing);
    }

    @Override
    public void init() {
        setImmerBarcolor(llHeader);
        ivHeaderLeft.setImageResource(R.drawable.back_icon_nav);
        ivHeaderLeft.setVisibility(View.VISIBLE);
        setHeaderBackground(R.color.color_white);
        tvHeaderTitle.setText("课程详情");
        data = getIntent().getParcelableExtra("data");
        if (data != null) {
            tvTitle.setText(data.getC_name());
            String startTime = DateUtil.toString(Long.parseLong(data.getC_start_time()), "yyyy-MM-dd");
            String endTime = DateUtil.toString(Long.parseLong(data.getC_end_time()), "yyyy-MM-dd");
            tvTime.setText(startTime + "-" + endTime + "(" + data.getClassnum() + "课时)");
            isbuy = data.getIsbuy();
            if (isbuy == 1) {//买过该课程
                aboutLjgmBtn();
            } else if (isbuy == 2) {//没买过该课程
                btnLjgm.setText("立即购买");
            }
        }
        rg.setOnCheckedChangeListener(this);
        selectFragment(R.id.rb_kechengxiangqingAct_kcjs);
    }

    private void aboutLjgmBtn() {
        btnLjgm.setText("进入课程");
        OkHttpUtils.get().url(MyConstant.CLASS_LISTDETAIL)
                .addParams("uid", Myapp.spUtil.getUid())
                .addParams("token", Myapp.spUtil.getToken())
                .addParams("c_id", data.getC_id())
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) throws IOException, JSONException {
                JSONObject object = new JSONObject(response);
                if (object.getString("code").equals("200")) {
                    ClassDetailBean bean = new Gson().fromJson(response, ClassDetailBean.class);
                    List<ClassDetailBean.DataBean> classList = bean.getData();
                    //
                    for (int i = 0; i < classList.size(); i++) {
                        isStart = classList.get(i).getIsstart();
//                                0：未开播  1：正在直播  2：已直播完毕需要下载
                        if (isStart.equals("0")) {
                            noPlay = 1;
                        } else {
                            noPlay = 2;
                        }
                        if (isStart.equals("2")) {
                            noEnd = 1;
                        } else {
                            noEnd = 2;
                        }
                        if (isStart.equals("1")) {
                            isClick = true;
                            sendData = classList.get(i);
                            return;
                        }

                    }

                }
            }
        });
    }

    private void selectFragment(@IdRes int i) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment f1 = fm.findFragmentByTag(FRAGMENT1);
        Fragment f2 = fm.findFragmentByTag(FRAGMENT2);
        Fragment f3 = fm.findFragmentByTag(FRAGMENT3);
        if (f1 != null) {
            ft.hide(f1);
        }
        if (f2 != null) {
            ft.hide(f2);
        }
        if (f3 != null) {
            ft.hide(f3);
        }
        switch (i) {
            case rb_kechengxiangqingAct_kcjs:
                if (f1 == null) {
                    f1 = new KechengJieshaoFragemtn();
                    ft.add(R.id.frame_content, f1, FRAGMENT1);
                } else {
                    ft.show(f1);
                }
                if (data == null)
                    return;
                Bundle bundle1 = new Bundle();
                bundle1.putString("pic", data.getC_intro_img());
                f1.setArguments(bundle1);
                break;
            case R.id.rb_kechengxiangqingAct_kcb:
                if (f2 == null) {
                    f2 = new KechengbiaoFragement();
                    ft.add(R.id.frame_content, f2, FRAGMENT2);
                } else {
                    ft.show(f2);
                }
                if (data == null)
                    return;
                Bundle bundle2 = new Bundle();
                bundle2.putString("c_id", data.getC_id());//课程列表id
                bundle2.putInt("isBuy", isbuy);
                f2.setArguments(bundle2);
                break;
            case R.id.rb_kechengxiangqingAct_lsjs:
                if (f3 == null) {
                    f3 = new LaoshiJieshaoFragemnt();
                    ft.add(R.id.frame_content, f3, FRAGMENT3);
                } else {
                    ft.show(f3);
                }
                break;
            default:
                break;
        }
        ft.commit();
    }

    public ArrayList<ClassBean.DataBean.TeacherBean> getData() {
        return data.getTeacher();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        selectFragment(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_header_left, R.id.btn_kechengxiangqingAct_ljgm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                finish();
                break;
            case R.id.btn_kechengxiangqingAct_ljgm:
                if (btnLjgm.getText().equals("立即购买")) {
                    Intent intent = new Intent(this, TijiaoDingdanActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("data", data);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 101);
                } else if (btnLjgm.getText().equals("进入课程")) {
                    //买过了课，要判断当前时间是否在购买课程的时间内，如果不是正在直播就不能进入
                    registerObservers(true);
                    if (noEnd == 1)
                        Toast.makeText(KechengXiangqingActivity.this, "直播已结束，请跳至相关页面下载", Toast.LENGTH_SHORT).show();
                    else if (noPlay == 1)
                        Toast.makeText(KechengXiangqingActivity.this, "未到开播时间", Toast.LENGTH_SHORT).show();
                    else if (isClick)
                        ZhiboActivity.start(KechengXiangqingActivity.this, sendData.getTeacher().getRoomid(), false, sendData);
                }
                break;
        }
    }

    private void registerObservers(boolean register) {
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(userStatusObserver, register);
    }

    Observer<StatusCode> userStatusObserver = new Observer<StatusCode>() {
        @Override
        public void onEvent(StatusCode statusCode) {
            if (statusCode.wontAutoLogin()) {
                LogoutHelper.logout(KechengXiangqingActivity.this, true);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerObservers(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == 1) {
            btnLjgm.setText("进入课程");
            aboutLjgmBtn();
        }
    }

}

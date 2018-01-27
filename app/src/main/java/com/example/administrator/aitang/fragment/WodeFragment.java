package com.example.administrator.aitang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.wode.UserInfoBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.http.HttpManager;
import com.example.administrator.aitang.http.listener.ServiceBackObjectListener;
import com.example.administrator.aitang.http.result.ServiceResult;
import com.example.administrator.aitang.ui.account.ExameTypeActivity;
import com.example.administrator.aitang.ui.mine.AiTangBroadcastActivity;
import com.example.administrator.aitang.ui.mine.CashCouponActivity;
import com.example.administrator.aitang.ui.mine.ChangeAccountInfoActivity;
import com.example.administrator.aitang.ui.mine.ContactUsActivity;
import com.example.administrator.aitang.ui.mine.QuestionFeedbackActivity;
import com.example.administrator.aitang.ui.mine.TeacherRankingsActivity;
import com.example.administrator.aitang.ui.mine.VideoCacheActivity;
import com.example.administrator.aitang.utils.basic.StringUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 武培培 on 2017/10/24.
 */

public class WodeFragment extends MyBaseFragment {

    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.iv_header_right)
    ImageView ivHeaderRight;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.rl_user_info)
    RelativeLayout rlUserInfo;
    @BindView(R.id.tv_exam_type)
    TextView tvExamType;
    @BindView(R.id.rl_exam_type)
    RelativeLayout rlExamType;
    @BindView(R.id.rl_cash_coupon)
    RelativeLayout rlCashCoupon;
    @BindView(R.id.rl_broadcast)
    RelativeLayout rlBroadcast;
    @BindView(R.id.rl_course_cache)
    RelativeLayout rlCourseCache;
    @BindView(R.id.rl_question_feedback)
    RelativeLayout rlQuestionFeedback;
    @BindView(R.id.rl_teacher_rankings)
    RelativeLayout rlTeacherRankings;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.rl_version)
    RelativeLayout rlVersion;
    @BindView(R.id.rl_contact_us)
    RelativeLayout rlContactUs;
    @BindView(R.id.headerview)
    LinearLayout llHeaderView;


    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public void init() {
        super.init();
        setImmerBarcolor(llHeaderView);
        setHeader();

        //设置版本号
        tvVersion.setText(MyConstant.VERSIONCODE);
    }


    /**
     * 设置标题
     */
    private void setHeader() {
        setHeaderBackground(R.color.color_white);
        setHeaderTitle("我的", MyConstant.Position.CENTER);
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserInfo();
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @OnClick({R.id.rl_user_info, R.id.rl_exam_type, R.id.rl_cash_coupon, R.id.rl_course_cache, R.id.rl_broadcast, R.id.rl_question_feedback, R.id.rl_teacher_rankings, R.id.rl_contact_us, R.id.rl_version})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.rl_user_info://账号信息

                jumpTo(ChangeAccountInfoActivity.class, false);

                break;
            case R.id.rl_exam_type://考试类型
                Intent intent = new Intent(getActivity(), ExameTypeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("flag", "2");
                intent.putExtras(bundle);
                startActivity(intent);

                break;
            case R.id.rl_cash_coupon://代金券

                jumpTo(CashCouponActivity.class, false);

                break;
            case R.id.rl_broadcast://爱唐播报

                jumpTo(AiTangBroadcastActivity.class, false);

                break;
            case R.id.rl_course_cache://课程缓存

                jumpTo(VideoCacheActivity.class, false);

                break;
            case R.id.rl_question_feedback://问题反馈

                jumpTo(QuestionFeedbackActivity.class, false);
                break;
            case R.id.rl_teacher_rankings://名师琅琊榜

                jumpTo(TeacherRankingsActivity.class, false);

                break;
            case R.id.rl_contact_us://联系我们

                jumpTo(ContactUsActivity.class, false);

                break;
            case R.id.rl_version://检查版本


                break;

            default:
                break;
        }

    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {

        Map paramsMap = new HashMap();
        String uid = Myapp.spUtil.getUid();
        String token = Myapp.spUtil.getToken();
        paramsMap.put("uid", uid);
        paramsMap.put("token", token);

        HttpManager.getInstance().getWithTag(MyConstant.GETUSERINFO, paramsMap, getActivity(), new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {

                UserInfoBean userInfoBean = new Gson().fromJson(response, UserInfoBean.class);

                if (null != userInfoBean) {
                    //保存用户信息实体类
                    Myapp.spUtil.putBean(getActivity(), MyConstant.USERINFO, userInfoBean);

                    if (!StringUtils.isEmpty(userInfoBean.getData().getUname())) {
                        tvUsername.setText(userInfoBean.getData().getUname());
                    }
                    if (null != userInfoBean.getData().getUtest_type()
                            && userInfoBean.getData().getUtest_type().size() > 0
                            && null != userInfoBean.getData().getUtest_type().get(0)
                            && userInfoBean.getData().getUtest_type().get(0).size() > 0) {

                        String examType = userInfoBean.getData().getUtest_type().get(0).get(0);
                        String examAddressType = "";
                        if (userInfoBean.getData().getUtest_type().get(0).size() == 2) {
                            examAddressType = userInfoBean.getData().getUtest_type().get(0).get(1);
                        }

                        if (!StringUtils.isEmpty(examType) && !StringUtils.isEmpty(examAddressType)) {
                            tvExamType.setText(examType + "," + examAddressType);
                        } else if (!StringUtils.isEmpty(examType)) {
                            tvExamType.setText(examType);
                        }
                    } else {
                        tvExamType.setText("");
                    }
                }

            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {

            }
        });
    }

    //    /**
//     * 检查版本
//     */
//    private void checkVersion() {
//
//        Map paramsMap = new HashMap();
//
//        String uid = Myapp.spUtil.getUid();
//        String token = Myapp.spUtil.getToken();
//
//        paramsMap.put("uid", uid);
//        paramsMap.put("token", token);
//
//        HttpManager.getInstance().get(MyConstant.CONFIG, paramsMap, new ServiceBackObjectListener() {
//            @Override
//            public void onSuccess(ServiceResult result, String response) {
//
//                ConfigBean configBean = new Gson().fromJson(response, ConfigBean.class);
//                String serviceVersion = configBean.getData().getEdition();
//                //比较版本号提示
//            }
//
//            @Override
//            public void onFailure(ServiceResult result, Object obj) {
//                toast(result.getDesc());
//            }
//        });
//
//    }

}

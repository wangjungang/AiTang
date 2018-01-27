package com.example.administrator.aitang.ui.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.http.HttpManager;
import com.example.administrator.aitang.http.listener.ServiceBackObjectListener;
import com.example.administrator.aitang.http.result.ServiceResult;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.example.administrator.aitang.utils.basic.ProgressUtil;
import com.example.administrator.aitang.utils.basic.StringUtils;
import com.example.administrator.aitang.views.ScrollEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuestionFeedbackActivity extends MyBaseActivity {

    @BindView(R.id.headerview)
    LinearLayout llHeaderView;

    @BindView(R.id.iv_header_left)
    ImageView ivHeaderLeft;
    @BindView(R.id.et_proposal)
    ScrollEditText etProposal;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_question_feedback);
    }

    @Override
    public void init() {
        super.init();
        setImmerBarcolor(llHeaderView);
        setHeader();
//        setKeyBoardPatch5497(this);
    }

    private void setHeader() {
        setHeaderBackground(R.color.color_white);
        setHeaderImage(MyConstant.Position.LEFT, R.drawable.back_icon_nav, false, null);
        setHeaderTitle("问题反馈", MyConstant.Position.CENTER, R.color.color_323232);
    }

    @OnClick({R.id.iv_header_left, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                QuestionFeedbackActivity.this.finish();
                break;
            case R.id.btn_submit:
                if (StringUtils.isEmpty(etProposal.getText().toString())) {
                    toast("请输入内容");
                    return;
                }
                submitData();
                break;
            default:
                break;
        }


    }

    /**
     * 提交数据
     */
    private void submitData() {


        ProgressUtil.show(this);
        Map paramsMap = new HashMap();

        String uid = Myapp.spUtil.getUid();
        String token = Myapp.spUtil.getToken();


        paramsMap.put("uid", uid);
        paramsMap.put("token", token);
        paramsMap.put("prodes", etProposal.getText().toString());

        HttpManager.getInstance().post(MyConstant.QUESTIONFEEDBACK, paramsMap, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {
                ProgressUtil.hide();
                toast("提交成功");
                QuestionFeedbackActivity.this.finish();
            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {
                ProgressUtil.hide();
                toast(result.getDesc());
            }
        });
    }
}

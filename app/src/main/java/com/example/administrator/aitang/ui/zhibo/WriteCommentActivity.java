package com.example.administrator.aitang.ui.zhibo;

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
import butterknife.OnClick;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * 更新后报错的话，添加下面的依赖
 *  compile 'me.zhanghai.android.materialratingbar:library:1.2.0'
 */

public class WriteCommentActivity extends MyBaseActivity {

    @BindView(R.id.iv_header_left)
    ImageView ivHeaderLeft;
    @BindView(R.id.headerview)
    LinearLayout llHeaderView;
    @BindView(R.id.et_write_comment)
    ScrollEditText etWriteComment;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.rb_comment)
    MaterialRatingBar rbComment;

    private String tid;
    private String cdid;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_write_comment);
    }

    @Override
    public void init() {
        super.init();
        setImmerBarcolor(llHeaderView);
        setHeader();

        Bundle bundle = getIntent().getExtras();
        tid = bundle.getString("tid", "");
        cdid = bundle.getString("cdid", "");
    }

    private void setHeader() {
        setHeaderBackground(R.color.color_white);
        setHeaderImage(MyConstant.Position.LEFT, R.drawable.back_icon_nav, false, null);
        setHeaderTitle("添加评论", MyConstant.Position.CENTER, R.color.color_323232);
    }


    @OnClick({R.id.iv_header_left, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                WriteCommentActivity.this.finish();
                break;
            case R.id.btn_submit:
                if (StringUtils.isEmpty(etWriteComment.getText().toString())) {
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
        int start = (int) rbComment.getRating();
        String score = "0";
        if (start == 0) {
            score = "0";
        } else if (start == 1) {
            score = "20";
        } else if (start == 2) {
            score = "40";
        } else if (start == 3) {
            score = "60";
        } else if (start == 4) {
            score = "80";
        } else if (start == 5) {
            score = "100";
        }

        paramsMap.put("uid", uid);
        paramsMap.put("token", token);
        paramsMap.put("cscore", score);
        paramsMap.put("ccontent", etWriteComment.getText().toString());
        paramsMap.put("tid", tid);
        paramsMap.put("cdid", cdid);

        HttpManager.getInstance().post(MyConstant.TEACHERCOMMENT, paramsMap, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {
                ProgressUtil.hide();
                toast("提交成功");
//                WriteCommentActivity.this.finish();
            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {
                ProgressUtil.hide();
                toast(result.getDesc());
            }
        });
    }

}

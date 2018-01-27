package com.example.administrator.aitang.ui.zhibo;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.example.administrator.aitang.utils.basic.DateUtil;
import com.example.administrator.aitang.utils.glide.GlideUtils;

import butterknife.BindView;

public class ZhiboNoStartActivity extends MyBaseActivity {

    @BindView(R.id.img_weikaibo_include)
    ImageView img;
    @BindView(R.id.tv_include_zhibo_weikaibo_time)
    TextView tvTime;
    private String cdimg;//课程的图片
    private String time;//开播时间
    private static final String IMGURL = "imgurl";
    private static final String TIME = "time";


    private void pareIntent() {
        Bundle data = getIntent().getExtras();
        cdimg = data.getString(IMGURL);
        time = data.getString(TIME);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_zhibo_no_start);
    }

    @Override
    public void init() {
        pareIntent();
        setImmerBarcolor(img);
        GlideUtils.setAvatar(cdimg, img, R.drawable.logo_img_qidongye, R.drawable.logo_img_qidongye, 0, 0);
        tvTime.setText("直播将在" + DateUtil.toString(Long.parseLong(time), "yyyy-MM-dd"));
    }
}

package com.example.administrator.aitang.ui.zhibo;

import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.bean.zhibo.ClassBean;
import com.example.administrator.aitang.fragment.zhibo.KechengkuFragemnt;
import com.example.administrator.aitang.fragment.zhibo.KechengriliFragemnt;
import com.example.administrator.aitang.ui.MyBaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.administrator.aitang.R.id.tv_myclassAct_kechengku;

public class MyClassActivity extends MyBaseActivity {
    public static String FRAGMENT1 = "fragment1";
    public static String FRAGMENT2 = "fragment2";
    @BindView(R.id.headerview)
    LinearLayout llHeader;
    @BindView(R.id.iv_header_left)
    ImageView ivLeft;
    @BindView(R.id.tv_header_title)
    TextView tvTitle;
    @BindView(R.id.tv_myclassAct_kechengku)
    TextView tvKechengku;
    @BindView(R.id.tv_myclassAct_kechengrili)
    TextView tvKechengrili;
    ArrayList<ClassBean.DataBean> been;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_my_class);
    }

    @Override
    public void init() {
        setImmerBarcolor(llHeader);
        ivLeft.setImageResource(R.drawable.back_icon_nav);
        ivLeft.setVisibility(View.VISIBLE);
        setHeaderBackground(R.color.color_white);
        tvTitle.setText("我的课程");
        been = new ArrayList<ClassBean.DataBean>();//要组装的
        onViewClicked(tvKechengku);
    }

//    private void getData() {
//        if (been.size() == 0) {
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    getData();
//                }
//            }, 1000);
//        }
//
//    }

    Handler handler = new Handler();

    private void selectFragment(@IdRes int i) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment f1 = fm.findFragmentByTag(FRAGMENT1);
        Fragment f2 = fm.findFragmentByTag(FRAGMENT2);
        if (f1 != null) {
            ft.hide(f1);
        }
        if (f2 != null) {
            ft.hide(f2);
        }
        switch (i) {
            case R.id.tv_myclassAct_kechengku:
                if (f1 == null) {
                    f1 = new KechengkuFragemnt();
                    ft.add(R.id.frame_content, f1, FRAGMENT1);
                } else {
                    ft.show(f1);
                }
//                if (been == null || been.size() == 0) {
//                    getData();
//                } else {
//                    Bundle bundle3 = new Bundle();
//                    bundle3.putParcelableArrayList("datas", been);
//                    f1.setArguments(bundle3);
//                }
                break;
            case R.id.tv_myclassAct_kechengrili:
                if (f2 == null) {
                    f2 = new KechengriliFragemnt();
                    ft.add(R.id.frame_content, f2, FRAGMENT2);
                } else {
                    ft.show(f2);
                }
//                if (been == null || been.size() == 0) {
//                    getData();
//                } else {
//                    Bundle bundle2 = new Bundle();
//                    bundle2.putParcelableArrayList("datas", been);
//                    f2.setArguments(bundle2);
//                }
                break;
            default:
                break;
        }
        ft.commit();
    }

    @OnClick({R.id.iv_header_left, tv_myclassAct_kechengku, R.id.tv_myclassAct_kechengrili})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                finish();
                break;
            case R.id.tv_myclassAct_kechengku:
            case R.id.tv_myclassAct_kechengrili:
                selectFragment(view.getId());
                selectText(view.getId());
                break;
        }
    }

    private void selectText(int id) {
        if (id == R.id.tv_myclassAct_kechengku) {
            tvKechengku.setTextColor(getResources().getColor(R.color.color_FF9B19));
            tvKechengrili.setTextColor(getResources().getColor(R.color.color_909090));
        } else if (id == R.id.tv_myclassAct_kechengrili) {
            tvKechengku.setTextColor(getResources().getColor(R.color.color_909090));
            tvKechengrili.setTextColor(getResources().getColor(R.color.color_FF9B19));
        }
    }
}

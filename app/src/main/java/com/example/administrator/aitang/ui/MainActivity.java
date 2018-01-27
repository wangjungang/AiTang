package com.example.administrator.aitang.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.fragment.LianxiFragment;
import com.example.administrator.aitang.fragment.WodeFragment;
import com.example.administrator.aitang.fragment.ZhiBoFragment;
import com.example.administrator.aitang.http.HttpManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 武培培 on 2017/10/24.
 */

public class MainActivity extends MyBaseActivity  implements View.OnClickListener{
    @BindView(R.id.radiogroup_bottom)
    RadioGroup radiogroup_bottom;
    @BindView(R.id.rb_lianxi)
    RadioButton rb_liaxin;
    @BindView(R.id.rb_zhibo)
    RadioButton rb_zhibo;
    @BindView(R.id.rb_wode)
    RadioButton rb_wode;
    private List<Fragment> fragmentList;
    FragmentManager fragmentManager;
    private long mExitTime = 0;
    @Override
    public void init() {
        super.init();
        setOnClickListener();
        fragmentManager = getSupportFragmentManager();
        fragmentList = new ArrayList<Fragment>();
        LianxiFragment lianxiFragment = new LianxiFragment();
        ZhiBoFragment zhiBoFragment = new ZhiBoFragment();
        WodeFragment wodeFragment = new WodeFragment();
        fragmentList.add(lianxiFragment);
        fragmentList.add(zhiBoFragment);
        fragmentList.add(wodeFragment);
        setOnClickListener();
        showFragment(0);
        radiogroup_bottom.check(R.id.rb_lianxi);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main3);
    }
    private void setOnClickListener(){
        rb_liaxin.setOnClickListener(this);
        rb_zhibo.setOnClickListener(this);
        rb_wode.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rb_lianxi:
                radiogroup_bottom.check(R.id.rb_lianxi);
                showFragment(0);
                break;
            case R.id.rb_zhibo:
                radiogroup_bottom.check(R.id.rb_zhibo);
                showFragment(1);
                break;
            case R.id.rb_wode:
                radiogroup_bottom.check(R.id.rb_wode);
                showFragment(2);
                break;
            default:
                break;
        }
    }

    private void showFragment(int index){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (int i = 0; i< fragmentList.size();i++){
            Fragment fragment= fragmentList.get(i);

            if (i == index) {
                if (fragment.isAdded()) {
                    fragmentTransaction.show(fragment);
                } else {
                    fragmentTransaction.add(R.id.fl_content, fragment);
                }
            } else {
                if (fragment.isAdded()) {
                    fragmentTransaction.hide(fragment);
                }
            }
        }
        fragmentTransaction.commit();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - mExitTime) > 3000) {
                Toast.makeText(this, "双击退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpManager.getInstance().cancleRequest(this);
    }
}

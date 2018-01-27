package com.example.administrator.aitang.zhibo.base.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bulong.rudeness.RudenessScreenHelper;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.zhibo.base.util.ReflectionUtil;
import com.example.administrator.aitang.zhibo.base.util.log.LogUtil;
import com.gyf.barlibrary.ImmersionBar;


public abstract class TActivity extends AppCompatActivity {

    private boolean destroyed = false;

    private static Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new RudenessScreenHelper(this.getApplication(), 750).activate();//屏幕适配方案
        LogUtil.ui("activity: " + getClass().getSimpleName() + " onCreate()");
    }

    protected void logd(String str) {
        log("d", str);
    }

    protected void logi(String str) {
        log("i", str);
    }

    protected void log(String s, String str) {
        if (MyConstant.DEBUG) {
            if (s.equals("d"))
                Log.d("TAG", "来自" + this.getClass().getSimpleName() + "的日志信息：" + str);
            if (s.equals("i"))
                Log.i("TAG", "来自" + this.getClass().getSimpleName() + "的日志信息：" + str);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LogUtil.ui("activity: " + getClass().getSimpleName() + " onDestroy()");
        destroyed = true;

    }

    @Override
    public void onBackPressed() {
        invokeFragmentManagerNoteStateNotSaved();
        super.onBackPressed();
    }

    protected void showKeyboard(boolean isShow) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if (isShow) {
            if (getCurrentFocus() == null) {
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            } else {
                imm.showSoftInput(getCurrentFocus(), 0);
            }
        } else {
            if (getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

        }
    }

    /**
     * 延时弹出键盘
     *
     * @param focus ：键盘的焦点项
     */
    protected void showKeyboardDelayed(View focus) {
        final View viewToFocus = focus;
        if (focus != null) {
            focus.requestFocus();
        }
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (viewToFocus == null || viewToFocus.isFocused()) {
                    showKeyboard(true);
                }
            }
        }, 200);
    }

    /**
     * 判断页面是否已经被销毁（异步回调时使用）
     */
    protected boolean isDestroyedCompatible() {
        if (Build.VERSION.SDK_INT >= 17) {
            return isDestroyedCompatible17();
        } else {
            return destroyed || super.isFinishing();
        }
    }

    @TargetApi(17)
    private boolean isDestroyedCompatible17() {
        return super.isDestroyed();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void invokeFragmentManagerNoteStateNotSaved() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ReflectionUtil.invokeMethod(getFragmentManager(), "noteStateNotSaved", null);
        }
    }

    protected void switchFragmentContent(TFragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(fragment.getContainerId(), fragment);
        try {
            transaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected final Handler getHandler() {
        if (handler == null) {
            handler = new Handler(getMainLooper());
        }
        return handler;
    }

    protected <T extends View> T findView(int resId) {
        return (T) (findViewById(resId));
    }

    protected void setImmerBarcolor(View view) {
        ImmersionBar.with(this)
                .titleBar(view) //可以为任意view，如果是自定义xml实现标题栏的话，最外层节点不能为RelativeLayout
                .init();
    }
}

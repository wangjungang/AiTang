package com.example.administrator.aitang.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andview.refreshview.XRefreshView;
import com.example.administrator.aitang.R;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.example.administrator.aitang.views.CustomerFooter;
import com.gyf.barlibrary.ImmersionBar;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/22.
 */

public abstract class MyBaseFragment extends Fragment {
    static View headerView;
    //    Banner banner;
    static MyBaseActivity baseActivity;
    public XRefreshView xRefreshView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = null;
        String clazzName = getClass().getSimpleName();//MessageFragment

//        if (clazzName.contains("Fragment")) {
//            //尝试自动赋值
//            String name = clazzName.substring(0, clazzName.indexOf("Fragment")).toLowerCase(Locale.US);//message
//            String layoutName = "fragment_" + name;//fragment_message
//            int layoutId = getResources().getIdentifier(layoutName, "layout", getActivity().getPackageName());//R.layout.fragment_message
//            if (layoutId != 0) {
//                view = inflater.inflate(layoutId, container, false);
//            } else {
//                view = createView(inflater, container, savedInstanceState);
//            }
//
//        } else {
        //调用抽象方法由子类手动赋值
        view = createView(inflater, container, savedInstanceState);
//        }
        ButterKnife.bind(this, view);
        //要求，所有的Fragment布局文件include header时，必须指定同样的id：R.id.headerview
        if (view == null)
            return null;
        headerView = view.findViewById(R.id.headerview);
//        banner = (Banner) view.findViewById(R.id.banner);
        xRefreshView = view.findViewById(R.id.xRefreshView);
        baseActivity = (MyBaseActivity) getActivity();
        init();
        return view;
    }

    public void init() {
        // NO-OP 钩子方法
    }

    public abstract View createView(LayoutInflater inflater, ViewGroup container,
                                    Bundle savedInstanceState);

    /**
     * 设置标题文字及居中方式
     *
     * @param title 标题文字
     * @param pos   居中方式：LEFT, CENTER, RIGHT
     */
    public void setHeaderTitle(String title, MyConstant.Position pos) {
        baseActivity.setHeaderView(headerView);
        baseActivity.setHeaderTitle(title, pos);
    }

    /**
     * 设置图片以及居中方式
     *
     * @param pos         居中方式：LEFT, CENTER, RIGHT
     * @param resId       图片资源id
     * @param colorFilter 是否需要设置滤镜
     * @param listener    监听
     */
    public void setHeaderImage(MyConstant.Position pos, int resId, boolean colorFilter, View.OnClickListener listener) {
        baseActivity.setHeaderView(headerView);
        baseActivity.setHeaderImage(pos, resId, colorFilter, listener);
    }

    /**
     * 设置Header的background
     *
     * @param drawable
     */

    public void setHeaderBackground(Drawable drawable) {
        baseActivity.setHeaderView(headerView);
        baseActivity.setHeaderBackground(drawable);
    }

    public void setHeaderBackground(int headerColor) {
        headerView.setBackground(getResources().getDrawable(headerColor));
    }

    /**
     * 吐司
     *
     * @param text 吐司内容
     */
    public void toast(String text) {
        baseActivity.toast(text);
    }

    /**
     * 打印日志
     *
     * @param log 日志显示内容
     */
    public void log(String log) {
        if (baseActivity != null)
            baseActivity.log(log);
    }

    /**
     * 吐司和打印日志
     *
     * @param text 吐司内容
     * @param log  打印内容
     */
    public void toastAndLog(String text, String log) {
        toast(text);
        log(log);
    }

    /**
     * Activity的跳转
     *
     * @param clazz    要跳转的Activity名字
     * @param isFinish 是否需要关闭当前Activity，true：关闭,false:不关闭
     */
    public void jumpTo(Class<?> clazz, boolean isFinish) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
        if (isFinish) {
            getActivity().finish();
        }
    }

    public void jumpToForResult(Class<?> clazz, int requestCode, boolean isFinish) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivityForResult(intent, requestCode);
        if (isFinish) {
            getActivity().finish();
        }
    }

    public void jumpToForResult(Intent intent, int requestCode, boolean isFinish) {
        startActivityForResult(intent, requestCode);
        if (isFinish) {
            getActivity().finish();
        }
    }

    /**
     * Activity的跳转
     *
     * @param intent   创建intent意图进行跳转
     * @param isFinish 是否需要关闭当前Activity，true：关闭,false:不关闭
     */
    public void jumpTo(Intent intent, boolean isFinish) {
        startActivity(intent);
        if (isFinish) {
            getActivity().finish();

        }
    }

    public void aboutRefreshView() {
        aboutRefreshView(true);
    }

    public void aboutRefreshView(boolean flag) {
        xRefreshView.setPullLoadEnable(flag);
        setXRefreshView(xRefreshView);
        //设置在上拉加载被禁用的情况下，是否允许界面被上拉
        xRefreshView.setMoveFootWhenDisablePullLoadMore(false);
        xRefreshView.setMoveForHorizontal(true);
        xRefreshView.setCustomFooterView(new CustomerFooter(baseActivity));
//		refreshView.setPinnedContent(true);
        //设置当非RecyclerView上拉加载完成以后的回弹时间
//        refreshView.setScrollBackDuration(300);
        xRefreshView.setXRefreshViewListener(refreshListener);
    }

    XRefreshView.SimpleXRefreshListener refreshListener = new XRefreshView.SimpleXRefreshListener()

    {
        @Override
        public void onRefresh(boolean isPullDown) {
            addData();
        }

        @Override
        public void onLoadMore(boolean isSilence) {
            addDataLoadMore();
        }
    };


    public void addData() {
    }

    public void addDataLoadMore() {
    }

    /**
     * 设置banner
     *
     * @param picUrls
     * @param picDiscribes
     * @param flag         为true,用指定参数的，否则用自定义的
     */
    public void addBannerUrls(List<String> picUrls, List<String> picDiscribes, boolean flag) {
//        baseActivity.setBannerView(banner);
        baseActivity.addBannerUrls(picUrls, picDiscribes, flag);
    }

    public List<String> getDis() {
        return null;
    }

    public List<String> getUrl() {
        return null;
    }

    public void setXRefreshView(XRefreshView refreshView) {
        baseActivity.setXRefreshView(refreshView);
    }

    public void setImmerBarcolor(View view) {
        baseActivity.setImmerBarcolor(view);
    }

    public void setImmerBarcolor(View view, boolean darkFont) {
        baseActivity.setImmerBarcolor(view, darkFont);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(baseActivity).destroy();
    }
}

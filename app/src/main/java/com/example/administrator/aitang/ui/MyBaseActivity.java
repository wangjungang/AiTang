package com.example.administrator.aitang.ui;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andview.refreshview.XRefreshView;
import com.example.administrator.aitang.R;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.utils.GImageLoader;
import com.example.administrator.aitang.utils.basic.AndroidBug5497Workaround;
import com.example.administrator.aitang.utils.basic.ProgressUtil;
import com.example.administrator.aitang.views.CustomerFooter;
import com.gyf.barlibrary.ImmersionBar;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/21.
 * 所有的activity都集成这个activity，该activity实现了最基础的方法
 */

public abstract class MyBaseActivity extends FragmentActivity {

    Toast toast;
    View headerView;
    Banner banner;// TODO 如果需要实现图片轮播功能，则打开此注释进行修改
    public XRefreshView xRefreshView;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        initLayout();
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        //每打开一个activity都记录下来，关闭的时候递归关闭所有activity
        Myapp.activities.add(this);
        init();
        ImmersionBar.with(this)
//                .statusBarColor(R.color.color_status)
//                .fitsSystemWindows(true)
                .init();
    }

    private void initLayout() {
        setContentView();
        bindViews();
    }

    //打印相关的方法
    public void toast(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        toast.setText(text);
        toast.show();
    }

    //打印日志的相关方法
    public void log(String log) {
        if (MyConstant.DEBUG)
            Log.d("TAG", "来自" + this.getClass().getSimpleName() + "的日志信息：" + log);
        else {
            //TODO 对于DEBUG为false时，打印Logcat无意义，应该将错误信息收集发送指定的服务器
        }
    }

    public void loge(String log) {
        if (MyConstant.DEBUG) {
            Log.e("TAG", "来自" + this.getClass().getSimpleName() + "的日志信息：" + log);
        }
    }

    public void toastAndLog(String text, String log) {
        toast(text);
        log("执行" + text + "，错误分析：" + log);
    }


    /**
     * 界面跳转相关
     *
     * @param clazz    要跳转的类
     * @param isFinish 是否关闭当前Activity，如果是true，关闭当前页面，否则不关闭
     */
    public void jumpTo(Class<?> clazz, boolean isFinish) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        if (isFinish) {
            this.finish();
        }
    }

    public void jumpToForResult(Class<?> clazz, int requestCode, boolean isFinish) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
        if (isFinish) {
            finish();
        }
    }

    public void jumpToForResult(Intent intent, int requestCode, boolean isFinish) {
        startActivityForResult(intent, requestCode);
        if (isFinish)
            finish();
    }

    /**
     * 跳转页面相关
     *
     * @param intent   自定义Intent进行跳转
     * @param isFinish 是否关闭当前Activity，如果是true，关闭当前页面，否则不关闭
     */
    public void jumpTo(Intent intent, boolean isFinish) {
        startActivity(intent);
        if (isFinish) {
            finish();
        }
    }

    //设置头部的相关代码
    public void bindViews() {
//        //！！注意：每一个布局文件在include headerView的时候
//        //id都统一写成headerview
        headerView = findViewById(R.id.headerview);
        //！！注意：每一个布局文件在include Banner的时候，id都要统一写成banner
//        banner = findViewById(R.id.banner);//图片轮播的Banner
        //！！注意：每一个布局文件用xRefreshView的时候，id都要同意写成xRefreshView
        xRefreshView = findViewById(R.id.xRefreshView);
        ButterKnife.bind(this);
    }

    public void aboutRefreshView(boolean pullLoadEnable) {
        setXRefreshView(xRefreshView);
        xRefreshView.setAutoRefresh(true);//自动刷新
        xRefreshView.setPullLoadEnable(pullLoadEnable);//上拉刷新
        //设置在上拉加载被禁用的情况下，是否允许界面被上拉
        xRefreshView.setMoveFootWhenDisablePullLoadMore(true);
        if (pullLoadEnable)
            xRefreshView.setCustomFooterView(new CustomerFooter(this));
        xRefreshView.setMoveForHorizontal(true);
//		refreshView.setPinnedContent(true);
        //设置当非RecyclerView上拉加载完成以后的回弹时间
//        refreshView.setScrollBackDuration(300);
        xRefreshView.setXRefreshViewListener(refreshListener);
//        xRefreshView.setEmptyView(R.layout.layout_xrefresh_emptyview);
    }

    XRefreshView.SimpleXRefreshListener refreshListener = new XRefreshView.SimpleXRefreshListener()

    {
        @Override
        public void onRefresh(boolean isPullDown) {
            //下拉的时候刷新（PageNum永远等于1）
            refresh();
        }

        @Override
        public void onLoadMore(boolean isSilence) {
            //上推的时候请求更多（pageNum会加1）
            loadMore();
        }
    };

    public void setHeaderView(View headerView) {
        this.headerView = headerView;

    }


    public void refresh() {
    }

    public void loadMore() {

    }

    ;

    //设置HeaderView的背景颜色
    public void setHeaderBackground(Drawable drawable) {
//        headerView.setBackgroundColor(backColor);
        headerView.setBackground(drawable);
    }

    public void setHeaderBackground(int headerColor) {
        headerView.setBackground(getResources().getDrawable(headerColor));
    }

    public void setBannerView(Banner banner) {
        this.banner = banner;
    }

    //设置HeaderView的标题
    public void setHeaderTitle(String title) {
        setHeaderTitle(title, MyConstant.Position.CENTER);
    }

    //设置HeaderView的标题
    public void setHeaderTitle(String title, MyConstant.Position pos) {
        setHeaderTitle(title, pos, 0);

    }

    /**
     * 设置HeaderView的标题
     *
     * @param title      标题
     * @param pos        标题位置
     * @param titleColor 标题文字颜色
     */
    public void setHeaderTitle(String title, MyConstant.Position pos, int titleColor) {
        TextView tvTitle = (TextView) headerView.findViewById(R.id.tv_header_title);
        switch (pos) {
            case LEFT:
                tvTitle.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                break;
            case CENTER:
                tvTitle.setGravity(Gravity.CENTER);
                break;
            default:
                tvTitle.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                break;
        }
        if (title == null) {
            tvTitle.setText("");
        } else {
            tvTitle.setText(title);
        }
        if (titleColor == 0) {

        } else {
            tvTitle.setTextColor(getResources().getColor(titleColor));
        }
    }


    /**
     * 设置Headerview的左右图片
     *
     * @param pos         设置左侧还是右侧
     * @param resId       显示的图片资源ID
     * @param colorFilter 是否需要添加colorFilter
     * @param listener    单击事件监听器
     */
    public void setHeaderImage(MyConstant.Position pos, int resId, boolean colorFilter, View.OnClickListener listener) {
        ImageView iv = null;
        switch (pos) {
            case LEFT:
                iv = (ImageView) headerView.findViewById(R.id.iv_header_left);
                iv.setVisibility(View.VISIBLE);
                break;

            default:
                iv = (ImageView) headerView.findViewById(R.id.iv_header_right);
                iv.setVisibility(View.VISIBLE);
                break;
        }
        iv.setImageResource(resId);

        if (colorFilter) {
            iv.setColorFilter(Color.WHITE);
        }

        if (listener != null) {
            iv.setOnClickListener(listener);
        }

    }

    /**
     * 判断多个EditText中是否有未输入内容的
     *
     * @return false 没有为空的
     * true  有未输入内容的EditText
     */

    public boolean isEmpty(EditText... editTexts) {

        for (EditText et : editTexts) {
            if (TextUtils.isEmpty(et.getText().toString())) {
                //更多span效果请参阅：http://www.cnblogs.com/JohnTsai/p/4547716.html
                SpannableString ss = new SpannableString("请输入完整！");
                //what:要设置啥效果
                //start / end 对ss添加what效果的开始/结束的位置
                //flags??
                ss.setSpan(new ForegroundColorSpan(Color.GREEN), 0, 3, SpannableString.SPAN_INCLUSIVE_INCLUSIVE);
                ss.setSpan(new BackgroundColorSpan(Color.BLACK), 3, 6, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
//                ss.setSpan(new ImageSpan(this, R.drawable.ue056),5,6,SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

                et.setError(ss);
                return true;
            }
        }

        return false;
    }

    /**
     * 要求子类必须实现
     * 如果一旦无法自动获得布局
     * 则调用该方法，使用子类提供的布局
     */
    public abstract void setContentView();

    /**
     * 钩子方法 “愿者上钩”
     * 子类可以选择性重写
     */
    public void init() {
        //NO-OP
    }

    /**
     * @param picUrls
     * @param picDiscribes
     * @param flag         为true,用指定参数的，否则用自定义的
     */
    public void addBannerUrls(List<String> picUrls, List<String> picDiscribes, boolean flag) {
        if (flag) {
            setBanner(picUrls, picDiscribes);
        } else {
            setBanner1(picUrls, picDiscribes);
        }
    }

    public void setBanner1(List<String> picUrls, List<String> picDiscribes) {
        //NOOP
        //设置图片集合
        banner.setImages(picUrls);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(picDiscribes);
    }

    public void setBanner(List<String> picUrls, List<String> picDiscribes) {
        //设置banner样式
//        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GImageLoader());
        //设置图片集合
        banner.setImages(picUrls);
        //设置banner动画效果
//        banner.setBannerAnimation(Transformer.DepthPage);
        banner.setBannerAnimation(Transformer.ScaleInOut);
        //设置标题集合（当banner样式有显示title时）
        if (picDiscribes != null) {
            banner.setBannerTitles(picDiscribes);
        }
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //banner设置方法全部调用完毕时最后调用
        banner.start();


    }

    public List<String> getDis() {
        return null;
    }

    public List<String> getUrl() {
        return null;
    }

    public void setXRefreshView(XRefreshView refreshView) {
        // 设置是否可以下拉刷新
        refreshView.setPullRefreshEnable(true);
        //设置内容加载出来以后header和fooder固定在页面的时间，默认为0
//        refreshView.setPinnedTime(1000);
//        //当下拉刷新被禁用时，调用这个方法并传入false可以不让头部被下拉
//        refreshView.setMoveHeadWhenDisablePullRefresh(true);
        // 设置是否可以上拉加载
        refreshView.setPullLoadEnable(true);
        // 设置是否可以自动刷新
        refreshView.setAutoRefresh(false);
    }

    /**
     * 设置状态栏颜色
     */
    public void setImmerBarcolor(int color) {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(color).init();
    }


    /**
     * 设置状态栏颜色
     * 解决重叠问题
     *
     * @param view
     */
    public void setImmerBarcolor(View view) {
        ImmersionBar.with(this)
                .titleBar(view) //可以为任意view，如果是自定义xml实现标题栏的话，最外层节点不能为RelativeLayout
                .statusBarDarkFont(true)
                .init();
    }

    public void setImmerBarcolor(View view, boolean darkFont) {
        ImmersionBar.with(this)
                .titleBar(view) //可以为任意view，如果是自定义xml实现标题栏的话，最外层节点不能为RelativeLayout
                .statusBarDarkFont(true)
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();//沉浸式状态栏销毁
        Myapp.activities.remove(this);
    }


    /**
     * 解决软键盘遮挡输入框
     * @param activity
     */
    public void setKeyBoardPatch5497(Activity activity){
        //解决软键盘遮挡输入框问题
        AndroidBug5497Workaround.assistActivity(activity);
    }

    /**
     * 显示等待框
     */
    public void showProgressDialog(){
        ProgressUtil.show(this);
    }
    /**
     * 隐藏等待框
     */
    public void hideProgressDialog(){
        ProgressUtil.hide();
    }
}

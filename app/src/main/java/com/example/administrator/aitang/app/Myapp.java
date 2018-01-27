package com.example.administrator.aitang.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.bulong.rudeness.RudenessScreenHelper;
import com.example.administrator.aitang.R;
import com.example.administrator.aitang.ui.WelcomeActivity;
import com.example.administrator.aitang.utils.FileProvider;
import com.example.administrator.aitang.utils.SPUtil;
import com.example.administrator.aitang.utils.okhttp.OkHttpUtils;
import com.example.administrator.aitang.zhibo.DemoCache;
import com.example.administrator.aitang.zhibo.base.util.ScreenUtil;
import com.example.administrator.aitang.zhibo.base.util.crash.AppCrashHandler;
import com.example.administrator.aitang.zhibo.base.util.log.LogUtil;
import com.example.administrator.aitang.zhibo.base.util.sys.SystemUtil;
import com.example.administrator.aitang.zhibo.im.config.AuthPreferences;
import com.example.administrator.aitang.zhibo.im.config.UserPreferences;
import com.example.administrator.aitang.zhibo.im.util.storage.StorageType;
import com.example.administrator.aitang.zhibo.im.util.storage.StorageUtil;
import com.example.administrator.aitang.zhibo.inject.FlavorDependent;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.MsgService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2017/10/18.
 */

public class Myapp extends Application {
    public static Context context;
    public static List<Activity> activities = new ArrayList<Activity>();
    public static SPUtil spUtil;

    // 存放修改手机号的3个activity--ChangeAccountInfoActivity、ChangePhoneOneActivity、ChangePhoneTwoActivity
    // 修改完手机号，直接返回到设置界面
    public static List<Activity> phoneActivities = new ArrayList<Activity>();

    // 存放做题的2个activity--KSZNLXActivity、LXBGActivity
    // 提交答案后在解析界面返回，直接回到答题之前的界面
    public static List<Activity> xiTiActivities = new ArrayList<Activity>();

    // 存放试题类型的activity--ExameTypeActivity、ExameTypeAddressActivity
    // 提交答案后在解析界面返回，直接回到答题之前的界面
    public static List<Activity> shitiTypeActivities = new ArrayList<Activity>();
    public static FileProvider fileProvider;

    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
        context = this.getApplicationContext();
        spUtil = new SPUtil(this.getApplicationContext(), "mysp");
        new RudenessScreenHelper(this, 750).activate();//屏幕适配方案
        DemoCache.setContext(this);
        fileProvider = new FileProvider(this, 1);
        NIMClient.init(this, getLoginInfo(), getOptions());
        //初始化okHttp
        initOkhttp();

        // crash handler
        AppCrashHandler.getInstance(this);

        if (inMainProcess()) {
            // 注册自定义消息附件解析器
            NIMClient.getService(MsgService.class).registerCustomAttachmentParser(FlavorDependent.getInstance().getMsgAttachmentParser());
            // init tools
            DemoCache.initImageLoaderKit();
            StorageUtil.init(this, null);
            ScreenUtil.init(this);
            // init log
            initLog();
            FlavorDependent.getInstance().onApplicationCreate();
        }

        //初始化极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

    }

    //实例化一次
    public static Context getAppContext() {
        return context;
    }

    private LoginInfo getLoginInfo() {
        String account = AuthPreferences.getUserAccount();
        String token = AuthPreferences.getUserToken();

        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            DemoCache.setAccount(account.toLowerCase());
            return new LoginInfo(account, token);
        } else {
            return null;
        }
    }

    private boolean inMainProcess() {
        String packageName = getPackageName();
        String processName = SystemUtil.getProcessName(this);
        return packageName.equals(processName);
    }

    private SDKOptions getOptions() {
        SDKOptions options = new SDKOptions();
        options.appKey = "8210a542cf018de3bb35c11282dbb23c";

        // 如果将新消息通知提醒托管给SDK完成，需要添加以下配置。
        StatusBarNotificationConfig config = UserPreferences.getStatusConfig();
        if (config == null) {
            config = new StatusBarNotificationConfig();
        }
        // 点击通知需要跳转到的界面
        config.notificationEntrance = WelcomeActivity.class;
        config.notificationSmallIconId = R.drawable.ic_stat_notify_msg;

        // 通知铃声的uri字符串
        config.notificationSound = "android.resource://com.netease.nim.demo/raw/msg";
        options.statusBarNotificationConfig = config;
        UserPreferences.setStatusConfig(config);

        // 配置保存图片，文件，log等数据的目录
        String sdkPath = Environment.getExternalStorageDirectory() + "/" + getPackageName() + "/nim/";
        options.sdkStorageRootPath = sdkPath;
        Log.i("demo", FlavorDependent.getInstance().getFlavorName() + " demo nim sdk log path=" + sdkPath);

        // 配置数据库加密秘钥
        options.databaseEncryptKey = "NETEASE";

        // 配置是否需要预下载附件缩略图
        options.preloadAttach = true;

        // 配置附件缩略图的尺寸大小，
        options.thumbnailSize = (int) (0.5 * ScreenUtil.screenWidth);

        // 用户信息提供者
        options.userInfoProvider = null;

        // 定制通知栏提醒文案（可选，如果不定制将采用SDK默认文案）
        options.messageNotifierCustomization = null;

        return options;
    }


    private void initLog() {
        String path = StorageUtil.getDirectoryByDirType(StorageType.TYPE_LOG);
        LogUtil.init(path, Log.DEBUG, this);
        LogUtil.i("demo", FlavorDependent.getInstance().getFlavorName() + " demo log path=" + path);
    }


    /**
     * 关闭所有未关闭的Activity
     */
    public static void clearAllActivities() {
        for (Activity act : activities) {
            act.finish();
        }
    }

    /**
     * 关闭当前activity之外所有的activity
     *
     * @param activity
     */
    public static void clearActivitiesExceptMe(Class<?> activity) {
        for (Activity act : activities) {
            if (!act.getClass().getSimpleName().equals(activity.getClass().getSimpleName())) {
                act.finish();
            }
        }
    }


    /**
     * 关闭当前修改手机号相关的所有activity
     *
     * @param
     */
    public static void clearPhoneActivities() {

        for (Activity act : phoneActivities) {
            if (null != act && !act.isFinishing()) {
                act.finish();
            }
        }

        activities.removeAll(phoneActivities);
        phoneActivities.clear();
    }


    /**
     * 关闭习题activity相关的所有activity
     *
     * @param
     */
    public static void clearXiTiActivities() {

        for (Activity act : xiTiActivities) {
            if (null != act && !act.isFinishing()) {
                act.finish();
            }
        }

        activities.removeAll(xiTiActivities);
        xiTiActivities.clear();
    }

    /**
     * 关闭选择实体类型activity相关的所有activity
     *
     * @param
     */
    public static void clearShiTiTypeActivities() {

        for (Activity act : shitiTypeActivities) {
            if (null != act && !act.isFinishing()) {
                act.finish();
            }
        }

        activities.removeAll(shitiTypeActivities);
        shitiTypeActivities.clear();
    }

    /**
     * 初始化okHttpClient
     */
    private void initOkhttp() {
//        try {
//            RequestManager.getInstance()
//                    .setCertificates(getAssets().open("srca.cer"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
//                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }


}

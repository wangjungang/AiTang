package com.example.administrator.aitang.ui.lianxi.shenlun;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.lianxi.ShenLunBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.fragment.lianxi.shenlun.ShenLunKSZNLXFragment;
import com.example.administrator.aitang.http.HttpManager;
import com.example.administrator.aitang.http.error.ErrorCode;
import com.example.administrator.aitang.http.listener.ServiceBackObjectListener;
import com.example.administrator.aitang.http.result.ServiceResult;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.example.administrator.aitang.utils.SocialHelperUtil;
import com.example.administrator.aitang.utils.permission.PermissionRequest;
import com.example.administrator.aitang.utils.photopicker.PhotoUtils;
import com.google.gson.Gson;
import com.tencent.connect.share.QQShare;
import com.yanzhenjie.permission.Permission;

import net.arvin.socialhelper.callback.SocialShareCallback;
import net.arvin.socialhelper.entities.ShareEntity;
import net.arvin.socialhelper.entities.WXShareEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 习题界面，有五个入口，智能组卷、预测试题、专项智能练习、历年真题、模拟试题
 */
public class ShenLunActivity extends MyBaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.img_header_left)
    ImageView img_HeaderLeft;
    @BindView(R.id.tv_header_title)
    TextView tv_HeaderTitle;
    @BindView(R.id.tv_header_right)
    TextView tv_HeaderRight;
    @BindView(R.id.tv_dati_timu)
    TextView tv_timu;
    @BindView(R.id.cm_dati_time)
    Chronometer cm;
    @BindView(R.id.img_dati_menu)
    ImageView img_menu;
    public static ViewPager vp_KsznlxAct;
    @BindView(R.id.ll_header)
    LinearLayout llHeader;

    int timuSum;
    private int currentPageIndex = 0;//当前题目的id
    private String title = "";//展示的标题
    private String qtid = "";

    public List<ShenLunKSZNLXFragment> fragments;
    public ShenLunKSZNLXFragment fragment;

    //因为是静态变量，这里再退出的时候，清空所有数据，防止数据错乱
    //接口解析得到的试题数据集合
    public static List<ShenLunBean.DataBean> shenLunData = new ArrayList<>();

    private PopupWindow poupwindow;
    private boolean shareIsRunning = false;//分享是否正在进行中
    private PermissionRequest permissionRequest;//权限请求工具类

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_ksznlx_shenlun);
        vp_KsznlxAct = findViewById(R.id.vp_ksznlxAct);
    }

    @Override
    public void init() {
        Myapp.xiTiActivities.add(this);

        setImmerBarcolor(llHeader);
        img_HeaderLeft.setImageResource(R.drawable.back_icon_nav);

        if (null != getIntent().getExtras()) {
            title = "申论专项批改（需购买）";
            qtid = getIntent().getExtras().getString(MyConstant.QTID, "");
        }

        tv_HeaderTitle.setText(title);
        tv_HeaderRight.setText("更多");
        img_menu.setImageResource(R.drawable.datika_icon);
        initData();
    }

    /**
     * 请求申论接口
     */
    private void initData() {
        Map paramsMap = new HashMap();

        String uid = Myapp.spUtil.getUid();
        String token = Myapp.spUtil.getToken();

        paramsMap.put("uid", uid);
        paramsMap.put("token", token);
        paramsMap.put("qtid", qtid);

        HttpManager.getInstance().get(MyConstant.SPECIALPRACTICE, paramsMap, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result1, String response) {
                //展示数据
                showData(response);

            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {
                if (result.getCode().equals(ErrorCode.ERROR_CODE_201)
                        || result.getCode().equals(ErrorCode.ERROR_CODE_012)
                        || result.getCode().equals(ErrorCode.ERROR_CODE_204)) {
                    toast("此分类没有题目");
                } else {
                    toast(result.getDesc());
                }
            }
        });
    }

    /**
     * 解析并展示数
     */
    private void showData(String response) {

        ShenLunBean shenLunBean = new Gson().fromJson(response, ShenLunBean.class);

        //解析Json数据
        shenLunData.clear();
        if (null != shenLunBean.getData()) {
            shenLunData.addAll(shenLunBean.getData());
        }

//        //创建答案模型
//        creatDaAnModel();


        if (null != shenLunData && shenLunData.size() > 0) {
            //初始化
            timuSum = shenLunData.size();
            initFragemnt();
            MyAdapter adapter = new MyAdapter(ShenLunActivity.this.getSupportFragmentManager());
            vp_KsznlxAct.setAdapter(adapter);
            // 设置ViewPager的监听事件
            vp_KsznlxAct.addOnPageChangeListener(ShenLunActivity.this);
            onPageSelected(0);
            cm.setBase(SystemClock.elapsedRealtime());
            cm.start();//开始计时
        } else {
            toast("此分类没有题目");
        }
    }

//    /**
//     * 创建答案模型
//     */
//    private void creatDaAnModel() {
//        //清空集合
//        tianXieDaAnList.clear();
//        zhengQueDaAnList.clear();
//
//        PracticeBean practiceBean;
//        //提取正确答案，并创建填写答案的模型集合
//        for (int i = 0; i < adapterData.size(); i++) {
//            practiceBean = adapterData.get(i);
//            TianXieDaAnBean tianXieDaAnBean = null;
//            ZhengQueDaAnBean zhengQueDaAnBean = null;
//            if (null != practiceBean) {
//                String type = practiceBean.getQtype();//试题类型：1选择题 2材料题 3主观题 其中12选择题、3主观题
//                //创建填写答案的模型
//                tianXieDaAnBean = new TianXieDaAnBean();
//                tianXieDaAnBean.setId(i);
//                tianXieDaAnBean.settId(practiceBean.getQid());
//                //创建正确答案的模型
//                zhengQueDaAnBean = new ZhengQueDaAnBean();
//                zhengQueDaAnBean.setId(i);
//                zhengQueDaAnBean.settId(practiceBean.getQid());
//
//                String success = practiceBean.getQsuccess();
//                //判断是否为主观题
//                if (!StringUtils.isEmpty(type) && !StringUtils.isEmpty(success) && (type.equals("1") || type.equals("2"))) {//选择题
//                    tianXieDaAnBean.setType("0");//选择题
//                    zhengQueDaAnBean.setType("0");
//                    //如果是多选，进行拆分
//                    if (success.length() > 1) {//长度大于1说明为多个答案
//                        tianXieDaAnBean.setIsSingle("1");
//                        zhengQueDaAnBean.setIsSingle("1");
//                    } else {
//                        tianXieDaAnBean.setIsSingle("0");
//                        zhengQueDaAnBean.setIsSingle("0");
//                    }
//
//                    int length = success.length();
//                    List<Integer> answerList = new ArrayList<Integer>();
//
//                    for (int j = 0; j < length; j++) {
//                        answerList.add(getPos(String.valueOf(success.charAt(j))));
//                    }
//                    zhengQueDaAnBean.setAnswerList(answerList);
//                } else if (!StringUtils.isEmpty(type) && type.equals("3")) {//主观题
//                    tianXieDaAnBean.setType("1");//主观题
//                    zhengQueDaAnBean.setType("1");
//                }
//
//                tianXieDaAnList.add(tianXieDaAnBean);//保存填写答案模型
//                zhengQueDaAnList.add(zhengQueDaAnBean);//保存正确答案模型
//            }
//        }
//    }


    /**
     * 初始化题目的fragemnt
     * i是从服务器获取的题目的个数
     * 1是选择的答案，在重新回到这个页面的时候需要用到
     */
    private void initFragemnt() {
        fragments = new ArrayList<ShenLunKSZNLXFragment>();
        Log.d("TAG", "size=" + shenLunData.size());
        for (int i = 0; i < shenLunData.size(); i++) {
            fragment = new ShenLunKSZNLXFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("data", shenLunData.get(i));
            bundle.putInt("num", i);
            if (i == shenLunData.size() - 1) {
                bundle.putBoolean("isLast", true);
            }
            fragment.setArguments(bundle);
            //把fragemnt添加到Fragemnt集合中
            fragments.add(fragment);
        }
    }


    /**
     * 点击事件
     *
     * @param view
     */
    @OnClick({R.id.img_header_left, R.id.tv_header_title, R.id.tv_header_right, R.id.img_dati_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_header_left:
                finish();
                break;
            case R.id.tv_header_title:
                break;
            case R.id.tv_header_right:
                getPopuwindow();
                break;
            case R.id.img_dati_menu:
                Intent intent = new Intent(this, ShenLunDaTiKaActivity.class);
                Bundle bundle = new Bundle();
//                bundle.putInt("sum", shenLunData.size());
                bundle.putLong("uptimes_start", cm.getBase());
                intent.putExtras(bundle);
                jumpTo(intent, false);
                break;
        }
    }


    public void getPopuwindow() {

        View contentView = View.inflate(this, R.layout.poupwindow_layout_xiti, null);
        poupwindow = new PopupWindow(contentView, 500, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置窗体是否可以触摸 默认是true
        poupwindow.setTouchable(true);
        //设置popupWindown获取焦点 这样输入框才能获取到焦点  默认为false
        poupwindow.setFocusable(true);
        //设置窗体外面部分可以触摸 如果true 触摸外面的时候将会隐藏窗体
        poupwindow.setOutsideTouchable(true);
        //上面的方法要结合着 设置背景去用 new BitmapDrawable()意思是一个空的背景
        /**
         * 两个方法结合使用 可以使点击周围的时候 窗体消失 ;点击返回键的时候窗体消失 而不是直接作用在activity上
         */
        poupwindow.setBackgroundDrawable(new BitmapDrawable());
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                poupwindow.dismiss();
            }
        });

        poupwindow.showAsDropDown(llHeader, 0, 0, Gravity.RIGHT);

        TextView tvShouCang = contentView.findViewById(R.id.tv_shoucang);
        TextView tvShare = contentView.findViewById(R.id.tv_share);
        tvShouCang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//收藏
                addCollection();
                cancelPopupWindow();
            }
        });

        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//分享
                requestPermissions();
                cancelPopupWindow();
            }
        });

    }

    /**
     * 取消popupwindow
     */
    public void cancelPopupWindow() {
        poupwindow.dismiss();
    }

    /**
     * 调用接口，收藏本题
     */
    private void addCollection() {
        Map paramsMap = new HashMap();

        String uid = Myapp.spUtil.getUid();
        String token = Myapp.spUtil.getToken();

        if (null == shenLunData || shenLunData.size() <= 0) {
            return;
        }
        String qid = shenLunData.get(currentPageIndex).getMqid();

        paramsMap.put("uid", uid);
        paramsMap.put("token", token);
        paramsMap.put("qid", qid);

        HttpManager.getInstance().post(MyConstant.ADDUSERCOLLECTION, paramsMap, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {
                toast("收藏成功");
            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {
                toast(result.getDesc());
            }
        });
    }

    /**
     * 请求权限，成功选择图片
     */
    private void requestPermissions() {
        if (null == permissionRequest) {
            permissionRequest = new PermissionRequest(this, new PermissionRequest.PermissionCallback() {
                @Override
                public void onSuccessful() {
                    //请求成功
                    showShareBottomDialog();
                }

                @Override
                public void onFailure() {
                    //失败，如果是拒绝默认提示，如果点击不再提示，下次提示跳转设置
                }
            });
        }
        //请求照相和读写内存权限
        permissionRequest.requestGroup(Permission.STORAGE);

    }

    /**
     * 显示分享菜单
     */
    private void showShareBottomDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_bottom_share, null);

        LinearLayout llQQ = view.findViewById(R.id.ll_qq);
        LinearLayout llWeiXin = view.findViewById(R.id.ll_weixin);
        LinearLayout llPengYouQuan = view.findViewById(R.id.ll_weixin_pengyouquan);
        llQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareTiMu(1);
            }
        });

        llWeiXin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareTiMu(2);
            }
        });

        llPengYouQuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareTiMu(3);
            }
        });
        dialog.setContentView(view);
        dialog.show();

    }

    /**
     * 分享题目到qq
     * <p>
     * type 1qq 2微信 3朋友圈
     */
    private void shareTiMu(int shareType) {
        Integer[] arr = new Integer[]{1, 2, 3};//1qq 2微信 3朋友
        final int type;
        //判断如果shareType存在则分享，否则弹出
        if (Arrays.asList(arr).contains(shareType)) {
            type = shareType;
        } else {
            return;
        }

        if (!shareIsRunning) {
            shareIsRunning = true;
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    Bitmap bitmap = fragments.get(currentPageIndex).creatShareBitmap();
                    String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";
                    boolean isOk = PhotoUtils.saveBitmap(bitmap, fileName);
                    if (isOk) {
                        Message message = Message.obtain();
                        message.what = type;
                        message.obj = fileName;
                        handler.sendMessage(message);
                    } else {
                        //如果失败，重置
                        shareIsRunning = false;
                    }

                }
            }.start();
        }

    }

    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            shareIsRunning = false;
            switch (msg.what) {
                case 1:
                    shareToQQ(msg);
                    break;
                case 2:
                    shareToWeiXin(msg);
                    break;
                case 3:
                    shareToWeiXinPengYouQuan(msg);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 分享题目到QQ
     */
    private void shareToQQ(Message msg) {
        String photoName = (String) msg.obj;
        String photoPath = PhotoUtils.getPhotoFolderPath() + photoName;
        Bundle params = new Bundle();

        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, photoPath);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "爱唐教育");
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        ShareEntity shareEntity = new ShareEntity(ShareEntity.TYPE_QQ);

        shareEntity.setParams(params);
        SocialHelperUtil.getInstance().socialHelper().shareQQ(ShenLunActivity.this, new SocialShareCallback() {
            @Override
            public void shareSuccess() {
                //通知后台分享完成
                requestAlreadyShare();
            }

            @Override
            public void socialError(String msg) {
                toast(msg);
            }
        }, shareEntity);

    }

    /**
     * 分享题目到weixin
     */
    private void shareToWeiXin(Message msg) {
        String photoName = (String) msg.obj;
        String photoPath = PhotoUtils.getPhotoFolderPath() + photoName;
        Bundle params = new Bundle();

        params.putInt(WXShareEntity.KEY_WX_TYPE, WXShareEntity.TYPE_IMG);
        params.putString(WXShareEntity.KEY_WX_IMG_LOCAL, photoPath);

        ShareEntity shareEntity = new ShareEntity(ShareEntity.TYPE_WX);
        shareEntity.setParams(params);
        SocialHelperUtil.getInstance().socialHelper().shareWX(this, new SocialShareCallback() {
            @Override
            public void shareSuccess() {
                //通知后台分享完成
                requestAlreadyShare();
            }

            @Override
            public void socialError(String msg) {
                toast(msg);
            }
        }, shareEntity);

    }

    /**
     * 分享题目到微信朋友圈
     */
    private void shareToWeiXinPengYouQuan(Message msg) {
        String photoName = (String) msg.obj;
        String photoPath = PhotoUtils.getPhotoFolderPath() + photoName;
        Bundle params = new Bundle();

        params.putInt(WXShareEntity.KEY_WX_TYPE, WXShareEntity.TYPE_IMG);
        params.putString(WXShareEntity.KEY_WX_IMG_LOCAL, photoPath);

        ShareEntity shareEntity = new ShareEntity(ShareEntity.TYPE_PYQ);
        shareEntity.setParams(params);
        SocialHelperUtil.getInstance().socialHelper().shareWX(this, new SocialShareCallback() {
            @Override
            public void shareSuccess() {
                //通知后台分享完成
                requestAlreadyShare();
            }

            @Override
            public void socialError(String msg) {
                toast(msg);
            }
        }, shareEntity);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && SocialHelperUtil.getInstance().socialHelper() != null) {//qq分享如果选择留在qq，通过home键退出，再进入app则不会有回调
            SocialHelperUtil.getInstance().socialHelper().onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 请求接口，已经分享完成
     */
    private void requestAlreadyShare() {

        Map paramsMap = new HashMap();

        String uid = Myapp.spUtil.getUid();
        String token = Myapp.spUtil.getToken();

        paramsMap.put("uid", uid);
        paramsMap.put("token", token);
        paramsMap.put("type", "2");//1 签到 2 分享

        HttpManager.getInstance().post(MyConstant.SIGN, paramsMap, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {
                toast("分享成功");
            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {
            }
        });

    }


    // 单页面被选择时
    @Override
    public void onPageSelected(int arg0) {
        tv_timu.setText((arg0 + 1) + "/" + timuSum);
        fragment.setNum(arg0);
        currentPageIndex = arg0;
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            return fragments.get(arg0);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return fragments.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消三方分享的回调绑定
        SocialHelperUtil.getInstance().socialHelper().clear();

        //因为是静态变量，这里再退出的时候，清空所有数据，防止数据错乱
        shenLunData.clear();
        Myapp.xiTiActivities.remove(this);
    }
}

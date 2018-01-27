package com.example.administrator.aitang.ui.lianxi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.lianxi.CollectionListBean;
import com.example.administrator.aitang.bean.lianxi.PracticeBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.fragment.lianxi.CTFXFragment;
import com.example.administrator.aitang.fragment.lianxi.CollectionDetailFragment;
import com.example.administrator.aitang.http.HttpManager;
import com.example.administrator.aitang.http.listener.ServiceBackObjectListener;
import com.example.administrator.aitang.http.result.ServiceResult;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.example.administrator.aitang.utils.basic.StringUtils;
import com.example.administrator.aitang.utils.permission.PermissionRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 习题解析界面
 */
public class CollectionDetailActivity extends MyBaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.iv_header_left)
    ImageView iv_left;
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.headerview)
    LinearLayout llHeader;
    @BindView(R.id.iv_header_right)
    ImageView iv_right;
    @BindView(R.id.tv_type_cuotifenxiAct)
    TextView tv_type;
    @BindView(R.id.vp_cuotifenxiAct)
    ViewPager vp;

    List<PracticeBean> datas = new ArrayList<>();//习题

    private int currentPageIndex = 0;//当前题目的id
    public static List<CollectionDetailFragment> jieXiFragments;
    private CollectionDetailFragment fragment;
    private PopupWindow poupwindow;
    private boolean shareIsRunning = false;//分享是否正在进行中
    private PermissionRequest permissionRequest;//权限请求工具类

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_cuo_ti_fen_xi);
    }

    @Override
    public void init() {
        setImmerBarcolor(llHeader);
        iv_left.setImageResource(R.drawable.back_icon_nav);
        iv_left.setVisibility(View.VISIBLE);
        tv_title.setText("收藏题目");

        iv_right.setImageResource(R.drawable.gengduo_icon_default_cuotifenxi);
        iv_right.setVisibility(View.GONE);

        requestDetail();
    }

    /**
     * 请求详情
     */
    private void requestDetail() {

        Bundle bundle = getIntent().getExtras();
        CollectionListBean.DataBean data = (CollectionListBean.DataBean) bundle.getSerializable("data");
        tv_type.setText(data.getName());


        Map paramsMap = new HashMap();
        String uid = Myapp.spUtil.getUid();
        String token = Myapp.spUtil.getToken();

        paramsMap.put("uid", uid);
        paramsMap.put("token", token);
        paramsMap.put("uqid", data.getUqid());

        HttpManager.getInstance().get(MyConstant.USERQUESTIONDETAIL, paramsMap, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {
//                Gson gson = new Gson();
//                CollectionDetailBean detailBean = gson.fromJson(response, CollectionDetailBean.class);

                List<PracticeBean> beanList = disposeJson(response);

                datas.clear();
                datas.addAll(beanList);
                initFragemnt();
            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {
//                toast(result.getDesc());
            }
        });

    }

    /**
     * 解析历年真题Json数据
     * <p>
     * 说明：如果字段返回的是“zxtc”,字符串，其实是空字符串，这里手动替换成""
     *
     * @param jsonStr
     */
    private List<PracticeBean> disposeJson(String jsonStr) {

        //处理得到的数据集合
        List<PracticeBean> beanList = new ArrayList<>();
        try {

            JSONObject obj = new JSONObject(jsonStr);
            JSONArray data = obj.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {

                PracticeBean bean = new PracticeBean();
//                JSONObject object = data.getJSONObject(i);
                JSONObject object = data.getJSONObject(i).getJSONObject("questions");

                bean.setQid(object.getString("qid"));
                bean.setQcid(object.getString("qcid"));
                bean.setQctypeid(object.getString("qctypeid"));
                bean.setQtpath(object.getString("qtpath"));

                bean.setQtitle(object.getString("qtitle").replace("zxtc", ""));
                bean.setQtid(object.getString("qtid"));
                bean.setAreaid(object.getString("areaid"));
                bean.setQtgroup(object.getString("qtgroup"));
                bean.setQtype(object.getString("qtype"));

                bean.setQerror(object.getString("qerror").replace("zxtc", ""));
                bean.setQsuccess(object.getString("qsuccess").replace("zxtc", ""));
                bean.setPlaynum(object.getString("playnum").replace("zxtc", ""));
                bean.setSuccessnum(object.getString("successnum").replace("zxtc", ""));
                bean.setQdegree(object.getString("qdegree").replace("zxtc", ""));
                bean.setTime(object.getString("time").replace("zxtc", ""));
                bean.setCome(object.getString("come").replace("zxtc", ""));
                bean.setKaodian(object.getString("kaodian").replace("zxtc", ""));
                bean.setAccuracy(object.getString("accuracy").replace("zxtc", ""));


                /*********************************题目***********************************/
                JSONArray qcontentArray = object.getJSONArray("qcontent");
                List<String> contentStrList = new ArrayList<>(); //按照服务器返回的数据保存成list是因为要换行
                for (int j = 0; j < qcontentArray.length(); j++) {

                    //文字
                    if (qcontentArray.get(j).getClass().equals(JSONArray.class)) {
                        StringBuilder contentBuilder = new StringBuilder("");
                        for (int k = 0; k < qcontentArray.getJSONArray(j).length(); k++) {
                            String contStr = qcontentArray.getJSONArray(j).getString(k).replace("&nbsp;", "");
                            if (!StringUtils.isEmpty(contStr) && contStr.contains("http://")) {
                                String s = "<br/><img src='" + contStr + "'/><br/>";
                                contentBuilder.append(s);
                            } else {
                                contentBuilder.append(contStr);
                            }
                        }

                        contentStrList.add(contentBuilder.toString());
                    }

                    //如果有标题，拼接到最后一个
                    if (!StringUtils.isEmpty(bean.getQtitle())) {
                        contentStrList.add(bean.getQtitle());
                    }
                }
                bean.setQcontent(contentStrList);


                /*********************************答案***********************************/
                //答案,可能返回数组，也可能返回单个字符串

                //答案的文字串
                List<String> answerStrList = new ArrayList<>();
                if (object.get("qanswer").getClass().equals(JSONArray.class)) {

                    JSONArray qanswerArray = object.getJSONArray("qanswer");

                    for (int j = 0; j < qanswerArray.length(); j++) {
                        StringBuilder answerBuilder = new StringBuilder("");
                        for (int k = 0; k < qanswerArray.getJSONArray(j).length(); k++) {

                            String answerStr = qanswerArray.getJSONArray(j).getString(k).replace("&nbsp;", "");
                            if (!StringUtils.isEmpty(answerStr) && answerStr.contains("http://")) {
                                //图片按一张取，用⑮进行拼接，展示时再拆分
                                String s = "⑮" + answerStr + "⑮";
                                answerBuilder.append(s);
                            } else {
                                answerBuilder.append(answerStr);
                            }

                        }
                        answerStrList.add(answerBuilder.toString());
                    }


                } else if (object.get("qanswer").getClass().equals(String.class)) {
                    //如果是字符串
                    String answerStr = object.getString("qanswer").replace("&nbsp;", "");
                    answerStrList.add(answerStr.replace("zxtc", ""));
                }
                bean.setQanswer(answerStrList);


                /*********************************解析***********************************/
                List<String> desStrList = new ArrayList<>();

                if (object.get("qdes").getClass().equals(JSONArray.class)) {

                    JSONArray desArray = object.getJSONArray("qdes");

                    for (int j = 0; j < desArray.length(); j++) {

                        //文字
                        if (desArray.get(j).getClass().equals(JSONArray.class)) {

                            StringBuilder desBuilder = new StringBuilder("");
                            for (int k = 0; k < desArray.getJSONArray(j).length(); k++) {

                                String desStr = desArray.getJSONArray(j).getString(k).replace("&nbsp;", "");

                                if (!StringUtils.isEmpty(desStr) && desStr.contains("http://")) {
                                    String s = "<br/><img src='" + desStr + "'/><br/>";
                                    desBuilder.append(s);
                                } else {
                                    desBuilder.append(desStr);
                                }

                            }
                            desStrList.add(desBuilder.toString());
                        }

                    }

                } else if (object.get("qdes").getClass().equals(String.class)) {
                    String desStr = object.getString("qdes").replace("&nbsp;", "");
                    desStrList.add(desStr.replace("zxtc", ""));
                }
                bean.setQdes(desStrList);

                //添加本行数据到集合
                beanList.add(bean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return beanList;
    }

    private void initFragemnt() {
        jieXiFragments = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            fragment = new CollectionDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("data", datas.get(i));//习题
            bundle.putInt("index", i);//习题下标
            fragment.setArguments(bundle);
            //把fragemnt添加到Fragemnt集合中
            fragment.setNum(i);//这只当前题目的下标，用来获取相应答案
            jieXiFragments.add(fragment);
        }
        MyAdapter adapter = new MyAdapter(CollectionDetailActivity.this.getSupportFragmentManager());
        vp.setAdapter(adapter);
        // 设置ViewPager的监听事件
        vp.addOnPageChangeListener(this);
        onPageSelected(0);
    }

    @OnClick({R.id.iv_header_left, R.id.iv_header_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                finish();
                break;
            case R.id.iv_header_right:
//                getPopuwindow();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPageIndex = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            return jieXiFragments.get(arg0);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return jieXiFragments.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

        }
    }


//    public void getPopuwindow() {
//
//        View contentView = View.inflate(this, R.layout.poupwindow_layout_cuotifenxi, null);
//        poupwindow = new PopupWindow(contentView, 500, ViewGroup.LayoutParams.WRAP_CONTENT);
//        //设置窗体是否可以触摸 默认是true
//        poupwindow.setTouchable(true);
//        //设置popupWindown获取焦点 这样输入框才能获取到焦点  默认为false
//        poupwindow.setFocusable(true);
//        //设置窗体外面部分可以触摸 如果true 触摸外面的时候将会隐藏窗体
//        poupwindow.setOutsideTouchable(true);
//        //上面的方法要结合着 设置背景去用 new BitmapDrawable()意思是一个空的背景
//        /**
//         * 两个方法结合使用 可以使点击周围的时候 窗体消失 ;点击返回键的时候窗体消失 而不是直接作用在activity上
//         */
//        poupwindow.setBackgroundDrawable(new BitmapDrawable());
//        contentView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                poupwindow.dismiss();
//            }
//        });
//
//        poupwindow.showAsDropDown(llHeader, 0, 0, Gravity.RIGHT);
//
//        TextView tvShouCang = contentView.findViewById(R.id.tv_shoucang);
//        TextView tvShare = contentView.findViewById(R.id.tv_share);
//        tvShouCang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {//收藏
//                addCollection();
//                cancelPopupWindow();
//            }
//        });
//
//        tvShare.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {//分享
//                requestPermissions();
//                cancelPopupWindow();
//            }
//        });
//
//        poupwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                iv_right.setImageResource(R.drawable.gengduo_icon_default_cuotifenxi);
//            }
//        });
//        iv_right.setImageResource(R.drawable.gengduo_icon_selected_cuotifenxi);
//    }
//
//
//    /**
//     * 调用接口，收藏本题
//     */
//    private void addCollection() {
//        showProgressDialog();
//        Map paramsMap = new HashMap();
//
//        String uid = Myapp.spUtil.getUid();
//        String token = Myapp.spUtil.getToken();
//        if (null == datas || datas.size() <= 0) {
//            return;
//        }
//        String qid = datas.get(currentPageIndex).getQid();
//
//        paramsMap.put("uid", uid);
//        paramsMap.put("token", token);
//        paramsMap.put("qid", qid);
//
//        HttpManager.getInstance().post(MyConstant.ADDUSERCOLLECTION, paramsMap, new ServiceBackObjectListener() {
//            @Override
//            public void onSuccess(ServiceResult result, String response) {
//                hideProgressDialog();
//                toast("收藏成功");
//            }
//
//            @Override
//            public void onFailure(ServiceResult result, Object obj) {
//                hideProgressDialog();
//                toast(result.getDesc());
//            }
//        });
//    }
//
//    /**
//     * 取消popupwindow
//     */
//    public void cancelPopupWindow() {
//        poupwindow.dismiss();
//    }
//
//
//    /**
//     * 请求权限，成功选择图片
//     */
//    private void requestPermissions() {
//        if (null == permissionRequest) {
//            permissionRequest = new PermissionRequest(this, new PermissionRequest.PermissionCallback() {
//                @Override
//                public void onSuccessful() {
//                    //请求成功
//                    showShareBottomDialog();
//                }
//
//                @Override
//                public void onFailure() {
//                    //失败，如果是拒绝默认提示，如果点击不再提示，下次提示跳转设置
//                }
//            });
//        }
//        //请求照相和读写内存权限
//        permissionRequest.requestGroup(Permission.STORAGE);
//
//    }
//
//    /**
//     * 显示分享菜单
//     */
//    private void showShareBottomDialog() {
//        BottomSheetDialog dialog = new BottomSheetDialog(this);
//        View view = LayoutInflater.from(this).inflate(R.layout.dialog_bottom_share, null);
//
//        LinearLayout llQQ = view.findViewById(R.id.ll_qq);
//        LinearLayout llWeiXin = view.findViewById(R.id.ll_weixin);
//        LinearLayout llPengYouQuan = view.findViewById(R.id.ll_weixin_pengyouquan);
//        llQQ.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                shareTiMu(1);
//            }
//        });
//
//        llWeiXin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                shareTiMu(2);
//            }
//        });
//
//        llPengYouQuan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                shareTiMu(3);
//            }
//        });
//        dialog.setContentView(view);
//        dialog.show();
//
//    }
//
//    /**
//     * 分享题目到qq
//     * <p>
//     * type 1qq 2微信 3朋友圈
//     */
//    private void shareTiMu(int shareType) {
//        Integer[] arr = new Integer[]{1, 2, 3};//1qq 2微信 3朋友
//        final int type;
//        //判断如果shareType存在则分享，否则弹出
//        if (Arrays.asList(arr).contains(shareType)) {
//            type = shareType;
//        } else {
//            return;
//        }
//
//        if (!shareIsRunning) {
//            shareIsRunning = true;
//            new Thread() {
//                @Override
//                public void run() {
//                    super.run();
//                    Bitmap bitmap = fragments_cuotifenxi.get(currentPageIndex).creatShareBitmap();
//                    String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";
//                    boolean isOk = PhotoUtils.saveBitmap(bitmap, fileName);
//                    if (isOk) {
//                        Message message = Message.obtain();
//                        message.what = type;
//                        message.obj = fileName;
//                        handler.sendMessage(message);
//                    } else {
//                        //如果失败，重置
//                        shareIsRunning = false;
//                    }
//
//                }
//            }.start();
//        }
//
//    }
//
//    private android.os.Handler handler = new android.os.Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//
//            shareIsRunning = false;
//            switch (msg.what) {
//                case 1:
//                    shareToQQ(msg);
//                    break;
//                case 2:
//                    shareToWeiXin(msg);
//                    break;
//                case 3:
//                    shareToWeiXinPengYouQuan(msg);
//                    break;
//                default:
//                    break;
//            }
//        }
//    };
//
//    /**
//     * 分享题目到QQ
//     */
//    private void shareToQQ(Message msg) {
//        String photoName = (String) msg.obj;
//        String photoPath = PhotoUtils.getPhotoFolderPath() + photoName;
//        Bundle params = new Bundle();
//
//        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, photoPath);
//        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "爱唐教育");
//        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
//        ShareEntity shareEntity = new ShareEntity(ShareEntity.TYPE_QQ);
//
//        shareEntity.setParams(params);
//        SocialHelperUtil.getInstance().socialHelper().shareQQ(CollectionDetailActivity.this, new SocialShareCallback() {
//            @Override
//            public void shareSuccess() {
//                //通知后台分享完成
//                requestAlreadyShare();
//            }
//
//            @Override
//            public void socialError(String msg) {
//            }
//        }, shareEntity);
//
//    }
//
//    /**
//     * 分享题目到weixin
//     */
//    private void shareToWeiXin(Message msg) {
//        String photoName = (String) msg.obj;
//        String photoPath = PhotoUtils.getPhotoFolderPath() + photoName;
//        Bundle params = new Bundle();
//
//        params.putInt(WXShareEntity.KEY_WX_TYPE, WXShareEntity.TYPE_IMG);
//        params.putString(WXShareEntity.KEY_WX_IMG_LOCAL, photoPath);
//
//        ShareEntity shareEntity = new ShareEntity(ShareEntity.TYPE_WX);
//        shareEntity.setParams(params);
//        SocialHelperUtil.getInstance().socialHelper().shareWX(this, new SocialShareCallback() {
//            @Override
//            public void shareSuccess() {
//                //通知后台分享完成
//                requestAlreadyShare();
//            }
//
//            @Override
//            public void socialError(String msg) {
//
//            }
//        }, shareEntity);
//
//    }
//
//    /**
//     * 分享题目到微信朋友圈
//     */
//    private void shareToWeiXinPengYouQuan(Message msg) {
//        String photoName = (String) msg.obj;
//        String photoPath = PhotoUtils.getPhotoFolderPath() + photoName;
//        Bundle params = new Bundle();
//
//        params.putInt(WXShareEntity.KEY_WX_TYPE, WXShareEntity.TYPE_IMG);
//        params.putString(WXShareEntity.KEY_WX_IMG_LOCAL, photoPath);
//
//        ShareEntity shareEntity = new ShareEntity(ShareEntity.TYPE_PYQ);
//        shareEntity.setParams(params);
//        SocialHelperUtil.getInstance().socialHelper().shareWX(this, new SocialShareCallback() {
//            @Override
//            public void shareSuccess() {
//                //通知后台分享完成
//                requestAlreadyShare();
//            }
//
//            @Override
//            public void socialError(String msg) {
//
//            }
//        }, shareEntity);
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (data != null && SocialHelperUtil.getInstance().socialHelper() != null) {//qq分享如果选择留在qq，通过home键退出，再进入app则不会有回调
//            SocialHelperUtil.getInstance().socialHelper().onActivityResult(requestCode, resultCode, data);
//        }
//    }
//
//    /**
//     * 请求接口，已经分享完成
//     */
//    private void requestAlreadyShare() {
//
//        Map paramsMap = new HashMap();
//
//        String uid = Myapp.spUtil.getUid();
//        String token = Myapp.spUtil.getToken();
//
//        paramsMap.put("uid", uid);
//        paramsMap.put("token", token);
//        paramsMap.put("type", "1");
//
//        HttpManager.getInstance().post(MyConstant.SIGN, paramsMap, new ServiceBackObjectListener() {
//            @Override
//            public void onSuccess(ServiceResult result, String response) {
////{"code":"200","data":{"uid":"52","depottype":"1","depotnum":"1","depottime":"1512703952","time":"1512703952","day":"1","tip":"zxtc"}}
//            }
//
//            @Override
//            public void onFailure(ServiceResult result, Object obj) {
//            }
//        });
//
//    }
}

package com.example.administrator.aitang.fragment.lianxi;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.lianxi.DaanAdapter;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.lianxi.PracticeBean;
import com.example.administrator.aitang.bean.lianxi.TianXieDaAnBean;
import com.example.administrator.aitang.bean.lianxi.UploadImgBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.fragment.MyBaseFragment;
import com.example.administrator.aitang.http.HttpManager;
import com.example.administrator.aitang.http.listener.ServiceBackObjectListener;
import com.example.administrator.aitang.http.result.ServiceResult;
import com.example.administrator.aitang.ui.lianxi.KSZNLXActivity;
import com.example.administrator.aitang.ui.lianxi.LXBGActivity;
import com.example.administrator.aitang.utils.HtmlStrUtils;
import com.example.administrator.aitang.utils.basic.ProgressUtil;
import com.example.administrator.aitang.utils.basic.StringUtils;
import com.example.administrator.aitang.utils.glide.GlideUtils;
import com.example.administrator.aitang.utils.permission.PermissionRequest;
import com.example.administrator.aitang.utils.photopicker.PhotoUtils;
import com.example.administrator.aitang.views.ImageUploadFailureDialog;
import com.example.administrator.aitang.views.htmltextview.HtmlTextView;
import com.google.gson.Gson;
import com.yanzhenjie.permission.Permission;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.example.administrator.aitang.ui.lianxi.KSZNLXActivity.adapterData;
import static com.example.administrator.aitang.ui.lianxi.KSZNLXActivity.tianXieDaAnList;
import static com.example.administrator.aitang.ui.lianxi.KSZNLXActivity.xitiType;


/**
 * 快速智能练习
 */
public class KSZNLXFragment extends MyBaseFragment implements AdapterView.OnItemClickListener {

    @BindView(R.id.sv_ksznlxAct)
    ScrollView sv;
    @BindView(R.id.tv_ksznlxAct_timucontent)
    HtmlTextView tvKsznlxActTimuContent;//题目内容
    //    @BindView(tv_ksznlxAct_timuType)
//    TextView tvKsznlxActTimuType;//试题类型
    @BindView(R.id.mlsv_ksznlxAct_daan)
    ListView mlsvKsznlxActDaan;//答案
    @BindView(R.id.btn_ksznlxAct_tijiao)
    Button btnKsznlxActTijiao;
    @BindView(R.id.et_answer)
    EditText etAnswer; //主观题答案的输入框
    @BindView(R.id.tv_text_num)
    TextView tvTextNum;//主观题的下一道题按钮
    @BindView(R.id.btn_next)
    Button btnNext;//主观题的下一道题按钮
    @BindView(R.id.ll_share_content)
    LinearLayout llShareContent;//要分享的内容
    @BindView(R.id.ll_zhuguanti_content_view)
    LinearLayout llZhuguantiContentView;//主观题的整体布局
    @BindView(R.id.btn_zhuguan_add_img)
    ImageView btnZhuguanAddImg;//主观题添加图片按钮
    @BindView(R.id.iv_zhuguan_1)
    ImageView ivZhuguan1;//主观题第一张图片
    @BindView(R.id.iv_zhuguan_2)
    ImageView ivZhuguan2;//主观题第二张图片
    @BindView(R.id.iv_zhuguan_3)
    ImageView ivZhuguan3;//主观题第三张图片
    View v;

    Chronometer cm;//activity中的计时器
    private long recordingTime = 0;// 记录下来的总时间
    int num;//当前题目的下标
    boolean isLast;//最后一个题目
    PracticeBean data;//习题数据源
    DaanAdapter adapter;

    private PermissionRequest permissionRequest;//权限请求工具类
    public static final int REQUEST_CODE_CHOOSE = 23;//选择图片请求码
    public static final int REQUEST_CODE_JIEXI = 30;//跳转解析界面的请求码
    public static final int RESULT_CODE_JIEXI = 31;//解析界面返回的返回码
    private List<Uri> mSelectedImgUris = new ArrayList<>();//已选择的图片Uri

    @Override
    public void onResume() {
//        if (timu.get(getNum()) != -1 && adapter != null) {
//            adapter.notifyDataSetChanged();
//        }
        super.onResume();
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_ksznlx, container, false);
        Bundle bundle = getArguments();//从activity传过来的Bundle
        if (bundle != null) {
            num = bundle.getInt("num");
            isLast = bundle.getBoolean("isLast", false);
            data = bundle.getParcelable("data");
        }

        return v;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof KSZNLXActivity) {
            KSZNLXActivity ksznlxActivity = (KSZNLXActivity) activity;
            cm = (Chronometer) ksznlxActivity.findViewById(R.id.cm_dati_time);
        }
    }

    @Override
    public void init() {
        if (isLast) {
            btnKsznlxActTijiao = v.findViewById(R.id.btn_ksznlxAct_tijiao);
            btnKsznlxActTijiao.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.GONE);
        }

        if (data != null) {
            String type = data.getQtype();//试题类型：1选择题 2材料题 3主观题 其中12展示listview、3展示输入框
            if (type.equals("1")) {
                showAnswerListView();
            } else if (type.equals("2")) {
                showAnswerListView();
            } else if (type.equals("3")) {
                showAnswerEditContentView();
            }
            //请求焦点防止界面抖动
            tvKsznlxActTimuContent.setFocusableInTouchMode(true);
            tvKsznlxActTimuContent.requestFocus();

            //展示题目
            List<String> contentList = data.getQcontent();
            tvKsznlxActTimuContent.setHtmlFromString(HtmlStrUtils.getInstance().getHtmlString(contentList), false);

        }

    }

    /**
     * 试题类型为3 展示为编辑框的view
     */
    private void showAnswerEditContentView() {
        //隐藏editcontent 展示listview
        llZhuguantiContentView.setVisibility(View.VISIBLE);
        mlsvKsznlxActDaan.setVisibility(View.GONE);

        etAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                tvTextNum.setText(charSequence.length() + "/1000");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //保存主观题填写内容
                tianXieDaAnList.get(num).setContent(etAnswer.getText().toString());
            }
        });

    }

    /**
     * 试题类型：1选择题 2材料题 展示listview
     */
    private void showAnswerListView() {
        //展示listview 隐藏editcontent
        llZhuguantiContentView.setVisibility(View.GONE);
        mlsvKsznlxActDaan.setVisibility(View.VISIBLE);

        mlsvKsznlxActDaan.setOnItemClickListener(this);
        List<String> datas = new ArrayList<>();
        adapter = new DaanAdapter(getActivity(), datas);
        mlsvKsznlxActDaan.setAdapter(adapter);
        setListViewHeightBasedOnChildren(mlsvKsznlxActDaan);
        List<String> daan = data.getQanswer();
        adapter.addAll(daan, false);
        sv.smoothScrollTo(0, 0);
        sv.scrollTo(0, 0);
    }

    @OnClick({R.id.btn_ksznlxAct_tijiao, R.id.btn_next, R.id.btn_zhuguan_add_img})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.btn_ksznlxAct_tijiao://提交
                uploadTiJiaoData();
                break;
            case R.id.btn_zhuguan_add_img://主观题添加图片
                //选择图片逻辑：只选取一张
                //先请判断请求权限，请求成功选取，选取成功后立即上传该图片，并展示，失败后提示重传
                requestPermissions();

                break;

            case R.id.btn_next://下一题
                KSZNLXActivity.vp_KsznlxAct.setCurrentItem(num + 1);
                break;
            default:
                break;
        }


    }

    /**
     * 提交答案
     */
    private void uploadTiJiaoData() {
        ProgressUtil.show(getActivity());
        Map paramsMap = new HashMap();

        String uid = Myapp.spUtil.getUid();
        String token = Myapp.spUtil.getToken();

        String practiceType = "1";
        if (xitiType.equals(MyConstant.ZHINENGZUJUAN)) {
            practiceType = "1";
        } else if (xitiType.equals(MyConstant.YUCESHITI)) {
            practiceType = "2";
        } else if (xitiType.equals(MyConstant.ZHUANXAINGLIANXI)) {
            practiceType = "3";
        } else if (xitiType.equals(MyConstant.LINIANZHENTI)) {
            practiceType = "4";
        } else if (xitiType.equals(MyConstant.MONISHITI)) {
            practiceType = "4";
        }

        StringBuilder upquestionBuilder = new StringBuilder("");
        StringBuilder upyesBuilder = new StringBuilder("");
        StringBuilder upnoBuilder = new StringBuilder("");

        for (int i = 0; i < adapterData.size(); i++) {

            if (i == adapterData.size() - 1) {
                upquestionBuilder.append(tianXieDaAnList.get(i).gettId());
                upyesBuilder.append(adapterData.get(i).getQsuccess());
            } else {
                upquestionBuilder.append(tianXieDaAnList.get(i).gettId() + ",");
                upyesBuilder.append(adapterData.get(i).getQsuccess() + ",");
            }
        }

        for (int i = 0; i < tianXieDaAnList.size(); i++) {
            if (i == tianXieDaAnList.size() - 1) {
                if (null != tianXieDaAnList.get(i).getAnswerList() && tianXieDaAnList.get(i).getAnswerList().size() > 0) {
                    upnoBuilder.append(getPos(tianXieDaAnList.get(i).getAnswerList().get(0)));
                }

            } else {
                if (null != tianXieDaAnList.get(i).getAnswerList() && tianXieDaAnList.get(i).getAnswerList().size() > 0) {
                    upnoBuilder.append(getPos(tianXieDaAnList.get(i).getAnswerList().get(0)) + ",");
                } else {
                    upnoBuilder.append(",");
                }

            }
        }
        String upquestion = upquestionBuilder.toString();
        String upyes = upyesBuilder.toString();
        String upno = upnoBuilder.toString();
        String uptimes = String.valueOf((SystemClock.elapsedRealtime() - cm.getBase()/1000));

        //主观题相关
        List<Map> list = new ArrayList<>();
        for (int i = 0; i < tianXieDaAnList.size(); i++) {
            TianXieDaAnBean tianXieDaAnBean = tianXieDaAnList.get(i);
            Map map = null;
            if (tianXieDaAnBean.getType().equals("1")) {//如果是主观题
                map = new HashMap();
                map.put("qid", tianXieDaAnBean.gettId());
                map.put("content", tianXieDaAnBean.getContent());
                map.put("img", tianXieDaAnBean.getImgPathList());
                list.add(map);
            }

        }

        String uplistStr = new Gson().toJson(list);

        paramsMap.put("uid", uid);
        paramsMap.put("token", token);
        paramsMap.put("practiceType", practiceType);//1 智能组卷 2预测试题 3专项智能练习 4模拟题 4历年真题
        paramsMap.put("upquestion", upquestion);//题目id(,)
        paramsMap.put("upyes", upyes);//正确答案(,)
        paramsMap.put("upno", upno);//错误答案(,)
        paramsMap.put("uptimes", uptimes);//所用时间
        paramsMap.put("uplist", uplistStr);//主观题id、答案和图片

        HttpManager.getInstance().post(MyConstant.UPLOADANSWER, paramsMap, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {
                ProgressUtil.hide();
                //提交成功，跳转解析
                startJieXiIntent();
            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {
                ProgressUtil.hide();
                toast(result.getDesc());
            }
        });
    }

    /**
     * 根据选项获取下标
     *
     * @param
     */
    private String getPos(int index) {
        String[] s = {"A", "B", "C", "D", "E", "F", "G"};
        int[] indexStrs = {0, 1, 2, 3, 4, 5, 6};

        for (int i = 0; i < indexStrs.length; i++) {

            if (index == indexStrs[i]) {
                return s[i];
            }
        }
        return "";
    }

    /**
     * 跳转解析界面
     */
    private void startJieXiIntent() {
        Intent intent = new Intent(getActivity(), LXBGActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("sum", tianXieDaAnList.size());
        bundle.putString("flag", "1");
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_CODE_JIEXI);
    }


    /**
     * 请求权限，成功选择图片
     */
    private void requestPermissions() {
        if (null == permissionRequest) {
            permissionRequest = new PermissionRequest(getActivity(), new PermissionRequest.PermissionCallback() {
                @Override
                public void onSuccessful() {
                    //请求成功，选择图片
                    pickPhoto();
                }

                @Override
                public void onFailure() {
                    //失败，如果是拒绝默认提示，如果点击不再提示，下次提示跳转设置
                }
            });
        }
        //请求照相和读写内存权限
        permissionRequest.requestGroup(Permission.CAMERA, Permission.STORAGE);

    }

    /**
     * 选择图片
     */
    private void pickPhoto() {

        if (null != mSelectedImgUris && mSelectedImgUris.size() < 3) {
            Matisse.from(this)
                    .choose(MimeType.allOf())
                    .theme(R.style.Matisse_AiTang)
                    .countable(true)
                    .capture(true)
                    .captureStrategy(
                            new CaptureStrategy(true, "com.example.administrator.aitang.fileprovider"))
                    .maxSelectable(1)
//                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                    .gridExpectedSize(
                            getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                    .thumbnailScale(0.85f)
                    .imageEngine(new GlideEngine())
                    .forResult(REQUEST_CODE_CHOOSE);
        } else {
            toast("最多添加三张照片");
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            if (null != Matisse.obtainResult(data) && Matisse.obtainResult(data).size() > 0) {
                Uri uploadImgUri = Matisse.obtainResult(data).get(0);
                if (null != uploadImgUri) {
                    mSelectedImgUris.add(uploadImgUri);
                    Log.d("Matisse", "mSelected: " + mSelectedImgUris);
                    //上传该张图片
                    img2Base64(uploadImgUri);
                }
            }
        } else if (requestCode == KSZNLXFragment.REQUEST_CODE_JIEXI && resultCode == KSZNLXFragment.RESULT_CODE_JIEXI) {
            Myapp.clearXiTiActivities();
            getActivity().finish();
        }
    }

    /**
     * 转换照片为base64
     * 将图片转为base64字符串上传,然后展示
     *
     * @param uploadImgUri
     */
    private void img2Base64(Uri uploadImgUri) {
        String imgBase64 = PhotoUtils.imageToBase64(PhotoUtils.getPathFromUri(getActivity(), uploadImgUri));
        uploadImg(imgBase64);
    }

    /**
     * 上传照片
     *
     * @param imgBase64
     */
    private void uploadImg(final String imgBase64) {
        if (!StringUtils.isEmpty(imgBase64)) {
            ProgressUtil.show(getActivity());
            Map paramsMap = new HashMap();
            paramsMap.put("file", imgBase64);
            paramsMap.put("type", "jpeg");
            HttpManager.getInstance().post(MyConstant.UPLOADIMAGE, paramsMap, new ServiceBackObjectListener() {
                @Override
                public void onSuccess(ServiceResult result, String response) {
                    ProgressUtil.hide();
                    Gson gson = new Gson();
                    UploadImgBean uploadImgBean = gson.fromJson(response, UploadImgBean.class);
                    String imgResultPath = uploadImgBean.getData();
                    //成功后展示该图片
                    showImg();
                    //保存当前照片的地址，用于上传答案用
                    if (!StringUtils.isEmpty(imgResultPath)) {
                        tianXieDaAnList.get(num).getImgPathList().add(imgResultPath);
                    }

                }

                @Override
                public void onFailure(ServiceResult result, Object obj) {
                    ProgressUtil.hide();
                    toast(result.getDesc());

                    //206 图片不合法 207 图片大于2M这两种情况无法重新上传
                    if (result.getCode() == "206" || result.getCode() == "207") {

                    } else {
                        //失败后提示重传
                        showFailureDialog(imgBase64);
                    }
                }
            });
        }

    }

    /**
     * 照片上传失败提示重新上传
     *
     * @param imgBase64
     */
    private void showFailureDialog(final String imgBase64) {
        ImageUploadFailureDialog failureDialog = new ImageUploadFailureDialog(getActivity(), new ImageUploadFailureDialog.OnConfirmBtnClickListener() {
            @Override
            public void click(View v) {
                uploadImg(imgBase64);
            }
        });
        failureDialog.setCancelable(false);
        failureDialog.show();
    }

    /**
     * 展示主观题的照片
     */
    private void showImg() {
        if (mSelectedImgUris.size() == 1) {
            GlideUtils.loadImg(mSelectedImgUris.get(0), ivZhuguan1);
        } else if (mSelectedImgUris.size() == 2) {
            GlideUtils.loadImg(mSelectedImgUris.get(1), ivZhuguan2);
        } else if (mSelectedImgUris.size() == 3) {
            GlideUtils.loadImg(mSelectedImgUris.get(2), ivZhuguan3);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        //设置答案
        String isSingle = tianXieDaAnList.get(num).getIsSingle();//是否为单选
        List<Integer> answerList = tianXieDaAnList.get(num).getAnswerList();

        //因为这里不考虑多选问题，全当单选处理
        answerList.clear();
        answerList.add(i);

        //设置当前选中的，改变颜色为橙色
        adapter.setCurrentChecked(i);
        //选择后自动跳转
        if (!isLast) {
            KSZNLXActivity.vp_KsznlxAct.setCurrentItem(num + 1);
        }
    }

    /**
     * 生成bitmap用来分享
     */
    public Bitmap creatShareBitmap() {
        return PhotoUtils.loadBitmapFromView(llShareContent);
    }


    /**
     * 重新计算listview高度
     *
     * @param listView
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        DaanAdapter listAdapter = (DaanAdapter) listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
}

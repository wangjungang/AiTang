package com.example.administrator.aitang.fragment.lianxi.shenlun;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.lianxi.ShenLunBean;
import com.example.administrator.aitang.bean.lianxi.UploadImgBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.fragment.MyBaseFragment;
import com.example.administrator.aitang.http.HttpManager;
import com.example.administrator.aitang.http.listener.ServiceBackObjectListener;
import com.example.administrator.aitang.http.result.ServiceResult;
import com.example.administrator.aitang.ui.lianxi.shenlun.ShenLunActivity;
import com.example.administrator.aitang.ui.lianxi.shenlun.ShenLunTijiaoDingDanActivity;
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
import static com.example.administrator.aitang.ui.lianxi.shenlun.ShenLunActivity.shenLunData;


/**
 * 快速智能练习
 */
public class ShenLunKSZNLXFragment extends MyBaseFragment {

    @BindView(R.id.sv_ksznlxAct)
    ScrollView sv;
    @BindView(R.id.tv_ksznlxAct_timucontent)
    HtmlTextView tvKsznlxActTimuContent;//题目内容
    @BindView(R.id.tv_question)
    TextView tvQuestion;//试题类型
    //    @BindView(R.id.mlsv_ksznlxAct_daan)
//    ListView mlsvKsznlxActDaan;//答案
    @BindView(R.id.btn_ksznlxAct_tijiao)
    Button btnKsznlxActTijiao;
    @BindView(R.id.ll_share_content)
    LinearLayout llShareContent;
    @BindView(R.id.et_answer)
    EditText etAnswer; //主观题答案的输入框
    @BindView(R.id.tv_text_num)
    TextView tvTextNum;//主观题的下一道题按钮
    @BindView(R.id.btn_next)
    Button btnNext;//主观题的下一道题按钮
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
    //    @BindView(R.id.tv_count)
//    TextView tvCount;
//    @BindView(R.id.tv_price)
//    TextView tvPrice;
    @BindView(R.id.ll_tijiao_content)
    LinearLayout llTijiaoContent;

    //    private long recordingTime = 0;// 记录下来的总时间
    int num;//当前题目的下标
    boolean isLast;//最后一个题目
    ShenLunBean.DataBean data;//习题数据源

    private PermissionRequest permissionRequest;//权限请求工具类
    public static final int REQUEST_CODE_CHOOSE = 23;//选择图片请求码
    public static final int REQUEST_CODE_JIEXI = 30;//跳转解析界面的请求码
    public static final int RESULT_CODE_JIEXI = 31;//解析界面返回的返回码
    private List<Uri> mSelectedImgUris = new ArrayList<>();//已选择的图片Uri

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_ksznlx_shenlun, container, false);
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
        if (activity instanceof ShenLunActivity) {
            ShenLunActivity shenLunActivity = (ShenLunActivity) activity;
            cm = (Chronometer) shenLunActivity.findViewById(R.id.cm_dati_time);
        }
    }

    @Override
    public void init() {
        if (isLast) {
            llTijiaoContent.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.GONE);
        } else {
            llTijiaoContent.setVisibility(View.GONE);
            btnNext.setVisibility(View.VISIBLE);
        }

        if (data != null) {

            //请求焦点防止界面抖动
            tvKsznlxActTimuContent.setFocusableInTouchMode(true);
            tvKsznlxActTimuContent.requestFocus();

            //展示题目
            List<List<String>> contentList = data.getMqintro();
            //格式化题目数据
            List<String> contentStrList = new ArrayList<>();
            for (int i = 0; i < contentList.size(); i++) {
                StringBuilder contentBuilder = new StringBuilder("");
                for (int j = 0; j < contentList.get(i).size(); j++) {

                    String contStr = contentList.get(i).get(j).replace("&nbsp;", "");
                    if (!StringUtils.isEmpty(contStr) && contStr.contains("http://")) {
                        String s = "<br/><img src='" + contStr + "'/><br/>";
                        contentBuilder.append(s);
                    } else {
                        contentBuilder.append(contStr);
                    }
                }
                contentStrList.add(contentBuilder.toString());
            }
            //如果标题不为空，添加标题
            String title = data.getMqtitle().replace("&nbsp;", "").replace("zxtc", "");
            if (!StringUtils.isEmpty(title)) {
//                contentStrList.add(title);//添加到题目里

                //添加为新的标题
                String content = "(主观题)  " + title;
                SpannableStringBuilder builder = new SpannableStringBuilder(content);
                builder.setSpan(new ForegroundColorSpan(Color.parseColor("#FF9B19")), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvQuestion.setText(builder);
            }

            tvKsznlxActTimuContent.setHtmlFromString(HtmlStrUtils.getInstance().getHtmlString(contentStrList), false);
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
                    shenLunData.get(num).setAnswerContent(etAnswer.getText().toString());
                }
            });
        }

    }


    @OnClick({R.id.btn_ksznlxAct_tijiao, R.id.btn_next, R.id.btn_zhuguan_add_img})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.btn_ksznlxAct_tijiao://提交
                startJieXiIntent();
                break;
            case R.id.btn_zhuguan_add_img://主观题添加图片
                //选择图片逻辑：只选取一张
                //先请判断请求权限，请求成功选取，选取成功后立即上传该图片，并展示，失败后提示重传
                requestPermissions();

                break;

            case R.id.btn_next://下一题
                ShenLunActivity.vp_KsznlxAct.setCurrentItem(num + 1);
                break;
            default:
                break;
        }


    }


    /**
     * 跳转解析界面
     */
    private void startJieXiIntent() {
        //TODO 下订单，付款，提交成功关闭页面，没有解析界面
        Intent intent = new Intent(getActivity(), ShenLunTijiaoDingDanActivity.class);
        startActivity(intent);
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
        } else if (requestCode == ShenLunKSZNLXFragment.REQUEST_CODE_JIEXI && resultCode == ShenLunKSZNLXFragment.RESULT_CODE_JIEXI) {
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
                        shenLunData.get(num).getImgPathList().add(imgResultPath);
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

    /**
     * 生成bitmap用来分享
     */
    public Bitmap creatShareBitmap() {
        return PhotoUtils.loadBitmapFromView(llShareContent);
    }
}

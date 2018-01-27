package com.example.administrator.aitang.ui.zhibo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bulong.rudeness.RudenessScreenHelper;
import com.example.administrator.aitang.R;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.lianxi.ShenLunDingDanResultBean;
import com.example.administrator.aitang.bean.wode.CashCouponBean;
import com.example.administrator.aitang.bean.zhibo.ClassBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.http.HttpManager;
import com.example.administrator.aitang.http.listener.ServiceBackObjectListener;
import com.example.administrator.aitang.http.result.ServiceResult;
import com.example.administrator.aitang.payutils.AliPayHelper;
import com.example.administrator.aitang.payutils.WXpayHelper;
import com.example.administrator.aitang.payutils.WXpayOrderBean;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.example.administrator.aitang.ui.mine.CoursenotesActivity;
import com.example.administrator.aitang.utils.basic.DateUtil;
import com.example.administrator.aitang.utils.basic.StringUtils;
import com.example.administrator.aitang.utils.glide.GlideUtils;
import com.example.administrator.aitang.utils.okhttp.OkHttpUtils;
import com.example.administrator.aitang.utils.okhttp.callback.StringCallback;
import com.example.administrator.aitang.views.PopuwindowView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class TijiaoDingdanActivity extends MyBaseActivity {

    ClassBean.DataBean bean;
    @BindView(R.id.rl_inflate_lsvitem_zhibofrg)
    RelativeLayout rl;
    @BindView(R.id.headerview)
    LinearLayout llHeader;
    @BindView(R.id.iv_header_left)
    ImageView ivHeaderLeft;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.ll_kechengquan_tijiaodingdanAct)
    LinearLayout ll_kechengquan;
    @BindView(R.id.tv_inflate_lsvitem_zhibofrg_banji)
    TextView tvInflateLsvitemZhibofrgBanji;
    @BindView(R.id.tv_inflate_lsvitem_zhibofrg_kechengzhaiyao)
    TextView tvInflateLsvitemZhibofrgKechengzhaiyao;
    @BindView(R.id.tv_inflate_lsvitem_zhibofrg_time)
    TextView tvInflateLsvitemZhibofrgTime;
    @BindView(R.id.img_inflate_lsvitem_zhibofrg_pic1)
    ImageView imgInflateLsvitemZhibofrgPic1;
    @BindView(R.id.tv_inflate_lsvitem_zhibofrg_name1)
    TextView tvInflateLsvitemZhibofrgName1;
    @BindView(R.id.img_inflate_lsvitem_zhibofrg_pic2)
    ImageView imgInflateLsvitemZhibofrgPic2;
    @BindView(R.id.tv_inflate_lsvitem_zhibofrg_name2)
    TextView tvInflateLsvitemZhibofrgName2;
    @BindView(R.id.img_inflate_lsvitem_zhibofrg_pic3)
    ImageView imgInflateLsvitemZhibofrgPic3;
    @BindView(R.id.tv_inflate_lsvitem_zhibofrg_name3)
    TextView tvInflateLsvitemZhibofrgName3;
    @BindView(R.id.tv_inflate_lsvitem_zhibofrg_price)
    TextView tvInflateLsvitemZhibofrgPrice;
    @BindView(R.id.tv_inflate_lsvitem_zhibofrg_pn)
    TextView tvInflateLsvitemZhibofrgPn;
    @BindView(R.id.tv_tijiaodingdanAct_kechengquan)
    TextView tvTijiaodingdanActKechengquan;
    @BindView(R.id.tv_tijiaodingdanAct_xianjindyq)
    TextView tvTijiaodingdanActXianjindyq;
    @BindView(R.id.cb_tijiaodingdanAct_kechengquan)
    CheckBox cbTijiaodingdanActKechengquan;
    @BindView(R.id.tv_tijiaodingdanAct_payMoney)
    TextView tvTijiaodingdanActPayMoney;
    @BindView(R.id.btn_tijiaodingdanAct_tijiaodingdan)
    Button btnTijiaodingdanActTijiaodingdan;
    @BindView(R.id.tv_tijiaodingdanAct_zhifufangshi)
    TextView tvTijiaodingdanActZhifufangshi;
    int kechengquanNum;//课程券数量
    private String orderID = "";//订单编号，用于通知后台成功

    private String classPrice = "";//课程价格
    private String kechengquanPrice = ""; //获取的课程券价格
    private String xjdiyongquanPrice = "";//获取的现金抵用券
    private String couponPriceStr = "";//要上传的现金券价格
    private String totalPrice;//总价格 总价格=课程价格-课程券钱-现金抵用券钱
    private String ucid = ""; //选择的优惠券的id

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_tijiao_dingdan);
    }

    @Override
    public void init() {
        setImmerBarcolor(llHeader);
        ivHeaderLeft.setImageResource(R.drawable.back_icon_nav);
        ivHeaderLeft.setVisibility(View.VISIBLE);
        setHeaderBackground(R.color.color_white);
        tvHeaderTitle.setText("提交订单");
        bean = getIntent().getParcelableExtra("data");
        rl.setBackgroundResource(R.color.color_08D2B2);
        OkHttpUtils.get().url(MyConstant.GETMINECOUPON).addParams("uid", Myapp.spUtil.getUid()).addParams("token", Myapp.spUtil.getToken())
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) throws IOException, JSONException {
                CashCouponBean cashCouponBean = new Gson().fromJson(response, CashCouponBean.class);
//                课程券
                List<CashCouponBean.DataBean.ClassBean> classBeanList = cashCouponBean.getData().getClassX();
                kechengquanNum = classBeanList.size();

                String kechenNumStr = String.valueOf(kechengquanNum);
                String content = kechenNumStr + "张可用";
                SpannableStringBuilder builder = new SpannableStringBuilder(content);
                int endIndex = kechenNumStr.length();
                builder.setSpan(new AbsoluteSizeSpan(40), endIndex, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.setSpan(new ForegroundColorSpan(Color.parseColor("#646464")), endIndex, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                tvTijiaodingdanActKechengquan.setText(builder);


                //代金券
                CashCouponBean.DataBean.AllBean allBean = cashCouponBean.getData().getAll();

                xjdiyongquanPrice = allBean.getPrice();
                if (!StringUtils.isEmpty(allBean.getPrice())) {
                    tvTijiaodingdanActXianjindyq.setText("￥" + xjdiyongquanPrice);
                    initCbListener();
                } else {
                    tvTijiaodingdanActXianjindyq.setText("￥0");
                }

            }
        });

        try {
            tvInflateLsvitemZhibofrgBanji.setText(bean.getC_name() + "");
            tvInflateLsvitemZhibofrgKechengzhaiyao.setText("课程摘要" + bean.getC_name() + "");
            String startTime = DateUtil.toString(Long.parseLong(bean.getC_start_time()), "yyyy-MM-dd");
            String endTime = DateUtil.toString(Long.parseLong(bean.getC_end_time()), "yyyy-MM-dd");
            tvInflateLsvitemZhibofrgTime.setText(startTime + "-" + endTime);
            List<ClassBean.DataBean.TeacherBean> teacher = bean.getTeacher();
            ImageView[] iv = {imgInflateLsvitemZhibofrgPic1, imgInflateLsvitemZhibofrgPic2, imgInflateLsvitemZhibofrgPic3};
            TextView[] tv = {tvInflateLsvitemZhibofrgName1, tvInflateLsvitemZhibofrgName2, tvInflateLsvitemZhibofrgName3};
            for (int k = 0; k < iv.length; k++) {
                iv[k].setVisibility(View.INVISIBLE);
                tv[k].setVisibility(View.INVISIBLE);
            }
            for (int j = 0; j < teacher.size(); j++) {
                ClassBean.DataBean.TeacherBean d = teacher.get(j);
                GlideUtils.setCircleAvatar(d.getTpic(), iv[j], R.drawable.touxiang_image_zhiboshouye, R.drawable.touxiang_image_zhiboshouye);
                iv[j].setVisibility(View.VISIBLE);
                tv[j].setText(d.getTname());
                tv[j].setVisibility(View.VISIBLE);
            }
            classPrice = bean.getC_price();
            tvInflateLsvitemZhibofrgPrice.setText("￥" + bean.getC_price());
            tvTijiaodingdanActPayMoney.setText("￥" + bean.getC_price());
            tvInflateLsvitemZhibofrgPn.setText(bean.getClassnum() + "");

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    /**
     * 初始化代金券的Listener
     */
    private void initCbListener() {

        cbTijiaodingdanActKechengquan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                calculatePrice();
            }
        });

    }

    /**
     * 计算使用券的情况
     */
    private void calculatePrice() {
        BigDecimal classPriceBD = new BigDecimal(classPrice);
        BigDecimal kechengquanPriceBD = null;
        if (!StringUtils.isEmpty(kechengquanPrice)) {
            kechengquanPriceBD = new BigDecimal(kechengquanPrice);
        }


        /****优先使用课程券****/
        if (null != kechengquanPriceBD) {
            //此方法，如果BigDecimal为小于val返回-1，如果BigDecimal为大于val返回1，如果BigDecimal为等于val返回0,
            int bj = kechengquanPriceBD.compareTo(classPriceBD);
            if (bj > 0) {
                //如果课程券大于课程价格，显示需支付为   需支付=0
                totalPrice = "0";
            } else {
                //如果课程券小于等于课程价格，显示需支付为 需支付=课程价格-课程券价格

                totalPrice = String.valueOf(classPriceBD.subtract(kechengquanPriceBD));
            }

        } else {
            totalPrice = classPrice;
        }


        /****然后考虑现金券*****/
        BigDecimal xjdiyongquanPriceBD = null;
        if (!StringUtils.isEmpty(xjdiyongquanPrice)) {
            xjdiyongquanPriceBD = new BigDecimal(xjdiyongquanPrice);
        }
        BigDecimal totalPriceBD = new BigDecimal(totalPrice);

        if (Double.valueOf(totalPrice) == 0) {
            //如果需支付为0 则不计算现金券
        } else {
            //选择了使用代金券
            if (cbTijiaodingdanActKechengquan.isChecked()) {

                int bj = totalPriceBD.compareTo(xjdiyongquanPriceBD);
                if (bj > 0) {
                    //如果需支付价格大于现金券，显示需支付
                    // 需支付=需支付-现金券
                    // 使用现金券价格=全部
                    couponPriceStr = xjdiyongquanPrice;
                    totalPrice = String.valueOf(totalPriceBD.subtract(xjdiyongquanPriceBD));

                } else {
                    //如果需支付价格小于等于现金券，显示需支付
                    // 使用现金券价格=需支付
                    // 需支付=0
                    couponPriceStr = totalPrice;
                    totalPrice = "0";
                }


            } else {
                //不使用使用代金券
                couponPriceStr = "0";
            }

        }

        //显示总价格
        tvTijiaodingdanActPayMoney.setText(totalPrice);
    }


    @OnClick({R.id.iv_header_left, R.id.tv_tijiaodingdanAct_zhifufangshi, R.id.btn_tijiaodingdanAct_tijiaodingdan, R.id.ll_kechengquan_tijiaodingdanAct})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                finish();
                break;
            case R.id.tv_tijiaodingdanAct_zhifufangshi:

                break;
            case R.id.btn_tijiaodingdanAct_tijiaodingdan:

                uploadTiJiaoData();
                break;
            case R.id.ll_kechengquan_tijiaodingdanAct://课程券
                startActivityForResult(new Intent(this, CoursenotesActivity.class), 100);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;

        if (requestCode == 100) {
            String price = data.getStringExtra("price");
            ucid = data.getStringExtra("ucid");
            kechengquanPrice = price;
            tvTijiaodingdanActKechengquan.setText("￥" + kechengquanPrice);

            calculatePrice();
        }
    }

    private void showPPW(final String payPrice, final String ordersn) {
        int[] a = {R.id.img_ppw_zhifufangshi_weixi, R.id.tv_ppw_zhifufangshi_weixi, R.id.img_ppw_zhifufangshi_zhifubao,
                R.id.tv_ppw_zhifufangshi_zhifubao};
        int width = (int) RudenessScreenHelper.pt2px(this, 600);
        final PopuwindowView ppw = new PopuwindowView(TijiaoDingdanActivity.this, R.layout.ppw_zhifufangshi, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.img_ppw_zhifufangshi_zhifubao:
                    case R.id.tv_ppw_zhifufangshi_zhifubao:
                        //支付宝
                        getAiliOrder(payPrice, ordersn);//payPric
                        break;
                    case R.id.img_ppw_zhifufangshi_weixi:
                    case R.id.tv_ppw_zhifufangshi_weixi:
                        //微信支付
                        getWXOrder(payPrice);//payPrice
                        break;
                }
            }
        },
                a,
                false, width,
                R.id.img_ppw_zhifufangshi_weixi, R.id.tv_ppw_zhifufangshi_weixi, R.id.img_ppw_zhifufangshi_zhifubao,
                R.id.tv_ppw_zhifufangshi_zhifubao, R.id.img_ppw_zhifufangshi_close
        );
        ppw.setBackgroundAlpha(0.5f);//设置屏幕的透明度
        ppw.setBackgroundDrawable(new BitmapDrawable());
        ppw.showAtLocation(findViewById(R.id.ll_tijiaodingdan), Gravity.BOTTOM, 0, 0);
        ppw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ppw.setBackgroundAlpha(1.0f);
            }
        });
    }


    /**
     * 提交订单，获取返回串，然后调用支付
     * <p>
     * 逻辑：先提交订单，会返回订单数据，用获得的钱调起支付，成功付款调用接口通知后台，失败或取消付款-微信需要调用接口取消订单
     */
    private void uploadTiJiaoData() {
        showProgressDialog();
        Map paramsMap = new HashMap();

        String uid = Myapp.spUtil.getUid();
        String token = Myapp.spUtil.getToken();

        paramsMap.put("uid", uid);
        paramsMap.put("token", token);
        paramsMap.put("type", "1");//类型：1是听课 2是申论
        if (!StringUtils.isEmpty(ucid)) {
            paramsMap.put("ucid", ucid);//用户优惠券id，只有课程券才有
        }
        paramsMap.put("c_id", bean.getC_id());//课程id,课程需要
        paramsMap.put("couponprice", couponPriceStr);//优惠券价格

//        if (cbTijiaodingdanActKechengquan.isChecked()) {//选择优惠券
//            BigDecimal couponPriceBD = new BigDecimal(xjdiyongquanPrice);
//            BigDecimal totalPriceBD = new BigDecimal(totalPrice);
//            int bj = couponPriceBD.compareTo(totalPriceBD);//此方法，如果BigDecimal为小于val返回-1，如果BigDecimal为大于val返回1，如果BigDecimal为等于val返回0,
//            String couponPriceStr = "";
//            if (bj > 0) { //如果优惠券总数大于订单总价，传订单价格，后台服务端会计算价格
//                couponPriceStr = String.valueOf(totalPrice);
//            } else { //如果优惠券总数小于订单总价，全选
//                couponPriceStr = String.valueOf(xjdiyongquanPrice);
//            }
//            paramsMap.put("couponprice", couponPriceStr);//优惠券价格
//        } else {
//            paramsMap.put("couponprice", "");//优惠券价格
//        }

        HttpManager.getInstance().post(MyConstant.ORDERINSERT, paramsMap, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {

                hideProgressDialog();
                //成功后获取订单串，拿到付款金额保存订单号（订单号用来通知后台支付成功），调用支付接口
                ShenLunDingDanResultBean resultBean = new Gson().fromJson(response, ShenLunDingDanResultBean.class);

                String payPrice = resultBean.getData().getOrdertotalprice();

                orderID = resultBean.getData().getOrderid();
                String ordersn = resultBean.getData().getOrdersn();

                //支付价格不为空，并且大于0
                if (!StringUtils.isEmpty(payPrice) && Double.valueOf(payPrice) > 0) {
                    showPPW(payPrice, ordersn);
                } else {
                    //成功调用接口通知后台付款成功
                    updateOrderStateSuccess();
                }
            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {
                hideProgressDialog();
                toast(result.getDesc());
            }
        });

    }

    private void getWXOrder(String payPrice) {

        Map paramsMap = new HashMap();
        String uid = Myapp.spUtil.getUid();
        paramsMap.put("uid", uid);
        paramsMap.put("title", "");
        paramsMap.put("price", payPrice);
        HttpManager.getInstance().post(MyConstant.WXPAY, paramsMap, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {

                WXpayOrderBean orderBean = new Gson().fromJson(response, WXpayOrderBean.class);

                WXpayOrderBean.DataBean dataBean = orderBean.getData();

                final String out_trade_no = dataBean.getOut_trade_no();//如果取消订单货未支付，重新下订单，需要关闭之前的订单用于关闭订单

                WXpayHelper.WXpayEntity wXpayEntity = new WXpayHelper.WXpayEntity();
                wXpayEntity.setAppId(dataBean.getAppid());
                wXpayEntity.setPartnerId(dataBean.getPartnerid());
                wXpayEntity.setPrepayId(dataBean.getPrepayid());
                wXpayEntity.setNonceStr(dataBean.getNoncestr());
                wXpayEntity.setTimeStamp(String.valueOf(dataBean.getTimestamp()));
                wXpayEntity.setSign(dataBean.getSign());
                WXpayHelper.getInstance().pay(wXpayEntity, new WXpayHelper.WXPayCallBack() {
                    @Override
                    public void onSuccess() {

                        //成功调用接口通知后台付款成功
                        updateOrderStateSuccess();

                    }

                    @Override
                    public void onFailure(String error) {

                        //微信取消或失败需要通知后台取消订单
                        colseWXorder(out_trade_no);
                    }
                });

            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {
                toast("获取订单失败");
            }
        });
    }

    /**
     * 关闭微信支付订单
     *
     * @param out_trade_no
     */
    private void colseWXorder(String out_trade_no) {

        Map paramsMap = new HashMap();
        paramsMap.put("out_trade_no", out_trade_no);
        HttpManager.getInstance().get(MyConstant.WXPAYORDERCLOSE, paramsMap, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {
            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {

            }
        });
    }


    private void getAiliOrder(String payPrice, String ordersn) {
        Map paramsMap = new HashMap();
        String uid = Myapp.spUtil.getUid();
        paramsMap.put("uid", uid);
        paramsMap.put("title", "爱唐教育");
        paramsMap.put("price", payPrice);
        paramsMap.put("ordersn", ordersn);
        HttpManager.getInstance().post(MyConstant.ALIPAY, paramsMap, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {
                try {
                    JSONObject object = new JSONObject(response);

                    if ("200".equals(object.getString("code"))) {
                        JSONObject data = object.getJSONObject("data");
                        String orderInfo = data.getString("str");
                        if (!StringUtils.isEmpty(orderInfo)) {
                            new AliPayHelper().pay(TijiaoDingdanActivity.this, orderInfo, new AliPayHelper.AliPayCallBack() {
                                @Override
                                public void onSuccess() {
                                    //成功调用接口通知后台付款成功
                                    updateOrderStateSuccess();
                                }

                                @Override
                                public void onFailure() {

                                }
                            });
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {
                toast("获取订单失败");
            }
        });
    }


    /**
     * 通知后支付成功
     */
    private void updateOrderStateSuccess() {

        Map paramsMap = new HashMap();
        String uid = Myapp.spUtil.getUid();
        String token = Myapp.spUtil.getToken();

        paramsMap.put("uid", uid);
        paramsMap.put("token", token);
        paramsMap.put("orderid", orderID);
        HttpManager.getInstance().post(MyConstant.SUCCESSORDER, paramsMap, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {
                hideProgressDialog();
                //成功关闭界面
                closeActivity();
            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {

                //有可能网络不好失败，重新调用该接口
                updateOrderStateSuccess();
            }
        });


    }


    /**
     * 关闭页面
     */
    private void closeActivity() {
        toast("支付成功");
        setResult(1);
        finish();
    }

}

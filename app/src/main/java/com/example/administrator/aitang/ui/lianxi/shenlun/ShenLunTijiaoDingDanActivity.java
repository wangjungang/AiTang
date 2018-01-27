package com.example.administrator.aitang.ui.lianxi.shenlun;

import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bulong.rudeness.RudenessScreenHelper;
import com.example.administrator.aitang.R;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.lianxi.ShenLunBean;
import com.example.administrator.aitang.bean.lianxi.ShenLunDingDanResultBean;
import com.example.administrator.aitang.bean.wode.CashCouponBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.http.HttpManager;
import com.example.administrator.aitang.http.listener.ServiceBackObjectListener;
import com.example.administrator.aitang.http.result.ServiceResult;
import com.example.administrator.aitang.payutils.AliPayHelper;
import com.example.administrator.aitang.payutils.WXpayHelper;
import com.example.administrator.aitang.payutils.WXpayOrderBean;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.example.administrator.aitang.utils.basic.DateUtil;
import com.example.administrator.aitang.utils.basic.ProgressUtil;
import com.example.administrator.aitang.utils.basic.StringUtils;
import com.example.administrator.aitang.views.PopuwindowView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.administrator.aitang.ui.lianxi.shenlun.ShenLunActivity.shenLunData;

/**
 * Author : wangzexin
 * Date : 2017/12/5
 * Describe : 申论提交订单界面
 */

public class ShenLunTijiaoDingDanActivity extends MyBaseActivity {
    @BindView(R.id.iv_header_left)
    ImageView ivHeaderLeft;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.iv_header_right)
    ImageView ivHeaderRight;
    @BindView(R.id.headerview)
    LinearLayout llHeaderView;
    @BindView(R.id.tv_datiAct_type)
    TextView tvDatiActType;
    @BindView(R.id.iv_clock)
    ImageView ivClock;
    @BindView(R.id.tv_dati_time)
    TextView tvDatiTime;
    @BindView(R.id.tv_dati_xinxi)
    TextView tvDatiXinxi;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.course_tip)
    TextView courseTip;
    @BindView(R.id.tv_xianjinquan)
    TextView tvXianjinquan;
    @BindView(R.id.cb_xianjinquan)
    CheckBox cbXianjinquan;
    @BindView(R.id.tv_zhifu_money)
    TextView tvZhifuMoney;
    @BindView(R.id.tv_zhifu_type)
    TextView tvZhifuType;
    @BindView(R.id.btn_tijiao_dingdan)
    Button btnTijiaoDingdan;

    private String couponPrice = "";//获取的代金券价格
    private int timuCount;//做的题数
    private String totalPrice = "";//总价
    private String couponPriceStr = "";//要上传的现金券价格
    private String orderID = "";//订单编号，用于通知后台成功

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_shenlun_tijiao_dingdan);
    }

    @Override
    public void init() {
        super.init();
        setImmerBarcolor(llHeaderView);
        setHeader();
        tvDatiTime.setText(DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm"));

        //请求代金券信息
        requestCoupon();


        //计算做题信息，题数和价格
        initZuoTiInfo();

    }

    private void setHeader() {
        setHeaderBackground(R.color.color_white);
        setHeaderImage(MyConstant.Position.LEFT, R.drawable.back_icon_nav, false, null);
        setHeaderTitle("提交订单", MyConstant.Position.CENTER, R.color.color_323232);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();//沉浸式状态栏销毁
    }

    /**
     * 计算做题信息，题数和价格
     */
    private void initZuoTiInfo() {

        timuCount = 0;
        BigDecimal totalFee = new BigDecimal("0");

        for (int i = 0; i < shenLunData.size(); i++) {
            ShenLunBean.DataBean dataBean = shenLunData.get(i);

            //如果答案为空，并且照片也为空，则认为这道题没有做，否则就是做了，计算收费
            if (StringUtils.isTrimEmpty(dataBean.getAnswerContent())
                    && (null == dataBean.getImgPathList() || dataBean.getImgPathList().size() == 0)) {

            } else {//否则就是做了，计算收费
                timuCount++;

                String price = dataBean.getPrice();

                //如果价格不为空，计算费用
                if (!StringUtils.isEmpty(price)) {
                    BigDecimal a1 = new BigDecimal(price);
                    totalFee = totalFee.add(a1);
                }
            }

        }

        totalPrice = String.valueOf(totalFee);
        tvZhifuMoney.setText("￥" + totalPrice);
        tvDatiXinxi.setText("共做" + timuCount + "道题目，共计：￥" + totalPrice);
    }

    /**
     * 请求代金券
     */
    private void requestCoupon() {
        Map paramsMap = new HashMap();

        String uid = Myapp.spUtil.getUid();
        String token = Myapp.spUtil.getToken();

        paramsMap.put("uid", uid);
        paramsMap.put("token", token);

        HttpManager.getInstance().post(MyConstant.GETMINECOUPON, paramsMap, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {
                ProgressUtil.hide();
                CashCouponBean cashCouponBean = new Gson().fromJson(response, CashCouponBean.class);

                //现金券和签到天数
                CashCouponBean.DataBean.AllBean allBean = cashCouponBean.getData().getAll();

                couponPrice = allBean.getPrice();
                if (!StringUtils.isEmpty(couponPrice)) {
                    tvXianjinquan.setText("￥" + couponPrice);
                    initCbListener();
                } else {
                    tvXianjinquan.setText("￥0");
                }
            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {
                toast(result.getDesc());
            }
        });

    }

    /**
     * 如果优惠券大于0，初始化优惠券的点击事件
     */
    private void initCbListener() {
        cbXianjinquan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    //选中，计算价格，展示需支付钱数

                    BigDecimal couponPriceBD = new BigDecimal(couponPrice);//
                    BigDecimal totalPriceBD = new BigDecimal(totalPrice);
                    int bj = couponPriceBD.compareTo(totalPriceBD);//此方法，如果BigDecimal为小于val返回-1，如果BigDecimal为大于val返回1，如果BigDecimal为等于val返回0,

                    String showPrice="0";
                    if (bj > 0) { //如果优惠券总数大于订单总价，传订单价格，后台服务端会计算价格
                        couponPriceStr = totalPrice;
                        showPrice="0";
                    } else { //如果优惠券总数小于订单总价，全选
                        couponPriceStr = couponPrice;
                        showPrice=String.valueOf(totalPriceBD.subtract(couponPriceBD));
                    }
                    tvZhifuMoney.setText("￥" + showPrice);
                } else {
                    //取消选中，计算价格，展示需支付钱数
                    couponPriceStr = "";
                    tvZhifuMoney.setText("￥" + totalPrice);
                }

            }
        });
    }


    @OnClick({R.id.iv_header_left, R.id.tv_zhifu_type, R.id.btn_tijiao_dingdan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                finish();
                break;
            case R.id.tv_zhifu_type:
                break;
            case R.id.btn_tijiao_dingdan:
                //提交订单
                uploadTiJiaoData();
                break;
            default:
                break;
        }
    }


    /**
     * 提交订单，获取返回串，然后调用支付
     * <p>
     * 逻辑：先提交答案，会返回订单数据，用获得的钱调起支付，成功付款调用接口通知后台，失败或取消付款-微信需要调用接口取消订单
     *
     */
    private void uploadTiJiaoData() {
        showProgressDialog();
        Map paramsMap = new HashMap();

        String uid = Myapp.spUtil.getUid();
        String token = Myapp.spUtil.getToken();

        StringBuilder mqidBuilder = new StringBuilder("");
        //主观题相关
        List<Map> list = new ArrayList<>();
        for (int i = 0; i < shenLunData.size(); i++) {
            ShenLunBean.DataBean dataBean = shenLunData.get(i);

            //如果答案为空，并且照片也为空，则认为这道题没有做，否则就是做了，计算收费
            if (StringUtils.isTrimEmpty(dataBean.getAnswerContent())
                    && (null == dataBean.getImgPathList() || dataBean.getImgPathList().size() == 0)) {

            } else {//否则就是做了
                if (i == shenLunData.size() - 1) {
                    mqidBuilder.append(shenLunData.get(i).getMqid());
                } else {
                    mqidBuilder.append(shenLunData.get(i).getMqid() + ",");
                }

                Map map = new HashMap();
                map.put("content", dataBean.getAnswerContent());
                map.put("img", dataBean.getImgPathList());
                list.add(map);
            }
        }

        String mqid = mqidBuilder.toString();
        String markintro = new Gson().toJson(list);

        paramsMap.put("uid", uid);
        paramsMap.put("token", token);
        paramsMap.put("type", "2");//类型：1是听课 2是申论
//        paramsMap.put("ucid", "");//用户优惠券id，好像只有课程券才有
//        paramsMap.put("c_id", "");//课程id,课程需要，申论这里不需要
        paramsMap.put("couponprice", couponPriceStr);//优惠券价格
//        if (cbXianjinquan.isChecked()) {//选择优惠券
//            BigDecimal couponPriceBD = new BigDecimal(couponPrice);
//            BigDecimal totalPriceBD = new BigDecimal(totalPrice);
//            int bj = couponPriceBD.compareTo(totalPriceBD);//此方法，如果BigDecimal为小于val返回-1，如果BigDecimal为大于val返回1，如果BigDecimal为等于val返回0,
//            String couponPriceStr = "";
//            if (bj > 0) { //如果优惠券总数大于订单总价，传订单价格，后台服务端会计算价格
//                couponPriceStr = totalPrice;
//            } else { //如果优惠券总数小于订单总价，全选
//                couponPriceStr = couponPrice;
//            }
//            paramsMap.put("couponprice", couponPriceStr);//优惠券价格
//        } else {
//            paramsMap.put("couponprice", "");//优惠券价格
//        }

        paramsMap.put("mqid", mqid);//申论专项题id(,)
        paramsMap.put("markintro", markintro);//申论答案和图片


        HttpManager.getInstance().post(MyConstant.ORDERINSERT, paramsMap, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {
                hideProgressDialog();

                //成功后获取订单串，拿到付款金额保存订单号（订单号用来通知后台支付成功），调用支付接口
                ShenLunDingDanResultBean resultBean = new Gson().fromJson(response, ShenLunDingDanResultBean.class);

                String payPrice = resultBean.getData().getOrdertotalprice();

                orderID = resultBean.getData().getOrderid();
                String ordersn= resultBean.getData().getOrdersn();

                //支付价格不为空，并且大于0
                if (!StringUtils.isEmpty(payPrice) && Double.valueOf(payPrice) > 0) {
                    showPPW(payPrice,ordersn);
                }else{
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

    /**
     * 显示支付方式界面
     * @param payPrice
     * @param ordersn
     */
    private void showPPW(final String payPrice, final String ordersn) {
        int[] a = {R.id.img_ppw_zhifufangshi_weixi, R.id.tv_ppw_zhifufangshi_weixi, R.id.img_ppw_zhifufangshi_zhifubao,
                R.id.tv_ppw_zhifufangshi_zhifubao};
        int width = (int) RudenessScreenHelper.pt2px(this, 600);
        final PopuwindowView ppw = new PopuwindowView(ShenLunTijiaoDingDanActivity.this, R.layout.ppw_zhifufangshi, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.img_ppw_zhifufangshi_weixi:
                    case R.id.tv_ppw_zhifufangshi_weixi:

                        getWXOrder(payPrice);//payPrice
                        break;
                    case R.id.img_ppw_zhifufangshi_zhifubao:
                    case R.id.tv_ppw_zhifufangshi_zhifubao:

                        getAiliOrder(payPrice,ordersn);//payPrice
                        break;
                    default:
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
     * 获取微信订单
     * @param payPrice
     */
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

    /**
     * 获取支付宝订单
     * @param payPrice
     * @param ordersn
     */
    private void getAiliOrder(String payPrice,String ordersn) {
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
                            new AliPayHelper().pay(ShenLunTijiaoDingDanActivity.this, orderInfo, new AliPayHelper.AliPayCallBack() {
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

        showProgressDialog();
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
        finish();
        Myapp.clearXiTiActivities();//关闭申论相关页面
    }
}

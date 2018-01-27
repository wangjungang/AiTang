package com.example.administrator.aitang.payutils;

import com.example.administrator.aitang.constant.MyConstant;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import static com.example.administrator.aitang.app.Myapp.context;

/**
 * Author : wangzexin
 * Date : 2017/12/10
 * Describe : 微信支付
 */

public class WXpayHelper {

    public interface WXPayCallBack {
        void onSuccess();

        void onFailure(String error);
    }

    private IWXAPI api;
    public static WXPayCallBack wxPayCallBack;

    private WXpayHelper() {
        api = WXAPIFactory.createWXAPI(context, null);
        // 将该app注册到微信
        api.registerApp(MyConstant.WXAPPID);//wxd930ea5d5a258f4f---不知道哪来的
    }

    public static WXpayHelper getInstance() {
        return SingetonHolder.instance;
    }

    private static class SingetonHolder {
        private static WXpayHelper instance = new WXpayHelper();
    }

    public void pay(WXpayEntity wxPayEntity, WXPayCallBack callBack) {
        wxPayCallBack = null;
        wxPayCallBack = callBack;
        PayReq request = new PayReq();
        request.appId = wxPayEntity.getAppId();
        request.partnerId = wxPayEntity.getPartnerId();
        request.prepayId = wxPayEntity.getPrepayId();
        request.packageValue = "Sign=WXPay";
        request.nonceStr = wxPayEntity.getNonceStr();
        request.timeStamp = wxPayEntity.getTimeStamp();
        request.sign = wxPayEntity.getSign();
        if (null == api) {
            api = WXAPIFactory.createWXAPI(context, null);
            // 将该app注册到微信
            api.registerApp(MyConstant.WXAPPID);//wxd930ea5d5a258f4f
        }
        api.sendReq(request);

    }

    public static class WXpayEntity {
        String appId = "";
        String partnerId = "";
        String prepayId = "";
        String packageValue = "Sign=WXPay";
        String nonceStr = "";
        String timeStamp = "";
        String sign = "";

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getPartnerId() {
            return partnerId;
        }

        public void setPartnerId(String partnerId) {
            this.partnerId = partnerId;
        }

        public String getPrepayId() {
            return prepayId;
        }

        public void setPrepayId(String prepayId) {
            this.prepayId = prepayId;
        }

        public String getPackageValue() {
            return packageValue;
        }

        public void setPackageValue(String packageValue) {
            this.packageValue = packageValue;
        }

        public String getNonceStr() {
            return nonceStr;
        }

        public void setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}

package com.example.administrator.aitang.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.andview.refreshview.XScrollView;
import com.bulong.rudeness.RudenessScreenHelper;
import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.BannerViewHolder;
import com.example.administrator.aitang.adapter.account.lianxi.QuestionAdapter;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.lianxi.ShouYeBean;
import com.example.administrator.aitang.bean.lianxi.SignBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.http.HttpManager;
import com.example.administrator.aitang.http.listener.ServiceBackObjectListener;
import com.example.administrator.aitang.http.result.ServiceResult;
import com.example.administrator.aitang.ui.account.ExameTypeActivity;
import com.example.administrator.aitang.ui.lianxi.CollectionTiMuActivity;
import com.example.administrator.aitang.ui.lianxi.HistoryRealQuestionACtivity;
import com.example.administrator.aitang.ui.lianxi.KSZNLXActivity;
import com.example.administrator.aitang.ui.lianxi.LianXiZhouBaoActivity;
import com.example.administrator.aitang.ui.lianxi.ShitiTypeActivity;
import com.example.administrator.aitang.ui.lianxi.ShujubaogaoActivity;
import com.example.administrator.aitang.ui.lianxi.UserMessageActivity;
import com.example.administrator.aitang.utils.basic.StringUtils;
import com.example.administrator.aitang.views.MyGridView;
import com.example.administrator.aitang.views.PopuwindowView;
import com.google.gson.Gson;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by 武培培 on 2017/10/24.
 */

public class LianxiFragment extends MyBaseFragment implements MZBannerView.BannerPageClickListener, View.OnClickListener {

    @BindView(R.id.tv_lianxi_title)
    TextView tvLianxiTitle;
    //考试类型
    @BindView(R.id.iv_exame_type)
    ImageView iv_exame_type;
    //签到
    @BindView(R.id.tv_qiandao)
    TextView tv_qiandao;
    //短信
    @BindView(R.id.iv_message)
    ImageView iv_message;
    //更多
    @BindView(R.id.tv_more)
    TextView tv_more;
    @BindView(R.id.banner)
    MZBannerView banner;

    //数据报告
    @BindView(R.id.ll_data_report)
    LinearLayout ll_data_report;
    //智能组件
    @BindView(R.id.ll_wise)
    LinearLayout ll_wise;
    //预测试题
    @BindView(R.id.ll_prepare_test)
    LinearLayout ll_repare_test;
    //模拟试题
    @BindView(R.id.ll_model_test)
    LinearLayout ll_model_test;
    @BindView(R.id.gridview)
    MyGridView gridView;
    @BindView(R.id.ll_header)
    LinearLayout ll_header;
    @BindView(R.id.sv)
    XScrollView sv;
    View view;
    List<ShouYeBean.DataBean.QuetionsBean> questionlist = new ArrayList<>();
    QuestionAdapter questionAdapter;
    PopupWindow poupwindow;
    private ShouYeBean shouYeBean;

    //静态标志在页面onresume时是否需要刷新页面，在练习页面点击左上角切换考试类型后，以及设置界面切换考试类型后都需要刷新
    //在ExameTypeAddressActivity界面跳转前修改为true,刷新后修改为false
    public static boolean isRefreshIndex = false;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lianxi, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        banner.start();
        //在练习页面点击左上角切换考试类型后，以及设置界面切换考试类型后都需要刷新
        if(isRefreshIndex){
            //刷新后设置为不需要刷新
            isRefreshIndex=false;
            addData();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        banner.pause();
    }

    @Override
    public void onPageClick(View view, int i) {

    }

    private void setClickListener() {
        tv_more.setOnClickListener(this);
        tv_qiandao.setOnClickListener(this);
        iv_message.setOnClickListener(this);
        iv_exame_type.setOnClickListener(this);
        ll_data_report.setOnClickListener(this);
        ll_model_test.setOnClickListener(this);
        ll_repare_test.setOnClickListener(this);
        ll_wise.setOnClickListener(this);
    }

    @Override
    public void init() {
        super.init();
        setImmerBarcolor(ll_header);
        setBanner_lunbo();
        aboutRefreshView();
        setClickListener();
        xRefreshView.startRefresh();
        questionAdapter = new QuestionAdapter(getActivity(), questionlist);
        gridView.setAdapter(questionAdapter);
        banner.setFocusableInTouchMode(true);
        banner.requestFocus();
        //点击grideview进入考试类型界面
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShouYeBean.DataBean.QuetionsBean questions = questionlist.get(i);
                Intent intent = new Intent(getActivity(), ShitiTypeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("questions", questions);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

    }


    @Override
    public void addData() {
        super.addData();
        Map paramsMap = new HashMap();
        paramsMap.put("uid", Myapp.spUtil.getUid());
        paramsMap.put("token", Myapp.spUtil.getToken());
        HttpManager.getInstance().getWithTag(MyConstant.SHOUYEMESSAGE, paramsMap, getActivity(), new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {

                shouYeBean = new Gson().fromJson(response, ShouYeBean.class);
                ShouYeBean.DataBean dataBean = shouYeBean.getData();

                //------------------------轮播------------------------
                final List<ShouYeBean.DataBean.SlideBean> slide = dataBean.getSlide();

                if (slide.size() != 0) {
                    addData_lunbo(slide);
                }

                questionlist.clear();
                questionlist.addAll(dataBean.getQuetions());

//                questionAdapter.addAll(questionlist, true);
                questionAdapter.notifyDataSetChanged();


                if (shouYeBean.getData().getIsdeport() == 0) {//0未签到
                    //TODO 设置是否已签到
                    isSign(false);
                } else if (shouYeBean.getData().getIsdeport() == 1) {//1已签到
                    isSign(true);
                }
                //设置标题
                tvLianxiTitle.setText(shouYeBean.getData().getUser().getUtest_type());

                xRefreshView.stopRefresh();
                xRefreshView.stopLoadMore();
                xRefreshView.setLoadComplete(true);
            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {
                Toast.makeText(getActivity(), result.getDesc(), Toast.LENGTH_SHORT).show();
                xRefreshView.stopRefresh();
                xRefreshView.stopLoadMore();
                xRefreshView.setLoadComplete(true);
            }
        });

    }

    /**
     * 是否已签到
     *
     * @param is true 签到 false未签到
     */
    private void isSign(Boolean is) {
        if (is) {
            tv_qiandao.setText("已签到");
            tv_qiandao.setClickable(false);
            tv_qiandao.setTextColor(getResources().getColor(R.color.color_646464));
        } else {
            tv_qiandao.setText("签到");
            tv_qiandao.setClickable(true);
            tv_qiandao.setTextColor(getResources().getColor(R.color.color_FF9B19));
        }
    }

    //设置轮播banner
    private void setBanner_lunbo() {
        banner.setBannerPageClickListener(this);
        banner.setDelayedTime(7000);//切换时间间隔
        banner.setDuration(2000);//切换速度
        banner.setIndicatorVisible(true);//设置是否显示indicator
//        banner.setIndicatorRes(R.drawable.sy_ewm, R.drawable.sy_hlsp);//设置indicator资源
//        banner.setBannerPageClickListener(this);//设置page点击事件
    }

    private void addData_lunbo(List<ShouYeBean.DataBean.SlideBean> datas) {
        banner.setPages(datas, new MZHolderCreator<BannerViewHolder>() {//设置页面数据
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
        banner.start();
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        Bundle bundle = null;
        switch (view.getId()) {
            case R.id.iv_exame_type:
                //考试类型
                intent = new Intent(getActivity(), ExameTypeActivity.class);
                bundle = new Bundle();
                bundle.putString("flag", "2");//跳转标志
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.tv_qiandao: //签到

                //判断是否已签到过
                if (null != shouYeBean || shouYeBean.getData().getIsdeport() == 0) {
                    requestSign();
                }

                break;
            case R.id.iv_message:
                //消息
                jumpTo(UserMessageActivity.class, false);
                break;
            case R.id.tv_more:
                getPopuwindow();
                break;
            case R.id.ll_data_report:
                //数据报告
                jumpTo(ShujubaogaoActivity.class, false);
                break;
            case R.id.ll_wise:
                //智能组卷
                intent = new Intent(getActivity(), KSZNLXActivity.class);
                bundle = new Bundle();
                bundle.putString(MyConstant.XITITYPEFLAG, MyConstant.ZHINENGZUJUAN);
                intent.putExtras(bundle);
                jumpTo(intent, false);
                break;
            case R.id.ll_model_test:
                //模拟试题
                intent = new Intent(getActivity(), KSZNLXActivity.class);
                bundle = new Bundle();
                bundle.putString(MyConstant.XITITYPEFLAG, MyConstant.MONISHITI);
                intent.putExtras(bundle);
                jumpTo(intent, false);
                break;
            case R.id.ll_prepare_test:
                //预测试题
                intent = new Intent(getActivity(), KSZNLXActivity.class);
                bundle = new Bundle();
                bundle.putString(MyConstant.XITITYPEFLAG, MyConstant.YUCESHITI);
                intent.putExtras(bundle);
                jumpTo(intent, false);
                break;
            default:
                break;
        }
    }

    /**
     * 请求签到
     */
    private void requestSign() {
        Map paramsMap = new HashMap();

        String uid = Myapp.spUtil.getUid();
        String token = Myapp.spUtil.getToken();
        paramsMap.put("uid", uid);
        paramsMap.put("token", token);
        paramsMap.put("type", "1");

        HttpManager.getInstance().post(MyConstant.SIGN, paramsMap, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {
                Gson gson = new Gson();
                SignBean signBean = gson.fromJson(response, SignBean.class);
                showSignPop(signBean);
                isSign(true);
            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {
                toast(result.getDesc());
            }
        });
    }

    /**
     * 签到
     */
    private void showSignPop(SignBean signBean) {
        int[] a = {R.id.tv_coupon_number, R.id.tv_sign_days};
        int width = (int) RudenessScreenHelper.pt2px(getActivity(), 600);
        final PopuwindowView ppw = new PopuwindowView(getActivity(), R.layout.popwindow_sign, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    default:
                        break;
                }
            }
        },
                a,
                false, width, R.id.img_ppw_zhifufangshi_close);
        ppw.setBackgroundAlpha(0.5f);//设置屏幕的透明度
        ppw.setBackgroundDrawable(new BitmapDrawable());
        ppw.showAtLocation(getActivity().findViewById(R.id.ll_tijiaodingdan), Gravity.BOTTOM, 0, 0);
        ppw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ppw.setBackgroundAlpha(1.0f);
            }
        });

        TextView tvSignDays = ppw.getView().findViewById(R.id.tv_sign_days);
        if (null != signBean && !StringUtils.isEmpty(signBean.getData().getDay())) {
            String content = "第" + signBean.getData().getDay() + "天";
            SpannableStringBuilder builder = new SpannableStringBuilder(content);
            int endIndex = signBean.getData().getDay().length();
            builder.setSpan(new AbsoluteSizeSpan(90), 1, endIndex + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new ForegroundColorSpan(Color.parseColor("#FF9B19")), 1, endIndex + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvSignDays.setText(builder);
        } else {
            tvSignDays.setText("第 天");
        }

    }

    /**
     * 更多
     */
    public void getPopuwindow() {
        //更多
        View contentView = View.inflate(getActivity(), R.layout.poupwindow_layout, null);
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
        poupwindow.showAsDropDown(tv_more, 0, 0);

        TextView tv_history = contentView.findViewById(R.id.tv_history_test);
        TextView tv_shousangtimu = contentView.findViewById(R.id.tv_souchangtimu);
        TextView tv_lianxizhoubao = contentView.findViewById(R.id.tv_lianxizhoubao);
        tv_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //历年真题
                Intent intent = new Intent(getActivity(), HistoryRealQuestionACtivity.class);
                jumpTo(intent, false);
                cancelPopupWindow();
            }
        });
        tv_shousangtimu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //收藏题目
                jumpTo(CollectionTiMuActivity.class, false);
                cancelPopupWindow();
            }
        });

        tv_lianxizhoubao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //练习周报
                jumpTo(LianXiZhouBaoActivity.class, false);
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

}

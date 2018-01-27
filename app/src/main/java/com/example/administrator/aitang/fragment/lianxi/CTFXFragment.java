package com.example.administrator.aitang.fragment.lianxi;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.lianxi.DaanAdapter;
import com.example.administrator.aitang.bean.lianxi.PracticeBean;
import com.example.administrator.aitang.fragment.MyBaseFragment;
import com.example.administrator.aitang.utils.HtmlStrUtils;
import com.example.administrator.aitang.utils.basic.StringUtils;
import com.example.administrator.aitang.utils.photopicker.PhotoUtils;
import com.example.administrator.aitang.views.MyListView;
import com.example.administrator.aitang.views.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.example.administrator.aitang.ui.lianxi.KSZNLXActivity.tianXieDaAnList;
import static com.example.administrator.aitang.ui.lianxi.KSZNLXActivity.zhengQueDaAnList;

public class CTFXFragment extends MyBaseFragment {

    @BindView(R.id.sv_cuotifenxiAct)
    ScrollView sv;
    @BindView(R.id.tv_cuotifenxiAct_timucontent)
    HtmlTextView tv_timucontent;
    @BindView(R.id.mlsv_cuotifenxiAct_daan)
    MyListView mlsv_daan;
    @BindView(R.id.tv_zhuguan_daan)
    TextView tvZhuguanDaan;
    @BindView(R.id.tv_cuotifenxiAct_kaodian)
    TextView tv_kaodian;
    @BindView(R.id.tv_cuotifenxiAct_zhengquelv)
    TextView tv_zhengquelv;
    @BindView(R.id.tv_cuotifenxiAct_yicuoxiang)
    TextView tv_yicuoxiang;
    @BindView(R.id.tv_cuotifenxiAct_jiexi)
    HtmlTextView tv_jiexi;
    @BindView(R.id.tv_cuotifenxiAct_laiyuan)
    TextView tv_laiyuan;
    @BindView(R.id.ll_jiexixuanxaing_content)
    LinearLayout llJiexixuanxaingContent;//解析中的选项分析，如果是主观题，隐藏掉
    @BindView(R.id.ll_share_content)
    LinearLayout llShareContent;//要分享的内容
    View v;
    int num;//当前题目在解析题的下标

    private PracticeBean data;
    private int index;//当前题目在总题数的下标
    private DaanAdapter adapter;


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_ctfx, container, false);
        Bundle bundle = getArguments();//从activity传过来的Bundle
        if (bundle != null) {
            data = bundle.getParcelable("data");
            index = bundle.getInt("index");
        }
        return v;
    }

    @Override
    public void init() {
        //请求焦点防止界面抖动
        tv_timucontent.setFocusableInTouchMode(true);
        tv_timucontent.requestFocus();
        if (data != null) {
            //题目
            List<String> contentList = data.getQcontent();
            if (null != contentList) {
                tv_timucontent.setHtmlFromString((index + 1) + "." + HtmlStrUtils.getInstance().getHtmlString(contentList), false);
            }

            //如果是选择题展示正确率和易错项
            String type = data.getQtype();//试题类型：1选择题 2材料题 3主观题 其中12展示listview、3展示输入框
            if (type.equals("1") || type.equals("2")) {//展示
                mlsv_daan.setVisibility(View.VISIBLE);
                tvZhuguanDaan.setVisibility(View.GONE);
                llJiexixuanxaingContent.setVisibility(View.VISIBLE);

                //选择题答案
                showDaAnList();
                if (!StringUtils.isEmpty(data.getAccuracy())) {
                    tv_zhengquelv.setText(data.getAccuracy() + "%");//全站正确率
                }
                if (!StringUtils.isEmpty(data.getQerror())) {
                    tv_yicuoxiang.setText(data.getQerror());//易错选项
                }
            } else if (type.equals("3")) {//隐藏
                mlsv_daan.setVisibility(View.GONE);
                tvZhuguanDaan.setVisibility(View.VISIBLE);
                llJiexixuanxaingContent.setVisibility(View.GONE);

                String content = tianXieDaAnList.get(index).getContent();
                if (!StringUtils.isEmpty(content)) {
                    tvZhuguanDaan.setText(content);
                } else {
                    tvZhuguanDaan.setText("");
                }

            }

            //考点
            tv_kaodian.setText(data.getKaodian());

            //展示解析文字
            List<String> desList = data.getQdes();
            tv_jiexi.setHtmlFromString(HtmlStrUtils.getInstance().getHtmlString(desList), false);

            //来源
            if (!StringUtils.isEmpty(data.getCome())) {
                tv_laiyuan.setText(data.getCome());
            }
        }
    }

    /**
     * 显示选择题的答案
     */
    private void showDaAnList() {
        //答案
        List<String> datas = new ArrayList<>();
        adapter = new DaanAdapter(getActivity(), datas) {
            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                View v = super.getView(i, view, viewGroup);
                ViewHolder holder = (ViewHolder) v.getTag();

                //判断答案对错，设置颜色
                List<Integer> answerList = tianXieDaAnList.get(index).getAnswerList();
                List<Integer> sucessList = zhengQueDaAnList.get(index).getAnswerList();

                if (null != answerList
                        && answerList.size() > 0
                        && null != sucessList
                        && sucessList.size() > 0
                        && sucessList.contains(answerList)
                        && answerList.get(0) == i) {//如果有正确答案，并且为当前item设置为绿色

                    holder.tvInflateLsvitemDaanContent.setTextColor(getResources().getColor(R.color.color_08D2B2));

                } else {

                    //设置默认颜色
                    holder.tvInflateLsvitemDaanContent.setTextColor(getResources().getColor(R.color.color_444444));

                    if (null != answerList
                            && answerList.size() > 0 &&
                            answerList.get(0) == i) {//设置用户选择答案为红色
                        holder.tvInflateLsvitemDaanContent.setTextColor(getResources().getColor(R.color.color_E51919));
                    }
                    if (null != sucessList
                            && sucessList.size() > 0
                            && sucessList.get(0) == i) {//设置正确答案为绿色
                        holder.tvInflateLsvitemDaanContent.setTextColor(getResources().getColor(R.color.color_08D2B2));
                    }

                }
                return v;
            }
        };
        mlsv_daan.setAdapter(adapter);
        List<String> daan = data.getQanswer();
        adapter.addAll(daan, false);
        sv.smoothScrollTo(0, 0);
        sv.scrollTo(0, 0);
    }


    /**
     * 生成bitmap用来分享
     */
    public Bitmap creatShareBitmap() {
        return PhotoUtils.loadBitmapFromView(llShareContent);
    }
}

package com.example.administrator.aitang.ui.lianxi;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bulong.rudeness.RudenessScreenHelper;
import com.example.administrator.aitang.R;
import com.example.administrator.aitang.app.Myapp;
import com.example.administrator.aitang.bean.lianxi.ShujubaogaoBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.http.HttpManager;
import com.example.administrator.aitang.http.listener.ServiceBackObjectListener;
import com.example.administrator.aitang.http.result.ServiceResult;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.example.administrator.aitang.utils.basic.DateUtil;
import com.example.administrator.aitang.utils.basic.StringUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ShujubaogaoActivity extends MyBaseActivity {

    public LineChart lineChart;
    public ArrayList<String> x_linechart = new ArrayList<String>();
    public ArrayList<Entry> y_linechart = new ArrayList<Entry>();
    public ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
    public LineData lineData = null;
    @BindView(R.id.headerview)
    LinearLayout llHeader;
    @BindView(R.id.iv_header_left)
    ImageView ivHeaderLeft;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    //---------------------------------预测分数-----------------------------------
    @BindView(R.id.tv_lianxizhoubaoAct_fenshu)
    TextView tv_Score;//我的分数
    @BindView(R.id.tv_shujubaogaoAct_zuigaofen)
    TextView tv_TotalPoints;//全站最高分
    @BindView(R.id.tv_shujubaogaoAct_wodepaimingfen)
    TextView tv_MyRanking;//我的排名
    @BindView(R.id.tv_lianxizhoubaoAct_shijian)
    TextView tv_PracticeTimes;//练习时间
    //---------------------------------答题量和正确率-----------------------------------
    @BindView(R.id.tv_shujubaogaoAct_datiliang)
    TextView tv_TopicQuantity;//答题量
    @BindView(R.id.tv_shujubaogaoAct_zhengquelv)
    TextView tv_Correct;//正确率
    @BindView(R.id.tv_shujubaogaoAct_wodepaiming)
    TextView tv_MyQuantity;//我的答题量
    @BindView(R.id.tv_shujubaogaoAct_zuigaodatiliang)
    TextView tv_MaxTopicQuantity;//全站最高答题量
    //---------------------------------预测分趋势-----------------------------------
    @BindView(R.id.tv_shujubaogaoAct_lianxitianshu)
    TextView tv_PracticeNum;//练习天数
    @BindView(R.id.tv_shujubaogaoAct_datishichang)
    TextView tv_AnswerTime;//答题时长
    @BindView(R.id.tv_shujubaogaoAct_lianxicishu)
    TextView tv_CreatePracticeTimes;//创建练习次数
    //---------------------------------生成时间-----------------------------------
    @BindView(R.id.tv_shujubaogaoAct_createTime)
    TextView tv_CreateTimes;//生成时间
    private List<ShujubaogaoBean.DataBean.UserListBean> userList;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_shujubaogao);
    }

    @Override
    public void init() {
        setImmerBarcolor(llHeader);
        ivHeaderLeft.setImageResource(R.drawable.back_icon_nav);
        ivHeaderLeft.setVisibility(View.VISIBLE);
        setHeaderBackground(R.color.color_white);
        tvHeaderTitle.setText("数据报告");
        requestData();
    }

    /**
     * 请求数据
     */
    private void requestData() {

        Map paramsMap = new HashMap();
        String uid = Myapp.spUtil.getUid();
        paramsMap.put("uid", uid);

        HttpManager.getInstance().get(MyConstant.DATEREPORT_REPORT, paramsMap, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {
                ShujubaogaoBean shujubaogaoBean = new Gson().fromJson(response, ShujubaogaoBean.class);

                initData(shujubaogaoBean);

            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {
                if ("201".equals(result.getCode())) {
                    //生成时间，取当前时间
                    tv_CreateTimes.setText("生成时间：" + DateUtil.date2Str(new Date(), "yyyy.MM.dd HH:mm"));
                    userList = new ArrayList<>();
                    boforeInit();
                }
            }
        });
    }

    /**
     * 初始化数据
     *
     * @param shujubaogaoBean
     */
    private void initData(ShujubaogaoBean shujubaogaoBean) {
        ShujubaogaoBean.DataBean dataBean = shujubaogaoBean.getData();
        String score = dataBean.getScore();
        if (!StringUtils.isEmpty(score)) {
            tv_Score.setText(String.valueOf(Math.round(Double.valueOf(score))));//预测分数

            //平均分数四舍五入取整
            tv_Correct.setText(Math.round(Double.valueOf(score)) + "%");//正确率
        }else{
            tv_Score.setText("0");
            tv_Correct.setText("0");
        }

        //练习时间
        tv_PracticeTimes.setText("练习时间：" + dataBean.getStart() + "-" + dataBean.getEnd());

        String maxScore = dataBean.getMaxranking();
        if (!StringUtils.isEmpty(maxScore)) {
            tv_TotalPoints.setText(maxScore);//全站最高分
        }else{
            tv_TotalPoints.setText("0");
        }

        String myScoreRank = dataBean.getScoreranking();
        String users = dataBean.getUsers();//总人数
        if (!StringUtils.isEmpty(myScoreRank)) {
            SpannableStringBuilder builder = new SpannableStringBuilder(myScoreRank + "/" + users);
            builder.setSpan(new AbsoluteSizeSpan((int) RudenessScreenHelper.pt2px(ShujubaogaoActivity.this, 45)), 0, myScoreRank.length() + 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);//字体
            builder.setSpan(new ForegroundColorSpan(Color.parseColor("#FF9B19")), 0, myScoreRank.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_MyRanking.setText(builder);//我的分数排名
        }else{
            tv_MyRanking.setText("0");
        }

        String datiNum = dataBean.getUserpnum();
        if (!StringUtils.isEmpty(datiNum)) {
            tv_TopicQuantity.setText(datiNum);//答题量
        }else{
            tv_TopicQuantity.setText("0");
        }

        String bestDatiNum = dataBean.getBest();
        if (!StringUtils.isEmpty(bestDatiNum)) {
            tv_MaxTopicQuantity.setText(bestDatiNum);//全站最高答题量
        }else{
            tv_MaxTopicQuantity.setText("0");
        }
        String myQuestionRank = dataBean.getQuestionranking();
        if (!StringUtils.isEmpty(myQuestionRank)) {
            SpannableStringBuilder builder = new SpannableStringBuilder(myQuestionRank + "/" + users);
            builder.setSpan(new AbsoluteSizeSpan((int) RudenessScreenHelper.pt2px(ShujubaogaoActivity.this, 45)), 0, myQuestionRank.length() + 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);//字体
            builder.setSpan(new ForegroundColorSpan(Color.parseColor("#FF9B19")), 0, myQuestionRank.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_MyQuantity.setText(builder);//我的答题量排名
        }else{
            tv_MyQuantity.setText("0");
        }


        String practiceDays = dataBean.getPracticedays();
        if (!StringUtils.isEmpty(practiceDays)&& !practiceDays.equals("zxtc")) {
            tv_PracticeNum.setText(practiceDays);//练习天数
        }else{
            tv_PracticeNum.setText("0");
        }


        String answerTime = dataBean.getMinute();
        if (!StringUtils.isEmpty(answerTime) && !answerTime.equals("zxtc")) {
            tv_AnswerTime.setText(answerTime);//答题时常
        }else{
            tv_AnswerTime.setText("0");
        }

        String practiceNum = dataBean.getPracticenum();
        if (!StringUtils.isEmpty(practiceNum)&& !practiceNum.equals("zxtc")) {
            tv_CreatePracticeTimes.setText(practiceNum);//答题时常
        }else{
            tv_CreatePracticeTimes.setText("0");
        }


        //生成时间，取当前时间
        tv_CreateTimes.setText("生成时间：" + DateUtil.date2Str(new Date(), "yyyy.MM.dd HH:mm"));


        userList = dataBean.getUserList();
        boforeInit();
    }


    private void boforeInit() {

        //----------------折线图--------------------
        lineChart = (LineChart) findViewById(R.id.linechart_shujubaogaoAct_zhexian);
        settingChat();
        setLineData();
        showChart();
    }

    /**
     * 设置预测分趋势折线图
     */
    private void settingChat() {
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴坐标显示的位置
        xAxis.setEnabled(true);//设置x轴启用或者禁用
        xAxis.setDrawAxisLine(true); // 上面第一行代码设置了false,所以下面第一行即使设置为true也不会绘制AxisLine
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(true);
        // 设置X轴文字的颜色
        xAxis.setTextSize(RudenessScreenHelper.pt2px(this, 26));
        xAxis.setTextColor(getResources().getColor(R.color.color_646464));
        xAxis.setLabelsToSkip(0);
//        xAxis.setAxisLineWidth(26f);// 设置轴行的间距

        // 设置右边的Y轴
        YAxis yAxis = lineChart.getAxisRight();//y轴右边的线
        yAxis.setDrawLabels(false);//不显示右边的标示
//        yAxis.setDrawGridLines(false);//不显示y轴上的横线
        yAxis.setDrawAxisLine(false);//不显示最外边的线

        YAxis leftAxis = lineChart.getAxisLeft();//y轴左边的线
        leftAxis.setTextSize(RudenessScreenHelper.pt2px(this, 12));
        leftAxis.setGridColor(getResources().getColor(R.color.color_BDBDBD));//y轴上横线的颜色
        leftAxis.setTextColor(getResources().getColor(R.color.color_323232));
        leftAxis.setDrawAxisLine(false);//最外边的线
        leftAxis.setStartAtZero(true);//是否从0开始
    }

    /**
     * gv
     * 初始化数据
     */
    public LineData setLineData() {
        if (null != userList) {

            for (int i = 0; i < userList.size(); i++) {//y轴的数据
                float result = Float.valueOf(userList.get(i).getUpscore());
                y_linechart.add(new Entry(result, i));
                x_linechart.add("");
            }
        }
        LineDataSet lineDataSet = new LineDataSet(y_linechart, "折线图");//y轴数据集合
        lineDataSet.setLineWidth(RudenessScreenHelper.pt2px(this, 2));//线宽
        lineDataSet.setColor(getResources().getColor(R.color.color_FF9B19));//折线的颜色
        lineDataSet.setDrawCubic(false);//设置true就会显示圆滑的曲线，否则是折线
        lineDataSet.setDrawCircles(true);
        lineDataSet.setCircleSize(RudenessScreenHelper.pt2px(this, 4));//现实圆形大小
        lineDataSet.setCircleColor(getResources().getColor(R.color.color_FF9B19));
        lineDataSet.setDrawValues(false);
        lineDataSets.add(lineDataSet);
        lineData = new LineData(x_linechart, lineDataSets);


        return lineData;
    }

    /**
     * 绘制预测分趋势折线图
     */
    public void showChart() {
        lineChart.setDrawBorders(false);//是否添加边框
        lineChart.setDescription("");//数据描述
        lineChart.setTouchEnabled(false);
//        lineChart.setNoDataTextDescription("我需要数据");//没数据显示
        lineChart.setDrawGridBackground(false);//是否启用网格背景
        lineChart.setBackgroundColor(Color.WHITE);//背景颜色
        lineChart.setData(lineData);//设置数据
        Legend legend = lineChart.getLegend();//设置比例图片标示，就是那一组Y的value
        legend.setEnabled(false);//是否绘制legend下面的方块
        legend.setForm(Legend.LegendForm.SQUARE);//样式
        legend.setFormSize(RudenessScreenHelper.pt2px(this, 26));//字体
        legend.setTextColor(Color.WHITE);//设置颜色
        lineChart.animateX(2000);//X轴的动画
        lineChart.setTouchEnabled(false);//设置不可点击
        lineChart.fitScreen();
    }

    @OnClick(R.id.iv_header_left)
    public void onViewClicked() {
        finish();
    }
}

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
import com.example.administrator.aitang.adapter.lianxi.DatishuAdapter;
import com.example.administrator.aitang.adapter.lianxi.ZhengquelvAdapter;
import com.example.administrator.aitang.bean.lianxi.DatishuBean;
import com.example.administrator.aitang.bean.lianxi.LianXiZhouBaoBean;
import com.example.administrator.aitang.bean.lianxi.ZhengquelvBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.http.HttpManager;
import com.example.administrator.aitang.http.listener.ServiceBackObjectListener;
import com.example.administrator.aitang.http.result.ServiceResult;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.example.administrator.aitang.utils.basic.StringUtils;
import com.example.administrator.aitang.views.MyListView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LianXiZhouBaoActivity extends MyBaseActivity {


    @BindView(R.id.iv_header_left)
    ImageView iv_left;
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.headerview)
    LinearLayout ll_header;
    @BindView(R.id.tv_lianxizhoubaoAct_fenshu)
    TextView tv_fenshu;
    @BindView(R.id.tv_lianxizhoubaoAct_shijian)
    TextView tv_shijian;
    @BindView(R.id.iv_header_right)
    ImageView ivHeaderRight;
    @BindView(R.id.tv_lianxizhoubaoAct_bishangzhou)
    TextView tv_bishangzhou;
    @BindView(R.id.tv_lianxizhoubaoAct_bishangzhoufenshu)
    TextView tv_bishangzhoufenshu;
    @BindView(R.id.tv_rankUp)
    TextView tvRankUp;
    @BindView(R.id.tv_guoKao_rank)
    TextView tvGuoKaoRank;//国考排名
    @BindView(R.id.tv_user_minute_timushu)
    TextView tvUserMinuteTimushu;
    @BindView(R.id.tv_quanzhan_pingjuntishu)
    TextView tvQuanzhanPingjuntishu;
    @BindView(R.id.tv_last_week_my_tishu)
    TextView tvLastWeekMyTishu;
    @BindView(R.id.tv_last_week_all_tishu)
    TextView tvLastWeekAllTishu;

    List<LianXiZhouBaoBean.DataBean.QuestionlistBean> questionlist = new ArrayList<>();//返回的问题List

    //------------------预测分趋势折线图---------------------
    public LineChart lineChart;//预测分趋势折线图
    public ArrayList<String> x_linechart = new ArrayList<String>();
    public ArrayList<Entry> y_linechart = new ArrayList<Entry>();
    public ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
    public LineData lineData = null;
    //------------------正确率---------------------
//    public RadarChart radarChart;
//    public ArrayList<String> x_radarchart = new ArrayList<String>();
//    public ArrayList<Entry> y_radarchart = new ArrayList<Entry>();
//    public ArrayList<RadarDataSet> radarDataSets = new ArrayList<RadarDataSet>();
//    public RadarData radarData = null;
    MyListView mlsv_zhengquelue;//正确率的lsv
    ZhengquelvAdapter zhengquelvAdapter;
    //------------------每分钟完成题目数---------------------
    @BindView(R.id.mlsv_datishu)
    MyListView mlsv_datishu;//每分钟完成题目数的lsv
    DatishuAdapter adapter_dts;
    //------------------柱形图---------------------
    BarChart barchart;
    public ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
    public BarDataSet bardataset;
    public ArrayList<String> labels_bar = new ArrayList<String>();


    private LianXiZhouBaoBean.DataBean dataBean;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_lian_xi_zhou_bao);
    }

    @Override
    public void init() {
        initTitleBar();
        //请求数据
        requestData();
    }

    /**
     * 初始化title
     */
    private void initTitleBar() {
        setImmerBarcolor(ll_header);
        iv_left.setVisibility(View.VISIBLE);
        ll_header.setBackground(getResources().getDrawable(R.color.color_white));
        iv_left.setImageResource(R.drawable.back_icon_nav);
        tv_title.setText("练习周报");

        tv_fenshu.setFocusable(true);
        tv_fenshu.setFocusableInTouchMode(true);
    }


    /**
     * 请求数据
     */
    private void requestData() {

        HttpManager.getInstance().get(MyConstant.WEEKDEPORT, null, new ServiceBackObjectListener() {
            @Override
            public void onSuccess(ServiceResult result, String response) {

//                String s = "{\"code\":\"200\",\"data\":{\"yuce\":0,\"stilltime\":\"2017\\/12\\/04-2017\\/12\\/10\",\"userminuteplay\":0,\"up\":0,\"weeklist\":[55,100,70,10,66,77,88],\"alluser\":\"9\",\"rankingup\":9,\"ranking\":null,\"allminuteplay\":0,\"userpractic\":null,\"allpractice\":0,\"questionlist\":[{\"all\": 70,\"num\": 14, \"name\": \"言语理解与表达\",\"baifenbi\": 100,\"zhenquelv\": 20 }]}}";

                LianXiZhouBaoBean lianXiZhouBaoBean = new Gson().fromJson(response, LianXiZhouBaoBean.class);
                dataBean = lianXiZhouBaoBean.getData();
                if (null != dataBean) {
                    initData();
                }

            }

            @Override
            public void onFailure(ServiceResult result, Object obj) {

                if ("201".equals(result.getCode())) {
                    //加载预测趋势折线图
                    initYuCeQushiLine();
                    //加载联系情况图
                    initLianXiQingKuang();
                }
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {

        int score = dataBean.getYuce();
        tv_fenshu.setText(String.valueOf(score));//分数

        String stillTime = dataBean.getStilltime();
        if (!StringUtils.isEmpty(stillTime)) {
            tv_shijian.setText("练习时间：" + stillTime);//练习时间
        } else {
            tv_shijian.setText("练习时间：");
        }

        tv_bishangzhoufenshu.setText(String.valueOf(dataBean.getUp()));//上周提高
        tvRankUp.setText(String.valueOf(dataBean.getRankingup()));//本周排名变化

        String allUserNum = dataBean.getAlluser();//总人数
        String myRank = dataBean.getRanking();
        if (!StringUtils.isEmpty(myRank) && !StringUtils.isEmpty(allUserNum)) {
            SpannableStringBuilder builder = new SpannableStringBuilder(myRank + "/" + allUserNum);
            builder.setSpan(new AbsoluteSizeSpan((int) RudenessScreenHelper.pt2px(this, 45)), 0, myRank.length() + 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);//字体
            builder.setSpan(new ForegroundColorSpan(Color.parseColor("#FF9B19")), 0, myRank.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvGuoKaoRank.setText(builder);//国考排名
        } else if (!StringUtils.isEmpty(allUserNum)) {
            tvGuoKaoRank.setText("/" + allUserNum);//国考排名
        }

        tvUserMinuteTimushu.setText(String.valueOf(StringUtils.double2StrRound(dataBean.getUserminuteplay())));//用户每分钟完成题数
        tvQuanzhanPingjuntishu.setText("全站平均" + StringUtils.double2StrRound(dataBean.getAllminuteplay()) + "题");//全站每分钟平均练习题数


        if (!StringUtils.isEmpty(dataBean.getUserpractic())) {
            tvLastWeekMyTishu.setText(dataBean.getUserpractic());//用户上周练习题数
        } else {
            tvLastWeekMyTishu.setText("0");//用户上周练习题数
        }

        tvLastWeekAllTishu.setText(StringUtils.double2StrRound(dataBean.getAllpractice()));//全站上周平均练习题数

        if (dataBean.getQuestionlist() != null) {
            questionlist.clear();
            questionlist.addAll(dataBean.getQuestionlist());
        }


        //加载预测趋势折线图
        initYuCeQushiLine();

        //加载正确率列表
        initZhengQueLvList();

        //加载答题数List
        initDaTiShuList();

        //加载联系情况图
        initLianXiQingKuang();
    }

    private void initLianXiQingKuang() {

        barchart = (BarChart) findViewById(R.id.barchart_lianxizhoubaoAct_zhuxing);
        initLianXiQingKuangData();
        showLianXiQingKuang();
    }


    /**
     * 加载预测趋势折线图
     */
    private void initYuCeQushiLine() {
        lineChart = (LineChart) findViewById(R.id.linechart_lianxizhoubaoAct_zhexian);
        List<Integer> yuCeQushiData = new ArrayList<>();
        if (dataBean != null && dataBean.getWeeklist() != null) {
            yuCeQushiData.clear();
            yuCeQushiData.addAll(dataBean.getWeeklist());
        }
        settingYuCeTuChat();
        setYuCeTuLineData(yuCeQushiData);
        showYuCeTuChart();
    }

    /**
     * 设置预测趋势折线图
     */
    private void settingYuCeTuChat() {
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴坐标显示的位置
        xAxis.setEnabled(true);//设置x轴启用或者禁用
        xAxis.setDrawAxisLine(true); // 上面第一行代码设置了false,所以下面第一行即使设置为true也不会绘制AxisLine
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(true);
        // 设置X轴文字的颜色
        xAxis.setTextSize(RudenessScreenHelper.pt2px(this, 8));
        xAxis.setTextColor(getResources().getColor(R.color.color_646464));
        xAxis.setAvoidFirstLastClipping(true); //设置x轴起点和终点label不超出屏幕
//        xAxis.setLabelsToSkip(0);
//        xAxis.setAxisLineWidth(26f);// 设置轴行的间距

        // 设置右边的Y轴
        YAxis yAxis = lineChart.getAxisRight();//y轴右边的线
        yAxis.setDrawLabels(false);//不显示右边的标示
//        yAxis.setDrawGridLines(false);//不显示y轴上的横线
        yAxis.setDrawAxisLine(false);//不显示最外边的线
        yAxis.setAxisMinValue(100);//设置该轴的自定义最小值。 如果设置了，这个值将不会是根据提供的数据计算出来的。
        YAxis leftAxis = lineChart.getAxisLeft();//y轴左边的线
        leftAxis.setTextSize(RudenessScreenHelper.pt2px(this, 10));
        leftAxis.setGridColor(getResources().getColor(R.color.color_BDBDBD));//y轴上横线的颜色
        leftAxis.setTextColor(getResources().getColor(R.color.color_646464));
        leftAxis.setDrawAxisLine(false);//最外边的线
        leftAxis.setStartAtZero(true);//是否从0开始
    }

    /**
     * 预测趋势折线图数据
     */
    public LineData setYuCeTuLineData(List<Integer> yuCeQushiData) {
        x_linechart.add("周一");
        x_linechart.add("周二");
        x_linechart.add("周三");
        x_linechart.add("周四");
        x_linechart.add("周五");
        x_linechart.add("周六");
        x_linechart.add("周天");
        for (int i = 0; i < yuCeQushiData.size(); i++) {//y轴的数据
            y_linechart.add(new Entry(yuCeQushiData.get(i), i));
        }
        LineDataSet lineDataSet = new LineDataSet(y_linechart, "折线图");//y轴数据集合
        lineDataSet.setLineWidth(RudenessScreenHelper.pt2px(this, 2));//线宽
        lineDataSet.setColor(getResources().getColor(R.color.color_08D2B2));//折线的颜色
//        lineDataSet.setDrawCubic(false);//设置true就会显示圆滑的曲线，否则是折线
        lineDataSet.setDrawCircles(true);
        lineDataSet.setCircleSize(RudenessScreenHelper.pt2px(this, 4));//现实圆形大小
        lineDataSet.setCircleColor(getResources().getColor(R.color.color_08D2B2));
        lineDataSet.setDrawValues(false);
        lineDataSets.add(lineDataSet);
        lineData = new LineData(x_linechart, lineDataSets);
        return lineData;
    }

    /**
     * 设置样式
     */
    public void showYuCeTuChart() {
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


    /**
     * 加载正确率list
     */
    private void initZhengQueLvList() {

        //----------------雷达图--------------------
//        radarChart = (RadarChart) findViewById(R.id.radarchart_lianxizhoubaoAct_leida);
//        RadarData resultLineData_leida = getRadarData(6, 100);
//        showChartForRadar();

        mlsv_zhengquelue = findViewById(R.id.mlsv_lianxizhoubaoAct_zhengquelue);
        List<ZhengquelvBean> datas_zql = new ArrayList<>();
        if (null != questionlist) {
            ZhengquelvBean zhengquelvBean;
            for (int i = 0; i < questionlist.size(); i++) {
                String name = questionlist.get(i).getName();
                double zhengquelv = questionlist.get(i).getZhenquelv();
                zhengquelvBean = new ZhengquelvBean(name, zhengquelv);
                datas_zql.add(zhengquelvBean);
            }
        }
        zhengquelvAdapter = new ZhengquelvAdapter(this, datas_zql);
        mlsv_zhengquelue.setAdapter(zhengquelvAdapter);
    }

    /**
     * 加载答题数List
     */
    private void initDaTiShuList() {
        List<DatishuBean> datas_dts = new ArrayList<>();
        if (null != questionlist) {
            DatishuBean datishuBean;
            for (int i = 0; i < questionlist.size(); i++) {
                String name = questionlist.get(i).getName();
                double num = questionlist.get(i).getNum();
                datishuBean = new DatishuBean(name, num);
                datas_dts.add(datishuBean);
            }
        }
        adapter_dts = new DatishuAdapter(this, datas_dts);
        mlsv_datishu.setAdapter(adapter_dts);
    }


    public void initLianXiQingKuangData() {

        if (null != questionlist) {
            BarEntry barEntry;
            for (int i = 0; i < questionlist.size(); i++) {
                String name = questionlist.get(i).getName();
                float num = (float) questionlist.get(i).getBaifenbi();

                if (questionlist.size() > 3 && !StringUtils.isEmpty(name) && name.length() > 4) {
                    name = name.substring(0, 4) + "...";
                }

                barEntry = new BarEntry(num, i);
                entries.add(barEntry);
                labels_bar.add(name);
            }

        }
    }

    public void showLianXiQingKuang() {
        barchart.setDrawGridBackground(false);//是否显示表格颜色
        barchart.setBackgroundColor(Color.WHITE);//背景颜色
        barchart.setTouchEnabled(false);
        XAxis xAxis = barchart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴坐标显示的位置
        xAxis.setAvoidFirstLastClipping(true); //设置x轴起点和终点label不超出屏幕
        xAxis.setEnabled(true);//设置x轴启用或者禁用
        xAxis.setDrawAxisLine(true); // 上面第一行代码设置了false,所以下面第一行即使设置为true也不会绘制AxisLine
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(true);
        // 设置X轴文字的颜色
        xAxis.setTextSize(RudenessScreenHelper.pt2px(this, 8));
        xAxis.setTextColor(getResources().getColor(R.color.color_646464));
        xAxis.setLabelsToSkip(0);
        xAxis.setSpaceBetweenLabels(50);
//        xAxis.setLabelRotationAngle(-60);
//        xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());
//        xAxis.setTypeface(Typeface.ITALIC);

//        xAxis.setAxisLineWidth(5f);// 设置轴行的间距 26f


        // 设置右边的Y轴
        YAxis yAxis = barchart.getAxisRight();//y轴右边的线
        yAxis.setDrawLabels(false);//不显示右边的标示
//        yAxis.setDrawGridLines(false);//不显示y轴上的横线
        yAxis.setDrawAxisLine(false);//不显示最外边的线

        YAxis leftAxis = barchart.getAxisLeft();//y轴左边的线
        leftAxis.setGridColor(getResources().getColor(R.color.color_BDBDBD));//y轴上横线的颜色
        leftAxis.setTextColor(getResources().getColor(R.color.color_646464));
        leftAxis.setTextSize(RudenessScreenHelper.pt2px(this, 8));
        leftAxis.setDrawAxisLine(false);//最外边的线
        leftAxis.setStartAtZero(true);//是否从0开始
        bardataset = new BarDataSet(entries, "");
        int[] colors = {getResources().getColor(R.color.color_FF9B19)};
        bardataset.setColors(colors);//ColorTemplate.VORDIPLOM_COLORS//设置柱子颜色
        bardataset.setBarSpacePercent(50f);
        BarData data = new BarData(labels_bar, bardataset);
        data.setDrawValues(false);//设置这个不会显示y轴上的数据
        LimitLine line = new LimitLine(10f);
        barchart.setData(data);
//      chart.animateXY(5000,5000);
//      chart.animateX(5000);
        barchart.animateY(2000);
        barchart.setDescription("");
        barchart.getLegend().setEnabled(false);//设置了这个下面的图例不显示

    }

//    /**
//     * gv
//     * 初始化数据
//     * count 表示坐标点个数，range表示等下y值生成的范围
//     */
//    public RadarData getRadarData(int count, float range) {
//        x_radarchart.add("数量关系");
//        x_radarchart.add("言语理解与表达");
//        x_radarchart.add("常识判断");
//        x_radarchart.add("申论");
//        x_radarchart.add("资料分析");
//        x_radarchart.add("判断推理");
//        for (int i = 0; i < count; i++) {//y轴的数据
//            double n = (Math.random() * range) + 3;
//            int m = (int) n;
//            float result = (float) n;
//            y_radarchart.add(new Entry(result, i));
//            datas_zql.add(new ZhengquelvBean(x_radarchart.get(i), m));
//        }
//        adapter.addAll(datas_zql, false);
//        RadarDataSet radarDataSet = new RadarDataSet(y_radarchart, "雷达图");//y轴数据集合
//        radarDataSet.setLineWidth(1f);//线宽
//        radarDataSet.setColor(getResources().getColor(color_FF9B19));//线的颜色
//        radarDataSet.setDrawFilled(true);//设置包括的范围区域填充颜色
//        radarDataSet.setDrawValues(false);
//        radarDataSets.add(radarDataSet);
//        radarData = new RadarData(x_radarchart, radarDataSets);
//        return radarData;
//    }
//
//    /**
//     * 设置样式
//     */
//    public void showChartForRadar() {
//        radarChart.getXAxis().setTextSize(RudenessScreenHelper.pt2px(this, 10));
//        radarChart.getYAxis().setEnabled(false);
//        radarChart.setTouchEnabled(false);
////        radarChart.setDrawBorders(false);//是否添加边框
//        radarChart.setDescription("");//数据描述
////        radarChart.setDrawGridBackground(true);//是否显示表格颜色
//        radarChart.setBackgroundColor(Color.WHITE);//背景颜色
//        radarChart.setData(radarData);//设置数据
//        Legend legend = radarChart.getLegend();//设置比例图片标示，就是那一组Y的value
//        legend.setEnabled(false);
//        radarChart.setEnabled(false);//设置不可点击
//        radarChart.animateY(2000);//X轴的动画
//
//    }


    @OnClick(R.id.iv_header_left)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                finish();
                break;
        }
    }

}

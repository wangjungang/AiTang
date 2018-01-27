package com.example.administrator.aitang.ui.lianxi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.aitang.R;
import com.example.administrator.aitang.adapter.lianxi.DatikaAdapter;
import com.example.administrator.aitang.bean.lianxi.PracticeBean;
import com.example.administrator.aitang.bean.lianxi.TianXieDaAnBean;
import com.example.administrator.aitang.bean.lianxi.ZhengQueDaAnBean;
import com.example.administrator.aitang.constant.MyConstant;
import com.example.administrator.aitang.fragment.lianxi.KSZNLXFragment;
import com.example.administrator.aitang.ui.MyBaseActivity;
import com.example.administrator.aitang.utils.basic.DateUtil;
import com.example.administrator.aitang.utils.basic.StringUtils;
import com.example.administrator.aitang.views.MyGridView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.administrator.aitang.ui.lianxi.KSZNLXActivity.adapterData;

public class LXBGActivity extends MyBaseActivity {

    int sum;//题目的总数量
    @BindView(R.id.iv_header_left)
    ImageView iv_left;
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.headerview)
    LinearLayout llHeader;
    @BindView(R.id.tv_datiAct_type)
    TextView tv_type;
    @BindView(R.id.tv_datiAct_time)
    TextView tv_time;
    @BindView(R.id.gv_datiAct)
    MyGridView gv;
    private List<Integer> datas;
    private DatikaAdapter adapter;
    private SparseBooleanArray array;
    private String flag = "";//跳转过来的标志

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_lxbg);
    }

    @Override
    public void init() {
        setImmerBarcolor(llHeader);
        setHeaderImage(MyConstant.Position.LEFT, R.drawable.back_icon_nav, false, null);
        setHeaderTitle("练习报告");
        tv_time.setText(DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm"));
        tv_type.setFocusableInTouchMode(true);
        tv_type.setFocusable(true);
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            flag = bundle.getString("flag");
            sum = bundle.getInt("sum", 0);
        }


        String xitiType = KSZNLXActivity.xitiType;
        if (StringUtils.isEmpty(xitiType)) {
            xitiType = "";
        }
        if (xitiType.equals(MyConstant.ZHINENGZUJUAN)) {
            tv_type.setText("练习类型：智能组卷");
        } else if (xitiType.equals(MyConstant.YUCESHITI)) {
            tv_type.setText("练习类型：预测试题");
        } else if (xitiType.equals(MyConstant.ZHUANXAINGLIANXI)) {
            tv_type.setText("练习类型：专项智能练习");
        } else if (xitiType.equals(MyConstant.LINIANZHENTI)) {
            tv_type.setText("练习类型：历年真题");
        } else if (xitiType.equals(MyConstant.MONISHITI)) {
            tv_type.setText("练习类型：模拟试题");
        }

        datas = new ArrayList<>();
        adapter = new DatikaAdapter(this, datas) {
            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                View v = super.getView(i, view, viewGroup);
                DatikaAdapter.ViewHolder holder = (ViewHolder) v.getTag();

                TianXieDaAnBean tianXieDaAnBean = KSZNLXActivity.tianXieDaAnList.get(i);
                ZhengQueDaAnBean zhengQueDaAnBean = KSZNLXActivity.zhengQueDaAnList.get(i);
                String type = tianXieDaAnBean.getType();

                holder.tv_tiNum.setTextColor(getResources().getColor(R.color.color_323232));
                holder.tv_tiNum.setBackgroundResource(R.drawable.weizuo_image_datika);
                //如果是选择题，判断对错
                if (!StringUtils.isEmpty(type) && type.equals("0")) {
                    List<Integer> answer = tianXieDaAnBean.getAnswerList();
                    List<Integer> success = zhengQueDaAnBean.getAnswerList();

                    if (answer.size() == 0) {//没做的
                        holder.tv_tiNum.setBackgroundResource(R.drawable.weizuo_image_datika);
                    } else {

                        if (answer.containsAll(success)) {//答案相同
                            holder.tv_tiNum.setBackgroundResource(R.drawable.zhengque_image_lianxibaogao);
                            holder.tv_tiNum.setTextColor(getResources().getColor(R.color.color_white));
                        } else {
                            holder.tv_tiNum.setBackgroundResource(R.drawable.cuowu_image_lianxibaogao);
                            holder.tv_tiNum.setTextColor(getResources().getColor(R.color.color_white));
                        }
                    }
                } else if (!StringUtils.isEmpty(type) && type.equals("1")) { //如果是主观题，如果做了为灰色，否则为透明

                    if (StringUtils.isEmpty(tianXieDaAnBean.getContent())) {
                        holder.tv_tiNum.setBackgroundResource(R.drawable.weizuo_image_datika);
                    } else {
                        holder.tv_tiNum.setBackgroundResource(R.drawable.yizuo_image_datika);
                    }
                }


                //如果是选中要解析的，设置颜色为橙色
                if (gv.isItemChecked(i)) {
                    holder.tv_tiNum.setBackgroundResource(R.drawable.xuanzhong_image_lianxibaogao);
                    holder.tv_tiNum.setTextColor(getResources().getColor(R.color.color_white));
                }

                return v;
            }
        };
        gv.setAdapter(adapter);
        gv.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                adapter.notifyDataSetChanged();

            }
        });
        gv.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                Log.d("TAG", "onItemCheckedStateChanged");
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                Log.d("TAG", "onCreateActionMode");
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                Log.d("TAG", "onPrepareActionMode");
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                Log.d("TAG", "onActionItemClicked");
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                Log.d("TAG", "onDestroyActionMode");

            }
        });

        for (int i = 0; i < sum; i++) {
            adapter.add((i + 1));
        }

    }


    @OnClick({R.id.iv_header_left, R.id.btn_datiAct_jiexixz, R.id.btn_datiAct_jiexiqb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                closeIntent();
                break;
            case R.id.btn_datiAct_jiexixz://解析选中题目
                ArrayList<PracticeBean> jiexiList = new ArrayList<>();
                ArrayList<Integer> jiexiListIndex = new ArrayList<>();
                array = gv.getCheckedItemPositions();
                for (int i = 0; i < sum; i++) {
                    if (array.get(i)) {
                        jiexiList.add(adapterData.get(i));
                        jiexiListIndex.add(i);
                    }
                }

                if (jiexiList.size() > 0) {
                    Intent intent = new Intent(this, CuoTiFenXiActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("datas", jiexiList);//要解析的习题
                    bundle.putIntegerArrayList("jiexiindex", jiexiListIndex);//要解析的习题的下标
                    intent.putExtras(bundle);
                    jumpTo(intent, false);
                } else {
                    toast("请选择要解析的习题");
                }

                break;
            case R.id.btn_datiAct_jiexiqb://解析全部
                ArrayList<PracticeBean> jiexiAllList = new ArrayList<>();
                jiexiAllList.clear();
                jiexiAllList.addAll(adapterData);

                ArrayList<Integer> jiexiListAllIndex = new ArrayList<>();
                for (int i = 0; i < adapterData.size(); i++) {
                    jiexiListAllIndex.add(i);
                }
                Intent intent1 = new Intent(this, CuoTiFenXiActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("datas", jiexiAllList);//要解析的习题
                bundle.putIntegerArrayList("jiexiindex", jiexiListAllIndex);//要解析的习题的下标
                intent1.putExtras(bundle);
                jumpTo(intent1, false);
                break;

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            closeIntent();
        }
        return super.onKeyDown(keyCode, event);

    }

    /**
     * 关闭当前页面，并通知关闭之前的页面
     */
    private void closeIntent() {
        Intent intent = new Intent();
        if (flag.equals("1")) {
            intent.setClass(this, KSZNLXActivity.class);
        } else if (flag.equals("2")) {
            intent.setClass(this, DaTiKa1Activity.class);
        }
        setResult(KSZNLXFragment.RESULT_CODE_JIEXI, intent);
        finish();
    }
}

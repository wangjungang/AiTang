package com.example.administrator.aitang.views;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * Created by Administrator on 2017/9/18.
 * ppw.setBackgroundDrawable(new BitmapDrawable());
 * 设置popuwidow显示的位置：ppw.showAtLocation(PayActivity.this.findViewById(R.id.ll_payAct),Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
 */

public class PopuwindowView extends PopupWindow {
    Activity mContext;
    View view;
    Boolean mFlag;

    /**
     * @param context
     * @param listener
     * @param resIds   布局内的id
     */
    public PopuwindowView(Activity context, int layoutId, View.OnClickListener listener, int[] resIds2, int... resIds) {
        super(context);
        mContext = context;
        init(context, listener, layoutId, resIds2, false, 0, resIds);
    }

    public PopuwindowView(Activity context, int layoutId, View.OnClickListener listener, int[] resIds2, boolean flag, int width, int... resIds) {
        super(context);
        mContext = context;
        mFlag = flag;
        init(context, listener, layoutId, resIds2, flag, width, resIds);
    }

    public View getView() {
        return view;
    }


    /**
     * @param context
     * @param listener
     * @param layoutId ppw的布局layout
     * @param resIds2  不需要在点击之后让ppw消失的组件
     * @param resIds   需要添加监听事件的组件
     */
    private void init(Activity context, final View.OnClickListener listener, int layoutId, final int[] resIds2, boolean mFlag, int width, int... resIds) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layoutId, null);
        if (resIds2.length > 0) {//如果大于0，说明不是所有按钮都需要点击之后消失，这个时候需要拿resIds2中的组件和resIds做判断，如果一样，就不走dismiss方法，否则走dismiss方法
            for (final int resId : resIds) {
                final View v = view.findViewById(resId);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onClick(view);
                        for (int a : resIds2) {
                            if (view.getId() == a) {
                                return;
                            }
                        }
                        dismiss();
                    }
                });
            }
        } else {//不大于0，说明所有按钮在点击之后都需要dismiss
            for (int resId : resIds) {
                final View v = view.findViewById(resId);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onClick(view);
                        dismiss();
                    }
                });
            }
        }
        //设置PopupWindow的View
        this.setContentView(view);
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int _width = dm.widthPixels;         // 屏幕宽度（像素）
        int _height = dm.heightPixels;       // 屏幕高度（像素）
        //设置PopupWindow弹出窗体的宽
//        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        if (width != 0)
            this.setWidth(width);
        else
            this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置是否允许点击外部让ppw消失
        this.setFocusable(mFlag);// 设置true,点击空白处时，隐藏掉pop窗口
    }

    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        mContext.getWindow().setAttributes(lp);
    }
}

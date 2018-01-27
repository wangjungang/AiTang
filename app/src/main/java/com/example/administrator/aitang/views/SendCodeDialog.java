package com.example.administrator.aitang.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.administrator.aitang.R;


/**
 * 两个按钮的dialog
 */
public class SendCodeDialog extends Dialog {
    //定义回调事件，用于dialog的点击事件
    public interface OnConfirmBtnClickListener {
        void click(View v);
    }

    public interface OnCancelBtnClickListener {
        void click(View v);
    }


    private OnConfirmBtnClickListener onconfirmBtnClickListener;
    private OnCancelBtnClickListener oncancelBtnClickListener;
    private TextView confirmBtn;
    private TextView cancelBtn;
    private TextView titleTxt;
    private TextView phoneNumberTxt;
    private String name = "";
    private String phoneNumber = "";


    public SendCodeDialog(Context context, String name, String phoneNumber, OnConfirmBtnClickListener onConfirmBtnClickListener, OnCancelBtnClickListener onCancelBtnClickListener) {
        super(context);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.onconfirmBtnClickListener = onConfirmBtnClickListener;
        this.oncancelBtnClickListener = onCancelBtnClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_send_code);
        //设置标题

        titleTxt = (TextView) findViewById(R.id.title);
        titleTxt.setText(name);
        phoneNumberTxt = (TextView) findViewById(R.id.tv_phone_number);
        phoneNumberTxt.setText(phoneNumber);

        confirmBtn = (TextView) findViewById(R.id.confirm);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onconfirmBtnClickListener.click(v);
                dismiss();
            }
        });
        cancelBtn = (TextView) findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oncancelBtnClickListener.click(v);
                dismiss();
            }
        });
    }

}


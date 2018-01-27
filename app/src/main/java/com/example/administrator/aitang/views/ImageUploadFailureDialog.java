package com.example.administrator.aitang.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.administrator.aitang.R;


/**
 * 两个按钮的dialog
 */
public class ImageUploadFailureDialog extends Dialog {
    //定义回调事件，用于dialog的点击事件
    public interface OnConfirmBtnClickListener {
        void click(View v);
    }

    private OnConfirmBtnClickListener onconfirmBtnClickListener;

    private TextView confirmBtn;


    public ImageUploadFailureDialog(Context context, OnConfirmBtnClickListener onConfirmBtnClickListener) {
        super(context);
        this.onconfirmBtnClickListener = onConfirmBtnClickListener;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_img_upload_failure);
        //设置标题
        confirmBtn = (TextView) findViewById(R.id.confirm);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onconfirmBtnClickListener.click(v);
                dismiss();
            }
        });

    }

}


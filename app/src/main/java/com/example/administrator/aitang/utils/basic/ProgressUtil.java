package com.example.administrator.aitang.utils.basic;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;

import com.example.administrator.aitang.R;


public class ProgressUtil {
    private static ProgressDialog dialog;

    public ProgressUtil(){
    }

    public static void show(Context context, int stringId){
        show(context, context.getResources().getString(stringId));
    }

    public static void show(Context context){
    	show(context, "加载中 ...");
    }
    
    public static void show(Context context, String msg){
        if(dialog == null || !dialog.isShowing()){
        	String titlename = SystemUtil.getAppName(context);
            (dialog = new ProgressDialog(context)).setTitle(titlename);
            dialog.setMessage(msg);
            dialog.setCancelable(false);
            dialog.show();
        }
        dialog.setOnKeyListener(onKeyListener);
    }
    private static  OnKeyListener onKeyListener = new OnKeyListener() {
		
		@Override
		public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
		           hide();
	      }
        return false;
		}
	};
    public static void hide(){
        if(dialog != null&&dialog.isShowing()){
            dialog.dismiss();
            dialog = null;
        }
    }
}

package com.clt.ledmanager.ui;


import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;

import com.mikepenz.materialdrawer.app.R;

/**
 * 
 * Dialog装饰
 */
public class DialogFactory{
	
	private static final int WIDTH=400;
	private static final int HEIGHT=300;
	
	public static Dialog createDialog(Context context,View view){
		return createDialog(context, view, WIDTH);
		
	}
	public static Dialog createDialog(Context context,View view,int width){
		Dialog dialog=new Dialog(context, R.style.dialog);
		LayoutParams params = new LayoutParams(width, LayoutParams.FILL_PARENT);
		dialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
		dialog.addContentView(view, params);
		return dialog;
	}
}

package com.remote.activity;

import com.remote.activity.R;
import com.remote.joshsera.HelpDialog;
import com.remote.joshsera.PrefsActivity;
import com.remote.joshsera.RemoteDroidActivity;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

public class TestActivity3 extends ActivityGroup {
	private ViewGroup tabcontent1 = null;
	private TextView button0 = null;
	private TextView button1 = null;
	private TextView button2 = null;
	private PopupWindow popup = null;
	private boolean boo = false;
	private boolean boo1 = false;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.test_activity_3);
		tabcontent1 = (ViewGroup) this.findViewById(R.id.frame1);
		showDefault();
		/*button0 = (TextView) this.findViewById(R.id.button00);
		button1 = (TextView) this.findViewById(R.id.button01);

		button0.setOnClickListener(new OnClickListenerImp1());

		button1.setOnClickListener(new OnClickListenerImp());

		button2 = (TextView) this.findViewById(R.id.button02);
		button2.setOnClickListener(new OnClickListenerImp2());*/
	
	}

	private void showDefault() {

		Intent i = new Intent(TestActivity3.this, RemoteDroidActivity.class);
		View v1 = getLocalActivityManager().startActivity(
				RemoteDroidActivity.class.getName(), i).getDecorView();
		tabcontent1.removeAllViews();
		tabcontent1.addView(v1);

	}

	private class OnClickListenerImp1 implements OnClickListener {
		public void onClick(View v) {
			Intent i = new Intent(TestActivity3.this, RemoteDroidActivity.class);
			View v1 = getLocalActivityManager().startActivity(
					RemoteDroidActivity.class.getName(), i).getDecorView();
			tabcontent1.removeAllViews();
			tabcontent1.addView(v1);
		}
	}

	private class OnClickListenerImp implements OnClickListener {
		public void onClick(View v) {
			Intent i = new Intent(TestActivity3.this, PrefsActivity.class);
			View v1 = getLocalActivityManager().startActivity(
					PrefsActivity.class.getName(), i).getDecorView();
			tabcontent1.removeAllViews();
			tabcontent1.addView(v1);
		}
	}


	private class OnClickListenerImp2 implements OnClickListener {

		public void onClick(View v) {
			LayoutInflater layFlat = TestActivity3.this.getLayoutInflater();
			View view = layFlat.inflate(R.layout.popup_menu, null);
			popup = new PopupWindow(view, 100, 100);
			popup.setFocusable(true);  
	       // popup.setOutsideTouchable(true);  
	        popup.setBackgroundDrawable(new BitmapDrawable());  

			int[] location = new int[2];
			button2.getLocationOnScreen(location);
			popup.showAtLocation(v, Gravity.NO_GRAVITY, location[0] - 10,
					location[1] - popup.getHeight());

			TextView text1 = (TextView) view.findViewById(R.id.t1);
			text1.setOnClickListener(new OnClickListenerImp3());
			TextView text2 = (TextView) view.findViewById(R.id.t2);
			text2.setOnClickListener(new OnClickListenerImp4());
		}
	}

	private class OnClickListenerImp3 implements OnClickListener {

		public void onClick(View v) {
			TestActivity3.this.popup.dismiss();
			HelpDialog help = new HelpDialog(TestActivity3.this);
			help.show();
			
		}

	}

	private class OnClickListenerImp4 implements OnClickListener {

		public void onClick(View v) {
			TestActivity3.this.popup.dismiss();
			dig();
			
		}

	}
	public void dig(){
		AlertDialog dia=new AlertDialog.Builder(this).
				setTitle("确定退出").
				setMessage("你真的要退出本程序").
				setIcon(android.R.drawable.ic_dialog_info).setPositiveButton("是",new DialogInterface.OnClickListener() {
			
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						System.exit(0);
						
					}
				}).setNegativeButton("否", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						
					}
				}).create();
		dia.show();
	}
	protected void onPause() {
		
		super.onPause();
		finish();
	}
}

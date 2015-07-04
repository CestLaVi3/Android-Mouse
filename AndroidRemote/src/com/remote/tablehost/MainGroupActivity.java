package com.remote.tablehost;
import com.remote.activity.AboutActivity;
import com.remote.activity.HelpActivity;
import com.remote.activity.MyPagerActivity;
import com.remote.activity.R;
import com.remote.activity.R.drawable;
import com.remote.activity.R.id;
import com.remote.activity.R.layout;
import com.remote.joshsera.HelpDialog;
import com.remote.joshsera.PrefsActivity;
import com.remote.joshsera.RemoteDroidActivity;
import com.remote.save.SaveIp;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;
public class MainGroupActivity extends TabActivity {
	private TabHost tabHost=null;
	private TabHost.TabSpec tabSpac=null;
	private Context context=null;
	private Resources resources=null;
	private Intent intent=null;
	public long mTime=0;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_group_activity_1);
		init();
		FillTbas();
	}
	private void init(){
		context=MainGroupActivity.this;
		resources= getResources();
		tabHost=getTabHost();
		tabSpac=null; 
		intent=null;
	}
	private void FillTbas(){
		LayoutInflater lay=LayoutInflater.from(this);
		
		View v1=lay.inflate(R.layout.main_group_activity_1_tab1, null,true);
		intent = new Intent().setClass(this, MyPagerActivity.class);
		tabSpac=tabHost.newTabSpec("tab1").
				setIndicator(v1).
				setContent(intent);
		tabHost.addTab(tabSpac);

		/*
		View v2=lay.inflate(R.layout.main_group_activity_1_tab2, null,true);
		intent = new Intent().setClass(this,HelpActivity.class);
		tabSpac=tabHost.newTabSpec("tab2").
				setIndicator(v2).
				setContent(intent);
		tabHost.addTab(tabSpac);
		*/
		
		View v3=lay.inflate(R.layout.main_group_activity_1_tab3, null,true);
		intent = new Intent().setClass(this, AboutActivity.class);
		tabSpac=tabHost.newTabSpec("tab3").
				setIndicator(v3).
				setContent(intent);
		tabHost.addTab(tabSpac);
		tabHost.setCurrentTabByTag("tab1");
	}
	
	//菜单
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE,Menu.FIRST+2,2,"退出");
		
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case Menu.FIRST+2:
			AlertDialog dig=new AlertDialog.Builder(this).
			setTitle("确定退出?").
			setMessage("你是否要确定退出程序").
			setIcon(android.R.drawable.ic_dialog_info).
			setPositiveButton("是",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					System.exit(0);
				}
			}).
			setNegativeButton("否",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			}).
			create();
			dig.show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	protected void onDestroy() {
		super.onDestroy();
	}

	protected void onStop() {
		super.onStop();
	}
}
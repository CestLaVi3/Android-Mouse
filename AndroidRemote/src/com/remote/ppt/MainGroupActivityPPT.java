package com.remote.ppt;
import com.remote.activity.ChangeActivity;
import com.remote.activity.MyPagerActivity;
import com.remote.activity.R;
import com.remote.activity.WifiActivity;
import com.remote.activity.R.drawable;
import com.remote.activity.R.id;
import com.remote.activity.R.layout;
import com.remote.joshsera.HelpDialog;
import com.remote.joshsera.PadActivity;
import com.remote.joshsera.PrefsActivity;
import com.remote.joshsera.RemoteDroidActivity;
import com.remote.save.SaveIp;
import com.remote.tablehost.MainGroupActivity;

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

public class MainGroupActivityPPT extends TabActivity {
	private TabHost tabHost=null;
	private TabHost.TabSpec tabSpac=null;
	private Context context=null;
	private Resources resources=null;
	private Intent intent=null;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_group_activity_ppt);
		init();
		FillTbas();
	}
	private void init(){
		context=MainGroupActivityPPT.this;
		resources= getResources();
		tabHost=getTabHost();
		tabSpac=null; 
		intent=null;
	}
	private void FillTbas(){
		View v=null;
		LayoutInflater lay=LayoutInflater.from(this);
		v=lay.inflate(R.layout.main_group_activity_ppt_tab1, null,true);
		intent = new Intent().setClass(this, PadActivity.class);
		tabSpac=tabHost.newTabSpec("tab1").
				setIndicator(v).
				setContent(intent);
		tabHost.addTab(tabSpac);
		
		lay=LayoutInflater.from(this);
		v=lay.inflate(R.layout.main_group_activity_ppt_tab2, null,true);
		intent = new Intent().setClass(this, PPTActivity.class);
		tabSpac=tabHost.newTabSpec("tab2").
				setIndicator(v).
				setContent(intent);
		tabHost.addTab(tabSpac);
		tabHost.setCurrentTabByTag("tab1");
	}
	/*public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == event.KEYCODE_BACK) {
			Intent intent = new Intent(MainGroupActivityPPT.this,
					ChangeActivity.class);
			startActivity(intent);
			MainGroupActivityPPT.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}*/
}
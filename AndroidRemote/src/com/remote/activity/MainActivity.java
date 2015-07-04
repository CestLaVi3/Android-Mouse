package com.remote.activity;


import com.remote.activity.R;
import com.remote.activity.R.id;
import com.remote.activity.R.layout;
import com.remote.joshsera.HelpDialog;
import com.remote.joshsera.RemoteDroidActivity;
import com.remote.joshsera.SlidingMenuView;

import android.os.Bundle;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends ActivityGroup {
	SlidingMenuView slidingMenuView;//滑动
	ViewGroup tabcontent;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        slidingMenuView = (SlidingMenuView) findViewById(R.id.sliding_menu_view);
        tabcontent = (ViewGroup) this.findViewById(R.id.sliding_body);
        showDefaultTab();
    }
    
    public void hideMenu(View view){
    	slidingMenuView.snapToScreen(1);
    	showDefaultTab();
    }
    
    public void showLeftMenu(View view){
    	slidingMenuView.snapToScreen(0);
    }
    
    public void showRightMenu(View view){
    	slidingMenuView.snapToScreen(2);
    }
    
    public void changeTab2(View view){
    	Intent i = new Intent(this,SldingDrawer.class);
    	View v = getLocalActivityManager().startActivity(SldingDrawer.class.getName(), i).getDecorView();
		tabcontent.removeAllViews();
		tabcontent.addView(v);
    }
    public void changeTab5(View view){
    	Intent i = new Intent(this,RemoteDroidActivity.class);
    	View v = getLocalActivityManager().startActivity(RemoteDroidActivity.class.getName(), i).getDecorView();
		tabcontent.removeAllViews();
		tabcontent.addView(v);
    }
    
   /* void showDefaultTab(){
    	Intent i = new Intent(this,RemoteDroidActivity.class);
    	View v = getLocalActivityManager().startActivity(RemoteDroidActivity.class.getName(), i).getDecorView();
		tabcontent.removeAllViews();
		tabcontent.addView(v);
    }*/
    void showDefaultTab(){
    	Intent i = new Intent(this,TestActivity3.class);
    	View v = getLocalActivityManager().startActivity(TestActivity3.class.getName(), i).getDecorView();
		tabcontent.removeAllViews();
		tabcontent.addView(v);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(Menu.NONE,Menu.FIRST+1,1,"帮助");
		menu.add(Menu.NONE,Menu.FIRST+2,2,"退出");
		
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case Menu.FIRST+1:
			HelpDialog help=new HelpDialog(this);
			help.show();
			break;
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


}

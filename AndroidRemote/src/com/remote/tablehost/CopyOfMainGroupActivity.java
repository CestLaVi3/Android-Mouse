package com.remote.tablehost;

import com.remote.activity.MyPagerActivity;
import com.remote.activity.R;
import com.remote.activity.R.drawable;
import com.remote.activity.R.id;
import com.remote.activity.R.layout;
import com.remote.joshsera.HelpDialog;
import com.remote.joshsera.PrefsActivity;
import com.remote.joshsera.RemoteDroidActivity;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class CopyOfMainGroupActivity extends ActivityGroup {

	private GridView gridViewTool = null;
	private LinearLayout liner1 = null;
	private ImageAdapter menu = null;
	private Intent i = null;
	
	private int imgs[] = new int[] { R.drawable.menu_wuxian,
			R.drawable.menu_init, R.drawable.menu_help_1,
			  };
	private int width;
	private int height;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_groupactivity);
		// 获取配置文件的信息
		gridViewTool = (GridView) this.findViewById(R.id.gridView);
		liner1 = (LinearLayout) this.findViewById(R.id.liner2);

		// 设置GridView的一些信息
		gridViewTool.setNumColumns(imgs.length);
		gridViewTool.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gridViewTool.setGravity(Gravity.CENTER);
		gridViewTool.setVerticalSpacing(0);

		// 获取宽度好和高度
		width = this.getWindowManager().getDefaultDisplay().getWidth()
				/ imgs.length;
		height = this.getWindowManager().getDefaultDisplay().getHeight() / 8;

		menu = new ImageAdapter(this, imgs, this.width, this.height,
				R.drawable.menu_select);
		
		gridViewTool.setAdapter(menu);
		this.SwithcId(0);
		gridViewTool.setOnItemClickListener(new OnItemClickListenerImp());
	}
	private   class OnItemClickListenerImp implements OnItemClickListener{

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			CopyOfMainGroupActivity.this.SwithcId(arg2);
			
		}
		
	}
	
	public void SwithcId(int id){
		this.menu.ImageFource(id);
		liner1.removeAllViews();
		//切花你选中的图片的ID
		switch(id){
			case 0:
				this.i=new Intent(CopyOfMainGroupActivity.this,MyPagerActivity.class);
				break;
			case 1:
				this.i=new Intent(CopyOfMainGroupActivity.this,PrefsActivity.class);
				break;
			case 2:
				this.i=new Intent(CopyOfMainGroupActivity.this,RemoteDroidActivity.class);
				break;
		}
		
		//获取Activity的内容
		this.i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		View v=this.getLocalActivityManager().startActivity("subActivity", this.i).getDecorView();//获取顶端视图
		this.liner1.addView(v,LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
	}
	
	//菜单
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==event.KEYCODE_BACK){
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
		}
		return super.onKeyDown(keyCode, event);
	}
}
package com.remote.activity;

import java.util.ArrayList;
import java.util.List;

import com.remote.joshsera.RemoteDroidActivity;
import com.remote.save.SaveIp;
import com.remote.tablehost.MainGroupActivity;


import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MyPagerActivity extends Activity {
	Context context = null;
	LocalActivityManager manager = null;
	ViewPager pager = null;
	TabHost tabHost = null;
	TextView t1,t2;
	//TextView t3;
	
	private int offset = 0;// ����ͼƬƫ����
	private int currIndex = 0;// ��ǰҳ�����
	private int bmpW;// ����ͼƬ���
	private ImageView cursor;//����ͼƬ
	private long mTime=0;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_pager_activity);
		context = MyPagerActivity.this;
		manager = new LocalActivityManager(this , true);
		manager.dispatchCreate(savedInstanceState);

		InitImageView();
		initTextView();
		initPagerViewer();

	}

	private void initTextView() {
		t1 = (TextView) findViewById(R.id.text1);
		t2 = (TextView) findViewById(R.id.text2);
		//t3 = (TextView) findViewById(R.id.text3);

		t1.setOnClickListener(new MyOnClickListener(0));
		t2.setOnClickListener(new MyOnClickListener(1));
		//t3.setOnClickListener(new MyOnClickListener(2));
		
	}

	private void initPagerViewer() {
		pager = (ViewPager) findViewById(R.id.viewpage);
		final ArrayList<View> list = new ArrayList<View>();
		//
		Intent intent = new Intent(context, RemoteDroidActivity.class);
		list.add(getView("RemoteDroidActivity", intent));
		//
		Intent intent2 = new Intent(context, HelpActivity.class);
		list.add(getView(SaveIp.class.getName(), intent2));
		//
		/*Intent intent3 = new Intent(context, HelpActivity.class);
		list.add(getView(HelpActivity.class.getName(), intent3));*/
		
		pager.setAdapter(new MyPagerAdapter(list));
		pager.setCurrentItem(0);
		pager.setOnPageChangeListener(new MyOnPageChangeListener());
	}


	private View getView(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();
	}
	
	private void InitImageView() {
		cursor = (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.roller)
		.getWidth();// ��ȡͼƬ���
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		int screenW = dm.widthPixels;// ��ȡ�ֱ��ʿ��
		offset = (screenW / 2 - bmpW) / 2;// ����ƫ����
		
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		
		cursor.setImageMatrix(matrix);// ���ö�����ʼλ��
	}


	

	public class MyOnPageChangeListener implements OnPageChangeListener {
		int one = offset * 2 + bmpW;// ҳ��1 -> ҳ��2 ƫ����
		//int two = one * 2;// ҳ��1 -> ҳ��3 ƫ����

		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
				}/* else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
				}*/
				break;
			case 1:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, one, 0, 0);
				} /*else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);	
				}*/
				break;
			/*case 2:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, two, 0, 0);
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
				}
				break;*/
			}
			currIndex = arg0;
			animation.setFillAfter(true);// True:ͼƬͣ�ڶ�������λ��
			animation.setDuration(300);
			cursor.startAnimation(animation);
		}
		public void onPageScrollStateChanged(int arg0) {
			
		}
		public void onPageScrolled(int arg0, float arg1, int arg2) {	
		}
	}

	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}
		public void onClick(View v) {
			pager.setCurrentItem(index);
		}
	}
	
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
			if (keyCode == event.KEYCODE_BACK) {
				AlertDialog dig = new AlertDialog.Builder(this)
						.setTitle("ȷ���˳�?")
						.setMessage("���Ƿ�Ҫȷ���˳�����")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setPositiveButton("��",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
										finish();
										System.exit(0);
									}
								})
						.setNegativeButton("��",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
									}
								}).create();
				dig.show();
			}
			return super.onKeyDown(keyCode, event);
		}
	

}

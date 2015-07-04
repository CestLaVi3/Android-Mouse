package com.remote.start;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import com.remote.activity.R;
import com.remote.tablehost.MainGroupActivity;
public class Welcome extends Activity implements Runnable {
	
	//�Ƿ��ǵ�һ��ʹ��
	private boolean isFirstUse;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_activity_welcome);
		/**
		 * ����һ���ӳ��߳�
		 */
		new Thread(this).start();
	}

	public void run() {
		try {
			/**
			 * �ӳ�����ʱ��
			 */
			Thread.sleep(2000);
			
			//��ȡSharedPreferences����Ҫ������
			SharedPreferences preferences = getSharedPreferences("isFirstUse",MODE_WORLD_READABLE);

	        isFirstUse = preferences.getBoolean("isFirstUse", true);

			if (isFirstUse) {
				startActivity(new Intent(Welcome.this, GuideViewDemoActivity.class));
			} else {
				startActivity(new Intent(Welcome.this, MainGroupActivity.class));
			}
			finish();
			
			//ʵ����Editor����
			Editor editor = preferences.edit();
	        //��������
	        editor.putBoolean("isFirstUse", false);
	        //�ύ�޸�
	        editor.commit();


		} catch (InterruptedException e) {

		}
	}
}

package com.remote.save;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.remote.activity.MyPagerActivity;
import com.remote.activity.R;
import com.remote.activity.R.drawable;
import com.remote.activity.R.id;
import com.remote.activity.R.layout;
import com.remote.activity.R.string;
import com.remote.joshsera.RemoteDroidActivity;
import com.remote.joshsera.Settings;
import com.remote.tablehost.MainGroupActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SaveIp extends Activity {
	//常量
	private static final String TAG = "RemoteDroid";
	public static final int MENU_PREFS = 0;
	public static final int MENU_HELP = 1;

	private ListView mHostlist;
	private RemoteDroidActivity remoteActivity=null;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.save_ip);
		// 初始化
		Settings.init(this.getApplicationContext());
		//
		mHostlist = (ListView) this.findViewById(R.id.lvHosts);
		//
		populateHostList();
	}

	private void populateHostList() {
		//填充主机列表
		LinkedList<String> ips = Settings.savedHosts;
		String[] from = new String[]{"hostip"};
		int[] to = new int[]{R.id.hostEntry};
		List<Map<String,String>> data = new ArrayList<Map<String, String>>();
		for (String s:ips){
			Map<String, String> map = new HashMap<String, String>();
			map.put("hostip", s);
			data.add(map);
		}

		SimpleAdapter adapter = new SimpleAdapter(this,data,R.layout.savedhost,from,to){
			public View getView(int position, View convertView, ViewGroup parent){
				View v = super.getView(position, convertView, parent);
				TextView text = (TextView) v.findViewById(R.id.hostEntry);
				final CharSequence str = text.getText();
				ImageButton b = (ImageButton) v.findViewById(R.id.hostbutton);
				// 为文本设置
				text.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						/*Intent inte=new Intent().setClass(SaveIp.this, MainGroupActivity.class);
						Bundle bundle=new Bundle();
						bundle.putCharSequence("HostIp", str);
						inte.putExtras(bundle);
						startActivity(inte);*/
					}
				});
				//为按钮设置
				b.setOnClickListener(new View.OnClickListener() {     				
					public void onClick(View v) { 
						onRemoveSavedHost(str);
					}
				});
				return v;
			}
		};
		mHostlist.setAdapter(adapter);
	}
	
	private void onRemoveSavedHost(CharSequence str) {
		try {
			Settings.removeSavedHost(str); 
			populateHostList();
		} catch (Exception e) {
		}

	}
	
	//销毁
		public void onDestroy() {
			super.onDestroy();
		}
		//开始
		public void onStart() {
			super.onStart();
		}
		//停止
		public void onStop() {
			super.onStop();
		}
		public void onResume() {
			super.onResume();
		}
		public void onPause() {
			super.onPause();
			
		}
}

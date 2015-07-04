package com.remote.joshsera;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.remote.activity.ChangeActivity;
import com.remote.activity.R;
import com.remote.activity.R.drawable;
import com.remote.activity.R.id;
import com.remote.activity.R.layout;
import com.remote.activity.R.string;
import com.remote.activity.WifiActivity;
import com.remote.ppt.MainGroupActivityPPT;
import com.remote.ppt.PPTActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView;
import android.widget.Toast;

public class RemoteDroidActivity extends Activity {
	// 常量
	private static final String TAG = "RemoteDroid";
	public static final int MENU_PREFS = 0;
	public static final int MENU_HELP = 1;
	// 变量
	public EditText tbIp;
	private HelpDialog dlHelp;
	private ListView mHostlist;
	private SlidingDrawer sliding = null;
	public boolean wifi = false;
	public ImageButton but = null;
	// 构造函数
	public RemoteDroidActivity() {
		super();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.remote_droid);
		// 注册
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(connectionReceiver, intentFilter);

		// 连接
		but = (ImageButton) this.findViewById(R.id.btnConnect);
		but.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				onConnectButton();
			}
		});
		
		// 初始化
		Settings.init(this.getApplicationContext());
		this.tbIp = (EditText) this.findViewById(R.id.etIp);

		if (Settings.ip != null) {
			this.tbIp.setText(Settings.ip);
		}
		this.tbIp.setOnClickListener(new OnClickListenerImp());

		sliding = (SlidingDrawer) this.findViewById(R.id.sliding_01);
		sliding.setOnDrawerOpenListener(new OnDrawerOpenListenerImp1());
		sliding.setOnDrawerCloseListener(new OnDrawerCloseListenerImp1());

		mHostlist = (ListView) this.findViewById(R.id.lvHosts);
		populateHostList();

	}

	// 监测wifi
	BroadcastReceiver connectionReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			ConnectivityManager connectMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
			NetworkInfo wifiNetInfo = connectMgr
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			wifi = wifiNetInfo.isConnected();
			if (wifi) {
				LinearLayout layout_01 = (LinearLayout) RemoteDroidActivity.this
						.findViewById(R.id.LinearLayout2);
				layout_01.setVisibility(View.INVISIBLE);
			} else {
				LinearLayout layout_01 = (LinearLayout) RemoteDroidActivity.this
						.findViewById(R.id.LinearLayout2);
				TextView tv = (TextView) RemoteDroidActivity.this
						.findViewById(R.id.wifi_text);
				layout_01.setVisibility(View.VISIBLE);
				Toast toast = Toast.makeText(getApplicationContext(),
						"当前wifi网络不可用，请连接重试？", Toast.LENGTH_SHORT);
				toast.show();
				tv.setText("当前wifi网络不可用，请检查你的网络设置");
				tv.setOnClickListener(new WifiOnClickListener());
			}
		}
	};

	private class WifiOnClickListener implements OnClickListener {
		public void onClick(View v) {
			startActivity(new Intent(
					android.provider.Settings.ACTION_WIFI_SETTINGS));
		}
	}

	private class OnDrawerOpenListenerImp1 implements OnDrawerOpenListener {
		public void onDrawerOpened() {
			EditText lay = (EditText) RemoteDroidActivity.this
					.findViewById(R.id.etIp);
			lay.setClickable(false);
			Log.d("ss", "aa");

			ImageView img = (ImageView) RemoteDroidActivity.this
					.findViewById(R.id.handle_01);
			img.setImageResource(R.drawable.handle_1);
		}
	}

	private class OnDrawerCloseListenerImp1 implements OnDrawerCloseListener {
		public void onDrawerClosed() {
			ImageView img = (ImageView) RemoteDroidActivity.this
					.findViewById(R.id.handle_01);
			img.setImageResource(R.drawable.handle);
		}
	}

	private class OnClickListenerImp implements OnClickListener {

		public void onClick(View v) {
			RemoteDroidActivity.this.tbIp.setText("");
		}
	}

	private void onConnectButton() {
		String ip = this.tbIp.getText().toString();
		if (ip.matches("^[0-9]{1,4}\\.[0-9]{1,4}\\.[0-9]{1,4}\\.[0-9]{1,4}$")) {
			try {
				Settings.setIp(ip);
				if (wifi) {
					Intent i = new Intent(this, ChangeActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("ip", ip);
					i.putExtras(bundle);
					this.startActivity(i);
					finish();
				} else {
					int hi = RemoteDroidActivity.this.but.getHeight();
					int wi = RemoteDroidActivity.this.but.getWidth();
					Toast toast = Toast.makeText(this, "你的手机未连入wifi网络",
							Toast.LENGTH_LONG);
					toast.setGravity(0, wi - 50, hi);
					toast.show();
				}
			} catch (Exception ex) {
				Toast.makeText(this,
						this.getResources().getText(R.string.toast_invalidIP),
						Toast.LENGTH_LONG).show();
				Log.d(TAG, ex.toString());
			}
		} else {
			Toast.makeText(this,
					this.getResources().getText(R.string.toast_invalidIP),
					Toast.LENGTH_LONG).show();
		}
	}

	private void populateHostList() {
		// 填充主机列表
		LinkedList<String> ips = Settings.savedHosts;
		String[] from = new String[] { "hostip" };
		int[] to = new int[] { R.id.hostEntry };
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		for (String s : ips) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("hostip", s);
			data.add(map);
		}

		SimpleAdapter adapter = new SimpleAdapter(this, data,
				R.layout.savedhost, from, to) {
			public View getView(int position, View convertView, ViewGroup parent) {
				View v = super.getView(position, convertView, parent);
				TextView text = (TextView) v.findViewById(R.id.hostEntry);
				final CharSequence str = text.getText();
				ImageButton b = (ImageButton) v.findViewById(R.id.hostbutton);
				// 为文本设置
				text.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						onSavedHost(str);
						RemoteDroidActivity.this.sliding.animateClose();
					}
				});
				// 为按钮设置
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
	private void onSavedHost(CharSequence s) {
		try {
			tbIp.setText(s);
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
	}

	// 销毁
	public void onDestroy() {
		super.onDestroy();
		
	}

	// 开始
	public void onStart() {
		super.onStart();
		
	}

	// 停止
	public void onStop() {
		super.onStop();
		
	}

	protected void onRestart() {
		super.onRestart();

	}

	public void onResume() {
		super.onResume();

	}

	public void onPause() {
		super.onPause();
		
	}
}

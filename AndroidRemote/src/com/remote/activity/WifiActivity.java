package com.remote.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.remote.tablehost.MainGroupActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class WifiActivity extends Activity {
	//
	public List<Map<String, Object>> ay = null;
	public List<Map<String, Object>> ay1 = null;
	public HashMap<String, Object> map = null;
	//
	public ListView listView = null;
	public ListView listView1 = null;
	public SimpleAdapter simple = null;
	//
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.wifi_activity);
		// WIFI
		ay = new ArrayList<Map<String, Object>>();
		getData();
		simple = new SimpleAdapter(this, ay, R.layout.wifi_adapter,
				new String[] { "name:", "type:" }, new int[] { R.id.wifi_name,
						R.id.wifi_mess });
		listView = (ListView) this.findViewById(R.id.listview);
		listView.setAdapter(simple);
		setListViewHeightBasedOnChildren(listView);
		// 手机
		ay1 = new ArrayList<Map<String, Object>>();
		getMobleData();
		simple = new SimpleAdapter(this, ay1, R.layout.wifi_adapter_message,
				new String[] { "name:", "type:" }, new int[] { R.id.wifi_name,
						R.id.wifi_mess });
		listView1 = (ListView) this.findViewById(R.id.listview1);
		listView1.setAdapter(simple);
		setListViewHeightBasedOnChildren(listView1);
	}
	public void setListViewHeightBasedOnChildren(ListView listView) {    
        ListAdapter listAdapter = listView.getAdapter();    
        if (listAdapter == null) {    
            return;    
        }    
        int totalHeight = 0;    
        for (int i = 0; i < listAdapter.getCount(); i++) {    
            View listItem = listAdapter.getView(i, null, listView);    
            listItem.measure(0, 0);   
            totalHeight += listItem.getMeasuredHeight();    
        }    
        ViewGroup.LayoutParams params = listView.getLayoutParams();    
        params.height = totalHeight    
                + (listView.getDividerHeight() * (listAdapter.getCount() + 1));    
        listView.setLayoutParams(params);    
    }    
	public void getMobleData() {
		TelephonyManager telephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		//
		map = new HashMap<String, Object>();
		map.put("name:", "手机型号：");
		map.put("type:", android.os.Build.MODEL);
		ay1.add(map);
		
		//
		map = new HashMap<String, Object>();
		map.put("name:", "系统版本号：");
		map.put("type:", android.os.Build.VERSION.RELEASE);
		ay1.add(map);
		//
		map = new HashMap<String, Object>();
		map.put("name:", "API版本号：");
		map.put("type:", "API  "+android.os.Build.VERSION.SDK_INT);
		ay1.add(map);
		//
		map = new HashMap<String, Object>();
		map.put("name:", "设备ID：");
		map.put("type:", telephony.getDeviceId());
		ay1.add(map);
		//
		map = new HashMap<String, Object>();
		map.put("name:", "设备的软件版本号：");
		map.put("type:", telephony.getDeviceSoftwareVersion());
		ay1.add(map);
	}

	private void getData() {
		WifiManager wifi = (WifiManager) getSystemService(WIFI_SERVICE);
		WifiInfo wifiInfo = wifi.getConnectionInfo();
		DhcpInfo dhcpinfo = wifi.getDhcpInfo();
		String s1 = null;
		//
		map = new HashMap<String, Object>();
		s1 = Formatter.formatIpAddress(wifiInfo.getIpAddress());
		map.put("name:", "手机ip地址：");
		map.put("type:", s1);
		ay.add(map);

		map = new HashMap<String, Object>();
		map.put("name:", "手机mac地址：");
		map.put("type:", wifiInfo.getMacAddress());
		ay.add(map);

		/*map = new HashMap<String, Object>();
		map.put("name:", "网络ID：");
		map.put("type:", wifiInfo.getNetworkId());
		ay.add(map);*/

		map = new HashMap<String, Object>();
		map.put("name:", "连接速度：");
		map.put("type:", wifiInfo.getLinkSpeed());
		ay.add(map);

		map = new HashMap<String, Object>();
		map.put("name:", "信号强度：");
		map.put("type:", wifiInfo.getRssi());
		ay.add(map);
		
		map = new HashMap<String, Object>();
		map.put("name:", "网关：");
		map.put("type:", Formatter.formatIpAddress(dhcpinfo.gateway));
		ay.add(map);

		map = new HashMap<String, Object>();
		map.put("name:", "子网掩码：");
		map.put("type:", Formatter.formatIpAddress(dhcpinfo.netmask));
		ay.add(map);

		/*map = new HashMap<String, Object>();
		map.put("name:", "服务器的IP：");
		map.put("type:", Formatter.formatIpAddress(dhcpinfo.serverAddress));
		ay.add(map);*/
		
		map = new HashMap<String, Object>();
		map.put("name:", "DNS：");
		map.put("type:", Formatter.formatIpAddress(dhcpinfo.dns1));
		ay.add(map);
/*
		map = new HashMap<String, Object>();
		map.put("name:", "备用DNS：");
		map.put("type:", Formatter.formatIpAddress(dhcpinfo.dns2));
		ay.add(map);*/
	}
	/*public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == event.KEYCODE_BACK) {
			Intent intent = new Intent(WifiActivity.this,
					ChangeActivity.class);
			startActivity(intent);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}*/
}
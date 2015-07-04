package com.remote.receiver;

import com.remote.activity.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class  ConnectionChangeReceiver extends BroadcastReceiver  {
	String packnameString = null;

	public void onReceive(Context context, Intent intent) {
		packnameString = context.getPackageName();

		ConnectivityManager connectMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiNetInfo = connectMgr
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (wifiNetInfo.isConnected()) {
			
		} else {
			LayoutInflater flater=LayoutInflater.from(context);
			View view=flater.inflate(R.layout.remote_droid, null,true);
			
			TextView tv = (TextView)view.findViewById(R.id.wifi_text);
			LinearLayout layout_01 = (LinearLayout)view. findViewById(R.id.LinearLayout2);
			
			layout_01.setVisibility(View.VISIBLE);
			tv.setText("��ǰwifi���粻���ã����������������");
			Toast.makeText(context, "��ǰwifi���粻���ã����������ԣ�",
					Toast.LENGTH_LONG).show();
		}
	}
	
}
package com.remote.activity;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.remote.joshsera.RemoteDroidActivity;
import com.remote.joshsera.Settings;
import com.remote.osc.OSCPort;
import com.remote.osc.OSCPortOut;
import com.remote.ppt.MainGroupActivityPPT;
import com.remote.ppt.PPTActivity;
import com.remote.tablehost.MainGroupActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ChangeActivity extends Activity {
	private ImageView button1 = null;
	private ImageView button2 = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.setContentView(R.layout.change_activity);
		Bundle bundle=this.getIntent().getExtras();
		String ip=bundle.getString("ip");
		System.out.println(ip);
		try{
			String s="#ip";
			TextView textView=(TextView)this.findViewById(R.id.about_text_01);
			
			DatagramSocket socket=new DatagramSocket();
			DatagramPacket pack=new DatagramPacket(s.getBytes(), s.length(),InetAddress.getByName(ip),57110);
			socket.send(pack);
			textView.setText(ip);
		}catch(Exception e){
			
		}
		button1 = (ImageView) this.findViewById(R.id.change_computer);
		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ChangeActivity.this,
						MainGroupActivityPPT.class);
				startActivity(intent);
				//finish();
			}
		});
		button2 = (ImageView) this.findViewById(R.id.change_watch);
		button2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ChangeActivity.this,
						WifiActivity.class);
				startActivity(intent);
				//finish();
			}
		});
	}

	public boolean onKeyUp(int keyCode, KeyEvent event) {
		Intent intent=new Intent(ChangeActivity.this,MainGroupActivity.class);
		startActivity(intent);
		finish();
		
		return super.onKeyUp(keyCode, event);
	}
}

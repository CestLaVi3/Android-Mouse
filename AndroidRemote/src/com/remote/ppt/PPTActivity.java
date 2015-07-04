package com.remote.ppt;

import java.io.IOException;

import com.remote.activity.ChangeActivity;
import com.remote.activity.R;
import com.remote.joshsera.Settings;
import com.remote.osc.OSCPort;
import com.remote.tablehost.MainGroupActivity;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class PPTActivity extends Activity {
	private ImageButton start = null;
	private Button preious = null;
	private Button end=null;
	private Button next = null;
	private Button first = null;
	private Button last = null;
	private Button close=null;
	boolean boo = true;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.ppt_activity);
		new Thread(new RunnableImp()).start();
	}

	private class OnClickListenerImp implements OnClickListener {
		String str = null;

		public OnClickListenerImp(String str) {
			this.str = str;
		}

		public void onClick(View v) {
			try {
				DatagramSocket ds;
				ds = new DatagramSocket();
				DatagramPacket outPack = new DatagramPacket(str.getBytes(),
						str.length(), InetAddress.getByName(Settings.ip),
						OSCPort.defaultSCOSCPort());
				ds.send(outPack);
			} catch (Exception e) {
			}
		}
	}

	private class StartOnClickListenerImp implements OnClickListener {
		String str = null;

		public void onClick(View v) {
			try {
				if (boo) {
					boo = false;
					str = "0";
					DatagramSocket ds;
					ds = new DatagramSocket();
					DatagramPacket outPack = new DatagramPacket(str.getBytes(),
							str.length(), InetAddress.getByName(Settings.ip),
							OSCPort.defaultSCOSCPort());
					ds.send(outPack);
				} else {
					boo = true;
					str = "3";
					DatagramSocket ds;
					ds = new DatagramSocket();
					DatagramPacket outPack = new DatagramPacket(str.getBytes(),
							str.length(), InetAddress.getByName(Settings.ip),
							OSCPort.defaultSCOSCPort());
					ds.send(outPack);
				}
			} catch (Exception e) {
			}
		}
	}

	private class RunnableImp implements Runnable {
		public void run() {
			//
			start = (ImageButton) PPTActivity.this.findViewById(R.id.start);
			start.setOnClickListener(new StartOnClickListenerImp());
			//
			next = (Button) PPTActivity.this.findViewById(R.id.froward);
			next.setOnClickListener(new OnClickListenerImp("1"));
			//
			preious = (Button ) PPTActivity.this.findViewById(R.id.back);
			preious.setOnClickListener(new OnClickListenerImp("2"));
			//
			end=(Button)PPTActivity.this.findViewById(R.id.end);
			end.setOnClickListener(new OnClickListenerImp("3"));
			//
			first = (Button) PPTActivity.this.findViewById(R.id.first);
			first.setOnClickListener(new OnClickListenerImp("4"));
			//
			last = (Button) PPTActivity.this.findViewById(R.id.last);
			last.setOnClickListener(new OnClickListenerImp("5"));
			//
			close=(Button)PPTActivity.this.findViewById(R.id.close);
			close.setOnClickListener(new OnClickListenerImp("6"));
		}
	}

/*	private class TiaoOnClickListenerImp implements OnClickListener {
		String str = null;
		String s1=null;
		public TiaoOnClickListenerImp(String str,String s1) {
			this.str = str;
			this.s1=s1;
		}

		public void onClick(View v) {
			try {
				DatagramSocket ds;
				ds = new DatagramSocket();
				String s=s1+" "+str;
				DatagramPacket outPack = new DatagramPacket(s.getBytes(),
						s.length(), InetAddress.getByName(Settings.ip),
						OSCPort.defaultSCOSCPort());
				ds.send(outPack);
			} catch (Exception e) {
			}
		}
	}*/

	/*public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == event.KEYCODE_BACK) {
			Intent intent = new Intent(PPTActivity.this,
					ChangeActivity.class);
			startActivity(intent);
			PPTActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}*/

}

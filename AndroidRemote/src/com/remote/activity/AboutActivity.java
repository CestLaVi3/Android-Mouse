package com.remote.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;

public class AboutActivity extends Activity{

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.about_activity);
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

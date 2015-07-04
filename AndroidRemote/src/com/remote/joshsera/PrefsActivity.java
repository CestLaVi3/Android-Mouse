package com.remote.joshsera;

import com.remote.activity.MainActivity;
import com.remote.activity.R;
import com.remote.activity.R.id;
import com.remote.activity.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.CheckBox;
import android.widget.SeekBar;

public class PrefsActivity extends Activity {
	private CheckBox cbTap;
	private CheckBox cbTwoTouch;
	private CheckBox cbShowButtons;
	private SeekBar sbTap;
	private SeekBar sbSensitivity;
	private SeekBar sbScrollSensitivity;
	private CheckBox cbScrollInverted;
	
	public PrefsActivity() {
		super();
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Settings.init(this.getApplicationContext());
		this.setContentView(R.layout.prefs);
		this.setTitle("Preferences");
		
		
		this.cbTap = (CheckBox)this.findViewById(R.id.cbTap);
		this.sbTap = (SeekBar)this.findViewById(R.id.sbClick);
		this.cbTwoTouch = (CheckBox)this.findViewById(R.id.cbTwoTouch);
		this.cbShowButtons = (CheckBox)this.findViewById(R.id.cbShowButtons);
		this.sbSensitivity = (SeekBar)this.findViewById(R.id.sbSensitivity);
		this.sbScrollSensitivity = (SeekBar)this.findViewById(R.id.sbScrollSensitivity);
		this.cbScrollInverted = (CheckBox)this.findViewById(R.id.cbScrollInverted);
		
		
		this.cbTap.setChecked(Settings.tapToClick);
		this.cbTwoTouch.setChecked(Settings.twoTouchRightClick);
		this.cbShowButtons.setChecked(Settings.hideMouseButtons);
		this.sbTap.setProgress(Settings.clickTime);
		this.sbSensitivity.setProgress(Settings.sensitivity);
		this.sbScrollSensitivity.setProgress(Settings.scrollSensitivity);
		this.cbScrollInverted.setChecked(Settings.scrollInverted);
	}
	
	public void savePrefs() {
		
		Settings.setTapToClick(this.cbTap.isChecked());
		Settings.setTwoTouchRightClick(this.cbTwoTouch.isChecked());
		Settings.setShowMouseButtons(this.cbShowButtons.isChecked());
		Settings.setClickTime(this.sbTap.getProgress());
		
		Settings.setSensitivity(this.sbSensitivity.getProgress());
		Settings.setScrollSensitivity(this.sbScrollSensitivity.getProgress());
		Settings.setScrollInverted(this.cbScrollInverted.isChecked());

	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			Intent i=new Intent(PrefsActivity.this,MainActivity.class);
			startActivity(i);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}

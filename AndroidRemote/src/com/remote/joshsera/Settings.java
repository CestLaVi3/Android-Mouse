package com.remote.joshsera;

import java.util.LinkedList;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.KeyCharacterMap;

public class Settings {
	public static String ip;
	public static KeyCharacterMap charmap;

	public static boolean tapToClick;
	public static int clickTime;
	public static int sensitivity;
	public static int scrollSensitivity;
	public static boolean scrollInverted;
	public static boolean hideMouseButtons;
	public static boolean twoTouchRightClick;
	public static boolean hideAdvancedControls;
	//
	private static SharedPreferences prefs;
	private static final String PREFS_IPKEY = "remoteip";
	private static final String PREFS_TAPTOCLICK = "tapclick";
	private static final String PREFS_TAPTIME = "taptime";
	private static final String PREFS_SENSITIVITY = "sensitivity";
	private static final String PREFS_RECENT_IP_PREFIX = "recenthost";
	private static final String PREFS_SCROLL_SENSITIVITY = "scrollSensitivity";
	private static final String PREFS_SCROLL_INVERTED = "scrollInverted";
	//
	private static final String PREFS_MULTITOUCH_RIGHTCLICK = "twoTouchRightClick";
	private static final String PREFS_ADVANCED_CONTROLS = "showAdvancedControls";
	private static final String PREFS_MOUSE_BUTTONS = "showMouseButtons";
	//����ΪXML�ļ����ļ���
	private static final String PREFS_FILENAME = "RemoteDroid";
	// ����IP��ַ������
	public static final int MAX_SAVED_HOSTS = 20;
	public static LinkedList<String> savedHosts;
	public static void init(Context con) {
		if (prefs == null) {
			
			prefs = con.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE);
			charmap = KeyCharacterMap.load(KeyCharacterMap.BUILT_IN_KEYBOARD);
			savedHosts = new LinkedList<String>();
			//ȡ�����IP��ַ
			populateRecentIPs();
			
			ip = prefs.getString(PREFS_IPKEY, "192.168.1.1");
			tapToClick = prefs.getBoolean(PREFS_TAPTOCLICK, true);
			clickTime = prefs.getInt(Settings.PREFS_TAPTIME, 200);
			sensitivity = prefs.getInt(Settings.PREFS_SENSITIVITY, 0);
			scrollSensitivity = prefs.getInt(Settings.PREFS_SCROLL_SENSITIVITY, 50);
			scrollInverted = prefs.getBoolean(PREFS_SCROLL_INVERTED, false);

	
			hideAdvancedControls = prefs.getBoolean(PREFS_ADVANCED_CONTROLS, false);
			twoTouchRightClick = prefs.getBoolean(PREFS_MULTITOUCH_RIGHTCLICK, true);
			hideMouseButtons = prefs.getBoolean(PREFS_MOUSE_BUTTONS, true);
			
		}
	}
	//�������IP��ַ
	public static void setIp(String ip) throws Exception {
		SharedPreferences.Editor edit = prefs.edit();
		
		testIPValid(ip);//������IP�Ƿ�Ϸ�
		edit.putString(Settings.PREFS_IPKEY, ip);
		edit.commit();
		Settings.ip = ip;
		
		//�����IP��ַ��ʱ�洢�����savedHosts
		
		if (!savedHosts.contains(ip)) {
			
			if (savedHosts.size() < MAX_SAVED_HOSTS) { //5
				savedHosts.addFirst(ip);
			} else {
				savedHosts.removeLast();
				savedHosts.addFirst(ip);
			}
		} else {
			while (savedHosts.contains(ip)) {
				savedHosts.remove(ip);
			}
			savedHosts.addFirst(ip);
		}
		// �洢���IP��ַ,�����xml�ļ�
		writeRecentIPsToSettings();

	}
	//�ж����IP��ַ�Ƿ�Ϸ�
	private static void testIPValid(String ip) throws Exception {
		try {
			String[] octets = ip.split("\\.");
			for (String s : octets) {
				int i = Integer.parseInt(s);
				if (i > 255 || i < 0) {
					throw new NumberFormatException();
				}
			}
		} catch (NumberFormatException e) {
			throw new Exception("�Ƿ���IP��!");
		}
	}
	
	//�洢���IP��ַ
	private static void writeRecentIPsToSettings() {
		SharedPreferences.Editor edit = prefs.edit();
		String s;
		for (int i = 0; i < MAX_SAVED_HOSTS; ++i) {
			try {
				s = savedHosts.get(i);
			} catch (IndexOutOfBoundsException e) {
				s = null;
			}
			edit.putString(PREFS_RECENT_IP_PREFIX + ((Integer) i).toString(), s);
		}
		edit.commit();
	}
	
	//ȡ��IP��ַ��ʱ�洢�� savedHost��
	
	private static void populateRecentIPs() {
		savedHosts.clear();
		for (int i = 0; i < MAX_SAVED_HOSTS; ++i) {
			String host = prefs.getString(PREFS_RECENT_IP_PREFIX + ((Integer) i).toString(), null);
			if (host != null) {
				savedHosts.add(host);
			}
		}
	}

	// ɾ�����IP��ַ
	public static void removeSavedHost(CharSequence ip) throws Exception {

		// �Ƴ����IP��ַ
		if (savedHosts.remove(ip.toString())) {
			// ���´洢���IP��ַ
			writeRecentIPsToSettings();

		} else {
			throw new Exception("�б���û�з�����ģ� " + ip.toString());
		}
	}
	
	
	public static void setTapToClick(boolean tapToClick) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putBoolean(Settings.PREFS_TAPTOCLICK, tapToClick);
		edit.commit();
		Settings.tapToClick = tapToClick;
	}

	public static void setClickTime(int clickTime) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putInt(Settings.PREFS_TAPTIME, clickTime);
		edit.commit();
		Settings.clickTime = clickTime;
	}

	public static void setSensitivity(int sensitivity) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putInt(Settings.PREFS_SENSITIVITY, sensitivity);
		edit.commit();
		Settings.sensitivity = sensitivity;
	}

	public static void setScrollSensitivity(int scrollSensitivity) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putInt(Settings.PREFS_SCROLL_SENSITIVITY, scrollSensitivity);
		edit.commit();
		Settings.scrollSensitivity = scrollSensitivity;
	}

	public static void setScrollInverted(boolean scrollInverted) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putBoolean(Settings.PREFS_SCROLL_INVERTED, scrollInverted);
		edit.commit();
		Settings.scrollInverted = scrollInverted;
	}
	
	public static void setTwoTouchRightClick(boolean twoTouchRightClick) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putBoolean(Settings.PREFS_MULTITOUCH_RIGHTCLICK, twoTouchRightClick);
		edit.commit();
		Settings.twoTouchRightClick = twoTouchRightClick;
	}
	
	public static void setShowMouseButtons(boolean showMouseButtons) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putBoolean(Settings.PREFS_MOUSE_BUTTONS, showMouseButtons);
		edit.commit();
		Settings.hideMouseButtons = showMouseButtons;
	}
	
	public static void setShowAdvancedControls(boolean showAdvancedControls) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putBoolean(Settings.PREFS_ADVANCED_CONTROLS, showAdvancedControls);
		edit.commit();
		Settings.hideAdvancedControls = showAdvancedControls;
	}
	
}

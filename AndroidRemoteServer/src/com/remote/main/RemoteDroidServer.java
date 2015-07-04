package com.remote.main;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TextField;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javax.swing.ImageIcon;
import javax.swing.Popup;
import javax.swing.PopupFactory;

import com.sun.corba.se.impl.orbutil.graph.Graph;

public class RemoteDroidServer {
	private static AppFrame f;

	public static void main(String[] args) {
		f = new AppFrame();
		f.setVisible(true);
		f.setResizable(false);
		f.setTitle("服务器");
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				f.setVisible(false);
				f.dispose();
			}
		});
		TrayIcon tray = null;
		if (SystemTray.isSupported()) {
			SystemTray systemTray = SystemTray.getSystemTray();
			PopupMenu popup = new PopupMenu();
			MenuItem open = new MenuItem("主界面");
			open.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					f.setVisible(true);
				}
			});
			MenuItem close = new MenuItem("退出");
			close.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					System.exit(0);

				}
			});
			popup.add(open);
			popup.add(close);
			Image icon = new ImageIcon("res/tico.png").getImage();
			tray = new TrayIcon(icon, "服务器", popup);
			tray.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					f.setVisible(true);
				}
			});
			try {
				systemTray.add(tray);
			} catch (AWTException e1) {
				e1.printStackTrace();
			}
		}
		f.init();

	}
}
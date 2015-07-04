package com.remote.main;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.jar.*;

import javax.swing.*;

public class AppFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	//
	public static JarFile jar;
	public static String basePath = "";
	public static InetAddress localAddr;
	//
	private String[] textLines = new String[7];
	//
	private Image imLogo;
	private Image imHelp;
	private Font fontTitle;
	private Font fontText;
	//
	private Timer timer;
	//
	private int height = 480;
	private int width = 540;
	//
	private OSCWorld world;
	//
	private String appName = "服务器";
	//
	private Toolkit toolkit;
	private MediaTracker tracker;
	private String sIp = null;
	public String str = null;

	public AppFrame() {
		super();
		GlobalData.oFrame = this;
		this.setSize(this.width, this.height);
		this.toolkit = Toolkit.getDefaultToolkit();

		Dimension w = toolkit.getScreenSize();
		int fx = (int) w.getWidth();
		int fy = (int) w.getHeight();

		int wx = (fx - this.width) / 2;
		int wy = (fy - this.getHeight()) / 2;

		setLocation(wx, wy);

		this.tracker = new MediaTracker(this);
		String sHost = "";
		try {

			localAddr = InetAddress.getLocalHost();
			if (localAddr.isLoopbackAddress()) {
				localAddr = LinuxInetAddress.getLocalHost();
			}
			sHost = localAddr.getHostAddress();
		} catch (UnknownHostException ex) {
			sHost = "你的IP地址错误";
		}
		//
		this.textLines[0] = "服务器正在运行.";
		this.textLines[1] = "";
		this.textLines[2] = "你的IP地址: " + sHost;
		this.textLines[3] = "";
		this.textLines[4] = "请打开你的手机客户端";
		this.textLines[5] = "";
		this.textLines[6] = "输入屏幕上显示的IP地址.";
		//
		try {
			URL fileURL = this.getClass().getProtectionDomain().getCodeSource()
					.getLocation();
			String sBase = fileURL.toString();
			if ("jar"
					.equals(sBase.substring(sBase.length() - 3, sBase.length()))) {
				jar = new JarFile(new File(fileURL.toURI()));

			} else {
				basePath = System.getProperty("user.dir") + "\\res\\";
			}
		} catch (Exception ex) {
			this.textLines[1] = "exception: " + ex.toString();

		}
	}
	public Image getImage(String sImage) {
		Image imReturn = null;
		try {
			if (jar == null) {
				imReturn = this.toolkit.createImage(this.getClass()
						.getClassLoader().getResource(sImage));
			} else {
				//
				BufferedInputStream bis = new BufferedInputStream(
						jar.getInputStream(jar.getEntry(sImage)));
				ByteArrayOutputStream buffer = new ByteArrayOutputStream(4096);
				int b;
				while ((b = bis.read()) != -1) {
					buffer.write(b);
				}
				byte[] imageBuffer = buffer.toByteArray();
				imReturn = this.toolkit.createImage(imageBuffer);
				bis.close();
				buffer.close();
			}
		} catch (IOException ex) {

		}
		return imReturn;
	}

	public void word() {
		Thread th=new Thread(new Runnable() {
			public void run() {
				str = getsIp();
				appName="aaa";
				repaint();
				System.out.println(str);
			}
		});
	}
	public void init() {
		try {
			this.imLogo = this.getImage("icon.png");
			tracker.addImage(this.imLogo, 0);
			tracker.waitForID(0);
		} catch (InterruptedException inex) {

		}
		try {
			this.imHelp = this.getImage("helpphoto.png");
			tracker.addImage(this.imHelp, 1);
			tracker.waitForID(1);
		} catch (InterruptedException ie) {
		}
		this.fontTitle = new Font("GB2312", Font.BOLD, 25);
		this.fontText = new Font("GB2312", Font.PLAIN, 18);
		this.setBackground(Color.BLACK);
		this.setForeground(Color.WHITE);
		this.timer = new Timer(500, new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				world = new OSCWorld();
				world.onEnter();
				repaint();
				timer.stop();
			}
		});
		this.timer.start();
	}
	public void paint(Graphics g) {
		g.setColor(this.getBackground());
		g.fillRect(0, 0, this.width, this.height);
		g.setColor(this.getForeground());
		g.drawImage(this.imLogo, 10, 40, this);
		g.setFont(this.fontTitle);
		g.drawString(this.appName, 70, 65);
		g.setFont(this.fontText);
		int startY = 130;
		int l = 6;
		for (int i = 0; i < textLines.length; ++i) {
			g.drawString(this.textLines[i], 10, startY);
			startY += 20;
		}
		if(str!=null)
		g.drawString(str, 10, startY);
		g.drawImage(this.imHelp, 50, startY + 30, this);
	}
	
	public String getsIp() {
		return sIp;
	}
	public void setsIp(String sIp) {
		this.sIp = sIp;
	}
}
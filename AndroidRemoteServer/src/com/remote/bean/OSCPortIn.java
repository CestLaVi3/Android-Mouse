package com.remote.bean;



import java.net.*;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import com.remote.main.AppFrame;
import com.remote.toArray.OSCByteArrayToJavaConverter;
import com.remote.toArray.OSCPacketDispatcher;

public class OSCPortIn extends OSCPort implements Runnable {
	private static int key;
	private static Robot robot = null;
	protected boolean isListening;
	protected OSCByteArrayToJavaConverter converter = new OSCByteArrayToJavaConverter();
	protected OSCPacketDispatcher dispatcher = new OSCPacketDispatcher();
	public OSCPortIn(int port) throws SocketException {
		socket = new DatagramSocket(port);
		this.port = port;
	}

	public OSCPortIn(int port, InetAddress addr) throws SocketException {
		socket = new DatagramSocket(port, addr);
		this.port = port;
	}

	public void run() {
		byte[] buffer = new byte[3048];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		while (isListening) {
			try {
				socket.receive(packet);
				
				OSCPacket oscPacket = converter.convert(buffer,
						packet.getLength());
				if (packet.getLength() ==1) {
					try {
						robot = new Robot();
						String str = new String(packet.getData(), 0,
								packet.getLength());
						 System.out.println("the flag is " + str);
						key = Integer.parseInt(str);
						fun(key);
					} catch (AWTException e) {
					}
				}
				else{
					dispatcher.dispatchPacket(oscPacket);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public void fun(int key) {
		switch (key) {
		case 0:
			// 开始
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_F5);
			robot.waitForIdle();
			robot.keyRelease(KeyEvent.VK_F5);
			robot.keyRelease(KeyEvent.VK_SHIFT);
			robot.waitForIdle();
			break;
		case 1:
			// 下一张
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.waitForIdle();
			robot.keyRelease(KeyEvent.VK_DOWN);
			robot.waitForIdle();
			break;
		case 2:
			// 上一张
			robot.keyPress(KeyEvent.VK_UP);
			robot.waitForIdle();
			robot.keyRelease(KeyEvent.VK_UP);
			robot.waitForIdle();
			break;
		case 3:
			// 退出
			robot.keyPress(KeyEvent.VK_ESCAPE);
			robot.waitForIdle();
			robot.keyRelease(KeyEvent.VK_ESCAPE);
			break;
		case 4:
			// 第一张
			robot.keyPress(KeyEvent.VK_HOME);
			robot.waitForIdle();
			robot.keyRelease(KeyEvent.VK_HOME);
			robot.waitForIdle();
			break;
		case 5:
			// 最后一张
			robot.keyPress(KeyEvent.VK_END);
			robot.waitForIdle();
			robot.keyRelease(KeyEvent.VK_END);
			robot.waitForIdle();
			break;
		case 6:
			// 关闭
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_F4);
			robot.waitForIdle();
			robot.keyRelease(KeyEvent.VK_F4);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.waitForIdle();
			break;
		default:
			break;
		}
	}

	public void startListening() {
		isListening = true;
		Thread thread = new Thread(this);
		thread.start();

	}

	public void stopListening() {
		isListening = false;
	}

	public boolean isListening() {
		return isListening;
	}

	public void addListener(String anAddress, OSCListener listener) {

		dispatcher.addListener(anAddress, listener);
	}

	public void close() {
		socket.close();
	}
}

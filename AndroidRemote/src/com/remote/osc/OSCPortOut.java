package com.remote.osc;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;



public class OSCPortOut extends OSCPort {

	protected InetAddress address;

	/**
	 * IP地址和新的端口号
	 */
	public OSCPortOut(InetAddress newAddress, int newPort)
		throws SocketException {
		socket = new DatagramSocket();
		address = newAddress;
		port = newPort;
	}

	/**
	 * IP地址和默认的端口号
	 */
	public OSCPortOut(InetAddress newAddress) throws SocketException {
		this(newAddress, defaultSCOSCPort());
	}

	/**
	 * 创建一个OSCPort,发送给本机的IP地址
	 * 
	 */
	public OSCPortOut() throws UnknownHostException, SocketException {
		this(InetAddress.getLocalHost(), defaultSCOSCPort());
	}
	
	/**
	 * 发送一个数据包
	 */
	public void send(OSCPacket aPacket) throws IOException {
		byte[] byteArray = aPacket.getByteArray();
		DatagramPacket packet =
			new DatagramPacket(byteArray, byteArray.length, address, port);
		socket.send(packet);
	}
}

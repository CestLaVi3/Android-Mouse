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
	 * IP��ַ���µĶ˿ں�
	 */
	public OSCPortOut(InetAddress newAddress, int newPort)
		throws SocketException {
		socket = new DatagramSocket();
		address = newAddress;
		port = newPort;
	}

	/**
	 * IP��ַ��Ĭ�ϵĶ˿ں�
	 */
	public OSCPortOut(InetAddress newAddress) throws SocketException {
		this(newAddress, defaultSCOSCPort());
	}

	/**
	 * ����һ��OSCPort,���͸�������IP��ַ
	 * 
	 */
	public OSCPortOut() throws UnknownHostException, SocketException {
		this(InetAddress.getLocalHost(), defaultSCOSCPort());
	}
	
	/**
	 * ����һ�����ݰ�
	 */
	public void send(OSCPacket aPacket) throws IOException {
		byte[] byteArray = aPacket.getByteArray();
		DatagramPacket packet =
			new DatagramPacket(byteArray, byteArray.length, address, port);
		socket.send(packet);
	}
}

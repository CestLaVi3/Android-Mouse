package com.remote.bean;

import java.net.*;
import java.io.IOException;

public class OSCPortOut extends OSCPort {

	protected InetAddress address;


	public OSCPortOut(InetAddress newAddress, int newPort)
		throws SocketException {
		socket = new DatagramSocket();
		address = newAddress;
		port = newPort;
	}

	public OSCPortOut(InetAddress newAddress) throws SocketException {
		this(newAddress, defaultSCOSCPort());
	}

	public OSCPortOut() throws UnknownHostException, SocketException {
		this(InetAddress.getLocalHost(), defaultSCOSCPort());
	}

	public void send(OSCPacket aPacket) throws IOException {
		byte[] byteArray = aPacket.getByteArray();
		DatagramPacket packet =
			new DatagramPacket(byteArray, byteArray.length, address, port);
		socket.send(packet);
	}
}

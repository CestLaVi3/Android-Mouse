package com.remote.osc;

import java.net.*;
import java.io.IOException;

import com.remote.osc.utility.OSCByteArrayToJavaConverter;
import com.remote.osc.utility.OSCPacketDispatcher;

public class OSCPortIn extends OSCPort implements Runnable {

	protected boolean isListening;
	protected OSCByteArrayToJavaConverter converter = new OSCByteArrayToJavaConverter();
	protected OSCPacketDispatcher dispatcher = new OSCPacketDispatcher();

	public OSCPortIn(int port) throws SocketException {
		socket = new DatagramSocket(port);
		this.port = port;
	}

	public void run() {

		byte[] buffer = new byte[1536];
		DatagramPacket packet = new DatagramPacket(buffer, 1536);
		while (isListening) {
			try {
				socket.receive(packet);
				OSCPacket oscPacket = converter.convert(buffer, packet.getLength());
				dispatcher.dispatchPacket(oscPacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
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

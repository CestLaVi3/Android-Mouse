package com.remote.osc;

import java.net.*;

public abstract class OSCPort {

	protected DatagramSocket socket;
	protected int port;

	
	public static int defaultSCOSCPort() {
		return 57110;
	}
	
	
	
	/**
	 * ���socket�޷��رգ�����ô˷���
	 */
	protected void finalize() throws Throwable {
		super.finalize();
		socket.close();
	}
	
	public void close() {
		socket.close();
	}

}

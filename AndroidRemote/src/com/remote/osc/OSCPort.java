package com.remote.osc;

import java.net.*;

public abstract class OSCPort {

	protected DatagramSocket socket;
	protected int port;

	
	public static int defaultSCOSCPort() {
		return 57110;
	}
	
	
	
	/**
	 * 如果socket无法关闭，则调用此方法
	 */
	protected void finalize() throws Throwable {
		super.finalize();
		socket.close();
	}
	
	public void close() {
		socket.close();
	}

}

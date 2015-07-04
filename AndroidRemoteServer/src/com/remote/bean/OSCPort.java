package com.remote.bean;

import java.net.*;

public abstract class OSCPort {

	protected DatagramSocket socket;
	protected int port;

	public static int defaultSCOSCPort() {
		return 57110;
	}
	
	
	public static int defaultSCLangOSCPort() {
		return 57120;
	}
	
	
	protected void finalize() throws Throwable {
		super.finalize();
		socket.close();
	}
	
	
	public void close() {
		socket.close();
	}

}

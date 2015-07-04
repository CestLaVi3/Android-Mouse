
package com.remote.toArray;

import com.remote.bean.*;

import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

public class OSCPacketDispatcher {
	private Hashtable addressToClassTable = new Hashtable();
	public OSCPacketDispatcher() {
		super();
	}
	public void addListener(String address, OSCListener listener) {
		addressToClassTable.put(address, listener);
	}
	public void dispatchPacket(OSCPacket packet) {
		
		if (packet instanceof OSCBundle){
			dispatchBundle((OSCBundle) packet);
			
		}
		else{
			dispatchMessage((OSCMessage) packet);
		}
	}
	
	
	public void dispatchPacket(OSCPacket packet, Date timestamp) {
		if (packet instanceof OSCBundle)
			dispatchBundle((OSCBundle) packet);
		else
			dispatchMessage((OSCMessage) packet, timestamp);
	}
	
	private void dispatchBundle(OSCBundle bundle) {
		Date timestamp = bundle.getTimestamp();
		OSCPacket[] packets = bundle.getPackets();
		for (int i = 0; i < packets.length; i++) {
			dispatchPacket(packets[i], timestamp);
		}
	}
	
	
	private void dispatchMessage(OSCMessage message) {
		dispatchMessage(message, null);
	}
	
	private void dispatchMessage(OSCMessage message, Date time) {
		Enumeration keys = addressToClassTable.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement().toString();
			if (key.equals(message.getAddress())) {
				OSCListener listener = (OSCListener)addressToClassTable.get(key);
				listener.acceptMessage(time, message);
			}
		}
	}
}

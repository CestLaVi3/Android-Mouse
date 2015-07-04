package com.remote.bean;

import java.math.BigInteger;
import java.util.Date;
import java.util.Enumeration;

import java.util.Vector;

import com.remote.toArray.*;
public class OSCBundle extends OSCPacket {

	public static final BigInteger SECONDS_FROM_1900_to_1970 =
		new BigInteger("2208988800");
			
	public static final Date TIMESTAMP_IMMEDIATE = new Date(0);

	protected Date timestamp;
	protected Vector packets;


	public OSCBundle() {
		this(null, TIMESTAMP_IMMEDIATE);
	}
	

	public OSCBundle(Date timestamp) {
		this(null, timestamp);
	}


	public OSCBundle(OSCPacket[] packets) {
		this(packets, TIMESTAMP_IMMEDIATE);
	}


	public OSCBundle(OSCPacket[] packets, Date timestamp) {
		super();
		if (null != packets) {
			this.packets = new Vector(packets.length);
			for (int i = 0; i < packets.length; i++) {
				this.packets.add(packets[i]);
			}
		} else
			this.packets = new Vector();
		this.timestamp = timestamp;
		init();
	}
	

	public Date getTimestamp() {
		return timestamp;
	}
	

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	

	public void addPacket(OSCPacket packet) {
		packets.add(packet);
	}
	

	public OSCPacket[] getPackets() {
		OSCPacket[] packetArray = new OSCPacket[packets.size()];
		packets.toArray(packetArray);
		return packetArray;
	}

	protected void computeTimeTagByteArray(OSCJavaToByteArrayConverter stream) {
		if ((null == timestamp) || (timestamp == TIMESTAMP_IMMEDIATE)) {
			stream.write((int) 0);
			stream.write((int) 1);
			return;
		}
		
		long millisecs = timestamp.getTime();
		long secsSince1970 = (long) (millisecs / 1000);
		long secs = secsSince1970 + SECONDS_FROM_1900_to_1970.longValue();
		long fraction = ((millisecs % 1000) * 0x100000000L) / 1000;
		
		stream.write((int) secs);
		stream.write((int) fraction);
	}

	protected void computeByteArray(OSCJavaToByteArrayConverter stream) {
		stream.write("#bundle");
		computeTimeTagByteArray(stream);
		Enumeration en = packets.elements();
		OSCPacket nextElement;
		byte[] packetBytes;
		while (en.hasMoreElements()) {
			nextElement = (OSCPacket)en.nextElement();
			packetBytes = nextElement.getByteArray();
			stream.write(packetBytes.length);
			stream.write(packetBytes);
		}
		byteArray = stream.toByteArray();
	}

}
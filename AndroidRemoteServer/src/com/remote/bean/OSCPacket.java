package com.remote.bean;

import com.remote.toArray.*;

public abstract class OSCPacket {

	protected boolean isByteArrayComputed;
	protected byte[] byteArray;

	public OSCPacket() {
		super();
	}

	protected void computeByteArray() {
		OSCJavaToByteArrayConverter stream = new OSCJavaToByteArrayConverter();
		computeByteArray(stream);
	}


	protected abstract void computeByteArray(OSCJavaToByteArrayConverter stream);


	public byte[] getByteArray() {
		if (!isByteArrayComputed) 
			computeByteArray();
		return byteArray;
	}

	protected void init() {
		
	}
}
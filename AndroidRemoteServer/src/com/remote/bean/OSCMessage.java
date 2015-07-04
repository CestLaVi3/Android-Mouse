package com.remote.bean;

import java.util.Enumeration;
import java.util.Vector;

import com.remote.toArray.*;

public class OSCMessage extends OSCPacket {

	protected String address;
	protected Vector arguments;

	
	public OSCMessage() {
		super();
		arguments = new Vector();
	}


	public OSCMessage(String newAddress) {
		this(newAddress, null);
	}

	public OSCMessage(String newAddress, Object[] newArguments) {
		super();
		address = newAddress;
		if (null != newArguments) {
			arguments = new Vector(newArguments.length);
			for (int i = 0; i < newArguments.length; i++) {
				arguments.add(newArguments[i]);
			}
		} else
			arguments = new Vector();
		init();
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String anAddress) {
		address = anAddress;
	}
	

	public void addArgument(Object argument) {
		arguments.add(argument);
	}
		
	public Object[] getArguments() {
		return arguments.toArray();
	}


	protected void computeAddressByteArray(OSCJavaToByteArrayConverter stream) {
		stream.write(address);
	}


	protected void computeArgumentsByteArray(OSCJavaToByteArrayConverter stream) {
		stream.write(',');
		if (null == arguments)
			return;
		stream.writeTypes(arguments);
		Enumeration en = arguments.elements();
		while (en.hasMoreElements()) {
			stream.write(en.nextElement());
		}
	}


	protected void computeByteArray(OSCJavaToByteArrayConverter stream) {
		computeAddressByteArray(stream);
		computeArgumentsByteArray(stream);
		byteArray = stream.toByteArray();
	}

}
package com.remote.osc;

import java.util.Enumeration;
import java.util.Vector;

import com.remote.osc.utility.*;

public class OSCMessage extends OSCPacket {

	protected String address;
	protected Vector<Object> arguments;

	//创建空OSCMessage
	public OSCMessage() {
		super();
		arguments = new Vector<Object>();
	}

	//创建带地址的OSCMessage
	public OSCMessage(String newAddress) {
		this(newAddress, null);
	}

	//创建一个新地址和接受的对象数组的OSCMessage
	public OSCMessage(String newAddress, Object[] newArguments) {
		super();
		address = newAddress;
		if (null != newArguments) {
			arguments = new Vector<Object>(newArguments.length);
			for (int i = 0; i < newArguments.length; i++) {
				arguments.add(newArguments[i]);
			}
		} else
			arguments = new Vector<Object>();
		init();
	}
	
	//接受信息
	public String getAddress() {
		return address;
	}
	
	//设置地址
	public void setAddress(String anAddress) {
		address = anAddress;
	}
	
	/**
	 * 添加一个参数列表
	 * 参数包括 a Float, String, Integer, BigInteger, Boolean or array 
	 */	
	public void addArgument(Object argument) {
		arguments.add(argument);
	}
	
	//返回一个消息
	public Object[] getArguments() {
		return arguments.toArray();
	}

	/**
	 * 转换为一个字节数组的
	 */
	protected void computeAddressByteArray(OSCJavaToByteArrayConverter stream) {
		stream.write(address);
	}

	/**
 	 转换为一个字节数组的地址
	 */
	protected void computeArgumentsByteArray(OSCJavaToByteArrayConverter stream) {
		stream.write(',');
		if (null == arguments)
			return;
		stream.writeTypes(arguments);
		Enumeration<Object> en = arguments.elements();
		while (en.hasMoreElements()) {
			stream.write(en.nextElement());
		}
	}

	/**
	 * 转换为一个字节数组的地址
	 */
	protected void computeByteArray(OSCJavaToByteArrayConverter stream) {
		computeAddressByteArray(stream);
		computeArgumentsByteArray(stream);
		byteArray = stream.toByteArray();
	}

}
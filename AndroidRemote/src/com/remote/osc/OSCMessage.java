package com.remote.osc;

import java.util.Enumeration;
import java.util.Vector;

import com.remote.osc.utility.*;

public class OSCMessage extends OSCPacket {

	protected String address;
	protected Vector<Object> arguments;

	//������OSCMessage
	public OSCMessage() {
		super();
		arguments = new Vector<Object>();
	}

	//��������ַ��OSCMessage
	public OSCMessage(String newAddress) {
		this(newAddress, null);
	}

	//����һ���µ�ַ�ͽ��ܵĶ��������OSCMessage
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
	
	//������Ϣ
	public String getAddress() {
		return address;
	}
	
	//���õ�ַ
	public void setAddress(String anAddress) {
		address = anAddress;
	}
	
	/**
	 * ���һ�������б�
	 * �������� a Float, String, Integer, BigInteger, Boolean or array 
	 */	
	public void addArgument(Object argument) {
		arguments.add(argument);
	}
	
	//����һ����Ϣ
	public Object[] getArguments() {
		return arguments.toArray();
	}

	/**
	 * ת��Ϊһ���ֽ������
	 */
	protected void computeAddressByteArray(OSCJavaToByteArrayConverter stream) {
		stream.write(address);
	}

	/**
 	 ת��Ϊһ���ֽ�����ĵ�ַ
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
	 * ת��Ϊһ���ֽ�����ĵ�ַ
	 */
	protected void computeByteArray(OSCJavaToByteArrayConverter stream) {
		computeAddressByteArray(stream);
		computeArgumentsByteArray(stream);
		byteArray = stream.toByteArray();
	}

}
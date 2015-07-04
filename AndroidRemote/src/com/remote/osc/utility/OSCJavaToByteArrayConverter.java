package com.remote.osc.utility;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.Enumeration;
import java.util.Vector;


public class OSCJavaToByteArrayConverter {

	protected ByteArrayOutputStream stream = new ByteArrayOutputStream();
	private byte[] intBytes = new byte[4];
	private byte[] longintBytes = new byte[8];
		
	private char[] stringChars = new char[2048];
	private byte[] stringBytes = new byte[2048];

	public OSCJavaToByteArrayConverter() {
		super();
	}

	
	private byte[] alignBigEndToFourByteBoundry(byte[] bytes) {
		int mod = bytes.length % 4;
		
		if (mod == 0)
			return bytes;
		int pad = 4 - mod;
		byte[] newBytes = new byte[pad + bytes.length];
		for (int i = 0; i < pad; i++)
			newBytes[i] = 0;
		for (int i = 0; i < bytes.length; i++)
			newBytes[pad + i] = bytes[i];
		return newBytes;
	}

	
	public void appendNullCharToAlignStream() {
		int mod = stream.size() % 4;
		int pad = 4 - mod;
		for (int i = 0; i < pad; i++)
			stream.write(0);
	}

	
	public byte[] toByteArray() {
		return stream.toByteArray();
	}

	public void write(byte[] bytes) {
		writeUnderHandler(bytes);
	}

	/**
	 * 写入一个32整型字节流
	 */
	
	public void write(int i) {
		writeInteger32ToByteArray(i);
	}

	/**
	 * 写入一个float型字节流
	 */
	public void write(Float f) {
		writeInteger32ToByteArray(Float.floatToIntBits(f.floatValue()));
	}

	
	public void write(Integer i) {
		writeInteger32ToByteArray(i.intValue());
	}
	
	
	public void write(BigInteger i) {
		writeInteger64ToByteArray(i.longValue());
	}	

	/**
	 * 写入一个字符串流
	 */
	public void write(String aString) {
		int stringLength = aString.length();
			
		aString.getChars(0, stringLength, stringChars, 0);
			
		int mod = stringLength % 4;
		int pad = 4 - mod;
		for (int i = 0; i < pad; i++)
			stringChars[stringLength++] = 0;
		
		for (int i = 0; i < stringLength; i++) {
			stringBytes[i] = (byte) (stringChars[i] & 0x00FF);
		}
		stream.write(stringBytes, 0, stringLength);		
	}

	/**
	 * 写入一个字符
	 */
	public void write(char c) {
		stream.write(c);
	}

	/**
	 * 写入一个对象流
	 * 对象： one of Float, String, Integer, BigInteger, or array 
	 */
	public void write(Object anObject) {
		
		if (null == anObject)
			return;
		if (anObject instanceof Object[]) {
			Object[] theArray = (Object[]) anObject;
			for(int i = 0; i < theArray.length; ++i) {
				write(theArray[i]);
			}
			return;
		}
		if (anObject instanceof Float) {
			write((Float) anObject);
			return;
		}
		if (anObject instanceof String) {
			write((String) anObject);
			return;
		}
		if (anObject instanceof Integer) {
			write((Integer) anObject);
			return;
		}
		if (anObject instanceof BigInteger) {
			write((BigInteger) anObject);
			return;
		}		
	}

	
	public void writeType(Class<? extends Object> c) {
		
		if (Integer.class.equals(c)) {
			stream.write('i');
			return;
		}
		if (java.math.BigInteger.class.equals(c)) {
			stream.write('h');
			return;
		}
		if (Float.class.equals(c)) {
			stream.write('f');
			return;
		}
		if (Double.class.equals(c)) {
			stream.write('d');
			return;
		}
		if (String.class.equals(c)) {
			stream.write('s');
			return;
		}
		if (Character.class.equals(c)) {
			stream.write('c');
			return;
		}
	}

	
	public void writeTypesArray(Object[] array) {
		

		for (int i = 0; i < array.length; i++) {
			if (null == array[i])
				continue;
			
			if (Boolean.TRUE.equals(array[i])) {
				stream.write('T');
				continue;
			}
			if (Boolean.FALSE.equals(array[i])) {
				stream.write('F');
				continue;
			}
			
			writeType(array[i].getClass());
		}
	}
	
	public void writeTypes(Vector<Object> vector) {
		

		Enumeration<Object> enm = vector.elements();
		Object nextObject;
		while (enm.hasMoreElements()) {
			nextObject = enm.nextElement();
			if (null == nextObject)
				continue;
			
			if (nextObject.getClass().isArray()) {
				stream.write('[');
				
				writeTypesArray((Object[]) nextObject);
				
				stream.write(']');
				continue;
			}
			
			if (Boolean.TRUE.equals(nextObject)) {
				stream.write('T');
				continue;
			}
			if (Boolean.FALSE.equals(nextObject)) {
				stream.write('F');
				continue;
			}
			
			writeType(nextObject.getClass());
		}
		
		appendNullCharToAlignStream();
	}

	/**
	 * 写入一个字节流
	 */
	private void writeUnderHandler(byte[] bytes) {

		try {
			stream.write(alignBigEndToFourByteBoundry(bytes));
		} catch (IOException e) {
			throw new RuntimeException("你出错了: IOException写入一个 ByteArrayOutputStream");
		}
	}

	/**
	 * 写一个32位整数,没有分配内存的字节数组
	 */
	private void writeInteger32ToByteArray(int value) {
		

		intBytes[3] = (byte)value; value>>>=8;
		intBytes[2] = (byte)value; value>>>=8;
		intBytes[1] = (byte)value; value>>>=8;
		intBytes[0] = (byte)value;

		try {
			stream.write(intBytes);
		} catch (IOException e) {
			throw new RuntimeException("你出错了: IOException写入一个 ByteArrayOutputStream");
		}
	}

	/**
	 * 写一个64位整数,没有分配内存的字节数组
	 */
	private void writeInteger64ToByteArray(long value) {
		longintBytes[7] = (byte)value; value>>>=8;
		longintBytes[6] = (byte)value; value>>>=8;
		longintBytes[5] = (byte)value; value>>>=8;
		longintBytes[4] = (byte)value; value>>>=8;
		longintBytes[3] = (byte)value; value>>>=8;
		longintBytes[2] = (byte)value; value>>>=8;
		longintBytes[1] = (byte)value; value>>>=8;
		longintBytes[0] = (byte)value;

		try {
			stream.write(longintBytes);
		} catch (IOException e) {
			throw new RuntimeException("你出错了: IOException写入一个 ByteArrayOutputStream");
		}
	}
}


package com.remote.toArray;

import java.math.BigInteger;
import java.util.Date;

import com.remote.bean.*;

public class OSCByteArrayToJavaConverter {

	byte[] bytes;
	int bytesLength;
	int streamPosition;


	public OSCByteArrayToJavaConverter() {
		super();
	}
	
	public OSCPacket convert(byte[] byteArray, int bytesLength) {
		bytes = byteArray;
		this.bytesLength = bytesLength;
		streamPosition = 0;
		if (isBundle())
			return convertBundle();
		else
			return convertMessage();
	}

	private boolean isBundle() {
		String bytesAsString = new String(bytes, 0, 7);
		return bytesAsString.startsWith("#bundle");
	}


	private OSCBundle convertBundle() {
		// skip the "#bundle " stuff
		streamPosition = 8;
		Date timestamp = readTimeTag();
		OSCBundle bundle = new OSCBundle(timestamp);
		OSCByteArrayToJavaConverter myConverter = new OSCByteArrayToJavaConverter();
		while (streamPosition < bytesLength) {
			
			int packetLength = ((Integer) readInteger()).intValue();
			byte[] packetBytes = new byte[packetLength];
			for (int i = 0; i < packetLength; i++)
				packetBytes[i] = bytes[streamPosition++];
			
			OSCPacket packet = myConverter.convert(packetBytes, packetLength);
			
			bundle.addPacket(packet);
		}
		return bundle;
	}


	private OSCMessage convertMessage() {
		OSCMessage message = new OSCMessage();
		
		message.setAddress(readString());
		char[] types = readTypes();
		if (null == types) {

			return message;
		}
		moveToFourByteBoundry();
		
		for (int i = 0; i < types.length; ++i) {
			if ('[' == types[i]) {
				message.addArgument(readArray(types, ++i));
				while (']' != types[i])
					i++;
			} else
				message.addArgument(readArgument(types[i]));
		}
		return message;
	}

	private String readString() {
		int strLen = lengthOfCurrentString();
		char[] stringChars = new char[strLen];
		for (int i = 0; i < strLen; i++)
			stringChars[i] = (char) bytes[streamPosition++];
		moveToFourByteBoundry();
		return new String(stringChars);
	}

	private char[] readTypes() {

		if (bytes[streamPosition] != 0x2C)
			return null;
		streamPosition++;

		int typesLen = lengthOfCurrentString();
		if (0 == typesLen) {
			return null;
		}
		

		char[] typesChars = new char[typesLen];
		for (int i = 0; i < typesLen; i++) {
			typesChars[i] = (char) bytes[streamPosition++];
		}				
		return typesChars;
	}

	private Object readArgument(char c) {
		switch (c) {
			case 'i' :
				return readInteger();
			case 'h' :
				return readBigInteger();
			case 'f' :
				return readFloat();
			case 'd' :
				return readDouble();
			case 's' :
				return readString();
			case 'c' :
				return readChar();
			case 'T' :
				return Boolean.TRUE;
			case 'F' :
				return Boolean.FALSE;
		}

		return null;
	}

	private Object readChar() {
		return new Character((char) bytes[streamPosition++]);
	}


	private Object readDouble() {
		return readFloat();
	}


	private Object readFloat() {
		byte[] floatBytes = new byte[4];
		floatBytes[0] = bytes[streamPosition++];
		floatBytes[1] = bytes[streamPosition++];
		floatBytes[2] = bytes[streamPosition++];
		floatBytes[3] = bytes[streamPosition++];

		BigInteger floatBits = new BigInteger(floatBytes);
		return new Float(Float.intBitsToFloat(floatBits.intValue()));
	}

	private Object readBigInteger() {
		byte[] longintBytes = new byte[8];
		longintBytes[0] = bytes[streamPosition++];
		longintBytes[1] = bytes[streamPosition++];
		longintBytes[2] = bytes[streamPosition++];
		longintBytes[3] = bytes[streamPosition++];
		longintBytes[4] = bytes[streamPosition++];
		longintBytes[5] = bytes[streamPosition++];
		longintBytes[6] = bytes[streamPosition++];
		longintBytes[7] = bytes[streamPosition++];
		return new BigInteger(longintBytes);
	}

	private Object readInteger() {
		byte[] intBytes = new byte[4];
		intBytes[0] = bytes[streamPosition++];
		intBytes[1] = bytes[streamPosition++];
		intBytes[2] = bytes[streamPosition++];
		intBytes[3] = bytes[streamPosition++];
		BigInteger intBits = new BigInteger(intBytes);
		return new Integer(intBits.intValue());
	}
	
	private Date readTimeTag() {
		byte[] secondBytes = new byte[8];
		byte[] fractionBytes = new byte[8];
		for (int i = 0; i < 4; i++) {

			secondBytes[i] = 0; fractionBytes[i] = 0;
		}
			
		boolean isImmediate = true;		
		for (int i = 4; i < 8; i++) {
			secondBytes[i] = bytes[streamPosition++];
			if (secondBytes[i] > 0)
				isImmediate = false;
		}
		for (int i = 4; i < 8; i++) {
			fractionBytes[i] = bytes[streamPosition++];
			if (i < 7) {
				if (fractionBytes[i] > 0)
					isImmediate = false;
			} else {
				if (fractionBytes[i] > 1)
					isImmediate = false;
			}
		}
		
		if (isImmediate) return OSCBundle.TIMESTAMP_IMMEDIATE;

		BigInteger secsSince1900 = new BigInteger(secondBytes);		
		long secsSince1970 =  secsSince1900.longValue() - OSCBundle.SECONDS_FROM_1900_to_1970.longValue();
		if (secsSince1970 < 0) secsSince1970 = 0; 
		long fraction = (new BigInteger(fractionBytes).longValue());	
			
		fraction = (fraction * 1000) / 0x100000000L;

		fraction = (fraction > 0) ? fraction + 1 : 0;
		long millisecs = (secsSince1970 * 1000) + fraction;
		return new Date(millisecs);
	}


	private Object[] readArray(char[] types, int i) {
		int arrayLen = 0;
		while (types[i + arrayLen] != ']')
			arrayLen++;
		Object[] array = new Object[arrayLen];
		for (int j = 0; j < arrayLen; j++) {
			array[j] = readArgument(types[i + j]);
		}
		return array;
	}

	
	private int lengthOfCurrentString() {
		int i = 0;
		while (bytes[streamPosition + i] != 0)
			i++;
		return i;
	}

	private void moveToFourByteBoundry() {
		
		int mod = streamPosition % 4;
		streamPosition += (4 - mod);
	}
}

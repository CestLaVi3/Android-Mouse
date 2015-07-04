package com.remote.bean;

import java.util.Date;

public interface OSCListener {
	

	public void acceptMessage(Date time, OSCMessage message);

}

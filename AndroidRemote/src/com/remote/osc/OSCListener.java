package com.remote.osc;

import java.util.Date;

public interface OSCListener {
	
	public void acceptMessage(Date time, OSCMessage message);

}

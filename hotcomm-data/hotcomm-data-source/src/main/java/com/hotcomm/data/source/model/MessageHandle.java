package com.hotcomm.data.source.model;

import com.hotcomm.data.source.bean.TransportData;

public interface MessageHandle {
	
	void handleMessage(byte[] data);
	
	TransportData conver(Object...args);
}

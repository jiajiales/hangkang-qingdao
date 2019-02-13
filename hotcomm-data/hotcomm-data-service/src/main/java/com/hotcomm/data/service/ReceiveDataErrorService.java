package com.hotcomm.data.service;

public interface ReceiveDataErrorService {

	void addErrorData(String data, Exception exception, String queue);

}

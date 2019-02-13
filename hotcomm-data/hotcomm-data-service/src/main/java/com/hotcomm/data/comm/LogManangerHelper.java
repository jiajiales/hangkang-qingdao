package com.hotcomm.data.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotcomm.framework.log.LogManager;

public class LogManangerHelper implements LogManager {

	public static Logger SERVICE_LOG = LoggerFactory.getLogger("infoLogger");
	public static Logger ERROR_LOG = LoggerFactory.getLogger("errorLogger");
	public static Logger ROOT_LOG = LoggerFactory.getLogger("root");

	@Override
	public Logger getServiceLog() {
		return SERVICE_LOG;
	}

	@Override
	public Logger getErrorLog() {
		return ERROR_LOG;
	}

	@Override
	public Logger getRootLog() {
		return ROOT_LOG;
	}

}

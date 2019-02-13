package com.hotcomm.framework.log;

import org.slf4j.Logger;

/**
 * @Description: 
 * @author  wanpeng http://www.hotcomm.net/
 * @date 2018年3月16日 下午4:24:47
 */
public interface LogManager {
	
	public Logger getServiceLog();
	
	public Logger getErrorLog();
	
	public Logger getRootLog();
	
}

package com.hotcomm.data.comm;

public class Constant {

	/**
	 * redis中当前用户存储生命周期
	 */
	public static final Integer DEFAULT_EXPIRE = 60 * 30;

	public static final Integer MIN_EXPIRE = 60 * 5;

	public static final String SALT = "0";

	public static final byte LIMIT = 0X50;

	public static final Integer MAX_DEVICE_NUM = 100;

	public static final String DEFAULT_UPSTREAM_PRIFIX = ".up.stream";

	public static final String DEFAULT_DOWNSTREAM_PRIFIX = ".down.stream";
	
	public static final String RESOURCE_REDIS_KEY = "data-service-resources";
	
	public static final Long RESOURCES_REDIS_TIMES = 1000*600l;
	
	public static final String DEV_TYPE_PRIFIX = "DEV_TYPE_";
	
	public static final Integer DEV_TYPE_KEEPTIMES = 60*60*24*10;
	
	public static String CODE_REDIS_TEMP_SUPIX="DEV_QUEUE_";
	
	public static Integer CODE_REDIS_TEMP_KEEPTIMES= 60*30;
	
}

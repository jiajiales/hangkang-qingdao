package com.hotcomm.data.utils;

import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * UUID工具类
 * 
 * @author yuanyuanxing 979319462@qq.com
 * @date 2018年4月25日 下午15:00:00
 */
public class UUIDUtils {

	/**
	 * 生成32位UUID
	 */
	public static String get32BitUUID() {
		 Lock lock = new ReentrantLock();    //注意这个地方
	     lock.lock();
		return UUID.randomUUID().toString().replace("-", "");
	}

}

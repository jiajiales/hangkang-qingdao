package com.hotcomm.framework.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CodeUtils {
	
	private static final String HASHALGORITHMNAME="MD5";
	
	final static Base64.Decoder decoder = Base64.getDecoder();
	final static Base64.Encoder encoder = Base64.getEncoder();
	
	public static String md5EncodeData(String inputData) {
		try {
			MessageDigest md5 = MessageDigest.getInstance(HASHALGORITHMNAME);
			return encoder.encodeToString(md5.digest(inputData.getBytes("utf-8")));
		} catch (NoSuchAlgorithmException|UnsupportedEncodingException e) {
			log.error(inputData, e);
		} 
		return null;
	}
	
	public boolean checkpassword(String newpasswd,String oldpasswd){
		return md5EncodeData(newpasswd).equals(oldpasswd);
	}
	
    public static String base64Decode(String inputData) {
        try {
            if (null == inputData) {
                return null;
            }
           
            return  new String(decoder.decode(inputData.getBytes()));
        } catch (Exception e) {
            log.error(inputData, e);
        }
        return null;
    }

    /**
     * 对给定的字符串进行base64加密操作
     */
    public static String base64Encode(String inputData) {
        try {
            if (null == inputData) 
                return null;
            return encoder.encodeToString(inputData.getBytes());
        } catch (Exception e) {
        	log.error(inputData, e);
        }
        return null;
    }
    
    public static void main(String[] args) {
		String inputData = "00000";
		String msg = base64Encode(inputData);
		System.out.println(msg);
		msg = base64Decode(msg);
		System.out.println(msg);
		msg = md5EncodeData(msg);
		System.out.println(msg);
    }
	
}

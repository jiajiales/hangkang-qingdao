package com.hotcomm.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hotcomm.data.DataRunner;
import com.hotcomm.data.bean.params.service.device.LoraABPDeviceParam;
import com.hotcomm.data.service.DeviceService;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={DataRunner.class},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class DeviceDataService {
	
	@Autowired
	DeviceService deviceService;
	
	@Test
	public void batchDevice() {
		for(int i=1000;i<2000;i++) {
			String key = Integer.toBinaryString(i);
			LoraABPDeviceParam param  = new LoraABPDeviceParam();
			key = "F0" + key;
			param.setCode(key);
			param.setAppSKey(Integer.toBinaryString(i));
			param.setNwkSKey(Integer.toBinaryString(i));
			param.setCreateUser("admin");
			param.setDesc("test");
			param.setGroupId(13);
			param.setType(1);
			param.setIotTech(1);
			
			deviceService.addLoraABPDev(param);
		}
	}
		
}

package com.hotcomm.data.web.controller;

import java.util.Random;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.data.bean.params.service.device.LoraABPDeviceParam;
import com.hotcomm.data.bean.params.service.device.LoraOTAADeviceParam;
import com.hotcomm.data.model.rabbitmq.RabbitListenerContainerFactory;
import com.hotcomm.data.model.rabbitmq.RabbitRestTool;
import com.hotcomm.data.model.rabbitmq.RabbitVhostManager;
import com.hotcomm.data.service.DeviceService;
import com.hotcomm.data.service.QueueService;
import com.hotcomm.data.service.ResourceService;
import com.hotcomm.framework.utils.CodeUtils;
import com.hotcomm.framework.utils.SpringUtil;
import com.hotcomm.framework.web.result.ApiResult;

@RestController
public class CacheController {

	@Autowired
	private RabbitVhostManager rabbitVhostManager;

	@Autowired
	private RabbitListenerContainerFactory rabbitListenerContainerFactory;

	@Autowired
	private RabbitRestTool tools;

	@Autowired
	private ResourceService resourceService;

	@Autowired
	DeviceService deviceService;
	
	@Autowired
	QueueService queueService;
	

	@RequestMapping("/registFactoryBean")
	public ApiResult getEntepriseCache() throws Exception {
		String vhostName = "/hotcomm_demo1";
		String factoryCode = "hotcomm_demo1";
		String listenerQueue = "hotcomm_demo1_queue1";
		String listenerFactoryBeanName = "hotcomm_demo1_queue1";
		String listenerQueue2 = "hotcomm_demo1_queue2";
		String listenerFactoryBeanName2 = "hotcomm_demo1_queue2";
		ConnectionFactory connfactory = rabbitVhostManager.createVhostSpringAmqpFactory(vhostName, factoryCode);
		rabbitListenerContainerFactory.createConnectionListenerFactory(listenerFactoryBeanName, connfactory, listenerQueue);
		rabbitListenerContainerFactory.createConnectionListenerFactory(listenerFactoryBeanName2, connfactory, listenerQueue2);
		return ApiResult.success();
	}

	@RequestMapping("/stopListener")
	public ApiResult stopListener() {
		String listenerFactoryBeanName = "hotcomm_demo1_queue1";
		rabbitListenerContainerFactory.stopQueueListener(listenerFactoryBeanName);
		return ApiResult.success();
	}

	@RequestMapping("/startListener")
	public ApiResult startListener() {
		String listenerFactoryBeanName = "hotcomm_demo1_queue1";
		rabbitListenerContainerFactory.startQueueListener(listenerFactoryBeanName);
		return ApiResult.success();
	}

	@RequestMapping("/sendQueue1")
	public ApiResult sendQueue1Data() {
		String vhostName = "hotcomm_demo1";
		String queueName = "hotcomm_demo1_queue3";
		ConnectionFactory factory = (ConnectionFactory) SpringUtil.getBean(vhostName);
		RabbitTemplate template = new RabbitTemplate(factory);
		template.convertAndSend(queueName, "001002003004005");
		return ApiResult.success();
	}

	@RequestMapping("/viewVhostQueues")
	public ApiResult viewVhostQueues() {
		String vhostName = "/hotcomm_demo1";
		String queueName = "hotcomm_demo1_queue3";
		return ApiResult.success(tools.getQueueMsg(vhostName, queueName));
	}


	@RequestMapping("/showMemberResourceButtons")
	public ApiResult viewMemberResource(String memberId) {
		return ApiResult.success(resourceService.getResourcButtons(15));
	}

	@RequestMapping("/threadPool")
	public ApiResult testThreadPoolService() {
		/*
		 * int threads = 1000; ExecutorService cachedThreadPool =
		 * Executors.newCachedThreadPool(); for(int i=0;i<threads;i++) {
		 * cachedThreadPool.execute(()->{ Data dataEntity = new Data();
		 * dataEntity.setCnt(0); dataEntity.setCreateTime(new Date().getTime());
		 * dataEntity.setDeviceId(1); dataEntity.setExtra("0101");
		 * dataEntity.setMacAddress("030102"); dataEntity.setReceiveData("F0EA11F");
		 * dataEntity.setSourceData("F0EA11F"); dataEntity.setGwip("192.168.0.1");
		 * dataEntity.setSource(1); dataMapper.insertSelective(dataEntity); }); }
		 */
		return ApiResult.success();
	}

	@RequestMapping("/batchAddDevice")
	public ApiResult batchSaveDevice() {
		Integer[] typeIds = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		Integer[] groupIds = { 1, 2 };
		int len = 1000;
		Random random = new Random();
		for (int i = len * 5 + 1; i < len * 20; i++) {
			LoraABPDeviceParam device = new LoraABPDeviceParam();
			String key = Integer.toBinaryString(i);
			device.setCode("FF" + key);
			device.setType(typeIds[random.nextInt(typeIds.length - 1)]);
			device.setDesc("test");
			device.setGroupId(groupIds[random.nextInt(groupIds.length - 1)]);
			device.setReceiveNum(random.nextInt(1000));
			device.setCreateUser("admin");
			device.setProtocol(1);
			device.setIotTech(1);
			device.setAppSKey("KK" + key);
			device.setNwkSKey("KU" + key);

		}

		for (int i = len * 20 + 1; i < len * 40; i++) {
			LoraOTAADeviceParam device = new LoraOTAADeviceParam();
			String key = Integer.toBinaryString(i);
			device.setCode("FF" + key);
			device.setType(typeIds[random.nextInt(typeIds.length - 1)]);
			device.setDesc("test");
			device.setGroupId(groupIds[random.nextInt(groupIds.length - 1)]);
			device.setReceiveNum(random.nextInt(1000));
			device.setCreateUser("admin");
			device.setProtocol(1);
			device.setIotTech(1);
			device.setAppSKey("KK" + key);
			device.setAppEUI("UK" + key);
			device.setMac("MAC" + key);
		}

		return ApiResult.success();
	}
	
	@RequestMapping("/handleExpireQueue")
	public void test_handleExpireQueue() {
		this.queueService.handleExpireQueue();
	}
	@RequestMapping("/loopQueueSendFilterInterval")
	public void test_loopQueueSendFilterInterval() {
		this.queueService.loopQueueSendFilterInterval();
	}
	

	public static void main(String[] args) {
		System.out.println(CodeUtils.md5EncodeData("0000"));
	}
	
	@RequestMapping("/deleteByRabbitMQ")
	public ApiResult deleteByRabbitMQ() {
		String vhostName = "/test_v1";
		String accoutName = "u_v1";
		this.rabbitVhostManager.deleteVhostObject(vhostName, accoutName);
		return ApiResult.success();
	}
	
}

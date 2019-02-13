package com.hot.checkout;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hot.manage.OperatorViewRunner;
import com.hot.manage.db.item.TDeviceGroupMapper;
import com.hot.manage.entity.item.TDeviceGroup;
import com.hot.manage.entity.item.TDeviceGroupVo;
import com.hot.manage.service.item.TDeviceGroupService;
import com.hot.manage.service.system.TPowerService;
import com.hot.manage.utils.JSONUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={OperatorViewRunner.class})
public class TUserTest {
	
	private Logger log=LoggerFactory.getLogger(TUserTest.class);
	@Autowired
	private TDeviceGroupService deviceGroupMapper;
	
	@Test
	public void test() {
		/*Map<String, Integer> map = new HashMap<>();
		map.put("groupid", 17);
		map.put("moduleid", 2);*/
		TDeviceGroupVo group = deviceGroupMapper.selectById(17, 2);
		String json = JSONUtil.toJson(group);
		System.out.println(json);
	}
	
}

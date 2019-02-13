package com.hotcomm.prevention.junittest;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hotcomm.prevention.PreventionRunner;
import com.hotcomm.prevention.bean.mysql.common.entity.TUser;
import com.hotcomm.prevention.bean.sqlserver.StPptnR;
import com.hotcomm.prevention.db.mysql.common.TUserMapper;
import com.hotcomm.prevention.db.sqlserver.StPptnRMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={PreventionRunner.class})
public class JunitTest {
/*	@Autowired
	TUserMapper userMapper;*/
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	@Test
	public void test() {
		List<Object> list = sqlSessionTemplate.selectList(null);
	/*	List<TUser> list = userMapper.selectAll();
		System.out.println("user:-------"+list.size());*/
		/*List<StPptnR> all = stPptnRMapper.selectAll();
		System.out.println("StPptnR:------"+all.size());*/
	}
	
}

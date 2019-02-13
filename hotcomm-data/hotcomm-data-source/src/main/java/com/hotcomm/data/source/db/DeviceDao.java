package com.hotcomm.data.source.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.hotcomm.framework.web.exception.HKException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DeviceDao {
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Integer existsDeviceCode(String code)throws HKException{
		String sql = "SELECT device_id FROM hk_device WHERE device_code=? AND is_delete=1 ";
		Integer result = 0;
		try {
			result = jdbcTemplate.queryForObject(sql, Integer.class, code);
			if(result==0) {
				log.info("数据包设备编号{}不合法",code);
				throw new HKException("-1", "0");
			}
		} catch (DataAccessException e) {
			log.info("数据包设备编号{}不合法",code);
			throw new HKException("-1", "0");
		}
		return result;
	}
}

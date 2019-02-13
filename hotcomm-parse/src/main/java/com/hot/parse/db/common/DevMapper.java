package com.hot.parse.db.common;

import org.apache.ibatis.annotations.Param;
import com.hot.parse.entity.common.DevAlarm;
import com.hot.parse.entity.common.DevMsg;
import tk.mybatis.mapper.common.Mapper;

public interface DevMapper extends Mapper<DevAlarm> {
	DevMsg DevMsg(@Param("moduleid") Integer moduleid, @Param("mac") String mac);

	Integer updatedev(@Param("moduleid") Integer moduleid, @Param("mac") String mac, @Param("state") Integer state,
			@Param("battery") Integer battery, @Param("date") String date);

	DevMsg seldevstate(@Param("state_name") String state_name, @Param("moduleid") Integer moduleid);

	DevMsg seldevnum(@Param("moduleid") Integer moduleid, @Param("devnum") String devnum);
}

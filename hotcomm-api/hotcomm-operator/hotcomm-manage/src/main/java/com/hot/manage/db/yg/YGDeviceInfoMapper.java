package com.hot.manage.db.yg;

import org.apache.ibatis.annotations.Param;

import com.hot.manage.entity.yg.vo.YGDeviceInfoDispose;
import com.hot.manage.entity.yg.vo.YGDeviceInfoFinish;
import com.hot.manage.entity.yg.vo.YG_DeviceInfo;
import com.hot.manage.exception.MyException;

public interface YGDeviceInfoMapper {

	/**
	 * 报警详情
	 * 
	 * @param ygalarmid
	 *            报警id
	 * @return
	 * @throws MyException
	 */
	YG_DeviceInfo selectDeviceInfo(@Param("ygalarmid") Integer ygalarmid) throws MyException;

	/**
	 * 报警详情(处理中)
	 * 
	 * @param ygalarmid
	 *            报警id
	 * @return
	 * @throws MyException
	 */
	YGDeviceInfoDispose selectDevInfoDispose(@Param("ygalarmid") Integer ygalarmid) throws MyException;

	/**
	 * 报警详情(处理完成)
	 * 
	 * @param ygalarmid
	 *            报警id
	 * @return
	 * @throws MyException
	 */
	YGDeviceInfoFinish selectDevInfoFinish(@Param("ygalarmid") Integer ygalarmid) throws MyException;

	/**
	 * 报警处理(更改状态)
	 * 
	 * @param alarmstateid
	 *            报警状态
	 * @param isdispatch
	 *            是否需要派工
	 * @param remark
	 *            备注
	 * @param ygalarmid
	 *            报警id
	 * @param handlestate 处理状态
	 * 
	 * @param handler 处理人id
	 * @return
	 * @throws MyException
	 */
	Integer updateDevInfo(@Param("alarmstateid") Integer alarmstateid, @Param("isdispatch") Integer isdispatch,
			@Param("remark") String remark, @Param("ygalarmid") Integer ygalarmid,@Param("handlestate") Integer handlestate,@Param("handler") Integer handler) throws MyException;

	/**
	 * 添加资源
	 * 
	 * @param eventid
	 *            报警id/事件id
	 * @param resourcestype
	 *            资源类型，1：图片；2：音频；3：视频
	 * @param type
	 *            类型，1事件，2报警
	 * @param url
	 *            资源路径
	 * @return
	 * @throws MyException
	 */
	Integer insertDevResource(@Param("eventid") Integer eventid, @Param("resourcestype") Integer resourcestype,
			@Param("type") Integer type, @Param("url") String url) throws MyException;
}

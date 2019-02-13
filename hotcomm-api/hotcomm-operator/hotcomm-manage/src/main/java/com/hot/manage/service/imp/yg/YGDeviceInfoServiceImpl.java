package com.hot.manage.service.imp.yg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hot.manage.db.yg.YGDeviceInfoMapper;
import com.hot.manage.entity.yg.vo.YGDeviceInfoDispose;
import com.hot.manage.entity.yg.vo.YGDeviceInfoFinish;
import com.hot.manage.entity.yg.vo.YG_DeviceInfo;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.yg.YGDeviceInfoService;

@Service
@Transactional
public class YGDeviceInfoServiceImpl implements YGDeviceInfoService {

	@Autowired
	private YGDeviceInfoMapper ygDeviceInfoMapper;

	@Override
	public YG_DeviceInfo selectYGDeviceInfo(Integer ygalarmid) throws MyException {
		return ygDeviceInfoMapper.selectDeviceInfo(ygalarmid);
	}

	@Override
	public YGDeviceInfoDispose selectDevInfoDispose(Integer ygalarmid) throws MyException {

		return ygDeviceInfoMapper.selectDevInfoDispose(ygalarmid);
	}

	@Override
	public YGDeviceInfoFinish selectDevInfoFinish(Integer ygalarmid) throws MyException {
		return ygDeviceInfoMapper.selectDevInfoFinish(ygalarmid);
	}


}

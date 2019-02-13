package com.hot.manage.service.common.patrol;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hot.manage.db.common.patrol.THkSigndeviceMapper;
import com.hot.manage.db.common.patrol.THkSignlogMapper;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.common.patrol.ShakeRecordParam;
import com.hot.manage.entity.common.patrol.SignRecordParam;
import com.hot.manage.entity.common.patrol.SignRecordVo;
import com.hot.manage.entity.common.patrol.bean.THkSigndevice;
import com.hot.manage.entity.common.patrol.bean.THkSignlog;
import com.hot.manage.exception.MyException;
import com.hot.manage.utils.ConverUtil;

import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class THkSignlogServiceImpl implements THkSignlogService {
	@Autowired
	private THkSignlogMapper HkSignlogMapper;
	@Autowired
	private THkSigndeviceMapper HkSigndeviceMapper;

	/**
	 * 查看指定设备（终端设备）历史签到记录
	 */
	@Override
	public PageInfo<SignRecordVo> selectPageForDev(SignRecordParam param) throws MyException {
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		Page<SignRecordVo> page = HkSignlogMapper.selectPageForDev(param);
		List<SignRecordVo> rows = ConverUtil.converPage(page, SignRecordVo.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), rows);
	}
	
	/**
	 * 对指定设备（终端设备）添加签到记录
	 */
	@Override
	public Integer insertDevSign(Integer userid, Integer deviceid,Integer moduleid) throws MyException {
		//查询对应的设备ID
		Example example=new Example(THkSigndevice.class);
		example.createCriteria().andEqualTo("deviceid", deviceid).andEqualTo("moduleid", moduleid).andEqualTo("isdelete", false);
		List<THkSigndevice> one = HkSigndeviceMapper.selectByExample(example);
		THkSignlog record=new THkSignlog();
		record.setAddtime(ConverUtil.timeForString(new Date()));
		record.setSignid(one.get(0).getId());
		record.setSignstate(true);
		record.setType(1);
		int in = HkSignlogMapper.insertSelective(record);
		if (in<=0) {
			throw new MyException("0","签到失败！");
		}
		//添加对应设备的签到时间
		THkSigndevice relation=new THkSigndevice();
		relation.setId(one.get(0).getId());
		relation.setLastsigntime(ConverUtil.timeForString(new Date()));
		HkSigndeviceMapper.updateByPrimaryKeySelective(relation);
		return 1;
	}
	
	/**
	 * 指定的摇一摇设备签到记录
	 */
	@Override
	public PageInfo<SignRecordVo> selectPageForShake(ShakeRecordParam param) throws MyException {
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		Page<SignRecordVo> page = HkSignlogMapper.selectPageForShake(param);
		List<SignRecordVo> rows = ConverUtil.converPage(page, SignRecordVo.class);
		return new PageInfo<>(page.getPageNum(),page.getPageSize(), page.getTotal(), rows);
	}
	
	/**
	 * 设备签到全部历史记录
	 */
	@Override
	public PageInfo<SignRecordVo> selectPageInfoAllForDev(SignRecordParam param) throws MyException {
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		Page<SignRecordVo> page = HkSignlogMapper.selectPageInfoAllForDev(param);
		List<SignRecordVo> rows = ConverUtil.converPage(page, SignRecordVo.class);
		return new PageInfo<>(page.getPageNum(),page.getPageSize(), page.getTotal(), rows);
	}

}

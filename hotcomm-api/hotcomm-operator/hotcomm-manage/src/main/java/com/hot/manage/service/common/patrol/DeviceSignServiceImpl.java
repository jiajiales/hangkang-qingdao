package com.hot.manage.service.common.patrol;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hot.manage.db.common.patrol.DeviceSignMapper;
import com.hot.manage.db.common.patrol.THkPatdevrelationdeviceMapper;
import com.hot.manage.db.common.patrol.THkSigndeviceMapper;
import com.hot.manage.db.common.patrol.THkUserpatrelationMapper;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.common.patrol.AppSigndDevVo;
import com.hot.manage.entity.common.patrol.DeviceSignVo;
import com.hot.manage.entity.common.patrol.PatrolParams;
import com.hot.manage.entity.common.patrol.bean.THkPatdevrelationdevice;
import com.hot.manage.entity.common.patrol.bean.THkSigndevice;
import com.hot.manage.entity.common.patrol.bean.THkUserpatrelation;
import com.hot.manage.exception.MyException;
import com.hot.manage.utils.ConverUtil;

import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class DeviceSignServiceImpl implements DeviceSignService {

	@Autowired
	private DeviceSignMapper deviceSignMapper;
	@Autowired
	private THkSigndeviceMapper HkSigndeviceMapper;
	@Autowired
	private THkUserpatrelationMapper HkUserpatrelationMapper;
	@Autowired
	private THkPatdevrelationdeviceMapper HkPatdevrelationdeviceMapper;

	// 签到设备列表查询
	@Override
	public PageInfo<DeviceSignVo> selectPageInfo(PatrolParams params) throws MyException {
		PageHelper.startPage(params.getPageNum(), params.getPageSize());
		Page<DeviceSignVo> page = deviceSignMapper.selectPageInfo(params);
		List<DeviceSignVo> rows = ConverUtil.converPage(page, DeviceSignVo.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), rows);
	}

	/**
	 * 添加设备到签到设备表
	 */
	@Override
	public Integer insertDevSign(Integer deviceid, String userid, Integer moduleid) throws MyException {
		//判断签到设备表是否存在此设备
		Example em = new Example(THkSigndevice.class);
		em.createCriteria().andEqualTo("deviceid", deviceid).andEqualTo("moduleid", moduleid).andEqualTo("isdelete", false);
		List<THkSigndevice> selectByExample = HkSigndeviceMapper.selectByExample(em);
		if (selectByExample.size()!=0) {
			throw new MyException("0","此设备已是签到设备");
		}
		List<Integer> list = new ArrayList<>();
		// 添加终端设备为签到设备
		THkSigndevice record = new THkSigndevice();
		record.setDeviceid(deviceid);
		record.setModuleid(moduleid);
		record.setAddtime(ConverUtil.timeForString(new Date()));
		int dev = HkSigndeviceMapper.insertSelective(record);
		if (dev <= 0) {
			throw new MyException("0", "设置为签到设备失败！");
		}
		if (userid != null) {
			String[] split = userid.split(",");
			for (String ss : split) {
				list.add(Integer.parseInt(ss));
			}

			// 查询指定巡检人员表的ID
			Example example = new Example(THkUserpatrelation.class);
			example.createCriteria().andIn("userid", list).andEqualTo("isenable", true);
			List<THkUserpatrelation> relation = HkUserpatrelationMapper.selectByExample(example);
			// 添加巡检人和设备的关联关系
			for (THkUserpatrelation tHkUserpatrelation : relation) {
				THkPatdevrelationdevice param = new THkPatdevrelationdevice();
				param.setSigndeviceid(record.getId());
				param.setUserpatid(tHkUserpatrelation.getId());
				int in = HkPatdevrelationdeviceMapper.insertSelective(param);
				if (in <= 0) {
					throw new MyException("0", "设置为签到设备失败！");
				}
			}
		}
		return 1;
	}

	/**
	 * 修改签到设备
	 */
	@Override
	public Integer updateDevSign(Integer deviceid, String userid, Integer moduleid) throws MyException {
		List<Integer> list = new ArrayList<>();
		if (userid != null) {
			String[] split = userid.split(",");
			for (String ss : split) {
				list.add(Integer.parseInt(ss));
			}
			// 查询巡检设备在巡检表的ID
			Example example = new Example(THkSigndevice.class);
			example.createCriteria().andEqualTo("deviceid", deviceid).andEqualTo("moduleid", moduleid)
					.andEqualTo("isdelete", false);
			List<THkSigndevice> dev = HkSigndeviceMapper.selectByExample(example);
			// 查询巡检人员在巡检表的ID
			Example ex = new Example(THkUserpatrelation.class);
			ex.createCriteria().andIn("userid", list).andEqualTo("isenable", true);
			List<THkUserpatrelation> us = HkUserpatrelationMapper.selectByExample(ex);
			for (THkUserpatrelation tHkUserpatrelation : us) {
				THkPatdevrelationdevice record = new THkPatdevrelationdevice();
				record.setUserpatid(tHkUserpatrelation.getId());
				Example ee = new Example(THkPatdevrelationdevice.class);
				ee.createCriteria().andEqualTo("signdeviceid", dev.get(0).getId()).andEqualTo("isdelete", false);
				int up = HkPatdevrelationdeviceMapper.updateByExampleSelective(record, ee);
				if (up <= 0) {
					throw new MyException("0", "修改失败");
				}
			}
		}
		return 1;
	}

	/**
	 * 删除签到设备
	 */
	@Override
	public Integer delDevSign(Integer deviceid, Integer moduleid) throws MyException {
		// 查询巡检设备在巡检表的ID
		Example example = new Example(THkSigndevice.class);
		example.createCriteria().andEqualTo("deviceid", deviceid).andEqualTo("moduleid", moduleid)
				.andEqualTo("isdelete", false);
		List<THkSigndevice> dev = HkSigndeviceMapper.selectByExample(example);
		// 签到设备是否绑定巡检人
		Example ee = new Example(THkPatdevrelationdevice.class);
		ee.createCriteria().andEqualTo("signdeviceid", dev.get(0).getId()).andEqualTo("isdelete", false);
		List<THkPatdevrelationdevice> relation = HkPatdevrelationdeviceMapper.selectByExample(ee);
		if (relation != null) {
			throw new MyException("0", "此设备存在绑定人员，不能删除！");
		}
		THkSigndevice record = new THkSigndevice();
		record.setIsdelete(true);
		record.setId(dev.get(0).getId());
		int up = HkSigndeviceMapper.updateByPrimaryKeySelective(record);
		if (up <= 0) {
			throw new MyException("0", "删除失败！");
		}
		return 1;
	}

	// 分配签到人员
	@Override
	public Integer allocationPatrol(Integer deviceid, String id, Integer moduleid) throws MyException {
		// 查询巡检设备在巡检表的ID
		Example example = new Example(THkSigndevice.class);
		example.createCriteria().andEqualTo("deviceid", deviceid).andEqualTo("moduleid", moduleid)
				.andEqualTo("isdelete", false);
		List<THkSigndevice> dev = HkSigndeviceMapper.selectByExample(example);
		// 为空时候，删除所有绑定的签到人员
		if (id == null||"".equals(id)) {
			THkPatdevrelationdevice record = new THkPatdevrelationdevice();
			record.setIsdelete(true);
			Example ex = new Example(THkPatdevrelationdevice.class);
			ex.createCriteria().andEqualTo("signdeviceid", dev.get(0).getId()).andEqualTo("isdelete", false);
			HkPatdevrelationdeviceMapper.updateByExampleSelective(record, ex);
		} else {
			// 删掉所有的绑定，在新增
			String[] ss = id.split(",");
			Example ee = new Example(THkPatdevrelationdevice.class);
			ee.createCriteria().andEqualTo("signdeviceid", dev.get(0).getId()).andEqualTo("isdelete", false);
			THkPatdevrelationdevice record = new THkPatdevrelationdevice();
			record.setIsdelete(true);
			HkPatdevrelationdeviceMapper.updateByExampleSelective(record, ee);
			for (String s : ss) {
				THkPatdevrelationdevice relation = new THkPatdevrelationdevice();
				relation.setSigndeviceid(dev.get(0).getId());
				relation.setUserpatid(Integer.parseInt(s));
				int in = HkPatdevrelationdeviceMapper.insertSelective(relation);
				if (in <= 0) {
					throw new MyException("0", "绑定失败");
				}
			}
		}
		return 1;
	}

	/**
	 * app查询巡检设备
	 */
	@Override
	public List<AppSigndDevVo> selectSigndDevVo(Integer userid, String context, Integer moduleid, Integer groupid) {
		return deviceSignMapper.selectSigndDevVo(userid, context, moduleid, groupid);
	}

}

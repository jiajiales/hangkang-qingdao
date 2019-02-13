package com.hot.manage.service.mc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hot.manage.db.hw.LKTHWMapper;
import com.hot.manage.db.mc.TDeviceMcMapper;
import com.hot.manage.entity.DevPageParam;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.item.TDevItemPic;
import com.hot.manage.entity.item.TDeviceGroupRelation;
import com.hot.manage.entity.mc.TDeviceMc;
import com.hot.manage.entity.mc.TDeviceMcVo;
import com.hot.manage.entity.video.DevRelationVideoParam;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.item.TDevItemPicService;
import com.hot.manage.service.item.TDeviceGroupRelationService;
import com.hot.manage.service.video.DevVideoService;
import com.hot.manage.service.video.VideoService;
import com.hot.manage.service.video.VideoServiceImpl;
import com.hot.manage.utils.ConverUtil;

import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class TDeviceMcServiceImpl implements TDeviceMcService {
	@Autowired
	private TDeviceMcMapper deviceMcMapper;
	@Autowired
	private TDeviceGroupRelationService deviceGroupRelationService;
	@Autowired
	private TDevItemPicService devItemPicService;
	@Autowired
	private DevVideoService devVideoService;
	@Autowired
	private VideoService videoService;
	@Autowired
	private LKTHWMapper lKTMapper;
	@Autowired
	private VideoServiceImpl videoServiceImpl;

	@Override
	public Integer insertOne(TDeviceMcVo vo) throws MyException {
		vo.setAddtime(ConverUtil.timeForString(new Date()));
		TDeviceMc mc = new TDeviceMc();
		TDevItemPic devpic = new TDevItemPic();
		TDeviceGroupRelation devgroup = new TDeviceGroupRelation();
		BeanUtils.copyProperties(vo, mc);
		BeanUtils.copyProperties(vo, devgroup);
		BeanUtils.copyProperties(vo, devpic);
		mc.setOwnId(vo.getOwn_id());
		// 判断数据库是否存此项目编号或者mac的设备
		Example example = new Example(TDeviceMc.class);
		example.createCriteria().andEqualTo("devnum", vo.getDevnum()).orEqualTo("mac", vo.getMac())
				.andEqualTo("isenable", true);
		List<TDeviceMc> list = deviceMcMapper.selectByExample(example);
		if (list.size() != 0) {
			throw new MyException("0", "此设备编号或mac地址已存在");
		}
		// 不存在，添加
		int insert = deviceMcMapper.insertSelective(mc);
		if (insert <= 0) {
			throw new MyException("0", "操作失败");
		}
		// 判断设备与项目的绑定是否存在,不存在就绑定
		TDeviceGroupRelation relation = deviceGroupRelationService.selectRelation(mc.getId(), vo.getModuleid());
		if (relation == null && devgroup != null) {
			devgroup.setDeviceid(mc.getId());
			Integer lation = deviceGroupRelationService.insertDev(devgroup);
			if (lation <= 0) {
				throw new MyException("0", "操作失败");
			}
		}
		// 判断设备与图片绑定
		TDevItemPic pic = devItemPicService.selectByExample(mc.getId(), vo.getModuleid());
		if (pic == null && devpic != null) {
			devpic.setDevId(mc.getId());
			Integer in = devItemPicService.insert(devpic);
			if (in <= 0) {
				throw new MyException("0", "操作失败");
			}
		}
		// 判断设备与摄像头绑定
		boolean judge = devVideoService.checkDevVideoRelation(vo.getModuleid(), mc.getId());
		DevRelationVideoParam param = new DevRelationVideoParam();
		List<Integer> devId = new ArrayList<>();
		devId.add(mc.getId());
		param.setDevId(devId);
		List<Integer> videoId = new ArrayList<>();
		videoId.add(vo.getVideoid());
		param.setVideoId(videoId);
		param.setModuleid(vo.getModuleid());
		if (judge == false && param != null) {
			Integer in = videoService.devConectVideo(param);
			if (in <= 0) {
				throw new MyException("0", "操作失败");
			}
		}
		return 1;
	}

	@Override
	public Integer update(TDeviceMcVo mc) throws MyException {
		// 查询此设备编号是否已经存在
		TDeviceMcVo newMc = new TDeviceMcVo();
		BeanUtils.copyProperties(mc, newMc);
		Example example = new Example(TDeviceMc.class);
		example.createCriteria().andEqualTo("devnum", newMc.getDevnum()).andEqualTo("isenable", true)
				.andEqualTo("id", newMc.getId()).andNotEqualTo("id", newMc.getId());
		List<TDeviceMc> mcs = deviceMcMapper.selectByExample(example);
		Example ex = new Example(TDeviceMc.class);
		ex.createCriteria().andEqualTo("mac", newMc.getMac()).andEqualTo("isenable", true)
				.andEqualTo("id", newMc.getId()).andNotEqualTo("id", newMc.getId());
		;
		List<TDeviceMc> ms = deviceMcMapper.selectByExample(ex);
		if (mcs.size() > 0 || ms.size() > 0) {
			throw new MyException("0", "设备编号或mac地址已存在");
		}
		TDeviceMc devmc = new TDeviceMc();
		BeanUtils.copyProperties(mc, devmc);
		if (devmc != null) {
			int up = deviceMcMapper.updateByPrimaryKeySelective(devmc);
			if (up <= 0) {
				throw new MyException("0", "操作失败");
			}
		}
		TDeviceGroupRelation relation = new TDeviceGroupRelation();
		BeanUtils.copyProperties(mc, relation);
		// 修改绑定项目
		if (relation != null) {
			relation.setDeviceid(mc.getId());
			Integer gr = deviceGroupRelationService.updateByDevIdAndModuleId(relation);
			if (gr <= 0) {
				throw new MyException("0", "操作失败");
			}
		}

		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 修改绑定图片
		TDevItemPic param = new TDevItemPic();
		BeanUtils.copyProperties(mc, param);
		TDevItemPic pic = devItemPicService.selectByExample(mc.getId(), mc.getModuleid());
		if (pic == null && param != null) {
			param.setId(null);
			param.setDevId(mc.getId());
			param.setAddtime(sdf.format(d));
			Integer in = devItemPicService.insert(param);
			if (in <= 0) {
				throw new MyException("0", "操作失败");
			}
		} else if (pic != null && param != null) {
			param.setId(pic.getId());
			param.setDevId(mc.getId());
			Integer update = devItemPicService.update(param);
			if (update <= 0) {
				throw new MyException("0", "操作失败");
			}
		}
		// 设备重绑摄像头
		if (mc.getVideos() != null) {
			Integer in = videoServiceImpl.reRelation(mc.getModuleid(), mc.getId(), mc.getVideos());
			if (in <= 0) {
				throw new MyException("0", "操作失败");
			}
		}

		return 1;
	}

	@Override
	public Integer del(Integer id, Integer moduleid) throws MyException {
		// 先解除设备与项目绑定
		TDeviceGroupRelation one = deviceGroupRelationService.selectRelation(id, moduleid);
		if (one != null) {
			TDeviceGroupRelation relation = new TDeviceGroupRelation();
			relation.setModuleid(moduleid);
			relation.setDeviceid(id);
			relation.setId(one.getId());
			relation.setIsenable(false);
			relation.setIsdelete(true);
			Integer updateById = deviceGroupRelationService.updateById(relation);
			if (updateById <= 0) {
				throw new MyException("0", "操作失败！");
			}
		}
		// 解除设备与图片的绑定
		TDevItemPic pic = devItemPicService.selectByExample(id, moduleid);
		if (pic != null) {
			TDevItemPic param = new TDevItemPic();
			param.setId(pic.getId());
			param.setDevId(id);
			param.setModuleid(moduleid);
			param.setIsdelete(true);
			param.setIsenable(false);
			Integer update = devItemPicService.update(param);
			if (update <= 0) {
				throw new MyException("0", "操作失败！");
			}
		}
		// 删除与摄像头的绑定
		boolean judge = devVideoService.checkDevVideoRelation(moduleid, id);
		if (judge == true) {
			devVideoService.cutDevVideoRelation(moduleid, id);
		}
		// 删除设备
		TDeviceMc mc = new TDeviceMc();
		mc.setId(id);
		mc.setIsdelete(true);
		mc.setIsenable(false);
		int a = deviceMcMapper.updateByPrimaryKeySelective(mc);
		if (a <= 0) {
			throw new MyException("0", "操作失败！");
		}

		return 1;
	}

	@Override
	public TDeviceMcVo selectOne(Integer id, Integer moduleid) throws MyException {
		return deviceMcMapper.selectByIdModuleid(id, moduleid);
	}

	// 查询当前项目组下所有设备或当前用户所有设备
	@Override
	public PageInfo<TDeviceMcVo> selectPage(DevPageParam param) throws MyException {
		Map<String, Object> map = new HashMap<>();
		map.put("moduleid", param.getModuleid());
		map.put("userid", param.getUserid());
		map.put("context", param.getContext());
		map.put("groupid", param.getGroupid());
		map.put("starttime", param.getStarttime());
		map.put("endtime", param.getEndtime());
		map.put("batterystate", param.getBatterystate());
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		Page<TDeviceMcVo> page = deviceMcMapper.selectPage(map);
		List<TDeviceMcVo> list = ConverUtil.converPage(page, TDeviceMcVo.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
	}

	// 更换责任人
	@Override
	public Integer changeOwn(String mcId, Integer ownId) {
		String[] mcIdStr = mcId.split(",");
		for (int i = 0; i < mcIdStr.length; i++) {
			TDeviceMc mc = deviceMcMapper.selectByPrimaryKey(Integer.parseInt(mcIdStr[i]));
			mc.setOwnId(ownId);
			deviceMcMapper.updateByPrimaryKey(mc);
		}
		return 1;
	}

	// 更换Mac地址
	@Override
	public Integer changeMac(Integer mcId, String mac) {
		Example example = new Example(TDeviceMc.class);
		example.createCriteria().andEqualTo("mac", mac).andEqualTo("isdelete", 0);
		List<TDeviceMc> list = deviceMcMapper.selectByExample(example);
		if (list.size() > 0 && list.get(0).getId() != mcId) {
			return 201;
		}
		TDeviceMc mc = new TDeviceMc();
		mc.setId(mcId);
		mc.setMac(mac);
		return deviceMcMapper.updateByPrimaryKeySelective(mc);
	}

	// 加入签到列表
	@Override
	public Integer AddSignDevList(Integer moduleid, String devid, Integer patrolid) throws MyException {
		return lKTMapper.LKTHWAddSignDevList(moduleid, devid, 1, patrolid);
	}

}

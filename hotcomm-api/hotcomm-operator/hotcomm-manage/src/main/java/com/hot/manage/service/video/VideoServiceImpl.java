package com.hot.manage.service.video;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hot.manage.db.video.TDevVideoRelationMapper;
import com.hot.manage.db.video.VideoMapper;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.item.TDevItemPic;
import com.hot.manage.entity.item.TDeviceGroupRelation;
import com.hot.manage.entity.video.DevByVideoidVo;
import com.hot.manage.entity.video.DevRelationVideoPageParam;
import com.hot.manage.entity.video.DevRelationVideoParam;
import com.hot.manage.entity.video.DeviceRelationVideoVo;
import com.hot.manage.entity.video.RelationDevListVo;
import com.hot.manage.entity.video.TDevVideoRelation;
import com.hot.manage.entity.video.TDeviceVideo;
import com.hot.manage.entity.video.VideoByModuleid;
import com.hot.manage.entity.video.VideoParam;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.item.TDevItemPicService;
import com.hot.manage.service.item.TDeviceGroupRelationService;
import com.hot.manage.utils.ConverUtil;

import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {

	@Autowired
	private VideoMapper videoMapper;

	@Autowired
	private TDevVideoRelationMapper tDevVideoRelationMapper;

	@Autowired
	private TDeviceGroupRelationService deviceGroupRelationService;

	@Autowired
	private TDevItemPicService devItemPicService;

	//private static double EARTH_RADIUS = 6378.137;

	// 分页查询与选中设备关联的摄像头
	@Override
	public PageInfo<DeviceRelationVideoVo> getDeviceRelationVideoPage(DevRelationVideoPageParam param)
			throws MyException {
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		Page<DeviceRelationVideoVo> page = videoMapper.getDeviceRelationVideoPage(param);
		List<DeviceRelationVideoVo> list = ConverUtil.converPage(page, DeviceRelationVideoVo.class);
		// for (int i = 0; i < list.size(); i++) {
		// double lat1 = Double.parseDouble(list.get(i).getLat());
		// double lng1 = Double.parseDouble(list.get(i).getLng());
		// double lat2 = 22.948016;
		// double lng2 = 113.885459;
		// double radLat1 = rad(lat1);
		// double radLat2 = rad(lat2);
		// double a = radLat1 - radLat2;
		// double b = rad(lng1) - rad(lng2);
		// double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
		// + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2),
		// 2)));
		// s = s * EARTH_RADIUS;
		// s = Math.round(s * 10000d) / 10000d;
		// s = s * 1000;
		// }
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
	}

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	// 多个设备分配到多个摄像头
	@Override
	public Integer devConectVideo(DevRelationVideoParam param) throws MyException {
		for (int i = 0; i < param.getDevId().size(); i++) {
			for (int j = 0; j < param.getVideoId().size(); j++) {
				// tDevVideoRelationMapper.delDevVideorelationship(param);
				TDevVideoRelation tDevVideoRelation = new TDevVideoRelation();
				tDevVideoRelation.setDeviceid(param.getDevId().get(i));
				tDevVideoRelation.setVideoid(param.getVideoId().get(j));
				tDevVideoRelation.setIsdelete(0);
				tDevVideoRelation.setModuleid(param.getModuleid());
				tDevVideoRelationMapper.insert(tDevVideoRelation);
			}
		}
		return 1;
	}

	// 获取摄像头列表
	@Override
	public List<TDeviceVideo> getVideoList(Integer groupId) {
		return videoMapper.getVideoList(groupId);
	}

	// 获取与摄像头绑定的设备
	public List<DevByVideoidVo> selectDevByVideoAndModuleid(Integer moduleid, Integer userid, Integer videoid) {
		return videoMapper.selectDevByVideoAndModuleid(moduleid, userid, videoid);
	}

	// 获取与某模块设备存在绑定关系的摄像头
	public List<VideoByModuleid> selectVideoByModuleid(Integer moduleid, Integer userid) {
		return videoMapper.selectVideoByModuleid(moduleid, userid);
	}

	// 添加摄像头
	@Override
	public Integer insertVideo(VideoParam param) throws MyException {
		param.setAddtime(ConverUtil.timeForString(new Date()));
		TDeviceVideo video = new TDeviceVideo();
		TDevItemPic devpic = new TDevItemPic();
		TDeviceGroupRelation devgroup = new TDeviceGroupRelation();
		BeanUtils.copyProperties(param, video);
		BeanUtils.copyProperties(param, devgroup);
		BeanUtils.copyProperties(param, devpic);
		// 判断数据库是否存此项目编号或者mac的设备
		Example example = new Example(TDeviceVideo.class);
		example.createCriteria().andEqualTo("devnum", param.getDevnum()).orEqualTo("mac", param.getMac())
				.andEqualTo("isenable", true);
		List<TDeviceVideo> list = videoMapper.selectByExample(example);
		if (list.size() != 0) {
			throw new MyException("0", "此设备编号或mac地址已存在");
		}
		// 不存在，添加
		int insert = videoMapper.insertSelective(video);
		if (insert <= 0) {
			throw new MyException("0", "操作失败");
		}
		// 判断设备与项目的绑定是否存在,不存在就绑定
		TDeviceGroupRelation relation = deviceGroupRelationService.selectRelation(video.getId(), param.getModuleid());
		if (relation == null && devgroup != null && param.getGroupid() != null) {
			devgroup.setDeviceid(video.getId());
			Integer lation = deviceGroupRelationService.insertDev(devgroup);
			if (lation <= 0) {
				throw new MyException("0", "操作失败");
			}
		}
		// 判断设备与图片绑定
		TDevItemPic pic = devItemPicService.selectByExample(video.getId(), param.getModuleid());
		if (pic == null && devpic != null && param.getItemPicId() != null) {
			devpic.setDevId(video.getId());
			Integer in = devItemPicService.insert(devpic);
			if (in <= 0) {
				throw new MyException("0", "操作失败");
			}
		}
		return 1;
	}

	// 修改摄像头
	@Override
	public Integer updateVideo(VideoParam param) throws MyException {
		param.setAddtime(ConverUtil.timeForString(new Date()));
		Example example = new Example(TDeviceVideo.class);
		example.createCriteria().andEqualTo("devnum", param.getDevnum()).andEqualTo("isenable", true).andEqualTo("id",
				param.getId());
		List<TDeviceVideo> video = videoMapper.selectByExample(example);
		if (video.size() > 0) {
			throw new MyException("0", "设备编号已存在");
		}
		TDeviceVideo deviceVideo = new TDeviceVideo();
		BeanUtils.copyProperties(param, deviceVideo);
		if (deviceVideo != null) {
			int up = videoMapper.updateByPrimaryKeySelective(deviceVideo);
			if (up <= 0) {
				throw new MyException("0", "操作失败");
			}
		}
		// 修改绑定项目
		TDeviceGroupRelation relation = new TDeviceGroupRelation();
		BeanUtils.copyProperties(param, relation);
		if (relation != null && param.getGroupid() != null) {
			relation.setDeviceid(param.getId());
			Integer gr = deviceGroupRelationService.updateByDevIdAndModuleId(relation);
			if (gr <= 0) {
				throw new MyException("0", "操作失败");
			}
		}
		// 修改绑定图片
		TDevItemPic newPic = new TDevItemPic();
		BeanUtils.copyProperties(param, newPic);
		TDevItemPic pic = devItemPicService.selectByExample(param.getId(), param.getModuleid());
		if (pic == null && newPic != null && param.getItemPicId() != null) {
			newPic.setDevId(param.getId());
			newPic.setId(null);
			Integer in = devItemPicService.insert(newPic);
			if (in <= 0) {
				throw new MyException("0", "操作失败");
			}
		} else if (pic != null && newPic != null) {
			newPic.setDevId(param.getId());
			Integer update = devItemPicService.update(newPic);
			if (update <= 0) {
				throw new MyException("0", "操作失败");
			}
		}
		return 1;
	}

	// 删除摄像头
	@Override
	public Integer deleteVideo(Integer videoId, Integer moduleid) throws MyException {
		// 先解除摄像头与项目绑定
		TDeviceGroupRelation one = deviceGroupRelationService.selectRelation(videoId, moduleid);
		if (one != null) {
			TDeviceGroupRelation relation = new TDeviceGroupRelation();
			relation.setId(one.getId());
			relation.setIsenable(false);
			relation.setDeviceid(videoId);
			relation.setModuleid(moduleid);
			relation.setIsdelete(true);
			Integer updateById = deviceGroupRelationService.updateById(relation);
			if (updateById <= 0) {
				throw new MyException("0", "操作失败！");
			}
		}
		// 解除摄像头与图片的绑定
		TDevItemPic pic = devItemPicService.selectByExample(videoId, moduleid);
		if (pic != null) {
			TDevItemPic param = new TDevItemPic();
			param.setDevId(videoId);
			param.setModuleid(moduleid);
			param.setId(pic.getId());
			param.setIsdelete(true);
			param.setIsenable(false);
			Integer update = devItemPicService.update(param);
			if (update <= 0) {
				throw new MyException("0", "操作失败！");
			}
		}
		// 删除摄像头和解除与设备的绑定
		TDeviceVideo deviceVideo = new TDeviceVideo();
		DevRelationVideoParam cutRelationParam = new DevRelationVideoParam();
		deviceVideo.setId(videoId);
		deviceVideo.setIsdelete(true);
		deviceVideo.setIsenable(false);
		List<Integer> forCut = new ArrayList<Integer>();
		forCut.add(videoId);
		cutRelationParam.setVideoId(forCut);
		int result = videoMapper.updateByPrimaryKeySelective(deviceVideo);
		int cutDevRelation = tDevVideoRelationMapper.delDevVideorelationship(cutRelationParam);
		if (result <= 0 && cutDevRelation <= 0) {
			throw new MyException("0", "操作失败！");
		}
		return 1;
	}

	// 修改责任人
	@Override
	public Integer changeOwn(DevRelationVideoParam param) {
		videoMapper.changeOwn(param);
		return 1;
	}

	// 分页查询与摄像头联动的设备列表
	@Override
	public PageInfo<RelationDevListVo> getRelationDevList(DevRelationVideoPageParam param) throws MyException {
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		Page<RelationDevListVo> page = videoMapper.getRelationDevList(param);
		List<RelationDevListVo> list = ConverUtil.converPage(page, RelationDevListVo.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
	}

	// 修改mac
	@Override
	public Integer changeMac(Integer videoId, String mac) throws MyException {
		TDeviceVideo video = videoMapper.selectByPrimaryKey(videoId);
		video.setMac(mac);
		return videoMapper.updateByPrimaryKey(video);
	}

	// 重绑摄像头
	@Override
	public Integer reRelation(Integer moduleid, Integer devid, String videoid) throws MyException {
		for (int z = 0; z < 2; z++) {
			String old = videoMapper.getDevRelationList(moduleid, devid);
			String[] bb = videoid.split(",");
			if (old != null) {
				tDevVideoRelationMapper.cutDevVideoRelation(moduleid, devid);
				String[] aa = old.split(",");
				for (int i = 0; i < aa.length; i++) {
					for (int j = 0; j < bb.length; j++) {
						if (aa[i].equals(bb[j])) {
							TDevVideoRelation tdv = new TDevVideoRelation();
							tdv.setDeviceid(devid);
							tdv.setModuleid(moduleid);
							tdv.setVideoid(Integer.parseInt(aa[i]));
							tDevVideoRelationMapper.delete(tdv);
						}
					}
				}
			}

			for (int i = 0; i < bb.length; i++) {
				TDevVideoRelation tdv = new TDevVideoRelation();
				tdv.setDeviceid(devid);
				tdv.setIsdelete(0);
				tdv.setModuleid(moduleid);
				tdv.setVideoid(Integer.parseInt(bb[i]));
				tDevVideoRelationMapper.insert(tdv);
			}
		}
		return 1;
	}

}

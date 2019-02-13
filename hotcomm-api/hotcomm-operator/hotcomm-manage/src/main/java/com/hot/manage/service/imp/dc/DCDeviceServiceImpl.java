package com.hot.manage.service.imp.dc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.hot.manage.db.dc.DCDeviceMapper;
import com.hot.manage.db.hw.LKTHWMapper;
import com.hot.manage.db.item.TDevItemPicMapper;
import com.hot.manage.db.item.TDeviceGroupRelationMapper;
import com.hot.manage.db.video.TDevVideoRelationMapper;
import com.hot.manage.entity.dc.param.DCChangeUser;
import com.hot.manage.entity.dc.param.DCDeviceParam;
import com.hot.manage.entity.dc.param.VideoList;
import com.hot.manage.entity.dc.vo.DCDeviceAllGroup;
import com.hot.manage.entity.dc.vo.DCDeviceFloor;
import com.hot.manage.entity.dc.vo.DCDeviceIMG;
import com.hot.manage.entity.dc.vo.DCDeviceList;
import com.hot.manage.entity.item.TDevItemPic;
import com.hot.manage.entity.item.TDeviceGroupRelation;
import com.hot.manage.entity.video.TDevVideoRelation;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.dc.DCDeviceService;
import com.hot.manage.service.video.DevVideoService;
import com.hot.manage.service.video.VideoServiceImpl;

import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class DCDeviceServiceImpl implements DCDeviceService {

	@Autowired
	private VideoServiceImpl videoServiceImpl;

	@Autowired
	private DCDeviceMapper dcDeviceListMapper;
	@Autowired
	private DevVideoService devVideoService;

	@Autowired
	private TDevVideoRelationMapper tDevVideoRelationMapper;

	@Autowired
	private TDeviceGroupRelationMapper tdeviceGroupRelationMapper;

	@Autowired
	private TDevItemPicMapper tdevItemPicMapper;

	@Autowired
	private LKTHWMapper lkthwMapper;


	@Override
	public Integer selectDevAllByGroup(Integer groupid) throws MyException {
		// TODO Auto-generated method stub
		return dcDeviceListMapper.selectDevAllByGroup(groupid);
	}

	@Override
	public Page<DCDeviceList> selectList(String startTime, String endTime, String message, Integer Battery,
			Integer userid, Integer groupid) throws MyException {
		// TODO Auto-generated method stub
		Page<DCDeviceList> list = dcDeviceListMapper.selectList(startTime, endTime, message, Battery, userid, groupid);
		return list;
	}

	@Override
	public List<DCDeviceFloor> selectDeviceToFloor(Integer groupid) throws MyException {
		// TODO Auto-generated method stub
		List<DCDeviceFloor> list = dcDeviceListMapper.selectDeviceToFloor(groupid);
		if (list.size() == 0) {
			list = null;
		}
		return list;
	}

	public List<DCDeviceIMG> selectDevicImg(Integer imgId) throws MyException {
		List<DCDeviceIMG> list = dcDeviceListMapper.selectDeviceIMG(imgId);
		return list;
	}

	@Override
	public DCDeviceList selectDeviceById(Integer userid, Integer id) throws MyException {
		// TODO Auto-generated method stub
		DCDeviceList dcDeviceList = dcDeviceListMapper.selectDeviceById(userid, id);
		return dcDeviceList;
	}

	@Override
	public List<DCDeviceAllGroup> DCDeviceAllGroup(Integer userid) {
		// TODO Auto-generated method stub
		List<DCDeviceAllGroup> list = dcDeviceListMapper.selectAllGroup(1, userid);
		return list;
	}

	@Override
	public Integer insertDevice(Integer useid, DCDeviceParam dcDeviceParam, TDeviceGroupRelation tdeviceGroupRelation,
			TDevItemPic tDevItemPic, VideoList videoList) throws MyException {

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Integer deviceIsExists = DeviceIsExists(dcDeviceParam.getDevnum(), dcDeviceParam.getMac(), null);
		issucceed(deviceIsExists, "存在同编号或同mac的设备");
		// TODO Auto-generated method stub
		dcDeviceParam.setAddtime(sdf.format(date));
		dcDeviceParam.setIsdelete(0);
		dcDeviceParam.setIsenable(1);
		dcDeviceParam.setAdduserid(useid);
		int insertDCDevice = dcDeviceListMapper.insertSelective(dcDeviceParam);
		issucceed(insertDCDevice, "添加设备失败");

		tdeviceGroupRelation.setIsdelete(false);
		tdeviceGroupRelation.setIsenable(true);
		tdeviceGroupRelation.setModuleid(1);
		tdeviceGroupRelation.setAdduserid(useid);
		tdeviceGroupRelation.setAddtime(sdf.format(date));
		tdeviceGroupRelation.setDeviceid(dcDeviceParam.getId());
		int insert = tdeviceGroupRelationMapper.insertSelective(tdeviceGroupRelation);
		issucceed(insert, "添加设备组关联表失败");

		if (tDevItemPic.getItemPicId() != null) {
			tDevItemPic.setAddtime(sdf.format(date));
			tDevItemPic.setIsdelete(false);
			tDevItemPic.setModuleid(1);
			tDevItemPic.setIsenable(true);
			tDevItemPic.setDevId(dcDeviceParam.getId());
			int insertSelective = tdevItemPicMapper.insertSelective(tDevItemPic);
			issucceed(insertSelective, "添加设备项目图片关联表失败");
		}
		// dcDeviceListMapper tdeviceGroupRelationMapper
		int id = dcDeviceListMapper.LKTDCDevIdByMac(dcDeviceParam.getMac());
		if (videoList.getVideoId() != null) {
			for (int j = 0; j < videoList.getVideoId().size(); j++) {

				TDevVideoRelation tDevVideoRelation = new TDevVideoRelation();
				tDevVideoRelation.setDeviceid(id);
				tDevVideoRelation.setVideoid(videoList.getVideoId().get(j));
				tDevVideoRelation.setIsdelete(0);
				tDevVideoRelation.setModuleid(1);
				tDevVideoRelationMapper.insert(tDevVideoRelation);
			}
		}
		return 1;
	}

	@Override
	public Integer updateDeviceById(DCDeviceParam dcDeviceParam, Integer userid,
			TDeviceGroupRelation tdeviceGroupRelation, TDevItemPic tdip, String videoid) throws MyException {

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DCDeviceList selectDeviceById = selectDeviceById(userid, dcDeviceParam.getId());// 根据id查询
		Integer deviceIsExists = 0;
		if (dcDeviceParam.getDevnum() != null || dcDeviceParam.getMac() != null) {
			deviceIsExists = DeviceIsExists(dcDeviceParam.getDevnum(), dcDeviceParam.getMac(), dcDeviceParam.getId());// 判断修改的设备是否有重复mac或者重复设备编号
		}
		if (deviceIsExists != 0) {// 不存在重复
			int updateByPrimaryKeySelective = dcDeviceListMapper.updateByPrimaryKeySelective(dcDeviceParam);// 更改内容
			issucceed(updateByPrimaryKeySelective, "修改设备");
			if (tdeviceGroupRelation.getGroupid() != selectDeviceById.getGroupid()
					&& tdeviceGroupRelation.getGroupid() != null) {
				Example example = new Example(TDeviceGroupRelation.class);
				example.createCriteria().andEqualTo("moduleid", 1).andEqualTo("groupid", selectDeviceById.getGroupid())
						.andEqualTo("deviceid", dcDeviceParam.getId()).andEqualTo("isdelete", 0)
						.andEqualTo("isenable", 1);
				tdeviceGroupRelation.setId(null);
				int i = tdeviceGroupRelationMapper.selectCountByExample(example);
				int j=0;
				if (i == 0) {
					tdeviceGroupRelation.setAddtime(sdf.format(date));
					tdeviceGroupRelation.setAdduserid(userid);
					tdeviceGroupRelation.setDeviceid(dcDeviceParam.getId());
					tdeviceGroupRelation.setModuleid(1);
					tdeviceGroupRelation.setIsdelete(false);
					tdeviceGroupRelation.setIsenable(true);
					 j = tdeviceGroupRelationMapper.insertSelective(tdeviceGroupRelation);
				} else {
					tdeviceGroupRelation.setDeviceid(dcDeviceParam.getId());
					tdeviceGroupRelation.setIsdelete(false);
					tdeviceGroupRelation.setIsenable(true);
					 j = tdeviceGroupRelationMapper
							.updateByExampleSelective(tdeviceGroupRelation, example);
				}
				issucceed(j, "修改设备项目关联表失败");
			}
			if (tdip.getItemPicId() != null) {
				Example example = new Example(TDevItemPic.class);
				example.createCriteria().andEqualTo("moduleid", 1).andEqualTo("devId", dcDeviceParam.getId())
						.andEqualTo("isdelete", 0).andEqualTo("isenable", 1);
				tdip.setId(null);
				int selectCountByExample = tdevItemPicMapper.selectCountByExample(example);
				if (selectCountByExample == 0) {
					tdip.setAddtime(sdf.format(date));
					tdip.setIsdelete(false);
					tdip.setIsenable(true);
					tdip.setDevId(dcDeviceParam.getId());
					tdip.setModuleid(1);
					tdevItemPicMapper.insertSelective(tdip);
				} else {
					tdevItemPicMapper.updateByExampleSelective(tdip, example);
				}
			}
		} else {
			issucceed(0, "输入的设备编号或mac有重复");
		}
		if (videoid != null) {
			videoServiceImpl.reRelation(1, dcDeviceParam.getId(), videoid);
		}
		return 1;
	}

	@Override
	public Integer deleteDeviceById(Integer id, Integer userid) throws MyException {
		// TODO Auto-generated method stub
		DCDeviceList selectDeviceById = selectDeviceById(userid, id);// 根据id查询

		DCDeviceParam dcDeviceParam = new DCDeviceParam();
		dcDeviceParam.setId(id);
		dcDeviceParam.setIsdelete(1);
		dcDeviceParam.setIsenable(0);
		int i = dcDeviceListMapper.updateByPrimaryKeySelective(dcDeviceParam);
		issucceed(i, "删除失败");

		Example example = new Example(TDevItemPic.class);
		example.createCriteria().andEqualTo("moduleid", 1).andEqualTo("devId", id).andEqualTo("isenable", 1);
		Integer iii = tdevItemPicMapper.selectCountByExample(example);
		if (iii > 0) {
			TDevItemPic tDevItemPic = new TDevItemPic();
			tDevItemPic.setIsdelete(true);
			tDevItemPic.setIsenable(false);
			int updateByExampleSelective = tdevItemPicMapper.updateByExampleSelective(tDevItemPic, example);
			issucceed(updateByExampleSelective, "删除项目图片与设备关联表失败");
		}
		TDeviceGroupRelation tDeviceGroupRelation = new TDeviceGroupRelation();
		tDeviceGroupRelation.setIsdelete(true);
		tDeviceGroupRelation.setIsenable(false);
		Example example2 = new Example(TDeviceGroupRelation.class);
		example2.createCriteria().andEqualTo("moduleid", 1).andEqualTo("groupid", selectDeviceById.getGroupid())
				.andEqualTo("deviceid", id).andEqualTo("isdelete", 0).andEqualTo("isenable", 1);
		int updateByExampleSelective2 = tdeviceGroupRelationMapper.updateByExampleSelective(tDeviceGroupRelation,
				example2);
		issucceed(updateByExampleSelective2, "删除设备项目关联表失败");
		// 删除与摄像头的绑定
		boolean judge = devVideoService.checkDevVideoRelation(1, id);
		if (judge == true) {
			devVideoService.cutDevVideoRelation(1, id);
		}

		return 1;
	}

	/**
	 * 判断是否执行成功
	 * 
	 * @param i
	 *            sql返回的执行情况
	 * @param msg
	 *            失败信息
	 * @return
	 * @throws MyException
	 */
	public boolean issucceed(Integer i, String msg) throws MyException {
		if (i <= 0) {
			throw new MyException("0", msg);
		} else {
			return true;
		}
	}

	/**
	 * 判断是否存在设备
	 * 
	 * @param devnum
	 *            设备编号
	 * @param mac
	 *            设备mac
	 * @return 1:存在设备; 0:不存在设备
	 * @throws MyException
	 */
	public Integer DeviceIsExists(String devnum, String mac, Integer id) throws MyException {

		Example example = new Example(DCDeviceParam.class);
		if (id != null) {
			example.createCriteria().andEqualTo("devnum", devnum).orEqualTo("mac", mac).andEqualTo("isdelete", 0)
					.andEqualTo("isenable", 1);
		} else {
			example.createCriteria().andEqualTo("devnum", devnum).orEqualTo("mac", mac).andEqualTo("isdelete", 0)
					.andEqualTo("isenable", 1);
		}
		example.and().andNotEqualTo("id", id);
		int i = dcDeviceListMapper.selectCountByExample(example);
		if (i != 0) {
			return 0;// 存在设备
		} else {
			return 1;// 不存在设备
		}

	}

	/**
	 * 更换设备
	 * 
	 * @param dcDeviceParam
	 *            设备
	 * @return
	 * @throws MyException
	 */

	@Override
	public Integer changeDevice(DCDeviceParam dcDeviceParam) throws MyException {
		return dcDeviceListMapper.changeDevice(dcDeviceParam);
	}

	/**
	 * 更换责任人
	 * 
	 * @param dCChangeUser
	 * 
	 * @return
	 * @throws MyException
	 */

	@Override
	public Integer changeDevOwn(DCChangeUser dCChangeUser) {

		return dcDeviceListMapper.changeDevOwn(dCChangeUser);
	}

	/**
	 * 加入设备签到列表
	 * 
	 * @param moduleid
	 *            模块id
	 * @param devid
	 *            设备id集合
	 * @return
	 * @throws MyException
	 */
	@Override
	public Integer LKTAddSignDevList(Integer moduleid, String devid, Integer patrolid) {
		// TODO Auto-generated method stub
		return lkthwMapper.LKTHWAddSignDevList(moduleid, devid, 1, patrolid);
	}



}

package com.hot.manage.service.imp.dc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.hot.manage.db.dc.DCGroupMapper;
import com.hot.manage.db.item.TDeviceGroupMapper;
import com.hot.manage.db.item.TDeviceGroupRelationMapper;
import com.hot.manage.db.item.TItemPicMapper;
import com.hot.manage.db.item.TUserDgroupRelationMapper;
import com.hot.manage.db.system.TUserMapper;
import com.hot.manage.entity.dc.param.DCGroupByIdParam;
import com.hot.manage.entity.dc.param.DCGroupParam;
import com.hot.manage.entity.dc.param.TItemPicParam;
import com.hot.manage.entity.dc.vo.DCGroupAddress;
import com.hot.manage.entity.dc.vo.DCGroupList;
import com.hot.manage.entity.dc.vo.DCItemPicVo;
import com.hot.manage.entity.dc.vo.DCMyGroupList;
import com.hot.manage.entity.dc.vo.GroupByOne;
import com.hot.manage.entity.dc.vo.Tools;
import com.hot.manage.entity.item.TDeviceGroup;
import com.hot.manage.entity.item.TDeviceGroupRelation;
import com.hot.manage.entity.item.TItemPic;
import com.hot.manage.entity.item.TUserDgroupRelation;
import com.hot.manage.entity.yg.LKTSelectHandle;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.dc.DCGroupService;

import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class DCGroupServiceImpl implements DCGroupService {

	@Autowired
	private DCGroupMapper dcGroupMapper;

	@Autowired
	private TDeviceGroupMapper tdeviceGroupMapper;
	
	@Autowired
	private TDeviceGroupRelationMapper tdeviceGroupRelationMapper;

	@Autowired
	private TUserDgroupRelationMapper tuserDgroupRelationMapper;

	@Autowired
	private TItemPicMapper titemPicMapper;

	@Autowired
	private TUserMapper tUserMapper;


	/**
	 * 项目列表(可根据时间,内容筛选)
	 */
	@Override
	public Page<DCGroupList> selectGroupList(String startTime, String endTime, String message, Integer userid)
			throws MyException {
		// TODO Auto-generated method stub
		Page<DCGroupList> list = dcGroupMapper.selectGroupList(startTime, endTime, message, userid);
		return list;
	}

	/**
	 * 添加项目组
	 */
	@Override
	public Integer insertGroup(Integer userid, TDeviceGroup deviceGroup, List<TItemPic> tItemPicList)
			throws MyException {
		// TODO Auto-generated method stub

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Integer groupisexist = GroupIsexists(deviceGroup.getGroupname(), deviceGroup.getItemnum());
		if (groupisexist != 1) {
			deviceGroup.setModuleid(1);// 设置模块id
			deviceGroup.setAdduserid(userid);// 设置添加人id
			deviceGroup.setAddtime(sdf.format(date));// 设置时间
			deviceGroup.setIsdelete(false);// 设置是否删除
			deviceGroup.setIsenable(true);// 设置是否可用
			int insertDeviceGroup = tdeviceGroupMapper.insertSelective(deviceGroup);
			issucceed(insertDeviceGroup, "添加组失败");
			TUserDgroupRelation tuserDgroupRelation = new TUserDgroupRelation();
			tuserDgroupRelation.setDevicegroupid(deviceGroup.getId());// 设置关联的项目组id
			tuserDgroupRelation.setUserid(userid);// 设置关联人id
			tuserDgroupRelation.setAdduserid(userid);// 设置添加人id
			tuserDgroupRelation.setIsdelete(false);// 设置是否删除
			tuserDgroupRelation.setIsenable(true);// 设置是否可用
			tuserDgroupRelation.setAddtime(date);// 设置时间
			int insertUserDgroupRelation = tuserDgroupRelationMapper.insertSelective(tuserDgroupRelation);
			issucceed(insertUserDgroupRelation, "用户与项目绑定失败");
			if (tItemPicList.size() > 0) {// 判断是否需要添加
				for (TItemPic tItemPic : tItemPicList) {
					tItemPic.setAddtime(sdf.format(date));// 设置添加时间
					tItemPic.setItemid(deviceGroup.getId());// 设置关联组id
					tItemPic.setIsdelete(false);// 设置是否删除
					tItemPic.setIsenable(true);// 设置是否可用
					int i = titemPicMapper.insertSelective(tItemPic);// 添加图片与组的表
					issucceed(i, "项目图片关联表添加失败");
				}
			}
		} else {
			issucceed(0, "组编号或者组名已存在!");
		}
		return 1;
	}

	/**
	 * 根据id 查询
	 */
	@Override
	public GroupByOne selectById(Integer id) throws MyException {
		// TODO Auto-generated method stub
		DCGroupParam dcGroupParam = dcGroupMapper.selectByPrimaryKey(id);// 根据id查出组
		GroupByOne groupByOne = Tools.cloneObj(dcGroupParam, GroupByOne.class);// 把属性赋值到vo类里
		Example example = new Example(TItemPic.class);
		example.createCriteria().andEqualTo("itemid", id);
		List<TItemPic> tiItemPic = titemPicMapper.selectByExample(example);// 查询出这个组下的所有楼层
		List<DCItemPicVo> list = new ArrayList<>();// 创建一个list对象
		for (TItemPic tItemPic : tiItemPic) {
			DCItemPicVo tItemPicVo = Tools.cloneObj(tItemPic, DCItemPicVo.class);
			list.add(tItemPicVo);// 把查询到的信息,添加到list对象里
		}

		System.out.println(list.toString());
		System.out.println(groupByOne.toString());
		groupByOne.setTItemPicVo(list);// 把list存入vo类中
		System.out.println(groupByOne.getTItemPicVo().toString());
		groupByOne.setAddusername(tUserMapper.selectByPrimaryKey(dcGroupParam.getAdduserid()).getContacts());// 查出添加人姓名
		groupByOne.setManagername(tUserMapper.selectByPrimaryKey(dcGroupParam.getManagerid()).getContacts());// 查出责任人id
		return groupByOne;// 放回vo类
	}

	/**
	 * 根据内容修改
	 */
	@Override
	public Integer updateById(DCGroupByIdParam dcGroupByIdParam) throws MyException {
		// TODO Auto-generated method stub

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		GroupByOne groupByOne = selectById(dcGroupByIdParam.getId());
		List<Integer> a = new ArrayList<>();
		for (DCItemPicVo byonelist : groupByOne.getTItemPicVo()) {
			a.add(byonelist.getId());
		}
		List<Integer> b = new ArrayList<>();
		for (TItemPicParam titemPicParamList : dcGroupByIdParam.getTItemPicParam()) {

			if (titemPicParamList.getId().equals(null)) {
				TItemPic itemPic = Tools.cloneObj(titemPicParamList, TItemPic.class);
				itemPic.setIsdelete(false);
				itemPic.setIsenable(true);
				itemPic.setItemid(dcGroupByIdParam.getId());
				itemPic.setAddtime(sdf.format(date));
				int insertSelective = titemPicMapper.insertSelective(itemPic);
				issucceed(insertSelective, "添加组图片失败");
			} else {
				b.add(titemPicParamList.getId());
			}
		}
		List<Integer> delete = getDiff(b, a);// 需要删除的id

		Integer groupisexists = GroupIsexists(dcGroupByIdParam.getGroupname(), dcGroupByIdParam.getItemnum());// 判断修改的组名或组编号是否重复
		if (groupisexists != 1) {
			DCGroupParam dcGroupParam = Tools.cloneObj(dcGroupByIdParam, DCGroupParam.class);// 把传入的参数赋值到tkmapper里
			Integer i = dcGroupMapper.updateByPrimaryKeySelective(dcGroupParam);// 修改组
			issucceed(i, "修改组失败");// 判断是否成功

			for (Integer idparam : delete) { // 删除没数据的楼层图片
				issucceed(PicisbindDevice(idparam) ? 1 : 0, "该楼层判定了设备");
				TItemPic itemPic = new TItemPic();
				itemPic.setId(idparam);
				itemPic.setIsdelete(true);
				itemPic.setIsenable(false);
				Integer isscd = titemPicMapper.updateByPrimaryKeySelective(itemPic);
				issucceed(isscd, "楼层图片删除失败");
			}
			List<TItemPicParam> tItemPicParam = dcGroupByIdParam.getTItemPicParam();// 取出该项目下的楼层图片部分

			for (TItemPicParam tItemPicParam2 : tItemPicParam) {// 遍历这个楼层
				int num = 0;
				Example example = new Example(TItemPic.class);
				example.createCriteria().andEqualTo("id", tItemPicParam2.getId()).andEqualTo("isenable", 1)
						.andEqualTo("isdelete", 0);// 查询
				num = titemPicMapper.selectCountByExample(example);// 查询是否存在
				if (num != 0) {// 修改
					TItemPic itemPic = Tools.cloneObj(tItemPicParam2, TItemPic.class);
					itemPic.setIsdelete(false);
					itemPic.setIsenable(true);
					itemPic.setItemid(dcGroupByIdParam.getId());
					int insertitempic = titemPicMapper.updateByPrimaryKeySelective(itemPic);
					issucceed(insertitempic, "修改组图片失败");
				}
			}

		} else {
			issucceed(0, "组编号或者组名已存在!");
		}

		return 1;
	}

	/**
	 * 根据id删除
	 */
	@Override
	public Integer deleteById(Integer groupid, Integer userid) throws MyException {
		// TODO Auto-generated method stub
		Integer groupRelationDev = GroupRelationDev(1, groupid);// 判断是否绑定设备
		if (groupRelationDev != 1) {// 没有绑定设备
			DCGroupParam dcGroupParam = new DCGroupParam();
			dcGroupParam.setId(groupid);
			dcGroupParam.setIsenable(0);
			dcGroupParam.setIsdelete(1);
			Integer i = dcGroupMapper.updateByPrimaryKeySelective(dcGroupParam);// 删除组
			issucceed(i, "删除组失败");

			Example example = new Example(TItemPic.class);// 判断条件
			example.createCriteria().andEqualTo("itemid", groupid).andEqualTo("isenable", 1).andEqualTo("isdelete", 0);
			TItemPic itemPic = new TItemPic();
			itemPic.setIsdelete(true);
			itemPic.setIsenable(false);
			int getresult = titemPicMapper.selectCountByExample(example);// 判断是否有该楼层存在
			if (getresult != 0) {
				int result = titemPicMapper.updateByExampleSelective(itemPic, example);// 删除楼层
				issucceed(result, "删除楼层图片失败");// 是否成功
			}

			Example example2 = new Example(TUserDgroupRelation.class);// 判断条件
			example2.createCriteria().andEqualTo("devicegroupid", groupid).andEqualTo("isenable", 1)
					.andEqualTo("isdelete", 0).andEqualTo("userid", userid);
			int getresult2 = tuserDgroupRelationMapper.selectCountByExample(example2);
			if (getresult2 != 0) {
				TUserDgroupRelation tUserDgroupRelation = new TUserDgroupRelation();
				tUserDgroupRelation.setIsdelete(true);
				tUserDgroupRelation.setIsenable(false);
				int result2 = tuserDgroupRelationMapper.updateByExampleSelective(tUserDgroupRelation, example2);// 删除项目用户表
				issucceed(result2, "删除项目用户关联表失败");// 判断是否成功
			}
		} else {
			issucceed(0, "该组下有设备,不能删除");
		}

		return 1;
	}

	/**
	 * 终端总数
	 */
	@Override
	public Integer selectAllDevice(Integer userid) throws MyException {
		// TODO Auto-generated method stub

		return dcGroupMapper.selectAllDevice(userid);
	}

	/**
	 * 我的项目
	 */
	@Override
	public List<DCMyGroupList> selectMyGroupList(Integer userid) throws MyException {
		// TODO Auto-generated method stub
		return dcGroupMapper.selectMygroupList(userid);
	}

	/**
	 * 项目地址
	 */
	@Override
	public List<DCGroupAddress> selectGroupAddress(Integer userid) throws MyException {
		// TODO Auto-generated method stub
		return dcGroupMapper.selectGroupAddress(userid);
	}

	/**
	 * 查询当前用户可选责任人
	 */
	@Override
	public List<LKTSelectHandle> SelectManager(Integer userid) throws MyException {
		// TODO Auto-generated method stub
		return dcGroupMapper.SelectManager(userid);
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
	 * 判断项目的组名和组编号是否存在
	 * 
	 * @param groupname
	 *            组名
	 * @param itemnum
	 *            组编号
	 * @return 0:不存在,可添加项目; 1:可用,有同组名或同组编号;2:删除,添加项目
	 * @throws MyException
	 */
	public int GroupIsexists(String groupname, String itemnum) throws MyException {
		Example example = new Example(DCGroupParam.class);
		example.createCriteria().andEqualTo("groupname", groupname).orEqualTo("itemnum", itemnum).andEqualTo("moduleid",
				1);
		int i = dcGroupMapper.selectCountByExample(example);
		if (i <= 0) {// 不存在,添加项目
			return 0;
		} else {// 存在
			example.and().andEqualTo("isenable", 0).andEqualTo("isdelete", 1);// 判断是否删除
			int isExists = dcGroupMapper.selectCountByExample(example) <= 0 ? 1 : 2; // 1:可用;2:删除
			return isExists;
		}

	}

	/**
	 * 判断新增的楼层是否重复(*)
	 */
	public Integer ItemPicIsExtsis(Integer itemid, String site) throws MyException {
		Example example = new Example(TItemPic.class);
		example.createCriteria().andEqualTo("itemid", itemid).andEqualTo("site", site).andEqualTo("isenable", 1)
				.andEqualTo("isdelete", 0);
		int selectCountByExample = titemPicMapper.selectCountByExample(example);

		return selectCountByExample <= 0 ? 1 : 0;

	}

	/**
	 * 判断该组下是否有设备(*)
	 * 
	 * @param moduleid
	 *            模块id
	 * @param groupid
	 *            组id
	 * @return 1:该组下有设备;0:该组下没设备
	 * @throws MyException
	 */
	public Integer GroupRelationDev(Integer moduleid, Integer groupid) throws MyException {
		Example example = new Example(TDeviceGroupRelation.class);
		example.createCriteria().andEqualTo("moduleid", moduleid).andEqualTo("id", groupid)
				.andEqualTo("isenable", 1).andEqualTo("isdelete", 0);
		int selectCountByExample = tdeviceGroupRelationMapper.selectCountByExample(example);
		return selectCountByExample > 0 ? 1 : 0;

	}

	/**
	 * 判断项目与楼层图片关联是否存在
	 * 
	 * @param site
	 *            楼层
	 * @param groupid
	 *            项目id
	 * @return 0:不存在这个关联;1:存在这个关联
	 * @throws MyException
	 */
	public int GroupPicRelationIsexists(String site, Integer groupid) throws MyException {
		Example example = new Example(TItemPic.class);
		example.createCriteria().andEqualTo("site", site).andEqualTo("itemid", groupid).andEqualTo("isenable", 1)
				.andEqualTo("isdelete", 0);
		int i = titemPicMapper.selectCountByExample(example);
		if (i <= 0) {// 不存在这个图片
			return 0;
		} else {// 存在这个图片
			return 1;
		}
	}

	/**
	 * 判断楼层是否绑定设备
	 */
	public boolean PicisbindDevice(Integer picid) throws MyException {
		Integer i = dcGroupMapper.selectPictoDevice(picid, 1);
		return i == 0 ? true : false;

	}

	/**
	 * 比较两个list 筛选出不同的部分
	 * 
	 * @param list1
	 *            筛选对象
	 * @param list2
	 *            被筛选对象
	 * @return list list1没有的部分
	 */
	public List<Integer> getDiff(List<Integer> list1, List<Integer> list2) {
		List<Integer> listAll = new ArrayList<Integer>();
		List<Integer> resultList = new ArrayList<Integer>();
		listAll.addAll(list1);
		listAll.addAll(list2);
		for (int i = 0; i < listAll.size(); i++) {
			if (list1.contains(listAll.get(i))) {
				continue;
			} else {
				resultList.add(listAll.get(i));
			}
		}
		return resultList;
	}

}

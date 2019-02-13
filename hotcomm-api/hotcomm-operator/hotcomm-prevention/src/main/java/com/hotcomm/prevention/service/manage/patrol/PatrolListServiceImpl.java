package com.hotcomm.prevention.service.manage.patrol;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotcomm.prevention.bean.mysql.common.entity.TUser;
import com.hotcomm.prevention.bean.mysql.common.vo.TUserVo;
import com.hotcomm.prevention.bean.mysql.manage.PageInfo;
import com.hotcomm.prevention.bean.mysql.manage.patrol.PatrolParams;
import com.hotcomm.prevention.bean.mysql.manage.patrol.PatrolPersonVo;
import com.hotcomm.prevention.bean.mysql.manage.patrol.bean.THkPatdevrelation;
import com.hotcomm.prevention.bean.mysql.manage.patrol.bean.THkUserpatrelation;
import com.hotcomm.prevention.db.mysql.manage.patrol.PatrolMapper;
import com.hotcomm.prevention.db.mysql.manage.patrol.THkPatdevrelationMapper;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.common.UserService;
import com.hotcomm.prevention.utils.ConverUtil;
import tk.mybatis.mapper.entity.Example;

@Service
public class PatrolListServiceImpl implements PatrolListService {
	@Autowired
	private UserService userService;
	@Autowired
	private PatrolMapper patrolMapper;
	@Autowired
	private THkPatdevrelationMapper HkPatdevrelationMapper;

	@Override
	public PageInfo<PatrolPersonVo> selectPageInfo(PatrolParams params) throws MyException {
		List<Object> list = new ArrayList<>();
		// 查询用户下面的子用户
		List<TUserVo> selectChildren = userService.selectChildren(params.getUserid());
		for (TUserVo tUserVo : selectChildren) {
			list.add(tUserVo.getId());
		}
		// 查询当前用下的巡检人员
		List<TUser> selectPatrolUser = patrolMapper.selectPatrolUser(list);
		list.clear();
		for (TUser tUser : selectPatrolUser) {
			list.add(tUser.getId());
		}
		if (list.size() == 0) {
			return new PageInfo<>(params.getPageNum(), params.getPageSize(), 0, null);
		}
		// 巡检人员列表分页
		PageHelper.startPage(params.getPageNum(), params.getPageSize());
		Page<PatrolPersonVo> page = patrolMapper.selectPageInfo(params, list);
		List<PatrolPersonVo> converPage = ConverUtil.converPage(page, PatrolPersonVo.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), converPage);
	}

	// 添加巡检人员
	@Transactional
	@Override
	public Integer insertPatrol(Integer userid) throws MyException {
		Example example = new Example(THkUserpatrelation.class);
		example.createCriteria().andEqualTo("userid", userid).andEqualTo("isdelete", false);
		List<THkUserpatrelation> selectByExample = patrolMapper.selectByExample(example);
		if (selectByExample != null && selectByExample.size() != 0) {
			throw new MyException("0", "此用户已添加！！");
		}
		THkUserpatrelation record = new THkUserpatrelation();
		record.setUserid(userid);
		record.setAddtime(ConverUtil.dateForString(new Date()));
		int in = patrolMapper.insertSelective(record);
		if (in <= 0) {
			throw new MyException("0", "此用户已添加失败！！");
		}
		return 1;
	}

	// 冻结巡检人员
	@Transactional
	@Override
	public Integer updatePatrol(Integer id, Integer isenable) throws MyException {
		THkUserpatrelation record = new THkUserpatrelation();
		if (isenable <= 0)
			record.setIsenable(false);
		else
			record.setIsenable(true);
		record.setId(id);
		Example example = new Example(THkUserpatrelation.class);
		example.createCriteria().andEqualTo("userid", id).andEqualTo("isdelete", false);
		int up = patrolMapper.updateByExampleSelective(record, example);
		if (up <= 0) {
			throw new MyException("0", "操作失败！");
		}
		return 1;
	}

	// 删除巡检人员
	@Transactional
	@Override
	public Integer delPatrol(Integer id) throws MyException {
		// 先判断此巡检人员是否存在
		Example ex = new Example(THkUserpatrelation.class);
		ex.createCriteria().andEqualTo("id", id).andEqualTo("isdelete", false);
		List<THkUserpatrelation> user = patrolMapper.selectByExample(ex);
		if (user.size() == 0) {
			throw new MyException("0", "此巡检人员不存在！");
		}
		// 判断用户与签到点是否存在关联数据
		Example example = new Example(THkPatdevrelation.class);
		example.createCriteria().andEqualTo("userpatid", user.get(0).getId());
		List<THkPatdevrelation> list = HkPatdevrelationMapper.selectByExample(example);
		if (list.size() != 0) {
			for (THkPatdevrelation tHkPatdevrelation : list) {
				int del = HkPatdevrelationMapper.deleteByPrimaryKey(tHkPatdevrelation.getId());
				if (del <= 0) {
					throw new MyException("0", "删除失败！");
				}
			}
		}
		// 删除
		THkUserpatrelation re = new THkUserpatrelation();
		re.setIsdelete(true);
		re.setIsenable(false);
		Example ee = new Example(THkUserpatrelation.class);
		ee.createCriteria().andEqualTo("id", user.get(0).getId());
		int up = patrolMapper.updateByExampleSelective(re, ee);
		if (up <= 0) {
			throw new MyException("0", "删除失败！");
		}
		return 1;
	}

	// 查询当前用户所有的巡检人员
	@Override
	public List<TUserVo> selectAllPatrol(Integer userid) throws MyException {
		List<Object> list = new ArrayList<>();
		// 查询用户下面的子用户
		List<TUserVo> selectChildren = userService.selectChildren(userid);
		for (TUserVo tUserVo : selectChildren) {
			list.add(tUserVo.getId());
		}
		// 查询当前用下的巡检人员
		List<TUser> selectPatrolUser = patrolMapper.selectPatrolUser(list);
		return convert(selectPatrolUser);
	}

	public static List<TUserVo> convert(List<TUser> list) {
		List<TUserVo> users = new ArrayList<>();
		if (list.size() != 0) {
			for (TUser tUser : list) {
				TUserVo vo = new TUserVo();
				BeanUtils.copyProperties(tUser, vo);
				vo.setAddtime(tUser.getAddtime());
				vo.setLastlogintime(tUser.getLastlogintime());
				users.add(vo);
			}
		}
		return users;
	}

	// 更换巡检人员
	@Transactional
	@Override
	public Integer replacePatrol(Integer patrolid, Integer id) throws MyException {
		// 更换巡检人员表
		THkUserpatrelation record = new THkUserpatrelation();
		record.setId(id);
		record.setUserid(patrolid);
		int up = patrolMapper.updateByPrimaryKeySelective(record);
		if (up <= 0) {
			throw new MyException("0", "更换失败");
		}
		return 1;
	}

}

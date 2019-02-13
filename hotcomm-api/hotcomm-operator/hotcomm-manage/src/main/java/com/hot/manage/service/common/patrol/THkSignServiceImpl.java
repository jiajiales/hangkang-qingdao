package com.hot.manage.service.common.patrol;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hot.manage.db.common.patrol.THkPatdevrelationMapper;
import com.hot.manage.db.common.patrol.THkSignMapper;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.common.patrol.ShakeSignParam;
import com.hot.manage.entity.common.patrol.ShakeSignVo;
import com.hot.manage.entity.common.patrol.SignLogInfoVO;
import com.hot.manage.entity.common.patrol.SignLogPageInfoParam;
import com.hot.manage.entity.common.patrol.SignLogPageInfoVO;
import com.hot.manage.entity.common.patrol.SignPlaceOnid;
import com.hot.manage.entity.common.patrol.THKSignParam;
import com.hot.manage.entity.common.patrol.checkSignDevnum;
import com.hot.manage.entity.common.patrol.newSignPlaceParam;
import com.hot.manage.entity.common.patrol.signPlacePageInfoParam;
import com.hot.manage.entity.common.patrol.signPlacePageInfoVO;
import com.hot.manage.entity.common.patrol.bean.THkPatdevrelation;
import com.hot.manage.entity.common.patrol.bean.THkSign;
import com.hot.manage.exception.MyException;
import com.hot.manage.utils.ConverUtil;

import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class THkSignServiceImpl implements THkSignService {
	@Autowired
	private THkSignMapper HkSignMapper;
	@Autowired
	private THkPatdevrelationMapper HkPatdevrelationMapper;
	@Autowired
	private THkSignService HkSignService;

	/**
	 * 摇一摇签到设备分页
	 */
	@Override
	public PageInfo<ShakeSignVo> selectPageinfo(ShakeSignParam param) throws MyException {
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		Page<ShakeSignVo> page = HkSignMapper.selectPageinfo(param);
		List<ShakeSignVo> rows = ConverUtil.converPage(page, ShakeSignVo.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), rows);
	}

	/**
	 * 添加签到设备
	 */
	@Override
	public Integer insertDev(THKSignParam param) throws MyException {
		Example example = new Example(THkSign.class);
		example.createCriteria().andEqualTo("devnum", param.getDevnum()).andEqualTo("isdelete", false);
		List<THkSign> list = HkSignMapper.selectByExample(example);
		if (list != null && list.size() != 0) {
			throw new MyException("0", "此编号已存在！");
		}
		THkSign record = new THkSign();
		BeanUtils.copyProperties(param, record);
		record.setAddtime(ConverUtil.timeForString(new Date()));
		int insertSelective = HkSignMapper.insertSelective(record);
		if (insertSelective <= 0) {
			throw new MyException("0", "添加失败！");
		}
		String userid = param.getSignid();
		if (userid != null) {
			String[] split = userid.split(",");
			// 添加签到用户和签到点关联
			for (String ss : split) {
				THkPatdevrelation relation = new THkPatdevrelation();
				relation.setSignid(record.getId());
				relation.setUserpatid(Integer.parseInt(ss));
				HkPatdevrelationMapper.insertSelective(relation);
			}
		}
		return 1;
	}

	/**
	 * 修改签到设备
	 */
	@Override
	public Integer update(THKSignParam param) throws MyException {
		if (param == null) {
			throw new MyException("0", "参数不能为空");
		}
		THkSign record = new THkSign();
		BeanUtils.copyProperties(param, record);
		HkSignMapper.updateByPrimaryKeySelective(record);
		String userid = param.getSignid();
		if (userid != null) {
			String[] ss = userid.split(",");
			Example example = new Example(THkPatdevrelation.class);
			example.createCriteria().andEqualTo("signid", param.getId());
			HkPatdevrelationMapper.deleteByExample(example);
			for (String s : ss) {
				THkPatdevrelation relation = new THkPatdevrelation();
				relation.setSignid(param.getId());
				relation.setUserpatid(Integer.parseInt(s));
				int in = HkPatdevrelationMapper.insertSelective(relation);
				if (in <= 0) {
					throw new MyException("0", "修改失败！");
				}
			}
		}
		return 1;
	}

	/**
	 * 删除签到设备
	 */
	@Override
	public Integer delete(Integer id) throws MyException {
		Example example = new Example(THkPatdevrelation.class);
		example.createCriteria().andEqualTo("signid", id);
		List<THkPatdevrelation> list = HkPatdevrelationMapper.selectByExample(example);
		if (list != null && list.size() != 0) {
			int del = HkPatdevrelationMapper.deleteByExample(example);
			if (del <= 0) {
				throw new MyException("0", "删除失败！");
			}
		}
		THkSign record = new THkSign();
		record.setIsdelete(true);
		record.setId(id);
		int up = HkSignMapper.updateByPrimaryKeySelective(record);
		if (up <= 0) {
			throw new MyException("0", "删除失败！");
		}
		return 1;
	}

	@Override
	public PageInfo<signPlacePageInfoVO> selectSignPlacePageInfo(signPlacePageInfoParam param) throws MyException {
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		Page<signPlacePageInfoVO> page = HkSignMapper.selectSignPlacePageInfo(param);
		List<signPlacePageInfoVO> rows = ConverUtil.converPage(page, signPlacePageInfoVO.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), rows);
	}

	@Override
	public Integer addSignAddress(newSignPlaceParam param) throws MyException {
		checkSignDevnum SignPlaceParam = HkSignMapper.checkSignPlaceDevnum(param.getDevnum());
		if (SignPlaceParam != null) {
			if (param.getDevnum().equals(SignPlaceParam.getDevnum())) {
				return 201;
			}
		}
		if (param != null) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			param.setADDTIME(df.format(new Date()));
			HkSignMapper.addSignAddress(param);
		}
		if (param.getPatUser() != null) {
			HkSignService.patUserRelationSign(param.getPatUser(), param.getInsertId().toString());
		}
		return 1;
	}

	@Override
	public Integer patUserRelationSign(String patUser, String signId) {
		String[] a = null;
		String[] b = null;
		if (patUser.length() > 0) {
			a = patUser.split(",");
		}
		if (signId.length() > 0) {
			b = signId.split(",");
		}
		for (int j = 0; j < b.length; j++) {
			HkSignMapper.cutRelation(Integer.parseInt(b[j]));
		}
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < b.length; j++) {
				HkSignMapper.patUserRelationSign(Integer.parseInt(a[i]), Integer.parseInt(b[j]));
			}
		}
		return 1;
	}

	@Override
	public Integer deleteSignPlace(Integer signId) {
		HkSignMapper.cutRelation(signId);
		HkSignMapper.clearSignLog(signId);
		HkSignMapper.deleteSignPlace(signId);
		return 1;
	}

	@Override
	public Integer updateSignPlace(newSignPlaceParam param) {
		checkSignDevnum check = HkSignMapper.checkSignPlaceDevnum(param.getDevnum());
		if (check != null) {
			if (!check.getId() .equals(param.getInsertId())&&check.getDevnum().equals(param.getDevnum())) {
				return 201;
			}
		}
		if (param != null) {
			HkSignMapper.updateSignPlace(param);
		}
		if (param.getPatUser() != null) {
			HkSignService.patUserRelationSign(param.getPatUser(), param.getInsertId().toString());
		}
		return 1;
	}

	@Override
	public SignPlaceOnid selectSignPlaceOnid(Integer signId) {
		return HkSignMapper.selectSignPlaceOnid(signId);
	}

	@Override
	public PageInfo<SignLogPageInfoVO> selectSignLogPageInfo(SignLogPageInfoParam param) throws MyException {
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		Page<SignLogPageInfoVO> page = HkSignMapper.selectSignLogPageInfo(param);
		List<SignLogPageInfoVO> rows = ConverUtil.converPage(page, SignLogPageInfoVO.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), rows);
	}

	@Override
	public SignLogInfoVO selectSignLogInfo(SignLogPageInfoParam param) {
		List<SignLogPageInfoVO> info = HkSignMapper.selectSignLogInfo(param);
		SignLogInfoVO vo = new SignLogInfoVO();
		vo.setInfo(info);
		return vo;
	}

}

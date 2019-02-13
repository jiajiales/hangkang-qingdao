package com.hotcomm.prevention.service.manage.patrol;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotcomm.prevention.bean.mysql.manage.PageInfo;
import com.hotcomm.prevention.bean.mysql.manage.patrol.CheckSignDevnum;
import com.hotcomm.prevention.bean.mysql.manage.patrol.SignLogPageInfoVO;
import com.hotcomm.prevention.bean.mysql.manage.patrol.SignPlaceOnid;
import com.hotcomm.prevention.bean.mysql.manage.patrol.SignPlacePageInfoVO;
import com.hotcomm.prevention.bean.mysql.manage.patrol.vaule.NewSignPlaceParam;
import com.hotcomm.prevention.bean.mysql.manage.patrol.vaule.SignInfo;
import com.hotcomm.prevention.bean.mysql.manage.patrol.vaule.SignLogPageInfoParam;
import com.hotcomm.prevention.bean.mysql.manage.patrol.vaule.SignPlacePageInfoParam;
import com.hotcomm.prevention.db.mysql.manage.patrol.ShakeDevMapper;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.utils.ConverUtil;
import com.hotcomm.prevention.utils.TextUtils;

@Service
public class ShakeDevServiceImpl implements ShakeDevService {

	@Autowired
	private ShakeDevMapper shaedevmapper;

	/**
	 * 签到地点分配巡检人员
	 */
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
			shaedevmapper.cutRelation(Integer.parseInt(b[j]));
		}
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < b.length; j++) {
				shaedevmapper.patUserRelationSign(Integer.parseInt(a[i]), Integer.parseInt(b[j]));
			}
		}
		return 1;
	}

	/**
	 * 删除签到地点
	 */
	@Override
	public Integer deleteSignPlace(Integer signId) {
		shaedevmapper.cutRelation(signId);
		shaedevmapper.clearSignLog(signId);
		shaedevmapper.deleteSignPlace(signId);
		return 1;
	}

	/**
	 * 更新设备签到地点
	 */
	@Override
	public Integer updateSignPlace(NewSignPlaceParam param) {
		CheckSignDevnum check = shaedevmapper.checkSignPlaceDevnum(param.getDevnum());
		if (check != null) {
			if (!check.getId().equals(param.getInsertId()) && check.getDevnum().equals(param.getDevnum())) {
				return 201;
			}
		}
		if (param != null) {
			shaedevmapper.updateSignPlace(param);
		}
		if (param.getPatUser() != null) {
			patUserRelationSign(param.getPatUser(), param.getInsertId().toString());
		}
		return 1;
	}

	/**
	 * 添加签到地点
	 */
	@Override
	public Integer addSignAddress(NewSignPlaceParam param) throws MyException {
		CheckSignDevnum SignPlaceParam = shaedevmapper.checkSignPlaceDevnum(param.getDevnum());
		if (SignPlaceParam != null) {
			if (param.getDevnum().equals(SignPlaceParam.getDevnum())) {
				return 201;
			}
		}
		if (param != null) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			param.setADDTIME(df.format(new Date()));
			shaedevmapper.addSignAddress(param);
		}
		if (param.getPatUser() != null) {
			patUserRelationSign(param.getPatUser(), param.getInsertId().toString());
		}
		return 1;
	}

	/**
	 * 查询单个签到地点
	 */
	@Override
	public SignPlaceOnid selectSignPlaceOnid(Integer signId) throws MyException {
		return shaedevmapper.selectSignPlaceOnid(signId);
	}

	/**
	 * 分页查询签到记录
	 */
	@Override
	public PageInfo<SignLogPageInfoVO> selectSignLogPageInfo(SignLogPageInfoParam param) throws MyException {
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		Page<SignLogPageInfoVO> page = shaedevmapper.selectSignLogPageInfo(param);
		List<SignLogPageInfoVO> rows = ConverUtil.converPage(page, SignLogPageInfoVO.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), rows);
	}

	/**
	 * 获取签到地点列表
	 */
	@Override
	public PageInfo<SignPlacePageInfoVO> selectSignPlacePageInfo(SignPlacePageInfoParam param) throws MyException {
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		Page<SignPlacePageInfoVO> page = shaedevmapper.selectSignPlacePageInfo(param);
		List<SignPlacePageInfoVO> rows = ConverUtil.converPage(page, SignPlacePageInfoVO.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), rows);
	}

	/**
	 * app扫码签到
	 */
	@Override
	public Integer AppSigns(SignInfo signInfo) throws MyException {
		Integer s = shaedevmapper.selectSignid(signInfo.getDevnum());
		signInfo.setSignid(s);
		Integer Pid = shaedevmapper.selectPid(signInfo.getUserid());
		Integer y = shaedevmapper.selectrelation(signInfo.getSignid(), Pid);
		if (y == 0) {
			return 0;
		}
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		signInfo.setAddtime(df.format(day));

		if (TextUtils.isEmpty(signInfo.getLng()) || TextUtils.isEmpty(signInfo.getLat())) {
			signInfo.setSignstate(3);
			shaedevmapper.AppSign(signInfo);
			return 3;
		}
		Integer dis = shaedevmapper.selectjw(signInfo.getSignid(), signInfo.getLat(), signInfo.getLng());
		if (dis > 100) {
			signInfo.setSignstate(2);
			shaedevmapper.AppSign(signInfo);
			return 2;
		}
		signInfo.setSignstate(1);
		shaedevmapper.AppSign(signInfo);
		shaedevmapper.AppUpdateSign(signInfo.getAddtime(), signInfo.getSignid());
		shaedevmapper.AppUpdateSignTime(signInfo.getAddtime(), signInfo.getUserid());
		return 1;
	}

}

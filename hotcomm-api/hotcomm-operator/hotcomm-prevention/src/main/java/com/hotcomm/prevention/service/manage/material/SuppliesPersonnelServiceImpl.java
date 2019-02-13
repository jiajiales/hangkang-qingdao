package com.hotcomm.prevention.service.manage.material;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.hotcomm.prevention.bean.mysql.manage.material.MaterialuserList;
import com.hotcomm.prevention.bean.mysql.manage.material.bean.T_hk_usermaterial;
import com.hotcomm.prevention.bean.mysql.manage.material.vaule.MaterialuserListVaule;
import com.hotcomm.prevention.db.mysql.manage.material.SuppliesPersonnelMapper;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.utils.ConverUtil;

import tk.mybatis.mapper.entity.Example;

@Service
public class SuppliesPersonnelServiceImpl implements SuppliesPersonnelService {

	@Autowired
	private SuppliesPersonnelMapper suppliesPersonnelMapper;

	/**
	 * 新增物资管理人员
	 */
	@Transactional
	@Override
	public Integer addmaterialuser(T_hk_usermaterial t_hk_material) throws MyException {
		// 根据用户id查询该用户是否已是物资管理人员
		Example example = new Example(T_hk_usermaterial.class);
		example.createCriteria().andEqualTo("adduserid", t_hk_material.getAdduserid()).andEqualTo("isenable", 1)
				.andEqualTo("isdelete", 0);
		List<T_hk_usermaterial> t = suppliesPersonnelMapper.selectByExample(example);
		// 判断该物资管理人员是否存在
		if (t != null && t.size() > 0) {
			// 存在
			throw new MyException("0", "该用户已经是物资管理人员！！");
		}
		// 不存在则新增
		t_hk_material.setAddtime(ConverUtil.dateForString(new Date()));
		Integer code = suppliesPersonnelMapper.insertSelective(t_hk_material);
		return code;
	}

	/**
	 * 冻结物资管理人员
	 */
	@Transactional
	@Override
	public Integer freezematerialuser(T_hk_usermaterial t_hk_material) throws MyException {
		return suppliesPersonnelMapper.updateByPrimaryKeySelective(t_hk_material);
	}

	/**
	 * 更换物资管理人员
	 */
	@Transactional
	@Override
	public Integer updatmaterialuser(T_hk_usermaterial t_hk_material) throws MyException {
		return suppliesPersonnelMapper.updateByPrimaryKeySelective(t_hk_material);
	}

	/**
	 * 删除物资管理人员
	 */
	@Transactional
	@Override
	public Integer deletematerialuser(T_hk_usermaterial t_hk_material) throws MyException {
		t_hk_material.setIsdelete(1);
		t_hk_material.setIsenable(0);
		return suppliesPersonnelMapper.updateByPrimaryKeySelective(t_hk_material);
	}

	/**
	 * 查询物资管理人员列表
	 */
	@Override
	public Page<MaterialuserList> materialuserList(MaterialuserListVaule materialuserListVaule) throws MyException {
		return suppliesPersonnelMapper.materialuserList(materialuserListVaule);
	}
}

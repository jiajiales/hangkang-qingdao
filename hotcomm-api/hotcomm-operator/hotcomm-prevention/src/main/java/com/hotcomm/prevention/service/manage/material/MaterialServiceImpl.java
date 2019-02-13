package com.hotcomm.prevention.service.manage.material;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.Page;
import com.hotcomm.prevention.bean.mysql.common.vo.ExcelTips;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.DeviceParam;
import com.hotcomm.prevention.bean.mysql.manage.material.Selarea;
import com.hotcomm.prevention.bean.mysql.manage.material.SelmaterialList;
import com.hotcomm.prevention.bean.mysql.manage.material.Selmaterialuser;
import com.hotcomm.prevention.bean.mysql.manage.material.bean.T_hk_material;
import com.hotcomm.prevention.bean.mysql.manage.material.vaule.SelmaterialListVaule;
import com.hotcomm.prevention.db.mysql.manage.material.MaterialMapper;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.utils.ConverUtil;

@Service
public class MaterialServiceImpl implements MaterialService {

	@Autowired
	private MaterialMapper materialMapper;

	/**
	 * 新增物资存放点
	 */
	@Transactional
	@Override
	public Integer addmaterial(T_hk_material t_hk_material) throws MyException {
		// 获取当前时间
		t_hk_material.setAddtime(ConverUtil.dateForString(new Date()));
		return materialMapper.insertSelective(t_hk_material);
	}

	/**
	 * 根据物资存放点id查询详细信息
	 */
	@Override
	public SelmaterialList selmaterialOnid(Integer id) throws MyException {
		return materialMapper.selmaterialOnid(id);
	}

	/**
	 * 查询物资存放点列表
	 */
	@Override
	public Page<SelmaterialList> selmaterialList(SelmaterialListVaule selmaterialListVaule) throws MyException {
		return materialMapper.selmaterialList(selmaterialListVaule);
	}

	/**
	 * 修改物资点信息
	 */
	@Override
	public Integer updatematerial(T_hk_material t_hk_material) throws MyException {
		t_hk_material.setUpdatetime(ConverUtil.dateForString(new Date()));
		return materialMapper.updateByPrimaryKeySelective(t_hk_material);
	}

	/**
	 * 责任人选择列表
	 */
	@Override
	public List<Selmaterialuser> selmaterialuser() throws MyException {
		return materialMapper.selmaterialuser();
	}

	/**
	 * 行政区选择列表
	 */
	@Override
	public List<Selarea> selarea() throws MyException {
		return materialMapper.selarea();
	}

	/**
	 * 删除物资点
	 */
	@Override
	public Integer delmaterial(Integer id) throws MyException {
		T_hk_material t = new T_hk_material();
		t.setId(id);
		t.setIsdelete(1);
		t.setIsenable(0);
		return materialMapper.updateByPrimaryKeySelective(t);
	}

	/**
	 * 查询所有物资点
	 */
	@Override
	public List<SelmaterialList> selall() throws MyException {
		return materialMapper.selall();
	}

	@Transactional
	@Override
	public ExcelTips saveExcelList(List<T_hk_material> typeLists, Integer groupid) throws MyException {
		List<String> listSuccese = new ArrayList<>(); // 这个数组用于装准备插入数据库的内容
		List<String> listFailded = new ArrayList<>();// 这张表用于装过滤掉的内容,过滤条件:与设备辨识重复的设备
		for (int k = 0; k < typeLists.size(); k++) {// 循环查询从Excel传过来的行,行中的设备信息是否存在于数据库中
			// 调用mapper的保存方法
			T_hk_material type = new T_hk_material();
			Integer i = materialMapper.selectCode(typeLists.get(k).getNum());// 调用方法查询
			if (i > 0) {
				listFailded.add(typeLists.get(k).getNum());// 如果存在重复信息,这一行内容加入过滤容器中
			} else {
				listSuccese.add(typeLists.get(k).getNum());// 如果未发现重复,加入插入容器中
				type = typeLists.get(k);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
				System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
				materialMapper.insertSelectives(type);// 插入数据库设备表
				// materialMapper.addGroup(groupid,type.getId(),type.getAreacode(),type.getMaterialname());//插入设备与用户与项目的关联
			}
		}
		ExcelTips tips = new ExcelTips();
		tips.setSuccesefull(listSuccese.size());
		tips.setFailed(listFailded.size());
		tips.setMac(listFailded);
		return tips; // 返回前端,告诉工作人员那些设备是插入失败的
	}

}

package com.hotcomm.prevention.service.manage.material;

import java.util.List;
import com.github.pagehelper.Page;
import com.hotcomm.prevention.bean.mysql.common.vo.ExcelTips;
import com.hotcomm.prevention.bean.mysql.manage.material.Selarea;
import com.hotcomm.prevention.bean.mysql.manage.material.SelmaterialList;
import com.hotcomm.prevention.bean.mysql.manage.material.Selmaterialuser;
import com.hotcomm.prevention.bean.mysql.manage.material.bean.T_hk_material;
import com.hotcomm.prevention.bean.mysql.manage.material.vaule.SelmaterialListVaule;
import com.hotcomm.prevention.exception.MyException;

public interface MaterialService {

	Integer addmaterial(T_hk_material t_hk_material) throws MyException;

	SelmaterialList selmaterialOnid(Integer id) throws MyException;

	Page<SelmaterialList> selmaterialList(SelmaterialListVaule selmaterialListVaule) throws MyException;

	Integer updatematerial(T_hk_material t_hk_material) throws MyException;

	List<Selmaterialuser> selmaterialuser() throws MyException;

	List<Selarea> selarea() throws MyException;
	
	ExcelTips saveExcelList(List<T_hk_material> typeLists,Integer groupid)throws MyException;

	Integer delmaterial(Integer id) throws MyException;

	List<SelmaterialList> selall() throws MyException;
}

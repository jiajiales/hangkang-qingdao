package com.hotcomm.prevention.db.mysql.manage.material;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hotcomm.prevention.bean.mysql.manage.material.Selarea;
import com.hotcomm.prevention.bean.mysql.manage.material.SelmaterialList;
import com.hotcomm.prevention.bean.mysql.manage.material.Selmaterialuser;
import com.hotcomm.prevention.bean.mysql.manage.material.bean.T_hk_material;
import com.hotcomm.prevention.bean.mysql.manage.material.vaule.SelmaterialListVaule;
import com.hotcomm.prevention.exception.MyException;

import tk.mybatis.mapper.common.Mapper;

public interface MaterialMapper extends Mapper<T_hk_material> {

	SelmaterialList selmaterialOnid(@Param("id") Integer id);

	Page<SelmaterialList> selmaterialList(SelmaterialListVaule selmaterialListVaule);

	List<Selmaterialuser> selmaterialuser();

	Integer selectCode(@Param("num") String num) throws MyException;
	
	Integer insertSelectives(@Param("pa")T_hk_material pa) throws MyException;
	
	void addGroup(@Param("groupid") Integer groupid,@Param("id") Integer id,@Param("areacode") String areacode, @Param("materialname")  String materialname);
	
	List<Selarea> selarea();

	List<SelmaterialList> selall();
}

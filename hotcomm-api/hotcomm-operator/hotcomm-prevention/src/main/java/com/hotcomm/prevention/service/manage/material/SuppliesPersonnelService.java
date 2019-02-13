package com.hotcomm.prevention.service.manage.material;

import com.github.pagehelper.Page;
import com.hotcomm.prevention.bean.mysql.manage.material.MaterialuserList;
import com.hotcomm.prevention.bean.mysql.manage.material.bean.T_hk_usermaterial;
import com.hotcomm.prevention.bean.mysql.manage.material.vaule.MaterialuserListVaule;
import com.hotcomm.prevention.exception.MyException;

public interface SuppliesPersonnelService {

	Integer addmaterialuser(T_hk_usermaterial t_hk_material) throws MyException;

	Integer freezematerialuser(T_hk_usermaterial t_hk_material) throws MyException;

	Integer updatmaterialuser(T_hk_usermaterial t_hk_material) throws MyException;

	Integer deletematerialuser(T_hk_usermaterial t_hk_material) throws MyException;

	Page<MaterialuserList> materialuserList(MaterialuserListVaule materialuserListVaule) throws MyException;
}

package com.hotcomm.prevention.db.mysql.manage.material;

import com.github.pagehelper.Page;
import com.hotcomm.prevention.bean.mysql.manage.material.MaterialuserList;
import com.hotcomm.prevention.bean.mysql.manage.material.bean.T_hk_usermaterial;
import com.hotcomm.prevention.bean.mysql.manage.material.vaule.MaterialuserListVaule;
import tk.mybatis.mapper.common.Mapper;

public interface SuppliesPersonnelMapper extends Mapper<T_hk_usermaterial> {

	Page<MaterialuserList> materialuserList(MaterialuserListVaule materialuserListVaule);
}

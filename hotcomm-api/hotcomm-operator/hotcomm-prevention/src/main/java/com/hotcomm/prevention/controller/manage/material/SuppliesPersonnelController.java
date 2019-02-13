package com.hotcomm.prevention.controller.manage.material;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotcomm.prevention.bean.mysql.manage.PageInfo;
import com.hotcomm.prevention.bean.mysql.manage.material.MaterialuserList;
import com.hotcomm.prevention.bean.mysql.manage.material.bean.T_hk_usermaterial;
import com.hotcomm.prevention.bean.mysql.manage.material.vaule.MaterialuserListVaule;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.manage.material.SuppliesPersonnelService;
import com.hotcomm.prevention.utils.ApiResult;
import com.hotcomm.prevention.utils.ConverUtil;

/**
 * 物资管理人员接口
 * 
 * @author lkt
 *
 */
@RestController
@RequestMapping("material")
public class SuppliesPersonnelController {

	@Autowired
	private SuppliesPersonnelService suppliesPersonnelService;

	/*
	 * 新增物资管理人员
	 */
	@PostMapping("/addmaterialuser")
	public ApiResult addmaterialuser(T_hk_usermaterial t_hk_material) {
		Integer code = suppliesPersonnelService.addmaterialuser(t_hk_material);
		if (code <= 0) {
			throw new MyException("0", "操作失败！！");
		}
		return ApiResult.resultInfo("1", "成功", code);
	}

	/*
	 * 冻结物资管理人员
	 */
	@PostMapping("/freezematerialuser")
	public ApiResult freezematerialuser(T_hk_usermaterial t_hk_material) {
		Integer code = suppliesPersonnelService.freezematerialuser(t_hk_material);
		if (code <= 0) {
			throw new MyException("0", "操作失败！！");
		}
		return ApiResult.resultInfo("1", "成功", code);
	}

	/*
	 * 更换物资管理人员
	 */
	@PostMapping("/updatmaterialuser")
	public ApiResult updatmaterialuser(T_hk_usermaterial t_hk_material) {
		Integer code = suppliesPersonnelService.updatmaterialuser(t_hk_material);
		if (code <= 0) {
			throw new MyException("0", "操作失败！！");
		}
		return ApiResult.resultInfo("1", "成功", code);
	}

	/*
	 * 删除物资管理人员
	 */
	@PostMapping("/deletematerialuser")
	public ApiResult deletematerialuser(T_hk_usermaterial t_hk_material) {
		Integer code = suppliesPersonnelService.deletematerialuser(t_hk_material);
		if (code <= 0) {
			throw new MyException("0", "操作失败！！");
		}
		return ApiResult.resultInfo("1", "成功", code);
	}

	/*
	 * 查询物资管理人员列表
	 */
	@PostMapping("/materialuserList")
	public ApiResult materialuserList(MaterialuserListVaule materialuserListVaule) {
		PageHelper.startPage(materialuserListVaule.getPageNum(), materialuserListVaule.getPageSize());
		Page<MaterialuserList> page = suppliesPersonnelService.materialuserList(materialuserListVaule);
		List<MaterialuserList> list = ConverUtil.converPage(page, MaterialuserList.class);
		PageInfo<MaterialuserList> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(),
				list);
		return ApiResult.resultInfo("1", "成功", pageinfo);
	}
}

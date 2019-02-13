package com.hotcomm.prevention.controller.manage.material;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotcomm.prevention.bean.mysql.common.vo.ExcelTips;
import com.hotcomm.prevention.bean.mysql.manage.PageInfo;
import com.hotcomm.prevention.bean.mysql.manage.material.SelmaterialList;
import com.hotcomm.prevention.bean.mysql.manage.material.bean.T_hk_material;
import com.hotcomm.prevention.bean.mysql.manage.material.vaule.SelmaterialListVaule;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.manage.material.MaterialService;
import com.hotcomm.prevention.utils.ApiResult;
import com.hotcomm.prevention.utils.ConverUtil;

/**
 * 物资存放管理接口
 * 
 * @author lkt
 *
 */
@RestController
@RequestMapping("material")
public class MaterialController {

	@Autowired
	private MaterialService materialService;

	/*
	 * 新增物资存放点
	 */
	@PostMapping("/addmaterial")
	public ApiResult addmaterial(T_hk_material t_hk_material) {
		Integer code = materialService.addmaterial(t_hk_material);
		if (code <= 0) {
			throw new MyException("0", "操作失败！！");
		}
		return ApiResult.resultInfo("1", "成功", code);
	}

	/*
	 * 根据物资存放点id查询详细信息
	 */
	@PostMapping("/selmaterialOnid")
	public ApiResult selmaterialOnid(Integer id) {
		return ApiResult.resultInfo("1", "成功", materialService.selmaterialOnid(id));
	}

	/*
	 * 查询物资存放点列表
	 */
	@PostMapping("/selmaterialList")
	public ApiResult selmaterialList(SelmaterialListVaule selmaterialListVaule) {
		PageHelper.startPage(selmaterialListVaule.getPageNum(), selmaterialListVaule.getPageSize());
		Page<SelmaterialList> page = materialService.selmaterialList(selmaterialListVaule);
		List<SelmaterialList> list = ConverUtil.converPage(page, SelmaterialList.class);
		PageInfo<SelmaterialList> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(),
				list);
		return ApiResult.resultInfo("1", "成功", pageinfo);
	}

	/*
	 * 修改物资点信息
	 */
	@PostMapping("/updatematerial")
	public ApiResult updatematerial(T_hk_material t_hk_material) {
		Integer code = materialService.updatematerial(t_hk_material);
		if (code <= 0) {
			throw new MyException("0", "操作失败！！");
		}
		return ApiResult.resultInfo("1", "成功", code);
	}

	/*
	 * 责任人选择列表
	 */
	@PostMapping("/selmaterialuser")
	public ApiResult selmaterialuser() {
		return ApiResult.resultInfo("1", "成功", materialService.selmaterialuser());
	}

	/*
	 * 行政区选择列表
	 */
	@PostMapping("/selarea")
	public ApiResult selarea() {
		return ApiResult.resultInfo("1", "成功", materialService.selarea());
	}

	/*
	 * 删除物资点
	 */
	@PostMapping("/delmaterial")
	public ApiResult delmaterial(Integer id) {
		Integer code = materialService.delmaterial(id);
		if (code <= 0) {
			throw new MyException("0", "操作失败！！");
		}
		return ApiResult.resultInfo("1", "成功", code);
	}

	/*
	 * 查询所有物资点
	 */
	@PostMapping("/selall")
	public ApiResult selall() {
		return ApiResult.resultInfo("1", "成功", materialService.selall());
	}

	/*
	 * 物资管理批量导入
	 */
	@PostMapping("/bulkimport")
	public ApiResult bulkimport(MultipartFile file, HttpServletRequest request, Integer userid, Integer groupid)
			throws MyException {
		try {
			List<T_hk_material> typeLists = new ArrayList<T_hk_material>();
			System.out.println("开始");
			// 使用POI解析Excel文件
			// 如果是xls，使用HSSFWorkbook；2003年的excel 如果是xlsx，使用XSSFWorkbook
			// 2007年excel
			HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
			// 根据名称获得指定Sheet对象
			HSSFSheet hssfSheet = workbook.getSheetAt(0);
			for (Row row : hssfSheet) {
				T_hk_material Type = new T_hk_material();
				int rowNum = row.getRowNum();
				if (rowNum == 0) {// 跳出第一行 一般第一行都是表头没有数据意义
					continue;
				}
				if (row.getCell(1) != null) {// 第2列数据
					row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
					Type.setMaterialname(row.getCell(1).getStringCellValue());
				}
				if (row.getCell(2) != null) {// 第3列
					row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
					Type.setNum(row.getCell(2).getStringCellValue());
				}
				if (row.getCell(3) != null) {// 第4列
					row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
					Type.setCode(row.getCell(3).getStringCellValue());
				}
				if (row.getCell(4) != null) {// 第5列
					row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
					Type.setLat(row.getCell(4).getStringCellValue());
				}
				if (row.getCell(5) != null) {// 第7列
					row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
					Type.setLng(row.getCell(5).getStringCellValue());
				}
				if (row.getCell(6) != null) {// 第8列
					row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
					Type.setAddtime(row.getCell(6).getStringCellValue());
				}
				if (row.getCell(8) != null) {// 第8列
					row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
					Type.setAreacode(row.getCell(8).getStringCellValue());
				}
				if (row.getCell(9) != null) {// 第8列
					row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
					Type.setMsg(row.getCell(9).getStringCellValue());
				}
				typeLists.add(Type);
			}
			// 调用service执行保存typeLists的方法
			ExcelTips tips = materialService.saveExcelList(typeLists, groupid);
			return ApiResult.resultInfo("1", null, tips);
		} catch (Exception e) {
			return ApiResult.resultInfo("3", "导入失败：输入内容格式错误", null);
		}

	}
}

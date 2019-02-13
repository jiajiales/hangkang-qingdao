package com.hotcomm.prevention.controller.manage.group;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hotcomm.prevention.bean.mysql.common.vo.ExcelTips;
import com.hotcomm.prevention.bean.mysql.manage.PageInfo;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.DeviceParam;
import com.hotcomm.prevention.bean.mysql.manage.group.GroupDevState;
import com.hotcomm.prevention.bean.mysql.manage.group.GroupInfo;
import com.hotcomm.prevention.bean.mysql.manage.group.GroupInfoVO;
import com.hotcomm.prevention.bean.mysql.manage.group.GroupListVO;
import com.hotcomm.prevention.bean.mysql.manage.group.GroupMap;
import com.hotcomm.prevention.bean.mysql.manage.group.GroupMapImg;
import com.hotcomm.prevention.bean.mysql.manage.group.GroupMaps;
import com.hotcomm.prevention.bean.mysql.manage.group.GroupParams;
import com.hotcomm.prevention.bean.mysql.manage.group.GroupSiteImgpath;
import com.hotcomm.prevention.bean.mysql.manage.group.SiteDevVO;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.manage.group.GroupManageService;
import com.hotcomm.prevention.utils.ApiResult;
import com.hotcomm.prevention.utils.ConverUtil;

@RestController
public class GroupManageController {
	@Autowired
	private GroupManageService groupManageService;

	// 根据id查询项目
	@PostMapping("groupall/selectgroupById")
	public ApiResult selectgroupById(Integer groupId) throws MyException {
		List<GroupInfoVO> list = groupManageService.selectgroupById(groupId);
		return ApiResult.resultInfo("1", "成功", list);
	}

	// 新增项目
	@PostMapping("groupall/insertgroup")
	public ApiResult insertGroup(GroupParams groupParams, String listsite) throws MyException {

		Integer dgcount = groupManageService.countdg(groupParams.getModuleid(), groupParams.getGroupname(),
				groupParams.getItemnum(), groupParams.getId());
		if (dgcount > 0) {
			return ApiResult.resultInfo("0", "已有相同项目名称或编号！", null);
		} else {
			List<GroupMaps> sitelist = new Gson().fromJson(listsite, new TypeToken<List<GroupMaps>>() {
			}.getType());
			groupParams.setSitelist(sitelist);
			int a = groupManageService.insertgroup(groupParams);
			if (a > 0) {
				return ApiResult.resultInfo("1", "增加成功！", null);
			} else {
				return ApiResult.resultInfo("0", "增加失败！", null);
			}
		}

	}

	// 修改项目

	@PostMapping("groupall/updategroup")

	public ApiResult updateGroup(GroupParams groupParams, String listsite) throws MyException {

		Integer code = groupManageService.countdg(groupParams.getModuleid(), groupParams.getGroupname(),
				groupParams.getItemnum(), groupParams.getId());

		if (code > 0) {
			return ApiResult.resultInfo("0", "已有相同项目名称或编号！", null);
		} else {
			List<GroupMaps> sitelist = new Gson().fromJson(listsite, new TypeToken<List<GroupMaps>>() {
			}.getType());

			groupParams.setSitelist(sitelist);

			int i = groupManageService.updateGroup(groupParams);
			if (i > 0) {
				return ApiResult.resultInfo("1", "修改成功！", null);

			} else {
				return ApiResult.resultInfo("0", "修改失败！", null);

			}
		}

	}

	// 删除项目

	@PostMapping("groupall/delgroup")
	public ApiResult deleteGroup(Integer groupid) throws MyException {
		Integer code = groupManageService.selectGroupDev(groupid);
		if (code > 0) {
			return ApiResult.resultInfo("0", "项目下有设备,不能删除！", null);
		} else {
			Integer status = groupManageService.deleteGroup(groupid);
			if (status > 0) {
				return ApiResult.resultInfo("1", "删除成功！", null);

			} else {
				return ApiResult.resultInfo("0", "删除失败！", null);
			}
		}

	}

	// 查询项目列表
	@PostMapping("groupall/grouplist")

	public ApiResult selectGroupList(Integer userid, Integer moduleid, String starttime, String endtime,
			String keywords, Integer pageNum, Integer pageSize) throws MyException {
		PageHelper.startPage(pageNum, pageSize);
		Page<GroupListVO> page = groupManageService.selectGroupList(userid, moduleid, starttime, endtime, keywords,
				pageNum, pageSize);
		List<GroupListVO> list = ConverUtil.converPage(page, GroupListVO.class);
		PageInfo<GroupListVO> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
		return ApiResult.resultInfo("1", "成功", pageinfo);
	}

	// 设备坐标图片添加
	@PostMapping("groupall/insertdevmapimg")
	public ApiResult insertDevMapImg(String picnum, String picpath, String site, Integer itemid) {

		Integer a = groupManageService.insertDevMapImg(picnum, picpath, site, itemid);
		if (a > 0) {
			return ApiResult.resultInfo("1", "添加成功！", null);
		} else {
			return ApiResult.resultInfo("0", "删除失败！", null);
		}
	}

	// 删除坐标图片

	@PostMapping("groupall/deldevmapimg")
	public ApiResult deleteImg(Integer mapimgid, Integer moduleid) {
		Integer a = groupManageService.deleteImg(mapimgid, moduleid);
		if (a < 0) {
			return ApiResult.resultInfo("0", "项目下有设备，不可删除！", null);
		} else if (a > 0) {
			return ApiResult.resultInfo("1", "删除成功!", null);
		} else {
			return ApiResult.resultInfo("0", "删除失败！", null);
		}
	}

	// 地图项目弹框
	@PostMapping("groupall/mapgroupxq")
	public ApiResult groupInfo(Integer groupid, Integer moduleid, Integer userid) {
		GroupInfo list = groupManageService.groupInfo(groupid, moduleid, userid);
		return ApiResult.resultInfo("1", "成功", list);
	}/*
		 * 项目设备状态及数量(我的项目数据)
		 * 
		 * @param userid
		 * 
		 * @param moduleid
		 * 
		 * @return
		 */

	@PostMapping("groupall/groupdevstate")
	public ApiResult selectgroupdevstate(Integer userid, Integer moduleid, Integer groupid, Integer pageNum,
			Integer pageSize) {
		if (pageNum == null && pageSize == null) {
			return ApiResult.resultInfo("1", "成功", groupManageService.selectGroupList(userid, moduleid, groupid, 0, 0));
		}
		PageHelper.startPage(pageNum, pageSize);
		Page<GroupDevState> page = groupManageService.selectGroupList(userid, moduleid, groupid, pageNum, pageSize);
		List<GroupDevState> list = ConverUtil.converPage(page, GroupDevState.class);
		PageInfo<GroupDevState> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
		return ApiResult.resultInfo("1", "成功", pageinfo);
	}

	/**
	 * 项目下设备数量（设备分布（设备终端数量））
	 * 
	 * @param userid
	 * @param moduleid
	 * @param groupid
	 * @return
	 */
	@PostMapping("groupall/groupdevcount")
	public ApiResult groupDevCount(Integer userid, Integer moduleid, Integer groupid) {
		Integer count = groupManageService.groupDevCount(userid, moduleid, groupid);
		return ApiResult.resultInfo("1", "成功", count);
	}

	/**
	 * 根据groupid查设备地图
	 * 
	 * @param groupid
	 * @param userid
	 * @param moduleid
	 * @return
	 */
	@PostMapping("groupall/selectgroupmapbyid")
	public ApiResult selectGroupImgByid(Integer groupid, Integer userid, Integer moduleid) {
		List<GroupMapImg> list = groupManageService.selectGroupImgByid(groupid, userid, moduleid);
		return ApiResult.resultInfo("1", "成功", list);
	}

	/**
	 * 根据groupid查设备地图和楼层（设备分布（中间地图图片））
	 * 
	 * @param groupid
	 * @param userid
	 * @param moduleid
	 * @return
	 */
	@PostMapping("groupall/selectgroupsiteimgpath")
	public ApiResult groupSiteImgPath(Integer groupid, Integer userid, Integer moduleid) {
		List<GroupSiteImgpath> list = groupManageService.groupSiteImgPath(groupid, userid, moduleid);
		return ApiResult.resultInfo("1", "成功", list);
	}

	/**
	 * 根据地图id查设备设备
	 * 
	 * @param mapimgid
	 * @param moduleid
	 * @return
	 */
	@PostMapping("groupall/selectdevbysiteid")
	public ApiResult selectDevbySiteid(Integer mapimgid, Integer moduleid) {
		List<SiteDevVO> list = groupManageService.selectDevbySiteid(mapimgid, moduleid);
		return ApiResult.resultInfo("1", "成功", list);
	}

	/**
	 * 修改项目坐标
	 * 
	 * @param groupid
	 * @param x
	 * @param y
	 * @param groupcode
	 * @return
	 */
	@ResponseBody
	@PostMapping("groupall/updategroupxy")
	public ApiResult updateGroupPosition(Integer groupid, Double x, Double y, String groupcode) {

		Integer a = groupManageService.updateGroupPosition(groupid, x, y, groupcode);
		if (a > 0) {
			return ApiResult.resultInfo("1", "修改成功！", null);
		} else {
			return ApiResult.resultInfo("0", "修改失败！", null);
		}
	}

	/**
	 * 当前用户指定模块下的所有项目
	 * 
	 * @param userid
	 * @param moduleid
	 * @return
	 * @throws MyException
	 */
	@PostMapping("groupall/selectAllItem")
	public ApiResult selectAllItem(Integer userid, Integer moduleid) throws MyException {
		List<GroupListVO> selectAllItem = groupManageService.selectAllItem(userid, moduleid);
		return ApiResult.resultInfo("1", "成功", selectAllItem);
	}

	/**
	 * 修改设备坐标
	 * 
	 * @param id
	 * @param x
	 * @param y
	 * @param code
	 * @param moduleid
	 * @return
	 */
	@PostMapping("groupall/updatedevxy")
	public ApiResult updateDevPosition(Integer id, Double x, Double y, String code, Integer moduleid) {

		if (x > 0 && y > 0 && x < 180 && y < 180) {
			Integer a = groupManageService.updateDevPosition(id, x, y, code, moduleid);
			if (a > 0) {
				return ApiResult.resultInfo("1", "修改成功！", null);
			} else {
				return ApiResult.resultInfo("0", "修改失败！", null);
			}
		} else {
			return ApiResult.resultInfo("0", "请输入正确的参数！", null);
		}
	}

	// 根据groupid查设备地图
	@PostMapping("groupall/selectgroupmap")
	public ApiResult selectGroupMap(Integer userid, Integer moduleid) {
		List<GroupMap> list = groupManageService.selectGroupMap(userid, moduleid);
		return ApiResult.resultInfo("1", "成功", list);
	}

	// 根据项目ID查询项目下所有设备

	@PostMapping("groupall/findgroupdev")
	public ApiResult selectDevByGroupID(Integer groupid, Integer moduleid) {
		return ApiResult.resultInfo("1", "成功", groupManageService.selectDevByGroupID(groupid, moduleid));
	}

	/**
	 * 修改楼层图片
	 * 
	 * @param itemid
	 * @param picurl
	 * @return
	 * @throws MyException
	 */
	@PostMapping("group/updateItemPic")
	public ApiResult updateItemPic(Integer itemid, String picurl) throws MyException {
		return ApiResult.resultInfo("1", "成功", groupManageService.updateItemPic(itemid, picurl));
	}

	// 根据项目id查询组信息

	@PostMapping("groupall/updateItemPic")
	public ApiResult selectGroupInfo(Integer moduleid, Integer groupid) throws MyException {
		ApiResult apiResult = new ApiResult("1", "执行成功", groupManageService.selectGroupInfo(moduleid, groupid));
		return apiResult;
	}

	// 组报警处理统计

	@PostMapping("group/selectGroupAlarmHandleNums")
	public ApiResult selectGroupAlarmHandleNums(Integer moduleid, Integer userid, Integer queryType, Integer groupid)
			throws MyException {
		ApiResult apiResult = new ApiResult("1", "执行成功",
				groupManageService.selectGroupAlarmHandleNums(moduleid, userid, queryType, groupid));
		return apiResult;
	}

	// 组报警类型统计

	@PostMapping("group/selectGroupAlarmNums")
	public ApiResult selectGroupAlarmNums(Integer moduleid, Integer userid, Integer queryType, Integer groupid)
			throws MyException {
		ApiResult apiResult = new ApiResult("1", "执行成功",
				groupManageService.selectGroupAlarmNums(moduleid, userid, queryType, groupid));
		return apiResult;
	}

	// 表单导入

	@RequestMapping("group/Excelfile")

	public ApiResult upload(MultipartFile file, HttpServletRequest request, Integer moduleid, Integer userid,
			Integer groupid) throws MyException {

		try {
			List<DeviceParam> typeLists = new ArrayList<DeviceParam>();

			System.out.println("开始");
			// 使用POI解析Excel文件
			// 如果是xls，使用HSSFWorkbook；2003年的excel 如果是xlsx，使用XSSFWorkbook
			// 2007年excel
			HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
			// 根据名称获得指定Sheet对象
			HSSFSheet hssfSheet = workbook.getSheetAt(0);
			for (Row row : hssfSheet) {
				DeviceParam Type = new DeviceParam();
				int rowNum = row.getRowNum();
				if (rowNum == 0) {// 跳出第一行 一般第一行都是表头没有数据意义
					continue;
				}
				if (row.getCell(1) != null) {// 第2列数据
					row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
					Type.setDevnum(row.getCell(1).getStringCellValue());
				}

				if (row.getCell(2) != null) {// 第3列
					row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
					Type.setMac(row.getCell(2).getStringCellValue());
				}

				if (row.getCell(3) != null) {// 第4列
					row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
					Type.setCode(row.getCell(3).getStringCellValue());
				}

				// 转换为Integer类型
				if (row.getCell(4) != null) {// 第5列
					row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
					Type.setLng(row.getCell(4).getStringCellValue());
					// Type.setAdduserid(Integer.parseInt(row.getCell(3).getStringCellValue()));
				}
				if (row.getCell(5) != null) {// 第7列
					row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
					Type.setLat(row.getCell(5).getStringCellValue());
				}

				if (row.getCell(6) != null) {// 第8列
					row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
					Type.setCoordinate(Integer.parseInt(row.getCell(6).getStringCellValue()));
				}
				// 转换为日期类型
				if (row.getCell(7) != null) {// 第5列
					row.getCell(7).setCellType(Cell.CELL_TYPE_NUMERIC);
					Type.setInstalltime(HSSFDateUtil.getJavaDate(row.getCell(7).getNumericCellValue()));
				}

				if (row.getCell(8) != null) {// 第8列
					row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
					Type.setAddtime(row.getCell(8).getStringCellValue());
				}

				if (row.getCell(9) != null) {// 第8列
					row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
					Type.setBattery(Integer.parseInt(row.getCell(9).getStringCellValue()));
				}

				if (row.getCell(10) != null) {// 第8列
					row.getCell(10).setCellType(Cell.CELL_TYPE_NUMERIC);
					Type.setX(row.getCell(10).getNumericCellValue());
				}

				if (row.getCell(11) != null) {// 第8列
					row.getCell(11).setCellType(Cell.CELL_TYPE_NUMERIC);
					Type.setY(row.getCell(11).getNumericCellValue());
				}
				if (row.getCell(12) != null && row.getCell(12).toString() != "") {// 第8列
					row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
					Type.setDevtype(Integer.parseInt(row.getCell(12).getStringCellValue()));
				}
				if (row.getCell(13) != null) {// 第8列
					row.getCell(13).setCellType(Cell.CELL_TYPE_STRING);
					Type.setState(0);
				}
				if (row.getCell(14) != null) {// 第8列
					row.getCell(14).setCellType(Cell.CELL_TYPE_STRING);
					Type.setAlarmstate(Integer.parseInt(row.getCell(14).getStringCellValue()));
				}
				if (row.getCell(15) != null) {// 第8列
					row.getCell(15).setCellType(Cell.CELL_TYPE_STRING);
					Type.setLastvalue(row.getCell(15).getStringCellValue());
				}
				if (row.getCell(16) != null) {// 第8列
					row.getCell(16).setCellType(Cell.CELL_TYPE_STRING);
					Type.setMaxAlarmvalue(row.getCell(16).getStringCellValue());
				}
				if (row.getCell(17) != null) {// 第8列
					row.getCell(17).setCellType(Cell.CELL_TYPE_STRING);
					Type.setMinAlarmvalue(row.getCell(17).getStringCellValue());
				}
				if (row.getCell(18) != null) {// 第8列
					row.getCell(18).setCellType(Cell.CELL_TYPE_STRING);
					Type.setJgCoverName(row.getCell(18).getStringCellValue());
				}
				if (row.getCell(19) != null) {// 第8列
					row.getCell(19).setCellType(Cell.CELL_TYPE_STRING);
					Type.setJgManu(row.getCell(19).getStringCellValue());
				}
				if (row.getCell(20) != null) {// 第8列
					row.getCell(20).setCellType(Cell.CELL_TYPE_STRING);
					Type.setJgMaterial(row.getCell(20).getStringCellValue());
				}

				if (row.getCell(21) != null) {// 第8列
					row.getCell(21).setCellType(Cell.CELL_TYPE_STRING);
					Type.setJgPurpose(Integer.parseInt(row.getCell(21).getStringCellValue()));
				}
				if (row.getCell(22) != null) {// 第8列
					row.getCell(22).setCellType(Cell.CELL_TYPE_STRING);
					Type.setJgLoadbear(Integer.parseInt(row.getCell(22).getStringCellValue()));
				}
				if (row.getCell(23) != null) {// 第8列
					row.getCell(23).setCellType(Cell.CELL_TYPE_NUMERIC);
					Type.setLjtHeight(row.getCell(23).getNumericCellValue());
				}
				if (row.getCell(24) != null) {// 第8列
					row.getCell(24).setCellType(Cell.CELL_TYPE_STRING);
					Type.setLjtAlarmvalue(row.getCell(24).getNumericCellValue());
				}
				if (row.getCell(25) != null) {// 第8列
					row.getCell(25).setCellType(Cell.CELL_TYPE_STRING);
					Type.setPm(row.getCell(25).getStringCellValue());
				}
				if (row.getCell(26) != null) {// 第8列
					row.getCell(26).setCellType(Cell.CELL_TYPE_STRING);
					Type.setPmOne(row.getCell(26).getStringCellValue());
				}
				if (row.getCell(27) != null) {// 第8列
					row.getCell(27).setCellType(Cell.CELL_TYPE_STRING);
					Type.setPmNoiseval(row.getCell(27).getStringCellValue());
				}
				if (row.getCell(28) != null) {// 第8列
					row.getCell(28).setCellType(Cell.CELL_TYPE_STRING);
					Type.setPmTemval(row.getCell(28).getStringCellValue());
				}
				if (row.getCell(29) != null) {// 第8列
					row.getCell(29).setCellType(Cell.CELL_TYPE_STRING);
					Type.setPmHumval(row.getCell(29).getStringCellValue());
				}
				if (row.getCell(30) != null) {// 第8列
					row.getCell(30).setCellType(Cell.CELL_TYPE_STRING);
					Type.setPmLight(row.getCell(30).getStringCellValue());
				}
				if (row.getCell(31) != null) {// 第8列
					row.getCell(31).setCellType(Cell.CELL_TYPE_STRING);
					Type.setSlVoltage(row.getCell(31).getStringCellValue());
				}
				if (row.getCell(32) != null) {// 第8列
					row.getCell(32).setCellType(Cell.CELL_TYPE_STRING);
					Type.setSlAmpere(row.getCell(32).getStringCellValue());
				}
				if (row.getCell(33) != null) {// 第8列
					row.getCell(33).setCellType(Cell.CELL_TYPE_STRING);
					Type.setSlWatt(row.getCell(33).getStringCellValue());
				}
				if (row.getCell(34) != null) {// 第8列
					row.getCell(34).setCellType(Cell.CELL_TYPE_STRING);
					Type.setSlLighteness(row.getCell(34).getStringCellValue());
				}
				if (row.getCell(35) != null) {// 第8列
					row.getCell(35).setCellType(Cell.CELL_TYPE_STRING);
					Type.setSlElectricity(row.getCell(35).getStringCellValue());
				}
				if (row.getCell(36) != null) {// 第8列
					row.getCell(36).setCellType(Cell.CELL_TYPE_STRING);
					Type.setSlOfftime(row.getCell(36).getStringCellValue());
				}

				if (row.getCell(37) != null) {// 第8列
					row.getCell(37).setCellType(Cell.CELL_TYPE_STRING);
					Type.setSlOntime(row.getCell(37).getStringCellValue());
				}
				if (row.getCell(38) != null) {// 第8列
					row.getCell(38).setCellType(Cell.CELL_TYPE_STRING);
					Type.setAlarmset(Integer.parseInt(row.getCell(38).getStringCellValue()));
				}
				if (row.getCell(39) != null) {// 第8列
					row.getCell(39).setCellType(Cell.CELL_TYPE_STRING);
					Type.setWtElectricity(row.getCell(39).getStringCellValue());
				}
				if (row.getCell(40) != null) {// 第8列
					row.getCell(40).setCellType(Cell.CELL_TYPE_STRING);
					Type.setYwjLastvalue1(Integer.parseInt(row.getCell(40).getStringCellValue()));
				}
				if (row.getCell(41) != null) {// 第8列
					row.getCell(41).setCellType(Cell.CELL_TYPE_STRING);
					Type.setYwjLastvalue2(row.getCell(41).getStringCellValue());
				}
				if (row.getCell(42) != null) {// 第8列
					row.getCell(42).setCellType(Cell.CELL_TYPE_STRING);
					Type.setYwjLastvalue3(row.getCell(42).getStringCellValue());
				}
				if (row.getCell(43) != null) {// 第8列
					row.getCell(43).setCellType(Cell.CELL_TYPE_STRING);
					Type.setYwjLastvalue4(row.getCell(43).getStringCellValue());
				}
				if (row.getCell(44) != null) {// 第8列
					row.getCell(44).setCellType(Cell.CELL_TYPE_STRING);
					Type.setYwjLastvalue5(row.getCell(44).getStringCellValue());
				}
				if (row.getCell(45) != null) {// 第8列
					row.getCell(45).setCellType(Cell.CELL_TYPE_STRING);
					Type.setYwjLastvalue6(row.getCell(45).getStringCellValue());
				}
				if (row.getCell(46) != null) {// 第8列
					row.getCell(46).setCellType(Cell.CELL_TYPE_STRING);
					Type.setYwjLlusminus(row.getCell(46).getStringCellValue());
				}

				Type.setModuleid(moduleid);
				Type.setAdduserid(userid);
				typeLists.add(Type);

			}
			// 调用service执行保存typeLists的方法
			ExcelTips tips = groupManageService.saveExcelList(typeLists, groupid);
			return ApiResult.resultInfo("1", null, tips);
		} catch (Exception e) {
			return ApiResult.resultInfo("3", "导入失败：输入内容格式错误", null);
		}

	}

	@PostMapping("groupall/sselectGroupDevInfo")
	public ApiResult selectGroupDevInfo(Integer moduleid, Integer groupid) throws MyException {
		ApiResult apiResult = new ApiResult("1", "执行成功", groupManageService.selectGroupDevInfo(moduleid, groupid));
		return apiResult;
	}

	// App查询工单，报警，事件未处理数
	@PostMapping("groupall/appMapDevnum")
	public ApiResult AppMapDevnum(Integer userid) {
		return ApiResult.resultInfo("1", "成功", groupManageService.AppMapDevnum(userid));
	}

	// 查询行政区域
	@PostMapping("groupall/selectAreaInfo")
	public ApiResult selectAreaInfo(Integer userid) throws MyException {
		ApiResult apiResult = new ApiResult("1", "执行成功", groupManageService.selectAreaInfo());
		return apiResult;
	}

}

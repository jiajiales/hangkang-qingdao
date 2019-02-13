package com.hotcomm.data.web.controller.service;

import java.io.IOException;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hotcomm.data.bean.params.service.device.DeviceBatchParams;
import com.hotcomm.data.bean.params.service.device.LoraABPDevicePageParam;
import com.hotcomm.data.bean.params.service.device.LoraABPDeviceParam;
import com.hotcomm.data.bean.params.service.device.LoraOTAADevicePageParam;
import com.hotcomm.data.bean.params.service.device.LoraOTAADeviceParam;
import com.hotcomm.data.bean.vo.device.LoraABPDeviceVO;
import com.hotcomm.data.bean.vo.device.LoraOTAADeviceVO;
import com.hotcomm.data.bean.vo.sys.MemberVO;
import com.hotcomm.data.comm.PageInfo;
import com.hotcomm.data.service.DeviceService;
import com.hotcomm.data.utils.ExcelData;
import com.hotcomm.data.utils.ExportExcelUtils;
import com.hotcomm.data.utils.UUIDUtils;
import com.hotcomm.data.web.controller.comm.BaseController;
import com.hotcomm.framework.annotation.Function;
import com.hotcomm.framework.annotation.LogEvent;
import com.hotcomm.framework.annotation.Param;
import com.hotcomm.framework.annotation.ParamsValidate;
import com.hotcomm.framework.web.exception.HKException;
import com.hotcomm.framework.web.result.ApiResult;

@RestController
public class DeviceController extends BaseController {

	@Resource
	private DeviceService deviceService;

	final String ABP_TEMP_DOWNLOAD_ADDRESS = "static/download/device/ABP.xlsx";

	final String OTAA_TEMP_DOWNLOAD_ADDRESS = "static/download/device/OTAA.xlsx";

	/**
	 * 新增设备-LORA-ABP
	 * @param param
	 * @return
	 * @throws HKException
	 */
	@Function(key = "device-lora-abp-add")
	@ParamsValidate(validateParams = { @Param(key = "code", code = "DEVICE_F02"),
			                           @Param(key = "groupId", code = "DEVICE_F07"),
			                           @Param(key = "type", code = "DEVICE_F08"),
			                           @Param(key = "protocol", code = "DEVICE_F09") })
	@RequestMapping("/device/lora/abp/add")
	@LogEvent(code = "DEVICE_ADD")
	public ApiResult addAPBDev(LoraABPDeviceParam param) throws HKException {
		param.setCreateUser(getLoginMember().getMemberName());
		deviceService.addLoraABPDev(param);
		return ApiResult.success();
	}

	/**
	 * 新增设备-LORA-OTAA
	 * @param param
	 * @return
	 * @throws HKException
	 */
	@Function(key = "device-lora-otaa-add")
	@ParamsValidate(validateParams = { @Param(key = "code", code = "DEVICE_F02"),
			                           @Param(key = "mac", code = "DEVICE_F03"),
			                           @Param(key = "groupId", code = "DEVICE_F07"),
			                           @Param(key = "type", code = "DEVICE_F08"),
			                           @Param(key = "protocol", code = "DEVICE_F09") })
	@RequestMapping("/device/lora/otaa/add")
	@LogEvent(code = "DEVICE_ADD")
	public ApiResult addOTAADev(LoraOTAADeviceParam param) throws HKException {
		param.setCreateUser(getLoginMember().getMemberName());
		deviceService.addLoraOTAADev(param);
		return ApiResult.success();
	}

	/**
	 * 获取设备-LORA-ABP
	 * @param deviceId
	 * @return
	 * @throws HKException
	 */
	@Function(key = "device-lora-abp-get")
	@RequestMapping("/device/lora/abp/get")
	@LogEvent(code = "DEVICE_GET")
	public ApiResult getAPBDev(Integer deviceId) throws HKException {
		return ApiResult.success(deviceService.getLoraABP(deviceId));
	}

	/**
	 * 获取设备-LORA-OTAA
	 * @param deviceId
	 * @return
	 * @throws HKException
	 */
	@Function(key = "device-lora-otaa-get")
	@RequestMapping("/device/lora/otaa/get")
	@LogEvent(code = "DEVICE_GET")
	public ApiResult getOTAADev(Integer deviceId) throws HKException {
		return ApiResult.success(deviceService.getLoraOTAA(deviceId));
	}

	/**
	 * 软删除设备
	 * @param deviceId
	 * @return
	 * @throws HKException
	 */
	@Function(key = "device-del")
	@ParamsValidate(validateParams = { @Param(key = "deviceId", code = "DEVICE_F01") })
	@RequestMapping("/device/del")
	@LogEvent(code = "DEVICE_DEL")
	public ApiResult delById(Integer deviceId) throws HKException {
		deviceService.delDev(deviceId);
		return ApiResult.success();
	}

	/**
	 * 批量分配设备组
	 * @param params
	 * @return
	 * @throws HKException
	 */
	@Function(key = "device-batch-allotDeviceGroup")
	@ParamsValidate(validateParams = { @Param(key = "deviceGroupId", code = "DEVICE_F07"),
			                           @Param(key = "deviceIds", code = "DEVICE_F11") })
	@RequestMapping("/device/batch/allotDeviceGroup")
	@LogEvent(code = "DEVICE_BATCH_ALLOT_DEVICEGROUP")
	public ApiResult batchAllotDeviceGroup(DeviceBatchParams params) throws HKException {
		deviceService.batchAllotDeviceGroup(params);
		return ApiResult.success();
	}

	/**
	 * 分页查询-ABP设备列表
	 * @param params
	 * @return
	 * @throws HKException
	 */
	@Function(key = "device-lora-abp-page")
	@RequestMapping("/device/lora/abp/page")
	@LogEvent(code = "DEVICE_PAG")
	public PageInfo<LoraABPDeviceVO> pageABPDevs(LoraABPDevicePageParam params) throws HKException {
		MemberVO member = getLoginMember();
		params.setMemberId(member.getId());
		params.setMemberType(member.getUserType());
		return deviceService.pageLoraABP(params);
	}

	/**
	 * 分页查询-OTAA设备列表
	 * @param params
	 * @return
	 * @throws HKException
	 */
	@Function(key = "device-lora-otaa-page")
	@RequestMapping("/device/lora/otaa/page")
	@LogEvent(code = "DEVICE_PAG")
	public PageInfo<LoraOTAADeviceVO> pageOTAADevs(LoraOTAADevicePageParam params) throws HKException {
		MemberVO member = getLoginMember();
		params.setMemberId(member.getId());
		params.setMemberType(member.getUserType());
		return deviceService.pageLoraOTAA(params);
	}

	/**
	 * 更新ABP设备信息
	 * @param params
	 * @return
	 * @throws HKException
	 */
	@Function(key = "device-lora-abp-update")
	@ParamsValidate(validateParams = { @Param(key = "deviceId", code = "DEVICE_F01"),
			                           @Param(key = "code", code = "DEVICE_F02"),
						               @Param(key = "groupId", code = "DEVICE_F07"),
						               @Param(key = "type", code = "DEVICE_F08"),
						               @Param(key = "protocol", code = "DEVICE_F09"),
						               @Param(key = "status", code = "DEVICE_F10") })
	@RequestMapping("/device/lora/abp/update")
	@LogEvent(code = "DEIVCE_UPD")
	public ApiResult updateDevABPById(LoraABPDeviceParam params) throws HKException {
		this.deviceService.updateLoraABPDev(params);
		return ApiResult.success();
	}

	/**
	 * 更新OTAA设备信息
	 * @param params
	 * @return
	 * @throws HKException
	 */
	@Function(key = "device-lora-otaa-update")
	@ParamsValidate(validateParams = { @Param(key = "deviceId", code = "DEVICE_F01"),
			                           @Param(key = "code", code = "DEVICE_F02"),
									   @Param(key = "mac", code = "DEVICE_F03"),
            						   @Param(key = "groupId", code = "DEVICE_F07"),
            						   @Param(key = "type", code = "DEVICE_F08"),
            						   @Param(key = "protocol", code = "DEVICE_F09"),
            						   @Param(key = "status", code = "DEVICE_F10") })
	@RequestMapping("/device/lora/otaa/update")
	@LogEvent(code = "DEIVCE_UPD")
	public ApiResult updateDevOTAAById(LoraOTAADeviceParam params) throws HKException {
		this.deviceService.updateLoraOTAADev(params);
		return ApiResult.success();
	}

	/**
	 * Lora-ABP-设备导入
	 * @param file
	 * @param request
	 * @param groupid
	 * @param memberId
	 * @return
	 * @throws IOException
	 */
	@Function(key = "device-lora-abp-import")
	@ParamsValidate(validateParams = { @Param(key = "groupid", code = "DEVICE_F07") })
	@RequestMapping(value = "/device/lora/abp/import", method = RequestMethod.POST)
	//@LogEvent(code = "DEVICE_IMPORT")
	public @ResponseBody Object importABP(@RequestParam("file") MultipartFile file, HttpServletRequest request, Integer groupid) throws IOException {
		MemberVO member = getLoginMember();
		return this.deviceService.importLoraABPDev(file.getInputStream(), groupid, member.getMemberName());
	}

	/**
	 * Lora-OTAA-设备导入
	 * @param file
	 * @param request
	 * @param groupid
	 * @param memberId
	 * @return
	 * @throws IOException
	 */
	@Function(key = "device-lora-otaa-import")
	@ParamsValidate(validateParams = { @Param(key = "groupid", code = "DEVICE_F07") })
	@RequestMapping(value = "/device/lora/otaa/import", method = RequestMethod.POST)
	//@LogEvent(code = "DEVICE_IMPORT")
	public @ResponseBody Object importOTAA(@RequestParam("file") MultipartFile file, HttpServletRequest request, Integer groupid) throws IOException {
		MemberVO member = getLoginMember();
		return this.deviceService.importLoraOTAADev(file.getInputStream(), groupid, member.getMemberName());
	}

	/**
	 * Lora-ABP-设备导出
	 * @param devGroupId
	 * @throws Exception
	 */
	@Function(key = "device-lora-abp-export")
	@RequestMapping(value = "/device/lora/abp/export", method = RequestMethod.GET)
	// @LogEvent(code = "DEVICE_OUTPUT")
	public void exportABPFile(HttpServletResponse response, HttpServletRequest request, Integer devGroupId) throws Exception {
		MemberVO member = getLoginMember();
		ExcelData data = this.deviceService.exportLoraABPDev(devGroupId, member.getId());
		String fileName = "ABP-_设备列表_" + UUIDUtils.get32BitUUID() + ".xlsx";

		try {
			String agent = (String) request.getHeader("USER-AGENT");

			if (agent != null && agent.indexOf("Firefox") != -1)
				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			else
				fileName = URLEncoder.encode(fileName, "UTF-8");

			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.setHeader("content-Type", "application/vnd.ms-excel");
			ExportExcelUtils.exportExcel(data, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Lora-OTAA-设备导出
	 * @param devGroupId
	 * @throws Exception
	 */
	@Function(key = "device-lora-otaa-export")
	@RequestMapping(value = "/device/lora/otaa/export", method = RequestMethod.GET)
	// @LogEvent(code = "DEVICE_OUTPUT")
	public void exportOTAAFile(HttpServletResponse response, HttpServletRequest request, Integer devGroupId) throws Exception {
		MemberVO member = getLoginMember();
		ExcelData data = this.deviceService.exportLoarOTAADev(devGroupId, member.getId());
		String fileName = "OTAA-_设备列表_" + UUIDUtils.get32BitUUID() + ".xlsx";

		try {
			String agent = (String) request.getHeader("USER-AGENT");

			if (agent != null && agent.indexOf("Firefox") != -1)
				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			else
				fileName = URLEncoder.encode(fileName, "UTF-8");

			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.setHeader("content-Type", "application/vnd.ms-excel");
			ExportExcelUtils.exportExcel(data, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取设备接收数量-LORA
	 * @param code
	 * @return
	 * @throws HKException
	 */
	@Function(key = "device-lora-get-receiveNum")
	@ParamsValidate(validateParams = { @Param(key = "code", code = "DEVICE_F02") })
	@RequestMapping("/device/lora/getReceiveNum")
	@LogEvent(code = "DEVICE_GET_RECEIVENUM")
	public ApiResult getReceiveNumByDeviceCode(String code) throws HKException {
		return ApiResult.success(deviceService.getReceiveNumByDeviceCode(code));
	}

}

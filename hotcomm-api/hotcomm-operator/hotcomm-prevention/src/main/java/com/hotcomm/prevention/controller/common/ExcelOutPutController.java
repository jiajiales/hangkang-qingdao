package com.hotcomm.prevention.controller.common;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.prevention.bean.mysql.datashow.dc.ExcelData;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.T_device_all;
import com.hotcomm.prevention.service.datashow.dc.DcService;
import com.hotcomm.prevention.utils.ExcelOutPutUtils;
 
@RestController
@RequestMapping("/excel")
public class ExcelOutPutController {
	
	@Autowired
	DcService dcService;
	
	 @RequestMapping(value = "/export", method = RequestMethod.GET)
	    public void excel(HttpServletResponse response) throws Exception {
	        ExcelData data = new ExcelData();
	        data.setName("hello");
	        List<String> titles = new ArrayList();
	        titles.add("设备编号");
	        titles.add("mac地址");
	        titles.add("安装位置");
	        titles.add("经度");
	        titles.add("纬度");
	        titles.add("经纬度类型");
	        titles.add("设备安装时间");
	        titles.add("可用");
	        titles.add("添加入库时间");
	        titles.add("添加人id");
	        titles.add("设备最后一次报警时间");
	        titles.add("电池电量");
	        titles.add("建筑楼层平面图设备x坐标");
	        titles.add("建筑楼层平面图设备y坐标");
	        titles.add("责任人ID");
	        titles.add("设备类型");
	        titles.add("设备状态");
	        titles.add("报警状态");
	        titles.add("设备最后一次监测到的值");
	        titles.add("最高报警阀值");
	        titles.add("最低报警阀值");
	        titles.add("井盖名称");
	        titles.add("井盖制作厂家");
	        titles.add("材料");
	        titles.add("用途类型");
	        titles.add("承重类型");
	        titles.add("垃圾桶容积高度");
	        titles.add("垃圾满溢报警阈值");
	        titles.add("pm2.5值μg/m3(pm)");
	        titles.add("pm1.0ug/m3");
	        titles.add("噪音值dB(pm)");
	        titles.add("温度值°C(pm)");
	        titles.add("湿度值%RH(pm)");
	        titles.add("光照度/Lux");
	        titles.add("路灯电压值V(sl)");
	        titles.add("路灯电流值A(sl)");
	        titles.add("路灯功率值W(sl)");
	        titles.add("路灯亮度值cd/m2");
	        titles.add("路灯电量(sl)");
	        titles.add("路灯关闭时间(sl)");
	        titles.add("路灯开启时间(sl)");
	        titles.add("0:不报警；1:低于最低报警；2:高于最高报警,3:低于最低或高于最高报警");
	        titles.add("用水量(wt)");
	        titles.add("液位M,");
	        titles.add("压力MPa");
	        titles.add("压力Bar");
	        titles.add("压力KPa");
	        titles.add("温度℃");
	        titles.add("流量m3/h");
	        titles.add("1:低于最低水位,报警；0:高于最高水位,报警");
	        data.setTitles(titles);
 
	        List<List<Object>> rows = new ArrayList();
	        List<T_device_all> list = dcService.selectDevice(1);
	        
	        for (int i = 0; i < list.size(); i++) {
	        	List<Object> row = new ArrayList();
	        	row.add(list.get(i).getDevnum());
	        	row.add(list.get(i).getMac());
	        	row.add(list.get(i).getCode());
	        	row.add(list.get(i).getLat());
	        	row.add(list.get(i).getLng());
	        	row.add(list.get(i).getCoordinate());
	        	row.add(list.get(i).getInstalltime());
	        	row.add(list.get(i).getIsenable());
	        	row.add(list.get(i).getAddtime());
	        	row.add(list.get(i).getAdduserid());//***adduser
	        	row.add(list.get(i).getLastalarmtime());
	        	row.add(list.get(i).getBattery());
	        	row.add(list.get(i).getX());
	        	row.add(list.get(i).getY());
	        	row.add(list.get(i).getOwn_id());
	        	row.add(list.get(i).getModuleid());
	        	row.add(list.get(i).getState());
	        	row.add(list.get(i).getAlarmstate());
	        	row.add(list.get(i).getLastvalue());
	        	row.add(list.get(i).getMax_alarmvalue());
	        	row.add(list.get(i).getMin_alarmvalue());
	        	row.add(list.get(i).getJg_coverName());
	        	row.add(list.get(i).getJg_manu());
	        	row.add(list.get(i).getJg_material());
	        	row.add(list.get(i).getJg_purpose());
	        	row.add(list.get(i).getJg_loadbear());
	        	row.add(list.get(i).getLjt_height());
	        	row.add(list.get(i).getLjt_alarmvalue());
	        	row.add(list.get(i).getPm());
	        	row.add(list.get(i).getPm_one());
	        	row.add(list.get(i).getPm_noiseval());
		        row.add(list.get(i).getPm_temval());
		        row.add(list.get(i).getPm_humval());
		        row.add(list.get(i).getPm_light());
		        row.add(list.get(i).getSl_voltage());
		        row.add(list.get(i).getSl_ampere());
		        row.add(list.get(i).getSl_watt());
		        row.add(list.get(i).getSl_lighteness());
		        row.add(list.get(i).getSl_electricity());
		        row.add(list.get(i).getSl_offtime());
		        row.add(list.get(i).getSl_ontime());
		        row.add(list.get(i).getAlarmset());
		        row.add(list.get(i).getWt_electricity());
		        row.add(list.get(i).getYwj_lastvalue1());
		        row.add(list.get(i).getYwj_lastvalue2());
		        row.add(list.get(i).getYwj_lastvalue3());
		        row.add(list.get(i).getYwj_lastvalue4());
		        row.add(list.get(i).getYwj_lastvalue5());
		        row.add(list.get(i).getYwj_lastvalue6());
		        row.add(list.get(i).getYwj_plusminus());
		        rows.add(i, row);
	        }
		        
	        data.setRows(rows);
 
 
	        //生成本地
	        /*File f = new File("c:/test.xlsx");
	        FileOutputStream out = new FileOutputStream(f);
	        ExportExcelUtils.exportExcel(data, out);
	        out.close();*/
	        SimpleDateFormat fdate=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	        String fileName=fdate.format(new Date())+".xlsx";
	        ExcelOutPutUtils.exportExcel(response,fileName,data);
	    }
}
package com.hotcomm.prevention.bean.mysql.manage.event;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AlarmDev {
	private Integer id;// 设备ID
	private String devnum;// 设备编号
	private String mac;// 设备mac
	private String lng;// 经度
	private String lat;// 维度
	private String code;// 安装位置
	private String ownid;// 责任人ID,设备和项目责任人
	private String contacts;// 责任人或联系人
	private Integer moduleid;//
	private String modulename;// 模块名称
	private String alarmtime;// 报警时间
	private String videopath;// 视频路径
	private int alarmid;// 报警id
}

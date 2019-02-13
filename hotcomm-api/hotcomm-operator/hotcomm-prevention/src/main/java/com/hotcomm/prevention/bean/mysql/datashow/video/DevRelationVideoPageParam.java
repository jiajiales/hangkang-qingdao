package com.hotcomm.prevention.bean.mysql.datashow.video;

import java.util.List;

import com.hotcomm.prevention.bean.mysql.manage.PageParam;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class DevRelationVideoPageParam extends PageParam{
	
	private List<Integer> devId;

	private Integer moduleid;
	
	private String startTime;
	
	private String endTime;
	
	private String lng;
	
	private String lat;
	
	private List<Integer> videoId;
	
	private String address;
	
	private String devNum;
	
	private Integer state;

}

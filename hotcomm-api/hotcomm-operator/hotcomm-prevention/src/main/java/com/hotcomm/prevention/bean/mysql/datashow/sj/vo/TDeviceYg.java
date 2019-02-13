package com.hotcomm.prevention.bean.mysql.datashow.sj.vo;

import java.util.Date;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class TDeviceYg {
	
	    private Integer id;//设备id

	    private String devnum;//设备编号
	  
	    private String mac;//设备Mac
	    
	    private String code;//设备详细位置

	    private Date addtime;//设备添加时间

	    private Boolean isdelete;//是否删除,1：删除，0：不删除

	    private Boolean isenable;//是否可用,1：可用；0：不可用

	    private Integer adduserid;//添加人

	    private Double lat;//纬度

	    private Double lng;//经度
	    
	    private Integer  groupid;//设备组
	    
	    
	    private Double x;//相对于地图的x方向位置

	    private Double y;//相对于地图y方向的位置

	    private Integer state;//当前设备状态,0：正常；1：报警，2：故障，3：其他

	    private Date lastalarmtime;//保留字段

	    private Integer battery;//电池电量
	    
	    private Integer own_id;//责任人ID
	    
}

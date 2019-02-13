package com.hot.manage.entity.common.patrol;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class THKSignParam {
	
	private Integer id;//定点签到设备ID
	
    private Integer itemid;//项目ID
	
    private String address;
	
    private String lng;
	
    private String lat;
	
    private String devnum;
	
    private String addtime;
    
    private String signid;//签到人ID，可以是多个，以,分割

}

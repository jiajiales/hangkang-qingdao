package com.hot.manage.entity.yg.item;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTWorkGbAppVaule {
	
	//(value = "工单id")
	private Integer woid;

	//(value = "处理描述")
	private String problemdesc;

	//(value = "选填，图片资源路径;多个的时候用中文分号；隔开-如:资源1；资源2；资源3")
	private String pictureurl;

}

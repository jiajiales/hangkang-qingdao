package com.hotcomm.prevention.bean.mysql.manage.group;

import java.io.Serializable;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class GroupSiteImgpath  implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer itemPicId;
    private String site;
    private String picpath;
    private Integer groupid;
    private Integer devcount;
}

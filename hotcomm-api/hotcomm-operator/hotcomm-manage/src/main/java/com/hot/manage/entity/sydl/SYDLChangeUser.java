package com.hot.manage.entity.sydl;

import java.util.List;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SYDLChangeUser {

	private List<Integer> devid;
	
    private Integer moduleid;
	
	private Integer ownid;
}

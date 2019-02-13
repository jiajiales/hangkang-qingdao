package com.hot.manage.entity.sxdy.value;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SXDYChangeUser {
	
    private List<Integer> devid;
	
    private Integer moduleid;
	
	private Integer ownid;
}

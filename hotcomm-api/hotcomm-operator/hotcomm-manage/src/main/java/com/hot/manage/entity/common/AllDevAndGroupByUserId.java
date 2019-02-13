package com.hot.manage.entity.common;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AllDevAndGroupByUserId implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<AllDevByUserid> allDevByUserid;

	private List<AllGroupByUserId> allGroupByUserId;
	
}

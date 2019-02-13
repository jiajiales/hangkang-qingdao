package com.hotcomm.data.bean.vo.sys;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.hotcomm.data.bean.entity.sys.Resource;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class MemberResource implements Serializable {

	private static final long serialVersionUID = 2025003627782358399L;

	private MemberVO member;

	private List<Resource> resources;
	
	private Map<Object,Map<String, Boolean>> authButtons;
}

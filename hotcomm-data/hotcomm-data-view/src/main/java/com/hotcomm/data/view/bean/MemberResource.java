package com.hotcomm.data.view.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class MemberResource implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2025003627782358399L;

	private Member member;
	
	private List<Resource> resources;
	
	Map<String, Map<String, Boolean>> authButtons;
}

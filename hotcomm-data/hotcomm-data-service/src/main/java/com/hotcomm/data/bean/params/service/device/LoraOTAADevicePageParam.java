package com.hotcomm.data.bean.params.service.device;

import com.hotcomm.data.bean.params.PageParams;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LoraOTAADevicePageParam extends PageParams {

	private String code;

	private Integer groupId;

	private Integer typeId;

//	private Integer minReceive;

//	private Integer maxReceive;

	private Integer memberId;

	private Integer memberType;

	private Integer selectMemberId;

}

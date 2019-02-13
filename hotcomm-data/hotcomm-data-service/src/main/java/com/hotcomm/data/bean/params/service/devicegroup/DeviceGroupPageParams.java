package com.hotcomm.data.bean.params.service.devicegroup;

import com.hotcomm.data.bean.params.PageParams;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor

public class DeviceGroupPageParams extends PageParams {

	private Integer devGroupStatus;

	private Integer deviceGroupId;

	private Integer selectMemberId;

}

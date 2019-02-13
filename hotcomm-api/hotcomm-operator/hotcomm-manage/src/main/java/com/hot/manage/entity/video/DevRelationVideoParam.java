package com.hot.manage.entity.video;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class DevRelationVideoParam{
	
	private List<Integer> devId;

	private Integer moduleid;
	
	private List<Integer> videoId;
	
	private Integer ownid;
	
}

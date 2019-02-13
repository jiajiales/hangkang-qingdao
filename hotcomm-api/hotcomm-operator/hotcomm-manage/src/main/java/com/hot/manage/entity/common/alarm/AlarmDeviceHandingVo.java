package com.hot.manage.entity.common.alarm;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AlarmDeviceHandingVo {

	// (value = "是否需要派工;0,已处理,不需要;1,需要派工")
	private Integer isdispatch;

	// (value = "紧急程度;1,无明确要求时间;2,3天内处理;3,1天内处理;4,8个小时内处理;5,必须立即处理")
	private Integer level;

	// (value = "处理状态，0：未处理；1：处理中；2：已处理")
	private Integer handlestate;

	// (value = "处理人")
	private String contacts;

	// (value = "处理时间")
	private String addtime;

	// (value = "报警状态")
	private String state_name;

	// (value = "语音备注")
	private List<VoiceUrlVo> voiceUrl;

	// (value = "备注")
	private String remark;

	// (value = "图片")
	private List<PictureUrlVo> pictureUrl;

}

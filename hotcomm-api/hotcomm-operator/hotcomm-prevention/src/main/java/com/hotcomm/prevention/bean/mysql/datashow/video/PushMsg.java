package com.hotcomm.prevention.bean.mysql.datashow.video;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class PushMsg implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String input;
	private String output;
	private String videoname;
	private String ffmpeg;
}

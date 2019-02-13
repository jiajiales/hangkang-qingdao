package com.hot.manage.entity.Chat.Record;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ChatRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String senderid;
	
	private String targetid;
	
	private String sendtime;
	
	private String txtcontent;
	
	private Integer type;
	
	private String imgcontent;
	
	private String imgurl;
	
	private long duration;
	
	private String videocontent;
}

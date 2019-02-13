package com.hot.manage.entity.video;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "t_dev_video_relation")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class TDevVideoRelation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "deviceid")
	private Integer deviceid;

	@Column(name = "videoid")
	private Integer videoid;

	@Column(name = "isdelete")
	private Integer isdelete;

	@Column(name = "moduleid")
	private Integer moduleid;

}
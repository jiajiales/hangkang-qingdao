package com.hotcomm.data.bean.entity.service;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "hk_data")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Data {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "data_id")
	private Long id;

	@Column(name = "device_code")
	private String deviceCode;

	@Column(name = "create_time")
	private Long createTime;

	@Column(name = "data")
	private String data;

	@Column(name = "source_data")
	private String sourceData;

	@Column(name = "data_source")
	private Integer source;

	public Data(String deviceCode, Long createTime, String data, String sourceData, Integer source) {
		super();
		this.deviceCode = deviceCode;
		this.createTime = createTime;
		this.data = data;
		this.sourceData = sourceData;
		this.source = source;
	}

}

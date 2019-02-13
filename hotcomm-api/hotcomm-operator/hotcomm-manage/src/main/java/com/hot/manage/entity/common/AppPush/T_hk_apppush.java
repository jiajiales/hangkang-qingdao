package com.hot.manage.entity.common.AppPush;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "t_hk_apppush")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class T_hk_apppush implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // id

	@Column(name = "userid")
	private Integer userid;// 用户ID

	@Column(name = "regid")
	private String regid;// 设备ID

}

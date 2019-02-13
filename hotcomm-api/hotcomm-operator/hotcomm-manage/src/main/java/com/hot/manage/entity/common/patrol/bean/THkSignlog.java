package com.hot.manage.entity.common.patrol.bean;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Table(name="t_hk_signlog")
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class THkSignlog implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="userid")
    private Integer userid;
	@Column(name="signid")
    private Integer signid;
	@Column(name="signstate")
    private Boolean signstate;
	@Column(name="addtime")
    private String addtime;
	@Column(name="type")
    private Integer type;

}
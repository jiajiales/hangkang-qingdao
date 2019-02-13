package com.hot.manage.entity.system;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Table(name="t_operation_log")
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class TOperationLog implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
	
	@Column(name="userid")
    private Integer userid;
	
	@Column(name="ip")
    private String ip;
    
	@Column(name="url")
    private String url;
    
	@Column(name="httpMethod")
    private String httpmethod;
    
	@Column(name="classMethod")
    private String classmethod;
    
	@Column(name="args")
    private String args;
	
	@Column(name="addtime")
    private String addtime;
}
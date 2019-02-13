package com.hot.analysis.bean.jg;

import java.io.Serializable;

public class JGType  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer moduleid; //模块id
	
	private Integer type;//类型
	
	private Integer childtype;//子类型
	
	private Integer count;//数量

	public JGType(){}
	
	public JGType(Integer moduleid, Integer type, Integer childtype, Integer count) {
		super();
		this.moduleid = moduleid;
		this.type = type;
		this.childtype = childtype;
		this.count = count;
	}

	public Integer getModuleid() {
		return moduleid;
	}

	public void setModuleid(Integer moduleid) {
		this.moduleid = moduleid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getChildtype() {
		return childtype;
	}

	public void setChildtype(Integer childtype) {
		this.childtype = childtype;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "JGType [moduleid=" + moduleid + ", type=" + type + ", childtype=" + childtype + ", count=" + count
				+ "]";
	}
	
}

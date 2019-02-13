package com.hotcomm.data.view.bean;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class Resource implements Serializable, Comparable<Object> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private Integer id;
	
	private String path;
	
	private Integer type; 
	
	private String name;
	
	private Integer weight;
	
	private Integer status;
	
	private Integer pid;
	
	private String key;
	
	private List<Resource> childrens;

	@Override
	public int compareTo(Object arg0) {
		Resource resource=(Resource)arg0;  
        /*根据id排序 
         * int stu0Id=Integer.parseInt(id); 
        int stu1Id=Integer.parseInt(stu1.id); 
        return stu0Id>stu1Id?1:(stu0Id==stu1Id?0:-1);*/  
        return weight.compareTo(resource.getWeight());//根据name排序  
	}
	
}

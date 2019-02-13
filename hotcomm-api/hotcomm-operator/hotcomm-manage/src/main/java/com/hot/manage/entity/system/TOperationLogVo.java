package com.hot.manage.entity.system;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class TOperationLogVo {
	
    private Integer id;

    private Integer userid;
	
    private String loginname;
	
    private String realname;

    private String ip;
    
    private String url;
    
    private String httpmethod;
    
    private String classmethod;
    
    private String args;
    
    private String addtime;
}
package com.hot.manage.entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class GroupParam {
	
    private Integer id;
    private Integer moduleid;
    private String groupname;
    private Integer fatherid;
    private String groupcode;//项目组地址
    private String x;
    private String y;
    private Integer coordinate;//坐标类型
    private Boolean isenable;
    private Boolean isdelete;
    private Integer adduserid;
    private String addtime;
	private Integer managerid;//项目负责人
    private Integer cityid;
    private String telephone;
	private String imgpath;//项目图片
    private String itemnum;//项目编号

}

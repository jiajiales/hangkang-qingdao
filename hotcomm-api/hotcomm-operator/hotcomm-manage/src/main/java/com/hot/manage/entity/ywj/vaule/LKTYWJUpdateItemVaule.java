package com.hot.manage.entity.ywj.vaule;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTYWJUpdateItemVaule {
	private Integer itemid;
	private String imgpath;
	private String itemnum;
	private String groupname;
	private String groupcode;
	private Integer contactsid;
	private double x;
	private double y;
	private Integer userid;
	private List<LKTYWJAddGroupVauleSite> sitelist;
}

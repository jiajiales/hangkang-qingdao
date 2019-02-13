package com.hot.manage.entity.jg.vaule;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTUpdateItemVaule {
	private Integer itemid;
	private String imgpath;
	private String itemnum;
	private String groupname;
	private String groupcode;
	private Integer contactsid;
	private String x;
	private String y;
	private Integer userid;
	private List<LKTJgAddGroupVauleSite> sitelist;
}

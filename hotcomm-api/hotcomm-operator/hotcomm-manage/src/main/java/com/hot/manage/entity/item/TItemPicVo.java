package com.hot.manage.entity.item;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TItemPicVo {

	private Integer id;

	private String picnum;// 图片编号

	private String picpath;// 图片路径

	private String site;// 所在楼层具体位置

	private Integer itemid;// 项目ID

	private String addtime;

	private Boolean isdelete;

	private Boolean isenable;

	private String updatetime;

	private List<?> relations;
}

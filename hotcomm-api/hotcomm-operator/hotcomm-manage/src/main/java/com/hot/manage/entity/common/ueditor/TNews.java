package com.hot.manage.entity.common.ueditor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Table(name="t_news")
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class TNews {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "Titel")
    private String titel;//标题
	@Column(name = "Introduce")
    private String introduce;//简介
	@Column(name = "MinImgUrl")
    private String minimgurl;//
	@Column(name = "Source")
    private String source;//来源
	@Column(name = "Sort")
    private Integer sort;//排序
	@Column(name = "Type")
    private String type;//类型
	@Column(name = "PublishTime")
    private String publishtime;//发布时间
	@Column(name = "IsShowIndex")
    private Boolean isshowindex;//是否显示首页
	@Column(name = "IsEnable")
    private Boolean isenable;//是否可用
	@Column(name = "AddTime")
    private String addtime;//添加时间
	@Column(name = "AddUserID")
    private Integer adduserid;//添加人ID
	@Column(name = "TextSrc")
    private String textsrc;//内容
}

package com.hot.manage.db.ywj;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hot.manage.entity.yg.LKTCode;
import com.hot.manage.entity.ywj.LKTYWJDevNum;
import com.hot.manage.entity.ywj.LKTYWJGroupList;
import com.hot.manage.entity.ywj.LKTYWJItemList;
import com.hot.manage.entity.ywj.LKTYWJItemListMap;
import com.hot.manage.entity.ywj.vaule.LKTYWJAddGroupVaule;
import com.hot.manage.entity.ywj.vaule.LKTYWJDevListVaule;
import com.hot.manage.entity.ywj.vaule.LKTYWJUpdateItemVaule;

public interface LKTYWJItemMapper {

	LKTYWJDevNum LKTYWJDevNum(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid);

	Page<LKTYWJItemList> LKTYWJItemList(LKTYWJDevListVaule lktywjDevListVaule);

	List<LKTYWJGroupList> LKTYWJGroupListOnid(@Param("userid") Integer userid, @Param("id") Integer id,
			@Param("moduleid") Integer moduleid, @Param("groupname") String groupname,
			@Param("itemnum") String itemnum);

	Integer LKTYWJDeleteItem(@Param("id") Integer id);

	Integer LKTYWJDeleteItemPic(@Param("id") Integer id);

	LKTCode LKTDeleteItemcondition(@Param("id") Integer id);

	List<LKTYWJItemListMap> LKTYWJItemListMap(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid);

	Integer LKTYWJAddGroup(LKTYWJAddGroupVaule lktywjAddGroupVaule);
	
	Integer LKTYWJAddGroupUser(LKTYWJAddGroupVaule lktywjAddGroupVaule);
	
	Integer LKTYWJAddGroupPic(@Param("picnum") String picnum, @Param("picpath") String picpath,
			@Param("site") String site, @Param("id") Integer id, @Param("addtime") String addtime);

	Integer LKTYWJUpdateItem(LKTYWJUpdateItemVaule lktywjUpdateItemVaule);

	Integer LKTYWJUpdateItemDelPic(LKTYWJUpdateItemVaule lktywjUpdateItemVaule);

	Integer LKTYWJUpdateItemPic(@Param("picnum") String picnum, @Param("picpath") String picpath,
			@Param("site") String site, @Param("itemid") Integer itemid, @Param("addtime") String addtime);
	
}

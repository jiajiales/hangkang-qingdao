package com.hot.manage.service.imp.ywj;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hot.manage.db.ywj.LKTYWJItemMapper;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.yg.LKTCode;
import com.hot.manage.entity.ywj.LKTYWJDevNum;
import com.hot.manage.entity.ywj.LKTYWJGroupList;
import com.hot.manage.entity.ywj.LKTYWJItemList;
import com.hot.manage.entity.ywj.LKTYWJItemListMap;
import com.hot.manage.entity.ywj.vaule.LKTYWJAddGroupVaule;
import com.hot.manage.entity.ywj.vaule.LKTYWJDevListVaule;
import com.hot.manage.entity.ywj.vaule.LKTYWJUpdateItemVaule;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.ywj.LKTYWJItemService;
import com.hot.manage.utils.ConverUtil;

@Transactional
@Service
public class LKTYWJItemServiceImpl implements LKTYWJItemService {

	@Autowired
	private LKTYWJItemMapper lktYWJItemMapper;

	@Override
	public LKTYWJDevNum LKTYWJDevNum(Integer moduleid, Integer userid) throws MyException {
		return lktYWJItemMapper.LKTYWJDevNum(moduleid, userid);
	}

	@Override
	public PageInfo<LKTYWJItemList> LKTYWJItemList(LKTYWJDevListVaule lktywjDevListVaule) throws MyException {
		PageHelper.startPage(lktywjDevListVaule.getPageNum(), lktywjDevListVaule.getPageSize());
		Page<LKTYWJItemList> page = lktYWJItemMapper.LKTYWJItemList(lktywjDevListVaule);
		List<LKTYWJItemList> list = ConverUtil.converPage(page, LKTYWJItemList.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
	}

	@Override
	public List<LKTYWJGroupList> LKTYWJGroupListOnid(Integer userid, Integer id, Integer moduleid) {
		// TODO Auto-generated method stub
		return lktYWJItemMapper.LKTYWJGroupListOnid(userid, id, moduleid, null, null);
	}

	@Override
	public Integer LKTYWJDeleteItem(Integer id) {
		// TODO Auto-generated method stub
		LKTCode code = lktYWJItemMapper.LKTDeleteItemcondition(id);
		if (code.getCode() > 0) {
			return 201;
		}
		lktYWJItemMapper.LKTYWJDeleteItemPic(id);
		return lktYWJItemMapper.LKTYWJDeleteItem(id);
	}

	@Override
	public List<LKTYWJItemListMap> LKTYWJItemListMap(Integer moduleid, Integer userid) {
		// TODO Auto-generated method stub
		return lktYWJItemMapper.LKTYWJItemListMap(moduleid, userid);
	}

	@Override
	public Integer LKTYWJAddGroup(LKTYWJAddGroupVaule lktywjAddGroupVaule) throws MyException {
		// TODO Auto-generated method stub
		List<LKTYWJGroupList> groupname = lktYWJItemMapper.LKTYWJGroupListOnid(lktywjAddGroupVaule.getUserid(), null,
				lktywjAddGroupVaule.getModuleid(), lktywjAddGroupVaule.getGroupname(), null);
		if (groupname.size() != 0) {
			return 201;
		}
		List<LKTYWJGroupList> item = lktYWJItemMapper.LKTYWJGroupListOnid(lktywjAddGroupVaule.getUserid(), null,
				lktywjAddGroupVaule.getModuleid(), null, lktywjAddGroupVaule.getItemnum());
		if (item.size() != 0) {
			return 202;
		}
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		lktywjAddGroupVaule.setFatherid(0);
		lktywjAddGroupVaule.setCount(0);
		lktywjAddGroupVaule.setAddtime(df.format(day));
		Integer code = lktYWJItemMapper.LKTYWJAddGroup(lktywjAddGroupVaule);
		if (code > 0) {
			code = lktYWJItemMapper.LKTYWJAddGroupUser(lktywjAddGroupVaule);
		}
		if (code > 0 & lktywjAddGroupVaule.getSitelist() != null) {
			for (int i = 0; i < lktywjAddGroupVaule.getSitelist().size(); i++) {
				code = lktYWJItemMapper.LKTYWJAddGroupPic(lktywjAddGroupVaule.getSitelist().get(i).getPicnum(),
						lktywjAddGroupVaule.getSitelist().get(i).getPicpath(),
						lktywjAddGroupVaule.getSitelist().get(i).getSite(), lktywjAddGroupVaule.getId(),
						lktywjAddGroupVaule.getAddtime());
				if (code <= 0) {
					return code;
				}
			}
		}
		return code;
	}

	@Override
	public Integer LKTYWJUpdateItem(LKTYWJUpdateItemVaule lktywjUpdateItemVaule) throws MyException {
		// TODO Auto-generated method stub
		if (lktywjUpdateItemVaule.getGroupname() != null) {
			List<LKTYWJGroupList> groupname = lktYWJItemMapper.LKTYWJGroupListOnid(lktywjUpdateItemVaule.getUserid(),
					null, 13, lktywjUpdateItemVaule.getGroupname(), null);
			if (groupname.size() != 0) {
				if (!groupname.get(0).getId().equals(lktywjUpdateItemVaule.getItemid())) {
					return 201;
				}
			}
		}
		if (lktywjUpdateItemVaule.getItemnum() != null) {
			List<LKTYWJGroupList> item = lktYWJItemMapper.LKTYWJGroupListOnid(lktywjUpdateItemVaule.getUserid(), null,
					13, null, lktywjUpdateItemVaule.getItemnum());
			if (item.size() != 0) {
				if (!item.get(0).getId().equals(lktywjUpdateItemVaule.getItemid())) {
					return 202;
				}
			}
		}
		Integer code = lktYWJItemMapper.LKTYWJUpdateItem(lktywjUpdateItemVaule);
		if (code > 0 & lktywjUpdateItemVaule.getSitelist() != null) {
			code = lktYWJItemMapper.LKTYWJUpdateItemDelPic(lktywjUpdateItemVaule);// 全部删除
			for (int i = 0; i < lktywjUpdateItemVaule.getSitelist().size(); i++) {
				Date day = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				code = lktYWJItemMapper.LKTYWJUpdateItemPic(
						lktywjUpdateItemVaule.getSitelist().get(i).getPicnum(),
						lktywjUpdateItemVaule.getSitelist().get(i).getPicpath(),
						lktywjUpdateItemVaule.getSitelist().get(i).getSite(), 
						lktywjUpdateItemVaule.getItemid(),
						df.format(day));// 修改楼层信息，并修改为未删除状态
				if (code <= 0) {// 修改行数为0，数据不存在，即为新增数据
					code = lktYWJItemMapper.LKTYWJAddGroupPic(
							lktywjUpdateItemVaule.getSitelist().get(i).getPicnum(),
							lktywjUpdateItemVaule.getSitelist().get(i).getPicpath(),
							lktywjUpdateItemVaule.getSitelist().get(i).getSite(), 
							lktywjUpdateItemVaule.getItemid(),
							df.format(day));
				}
				if (code <= 0) {
					return code;
				}
			}
		}
		return code;
	}

}

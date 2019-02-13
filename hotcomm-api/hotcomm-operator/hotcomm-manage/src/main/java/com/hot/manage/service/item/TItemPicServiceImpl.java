package com.hot.manage.service.item;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hot.manage.db.item.TItemPicMapper;
import com.hot.manage.entity.item.TItemPic;
import com.hot.manage.exception.MyException;
import com.hot.manage.utils.ConverUtil;

@Service
@Transactional
public class TItemPicServiceImpl implements TItemPicService {

	@Autowired
	private TItemPicMapper itemPicMapper;

	@Override
	public Integer insert(TItemPic param) throws MyException {
		param.setAddtime(ConverUtil.timeForString(new Date()));
		return itemPicMapper.insertSelective(param);
	}

	@Override
	public Integer update(TItemPic param) throws MyException {
		param.setUpdatetime(ConverUtil.timeForString(new Date()));
		return itemPicMapper.updateByPrimaryKeySelective(param);
	}

	// 根据项目ID查询，所有的项目地图
	@Override
	public List<TItemPic> selectList(Integer groupid) throws MyException {
		return itemPicMapper.selectList(groupid);
	}

	// 根据楼层所在位置编号，查询具体的某一项目地图
//	@Override
//	public TItemPicVo selectOne(String site, Integer groupid, Integer moduleid) throws MyException {
//		TItemPicVo tItemPicVo = new TItemPicVo();
//		TItemPic pic = itemPicMapper.selectTItemPic(site, groupid);
//		BeanUtils.copyProperties(pic, tItemPicVo);			
//		if (moduleid==2) {
//			List<YGDevice> list = yGDeviceMapper.selectListByExampl(groupid, site, moduleid);
//			tItemPicVo.setRelations(list);
//		}else if (moduleid==11) {
//			List<TDeviceMc> list = deviceMcMapper.selectListByExampl(groupid, site, moduleid);
//			tItemPicVo.setRelations(list);
//		} 
//		return tItemPicVo;
//	}

}

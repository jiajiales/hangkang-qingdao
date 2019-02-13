package com.hot.manage.service.sj;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hot.manage.db.group.IsxistMapper;
import com.hot.manage.db.sj.SJDevMapper;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.group.Isexist;
import com.hot.manage.entity.sj.SJAlarmNum;
import com.hot.manage.entity.sj.SJDev;
import com.hot.manage.entity.sj.SJDevone;
import com.hot.manage.entity.sj.SJDevv;
import com.hot.manage.utils.ConverUtil;

@Transactional
@Service
public class SJDevServiceImpl implements SJDevService {
    @Autowired
    private SJDevMapper sjDevMapper;

    @Autowired
    private IsxistMapper isxistMapper;

    @Override
    public PageInfo<SJDev> selectdevlist(SJDevv sjDevv) {
        PageHelper.startPage(sjDevv.getPageNum(), sjDevv.getPageSize());
        Page<SJDev> page = sjDevMapper.selectdevlist(sjDevv);
        List<SJDev> list = ConverUtil.converPage(page, SJDev.class);
        return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
    }


    @Override
    public SJDevone selectdevbyid(Integer devid) {
        return sjDevMapper.selectdevbyid(devid);
    }

	@Override
	public Integer updatedevmac(Integer devid, String mac) {

		return sjDevMapper.updatedevmac(devid, mac);
	}

    @Override
    public Integer updatedev(Integer devid,String devnum,String mac,Integer groupid, String code,Double lat,Double lng,Integer x,Integer y,Integer mapimgid,Integer own_id,Integer moduleid) {


        sjDevMapper.updatedev(devid,devnum,mac,groupid,code,lat,lng,x,y,mapimgid,own_id);

        Isexist res=isxistMapper.selectsiterelation(devid,moduleid);
        Integer aa=res.getDgcount();
        if(aa>0){
            return   sjDevMapper.updatesiterelation(devid,mapimgid);
        }else{
            return   sjDevMapper.insertsiterelation(devid,mapimgid);
        }
    }


    @Override
    public Integer insertdev(SJDev sjDev) {

        if(sjDev.getMapimgid() !=null){
            sjDevMapper.insertdev(sjDev);
            sjDevMapper.insertgroup(sjDev);
            return sjDevMapper.insertimggroup(sjDev);
        }else{
            sjDevMapper.insertdev(sjDev);
            return sjDevMapper.insertgroup(sjDev);

        }
    }
    
    
	@Override
	public Integer updateownid(String devids, Integer own_id) {
		List<Object> list = new ArrayList<>();
		if (devids != null) {
			String[] split = devids.split(",");
			for (String s : split) {
				list.add(s);
			}
		}
		return sjDevMapper.updateownid(list, own_id);
	}


	@Override
	public List<SJAlarmNum> selectSJAlarmNums(Integer Time, Integer moduleID, Integer userid) {
		List<SJAlarmNum> list = sjDevMapper.selectSJAlarmNums(Time, moduleID, userid);
		return list;
	}

}


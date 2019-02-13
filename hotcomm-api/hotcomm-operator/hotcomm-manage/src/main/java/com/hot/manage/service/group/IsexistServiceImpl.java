package com.hot.manage.service.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hot.manage.db.group.IsxistMapper;
import com.hot.manage.entity.group.Isexist;

@Transactional
@Service
public class IsexistServiceImpl implements IsexistService {
        @Autowired
        private IsxistMapper isxistMapper;

        @Override
        public Isexist countdg(Integer dgid, String keywords1, String keywords2,Integer devid){
            return isxistMapper.countdg(dgid,keywords1,keywords2,devid);
        }
        @Override
        public Isexist delgroup_before(Integer id){
                return isxistMapper.delgroup_before(id);
        }

        @Override
        public Isexist selectsiterelation(Integer devid,Integer moduleid){
                return isxistMapper.selectsiterelation(devid,moduleid);
        }

        @Override
        public Isexist selectsiterelation2(Integer mapimgid,Integer moduleid){
                return isxistMapper.selectsiterelation(mapimgid,moduleid);
        }
}

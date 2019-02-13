package com.hot.manage.service.group;

import com.hot.manage.entity.group.Isexist;
import com.hot.manage.exception.MyException;

public interface IsexistService {
    public Isexist countdg(Integer dgid, String keywords1, String keywords2,Integer devid) throws MyException;

    public Isexist delgroup_before(Integer id) throws MyException;

    public Isexist selectsiterelation(Integer devid,Integer moduleid) throws MyException;

    public Isexist selectsiterelation2(Integer mapimgid,Integer moduleid) throws MyException;


}

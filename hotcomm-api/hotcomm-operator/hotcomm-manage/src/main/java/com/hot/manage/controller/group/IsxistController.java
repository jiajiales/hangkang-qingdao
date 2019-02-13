package com.hot.manage.controller.group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hot.manage.entity.group.Isexist;
import com.hot.manage.service.group.IsexistService;


@Controller
@RequestMapping("/isxist")
public class IsxistController {
    @Autowired
    private IsexistService isexistService;
    @ResponseBody
    @PostMapping("/changebefore")
    public Integer countdg(Integer dgid,String keywords1,String keywords2,Integer devid){
        Isexist res = isexistService.countdg(dgid,keywords1,keywords2,devid);
        return res.getDgcount();
    }

    
    //删除前检查项目是否有设备
    @ResponseBody
    @PostMapping("/delgroupbefore")
    public Integer delgroup_before(Integer id){
        Isexist res = isexistService.delgroup_before(id);
        return res.getDgcount();
    }

}











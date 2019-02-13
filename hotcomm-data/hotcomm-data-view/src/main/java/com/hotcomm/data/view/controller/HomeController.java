package com.hotcomm.data.view.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotcomm.data.view.bean.Member;
import com.hotcomm.data.view.bean.MemberResource;
import com.hotcomm.data.view.bean.Resource;
import com.hotcomm.framework.utils.RedisHelper;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	
	public static final String INDEX_PAGE="index";
	public static final String INCLUDE_PAGE = "include";
	
	@Autowired
	RedisHelper redisHelper;
	
	
	@GetMapping("/")
	public String home() {return "redirect:/login";}
	
	@GetMapping("/index")
	public ModelAndView  index(HttpServletRequest request) {
		Map<String,Object> model = new HashMap<String,Object>();  
		HttpSession session =  request.getSession();
		Object menusObj = session.getAttribute("menus");
		if(menusObj!=null) {
			model.put("menus", menusObj);
			if(session.getAttribute("memberId") != null) {
				model.put("memberId", session.getAttribute("memberId"));
			}
			if(session.getAttribute("userType") != null) {
				model.put("userType", session.getAttribute("userType"));
			}
			return new ModelAndView(INDEX_PAGE, model);
		}
		Object token = request.getSession().getAttribute("token");
		try {
			if(token!=null) {
				String tokenJson = redisHelper.get(String.valueOf(token));
				log.info("login-token-redis-menus---->{}",tokenJson);
				if(tokenJson!=null) {
					ObjectMapper mapper = new ObjectMapper();
					mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
					MemberResource resources = mapper.readValue(tokenJson, MemberResource.class);
					
					session.setAttribute("authButtons", resources.getAuthButtons());
					Set<Resource> Resourceset=new HashSet<Resource>();
					List<Resource> rs = resources.getResources();
					rs.addAll(Resourceset);
					Collections.sort(rs);
					
					List<Resource> firstMenus = new ArrayList<>();
					List<Resource> sencondMenus = new ArrayList<>();
					rs.forEach(r->{ 
						if(r.getType() == 1) {
							if(r.getPid()!=0) 
								sencondMenus.add(r);
							if(r.getPid()==0) {
								r.setChildrens(new ArrayList<>());
								firstMenus.add(r);
							}
						}
					});
					firstMenus.forEach(f1->{
						Integer id = f1.getId();
						List<Resource> childrens = f1.getChildrens();
						sencondMenus.forEach(f2->{
							Integer pid = f2.getPid();
							if(id==pid) {
								childrens.add(f2);
								f1.setChildrens(childrens);
							}
						});
						Collections.sort(childrens);
					});
					model.put("menus", firstMenus);
					session.setAttribute("menus", firstMenus);
					
					Member member = resources.getMember();
					if(member!=null) {
						model.put("memberId", member.getId());
						model.put("userType", member.getUserType());
						session.setAttribute("memberId", member.getId());
						session.setAttribute("userType", member.getUserType());
					}
					System.out.println(firstMenus);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.put("data-error", 1);
		}
		return new ModelAndView(INDEX_PAGE, model);
	}
	
	@GetMapping("/include")
	public String include(Model model) {return INCLUDE_PAGE;}

}

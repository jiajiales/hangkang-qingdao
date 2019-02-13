package com.hot.analysis.controller.mc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.hot.analysis.service.mc.McService;

@RestController
public class McController {

	@Autowired
	private McService mcService;


}

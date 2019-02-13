package com.hotcomm.prevention.controller.common;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hotcomm.prevention.service.common.ModuleService;
import com.hotcomm.prevention.utils.ApiResult;


@RestController
@RequestMapping("/test/")
public class TestController {
 
    @Autowired
    private ModuleService ModuleService;
 
//    @PostMapping("/import")
//    public boolean addUser(@RequestParam("file") MultipartFile file,Integer userid) {
//        boolean a = false;
//        String fileName = file.getOriginalFilename();
//        try {
//             a = deviceService.batchImport(fileName, file,userid);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return  a;
//    }
    
 	@PostMapping("/import")
 	public ApiResult importDev(@RequestParam("file") MultipartFile file,Integer userid) throws Exception {
 		String fileName = file.getOriginalFilename();
 		ApiResult apiResult = new ApiResult("1", "执行成功", ModuleService.batchImport(fileName, file,userid));
 		return apiResult;
 	}
 
}
package com.dri.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.dri.aop.log.SysLogOption;
import com.dri.aop.log.SysLogOption.Opr;
import com.dri.aop.log.SysLogOption.Sys;
import com.dri.service.BusinessService;

@RestController
public class ServiceController {
	
	@Autowired
	private BusinessService businessService;

	@Value("${server.port}")
	String port;
	
	@Value("${spring.application.name}")
	String serviceName;

//	@SentinelResource("resource")
	@GetMapping("/get")
	public String query(String param) throws Exception {	
		return serviceName + ":" + port;
	}
	
	@SysLogOption(sys =Sys.EAP_OA ,operation = Opr.QUERY,module = "test")
	@GetMapping("/get1")
	public String query1(String param) throws Exception {	
		return serviceName + ":" + port;
	}
	@GetMapping("/get2")
	public String query2(String param) throws Exception {	
		return serviceName + ":" + port;
	}
	@GetMapping("/get3")
	public String query3(String param) throws Exception {	
		return serviceName + ":" + port;
	}
	
	
	//@HystrixCommand(fallbackMethod = "processHystrix_Get")
	@GetMapping("/query")
	public String query() throws Exception {

//		Thread.sleep(3000);
		
		String param = "good";
		return businessService.test(param);
		//return "get A service";
	}

	/**
	 * 服务降级
	 * @return
	 */
	public String processHystrix_Get(){

		//Thread.sleep(1900);
		System.out.println("degree===============");
		return "get AprocessHystrix_Get service";
	}

	@GetMapping("/getfor")
	public String getfor() throws Exception {

		String param = "good";
		return businessService.test(param);
	}

	@PostMapping("/test")
	public String test() {
		System.out.println("test===============");

		String param = "good";
		return businessService.test(param);
	}

}

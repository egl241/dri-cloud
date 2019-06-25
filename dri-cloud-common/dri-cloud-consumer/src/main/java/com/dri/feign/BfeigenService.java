package com.dri.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "dri-cloud-provider")
public interface BfeigenService {

	@GetMapping("/get")
	String get(@RequestParam("param") String param);
}

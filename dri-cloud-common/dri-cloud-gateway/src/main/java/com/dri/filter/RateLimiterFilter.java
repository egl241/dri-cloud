package com.dri.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;


public class RateLimiterFilter extends ZuulFilter{
	
	private static final Logger log = LoggerFactory.getLogger(RateLimiterFilter.class);
	
	private static final RateLimiter RATE_LIMITER=RateLimiter.create(50);

	@Override
	public boolean shouldFilter() {
		
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext requestContext = RequestContext.getCurrentContext();
		
		//每调用一次tryAcquire()方法，令牌数量减1，当1000个用完后，那么后面进来的用户无法访问上面接口
        if (!RATE_LIMITER.tryAcquire()) {
        	log.info("request be limited because of TOO_MANY_REQUESTS!");
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
        }
        log.info("RateLimiterFilter run() method");
		return null;
	}

	@Override
	public String filterType() {
		
		return "pre";
	}

	@Override
	public int filterOrder() {
		
		return -4;
	}

}

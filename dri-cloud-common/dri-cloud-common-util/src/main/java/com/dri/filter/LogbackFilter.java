package com.dri.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
//https://blog.csdn.net/qq_25646191/article/details/81004852

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class LogbackFilter implements Filter {

	private static final String UNIQUE_ID = "traceRootId";
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		boolean bInsertMDC = insertMDC();
		try {
			chain.doFilter(request, response);
		} finally {
			if(bInsertMDC) {
				MDC.remove(UNIQUE_ID);
			}
		}


	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
	
	private boolean insertMDC() {
		UUID uuid = UUID.randomUUID();
		//String uniqueId = UNIQUE_ID +"-"+ uuid.toString().replace("-", "");
		MDC.put(UNIQUE_ID, uuid.toString());
		return true;
	}


}

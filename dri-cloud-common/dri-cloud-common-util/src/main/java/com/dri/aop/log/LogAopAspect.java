package com.dri.aop.log;

import java.lang.reflect.Method;
import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.dri.utils.Constants;

/**
 * AOP实现日志
 *
 */
@Component
@Aspect
public class LogAopAspect {

	private static final Logger log = LoggerFactory.getLogger(LogAopAspect.class);

	@Autowired
	private AmqpTemplate amqptemplate;

	@Around("@annotation(com.dri.aop.log.SysLogOption)")
	public Object aroundAdvice(ProceedingJoinPoint point) throws Throwable {
		long begin = System.currentTimeMillis();
		String result = Constants.SUCCESS_CN;

		try {

			return point.proceed();
		} catch (Exception e) {
			result = Constants.FAIL_CN;
			throw e;
		} finally {
			long end = System.currentTimeMillis();
			saveLog(point, result, end - begin);
		}
	}

	private void saveLog(ProceedingJoinPoint point, String result, long time) {

		String userName = "Anonymous";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (null != principal) {
			userName = principal.toString();
		}
		log.info("===============userName:{}", userName);

		// 1.方法执行前的处理，相当于前置通知
		// 获取方法签名
		MethodSignature methodSignature = (MethodSignature) point.getSignature();
		// 获取方法
		Method method = methodSignature.getMethod();
		// 获取方法上面的注解
		SysLogOption logOption = method.getAnnotation(SysLogOption.class);
		SysLog sysLog = new SysLog(UUID.randomUUID().toString(), logOption.logtype().getKey(),
				System.currentTimeMillis());
		sysLog.setLogOption(logOption);

		// 请求的方法名
		String className = point.getTarget().getClass().getName();
		String methodName = methodSignature.getName();
		sysLog.setMethod(className + "." + methodName + "()");

		// 请求的方法参数
		Object[] args = point.getArgs();
		getParams(method, sysLog, args);
		HttpServletRequest request = getRequest();
		if(request != null) {
			// TODO setIp
//			sysLog.setIp();
		}
		
		// TODO setAPPHost
		
		// TODO setUserName
		
		sysLog.setUsedTime(time);
		sysLog.setResult(result);
		
		// TODO amqptemplate.convertAndSend(exchange, routingKey, message);

	}

	private void getParams(Method method, SysLog sysLog, Object[] args) {
		LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
		String[] params = u.getParameterNames(method);
		if (args != null && params != null) {
			StringBuilder paramsSB = new StringBuilder();
			for (int i=0; i<args.length; i++) {
				if (args[i] instanceof ServletRequest 
						|| args[i] instanceof ServletResponse 
						|| args[i] instanceof MultipartFile) {
					continue;
				}
				paramsSB.append("  ").append(params[i]).append(": ").append(JSON.toJSONString(args[i]));
			}
			sysLog.setParams(paramsSB.toString());
		}
	}
	
	public static HttpServletRequest getRequest() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attributes != null) {
			return attributes.getRequest();
		}
		return null;
	}
}

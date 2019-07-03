package com.dri.aop.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * 日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SysLogOption {
	// 系统
	Sys sys();
	// 模块
	String module() default "";
	// 操作类型
	Opr opr() default Opr.QUERY;
	// 操作描述
	String desc() default "";
	// 日志类型
	LogType logtype() default LogType.SYSTEM_OPERATION;
	
	public enum Opr {
		QUERY("查询"),
		ADD("新增"),
		UPDATE("更新"),
		DELETE("删除"),
		CHECK("校验"),
		API_RECEIVE_DATA("接收数据"),
		API_SEND_DATA("发送数据");
		
		private final String text;

		private Opr(String text) {
			this.text = text;
		}
		
		public String getText() {
			return text;
		}
	}
	
	public enum Sys{
		EAP_OA("eap-oa", "协同办公", "OA");

		private final String queueName;
		private final String chineseName;
		private final String resourceTypeCode;
		
		private Sys(String queueName, String chineseName, String resourceTypeCode) {
			this.queueName = queueName;
			this.chineseName = chineseName;
			this.resourceTypeCode = resourceTypeCode;
		}

		public String getQueueName() {
			return queueName;
		}

		public String getChineseName() {
			return chineseName;
		}

		public String getResourceTypeCode() {
			return resourceTypeCode;
		}
		
	}
	
	public enum LogType {
		
		SYSTEM_OPERATION("systemOperation", "系统操作日志"),
		SYSTEM_MANAGE("systemManage", "系统管理日志"),
		SECURITY_MANAGE("securityManage", "安全管理日志"),
		SECURITY_AUDIT("securityAudit", "安全审计日志");

		private final String key;
		private final String value;
		
		private LogType(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}
		
	}
	
}

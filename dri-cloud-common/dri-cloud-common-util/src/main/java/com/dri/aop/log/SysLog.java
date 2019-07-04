package com.dri.aop.log;

import java.io.Serializable;

/*
 * 系统日志
 */
public class SysLog implements Serializable {

	private static final long serialVersionUID = -5012237176781777292L;

	// 标识
	private String id;
	// 日志类型(操作日志OPR/系统日志SYS)
	private String logType;
	// 系统编码
	private String sysCode;
	// 模块名称
	private String moduleName;
	// 方法名称
	private String method;
	// IP地址
	private String ip;
	// 应用服务地址
	private String appHost;
	// 操作类型
	private String oprType;
	// 操作用户
	private String oprUser;
	// 操作时间
	private Long operTime;
	// 操作时间-字符串
	private String operTimeStr;
	// 创建时间
	private String createTime;
	// 操作描述
	private String desc;
	// 参数
	private String params;
	// 耗时
	private Long usedTime ;
	// 结果
	private String result;
	
	public SysLog() {}

	public SysLog(String id, String logType, Long operTime) {
		super();
		this.id = id;
		this.logType = logType;
		this.operTime = operTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAppHost() {
		return appHost;
	}

	public void setAppHost(String appHost) {
		this.appHost = appHost;
	}

	public String getOprType() {
		return oprType;
	}

	public void setOprType(String oprType) {
		this.oprType = oprType;
	}

	public String getOprUser() {
		return oprUser;
	}

	public void setOprUser(String oprUser) {
		this.oprUser = oprUser;
	}

	public Long getOperTime() {
		return operTime;
	}

	public void setOperTime(Long operTime) {
		this.operTime = operTime;
	}

	public String getOperTimeStr() {
		return operTimeStr;
	}

	public void setOperTimeStr(String operTimeStr) {
		this.operTimeStr = operTimeStr;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Long getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(Long usedTime) {
		this.usedTime = usedTime;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setLogOption(SysLogOption logOption) {
		if (logOption != null) {
			this.moduleName = logOption.module();
			this.oprType = logOption.opr().getText();
			this.desc = logOption.desc();
			this.sysCode = logOption.sys().getResourceTypeCode();
		}
	}

	@Override
	public String toString() {
		return "SysLog [id=" + id + ", logType=" + logType + ", sysCode=" + sysCode + ", moduleName=" + moduleName
				+ ", method=" + method + ", ip=" + ip + ", appHost=" + appHost + ", oprType=" + oprType + ", oprUser="
				+ oprUser + ", operTime=" + operTime + ", operTimeStr=" + operTimeStr
				+ ", createTime=" + createTime + ", desc=" + desc + ", params=" + params + ", usedTime=" + usedTime
				+ ", result=" + result + "]";
	}
	
	
}

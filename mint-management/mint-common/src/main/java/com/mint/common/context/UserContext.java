package com.mint.common.context;

import java.io.Serializable;
import java.util.Set;

public class UserContext implements Serializable {

	private static final long serialVersionUID = -7630251658711933733L;
	// 操作者ID
	private Long accountId;
	// 操作者角色ID集合
	private Set<Long> roleIds;
	// 上次登录时间
	private long prevLoginTime;
	// cookie域
	private String cookieDomain;
	// 过期周期
	private long expirationPeriodMs;
	
	private Long businessId;
	
	private String givenName;
	
	private String familyName;
	
	private String email;
	
	private String fromIP;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Set<Long> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Set<Long> roleIds) {
		this.roleIds = roleIds;
	}

	public long getPrevLoginTime() {
		return prevLoginTime;
	}

	public void setPrevLoginTime(long prevLoginTime) {
		this.prevLoginTime = prevLoginTime;
	}

	public String getCookieDomain() {
		return cookieDomain;
	}

	public void setCookieDomain(String cookieDomain) {
		this.cookieDomain = cookieDomain;
	}

	public long getExpirationPeriodMs() {
		return expirationPeriodMs;
	}

	public void setExpirationPeriodMs(long expirationPeriodMs) {
		this.expirationPeriodMs = expirationPeriodMs;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFromIP() {
		return fromIP;
	}

	public void setFromIP(String fromIP) {
		this.fromIP = fromIP;
	}

	public static UserContext fromLite(LiteUserContext lc) {
		UserContext context = new UserContext();
		context.setAccountId(lc.getAccountId());
		return context;
	}
	
}

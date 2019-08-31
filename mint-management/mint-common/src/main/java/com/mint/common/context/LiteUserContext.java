package com.mint.common.context;

public class LiteUserContext {
	
	private Long accountId;
	
	private Long expirePeriodMs;
	
	private Long PrevLoginTime;
	
	private String cookieDomain;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getExpirePeriodMs() {
		return expirePeriodMs;
	}

	public void setExpirePeriodMs(Long expirePeriodMs) {
		this.expirePeriodMs = expirePeriodMs;
	}

	public Long getPrevLoginTime() {
		return PrevLoginTime;
	}

	public void setPrevLoginTime(Long prevLoginTime) {
		PrevLoginTime = prevLoginTime;
	}

	public String getCookieDomain() {
		return cookieDomain;
	}

	public void setCookieDomain(String cookieDomain) {
		this.cookieDomain = cookieDomain;
	}
	
}

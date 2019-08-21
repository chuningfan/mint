package com.mint.common.context;

import java.util.Date;
import java.util.List;

public class UserContext {
	// 操作者ID
	private Long userId;
	// 操作者角色ID集合
	private List<Long> roleIds;
	// 描述
	private String description;
	// 上次登录时间
	private Date prevLoginTime;
	// 令牌码（暂定）
	private String token;
	// 顶层业务分类ID
	private Long marketId;
	// 操作者分类ID（consumer or agency）
	private Long userTypeId;
	// 所属机构ID（agency公司）
	private Long orgId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<Long> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPrevLoginTime() {
		return prevLoginTime;
	}

	public void setPrevLoginTime(Date prevLoginTime) {
		this.prevLoginTime = prevLoginTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getMarketId() {
		return marketId;
	}

	public void setMarketId(Long marketId) {
		this.marketId = marketId;
	}

	public Long getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Long userTypeId) {
		this.userTypeId = userTypeId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

}

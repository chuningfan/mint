package com.mint.service.user.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.mint.common.enums.AccountStatus;
import com.mint.service.database.entity.IdentifiedEntity;

@Entity
@Table(name="accounts")
public class AccountEntity extends IdentifiedEntity{

	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="status")
	@Enumerated(EnumType.STRING)
	private AccountStatus status;
    
    @Column(name="last_login_time")
    private Date lastLoginTime;
    
    @Column(name="business_id")
    private Long businessId;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<RoleEntity> roles;
    
    @OneToOne(mappedBy="account",cascade = CascadeType.ALL)
	private UserEntity user;
    
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public Set<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleEntity> roles) {
		this.roles = roles;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}
	
}

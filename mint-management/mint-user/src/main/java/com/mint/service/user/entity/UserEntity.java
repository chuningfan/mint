package com.mint.service.user.entity;



import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.mint.common.enums.GenderType;
import com.mint.common.enums.UserStatus;
import com.mint.service.database.entity.IdentifiedEntity;

@Entity
@Table(name="user")
public class UserEntity  extends IdentifiedEntity{
	    @Column(name="name")
		private String name;
	    
	    @Column(name="gender")
		private  GenderType gender;
	    
	    @Column(name="avatar")
		private String avatar;
	    
	    @Column(name="login_name")
		private String loginName;
	    
	    @Column(name="password")
		private String password;
	    
	    @Column(name="mobile")
		private String mobile;
	    
	    @Column(name="email")
		private String email;
	    
	    @Column(name="status")
		private UserStatus status;
	    
	    @Column(name="last_login_time")
	    private Date lastLoginTime;
	    
	    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
	    private Set<AddressEntity> address;
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public GenderType getGender() {
			return gender;
		}

		public void setGender(GenderType gender) {
			this.gender = gender;
		}

		public String getAvatar() {
			return avatar;
		}

		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}

		public String getLoginName() {
			return loginName;
		}

		public void setLoginName(String loginName) {
			this.loginName = loginName;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public UserStatus getStatus() {
			return status;
		}

		public void setStatus(UserStatus status) {
			this.status = status;
		}

		public Date getLastLoginTime() {
			return lastLoginTime;
		}

		public void setLastLoginTime(Date lastLoginTime) {
			this.lastLoginTime = lastLoginTime;
		}

		public Set<AddressEntity> getAddress() {
			return address;
		}

		public void setAddress(Set<AddressEntity> address) {
			this.address = address;
		}
	    
		

}

package com.mint.service.user.entity;



import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.mint.service.database.entity.IdentifiedEntity;
import com.mint.service.user.enums.GenderType;

@Entity
@Table(name="users")
public class UserEntity  extends IdentifiedEntity{
	    @Column(name="family_name")
		private String familyName;
	    
	    @Column(name="given_name")
		private String givenName;
	    
	    @Column(name="gender")
	    @Enumerated(EnumType.STRING)
		private  GenderType gender;
	    
	    @Column(name="avatar")
		private String avatar;
	    
	    @Column(name="mobile")
		private String mobile;
	    
	    @Column(name="email")
		private String email;
	    
	    @Column(name="id_number")
	    private String IdNumber;
	    
	    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	    private Set<AddressEntity> address;
	    
	    @OneToOne(cascade = CascadeType.ALL)
	    @JoinColumn(name = "account_id")
	    private  AccountEntity account;
	    
		public String getFamilyName() {
			return familyName;
		}

		public void setFamilyName(String familyName) {
			this.familyName = familyName;
		}

		public String getGivenName() {
			return givenName;
		}

		public void setGivenName(String givenName) {
			this.givenName = givenName;
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

		public Set<AddressEntity> getAddress() {
			return address;
		}

		public void setAddress(Set<AddressEntity> address) {
			this.address = address;
		}

		public String getIdNumber() {
			return IdNumber;
		}

		public void setIdNumber(String idNumber) {
			IdNumber = idNumber;
		}

		public AccountEntity getAccount() {
			return account;
		}

		public void setAccount(AccountEntity account) {
			this.account = account;
		}
		
		

}

package com.mint.service.user.dto.reg;

import java.util.Set;

import com.mint.service.user.enums.GenderType;

public class BasicInfo {
	
	private Long accountId;
	
	private String familyName;
	
	private String givenName;
	
	private  GenderType gender;
    
	private String avatar;
    
	private String mobile;
    
	private String email;
    
    private String IdNumber;
    
    private Set<AddressDto> addresses;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

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

	public String getIdNumber() {
		return IdNumber;
	}

	public void setIdNumber(String idNumber) {
		IdNumber = idNumber;
	}

	public Set<AddressDto> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<AddressDto> addresses) {
		this.addresses = addresses;
	}
	
}

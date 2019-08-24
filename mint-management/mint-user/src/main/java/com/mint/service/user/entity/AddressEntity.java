package com.mint.service.user.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mint.service.database.entity.IdentifiedEntity;
import com.mint.service.user.dto.reg.AddressDto;

@Entity
@Table(name="address")
public class AddressEntity extends IdentifiedEntity {
	
	@Column(name="country")
	private Long countryId;
	
	@Column(name="province")
	private Long provinceId;
	
	@Column(name="city")
	private Long cityId;
	
	@Column(name="district")
	private Long districtId;
	
	@Column(name="detail")
	private String detail;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public static AddressEntity createEntity(AddressDto dto) {
		AddressEntity entity = new AddressEntity();
		entity.setId(dto.getId());
		entity.countryId = dto.getCountryId();
		entity.provinceId = dto.getProvinceId();
		entity.cityId = dto.getCityId();
		entity.districtId = dto.getDistrictId();
		return entity;
	}
	
}

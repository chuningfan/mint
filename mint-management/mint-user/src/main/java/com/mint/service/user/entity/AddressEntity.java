package com.mint.service.user.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mint.service.database.entity.IdentifiedEntity;

@Entity
@Table(name="address")
public class AddressEntity extends IdentifiedEntity {
	
	@Column(name="name")
	private String name;
	
	@ManyToOne
	private UserEntity user;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}
	
	
	
}

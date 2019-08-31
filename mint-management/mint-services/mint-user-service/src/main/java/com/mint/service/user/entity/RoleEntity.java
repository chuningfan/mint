package com.mint.service.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mint.service.database.entity.IdentifiedEntity;

@Entity
@Table(name="roles")
public class RoleEntity extends IdentifiedEntity {
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "decription")
	private String decription;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private UserEntity user;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDecription() {
		return decription;
	}

	public void setDecription(String decription) {
		this.decription = decription;
	}
	
}

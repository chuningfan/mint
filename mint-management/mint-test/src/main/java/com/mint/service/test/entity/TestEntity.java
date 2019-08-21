package com.mint.service.test.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mint.service.database.entity.IdentifiedEntity;

@Entity
@Table(name="test_entity")
public class TestEntity extends IdentifiedEntity {
	
	@Column(name="descr")
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}

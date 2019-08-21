package com.mint.service.test.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mint.service.test.db.jpa.JpaTestDao;
import com.mint.service.test.entity.TestEntity;

@Service
public class JPATestService {

	@Autowired
	private JpaTestDao jpaTestDao;
	
	@Transactional
	public void save() {
		TestEntity e = new TestEntity();
		e.setDescription("Saved by JPA");
		jpaTestDao.save(e);
	}
	
}

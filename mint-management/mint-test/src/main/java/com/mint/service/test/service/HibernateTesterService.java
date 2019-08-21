package com.mint.service.test.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mint.service.test.db.hibernate.HibernateTestDao;
import com.mint.service.test.entity.TestEntity;

@Service
public class HibernateTesterService {

	@Autowired
	private HibernateTestDao hibernateTestDao;
	
	@Transactional
	public void save() {
		TestEntity e = new TestEntity();
		e.setDescription("Saved by hibernate");
		hibernateTestDao.save(e);
	}
	
}

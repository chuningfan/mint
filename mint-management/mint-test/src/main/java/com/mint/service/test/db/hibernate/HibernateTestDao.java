package com.mint.service.test.db.hibernate;

import org.springframework.stereotype.Repository;

import com.mint.service.database.support.hibernate.HibernateGenericDAO;
import com.mint.service.test.entity.TestEntity;

@Repository("hibernateTestDao")
public class HibernateTestDao extends HibernateGenericDAO<TestEntity, Long> {
	
}

package com.mint.service.database.support;

import java.io.Serializable;
import java.util.Collection;

import org.hibernate.query.Query;


public interface GenericDAOInterface<T, K extends Serializable> {

	T save(T entity);
	
	void deleteById(K id);
	
	T getOne(K id);
	
	Collection<T> findAll(int offset, int pageSize, Query<T> query);
	
}

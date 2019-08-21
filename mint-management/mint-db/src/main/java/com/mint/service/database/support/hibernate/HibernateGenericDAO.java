package com.mint.service.database.support.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import com.mint.service.database.support.GenericDAOInterface;

/**
 * 如果使用Hibernate， JPA就无法使用
 * @author ningfanchu
 *
 * @param <T>
 * @param <K>
 */
public class HibernateGenericDAO<T,  K extends Serializable> implements GenericDAOInterface<T, K> {

	private final Class<T> clazz;
	
	@Autowired
	private EntityManagerFactory  entityManagerFactory;
	
	protected HibernateGenericDAO() {
		clazz = getSuperClassGenricType(0);
	}

	@SuppressWarnings("unchecked")
	private Class<T> getSuperClassGenricType(final int index) {
        Type genType = getClass().getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return (Class<T>) Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return (Class<T>) Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return (Class<T>) Object.class;
        }
        return (Class<T>) params[index];
    }
	
	protected Session getSession() {
		return entityManagerFactory.unwrap(SessionFactory.class).getCurrentSession();
	}
	
	@Override
	public T save(T entity) {
		getSession().saveOrUpdate(entity);
		return entity;
	}

	@Override
	public void deleteById(K id) {
		getSession().delete(getOne(id));
	}

	@Override
	public T getOne(K id) {
		return getSession().get(clazz, id);
	}

	@Override
	public Collection<T> findAll(int offset, int pageSize, Query<T> query) {
		query.setFirstResult(offset).setMaxResults(pageSize);
		return query.list();
	}

}

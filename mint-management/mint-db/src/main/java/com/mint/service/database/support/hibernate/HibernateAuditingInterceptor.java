package com.mint.service.database.support.hibernate;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import com.mint.common.context.UserContext;
import com.mint.common.context.UserContextThreadLocal;
import com.mint.service.database.entity.AuditingEntity;
import com.mint.service.database.entity.SerialEntity;
import com.mint.service.database.exception.AuditingException;

public class HibernateAuditingInterceptor extends EmptyInterceptor {

	private static final long serialVersionUID = -3840176715234601768L;

	
	
	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		int crByIndex = -1;
		int crDtIndex = -1;
		for (int i = 0; i < propertyNames.length; i ++) {
			if ("createdBy".equals(propertyNames[i])) {
				crByIndex = i;
			}
			if ("createdDate".equals(propertyNames[i])) {
				crDtIndex = i;
			}
		}
		UserContext context = UserContextThreadLocal.get();
		if (context == null) {
			throw new AuditingException("No auditor info!");
		}
		state[crByIndex] = context.getUserId();
		state[crDtIndex] = new Date();
		return true;
	}



	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
		if (entity instanceof SerialEntity) {
			int versionNumIndex = -1;
			int prevVersionNumIndex = -1;
			for (int i = 0; i < propertyNames.length; i ++) {
				if ("versionNumber".equals(propertyNames[i])) {
					versionNumIndex = i;
				}
				if ("previousVersionNumber".equals(propertyNames[i])) {
					prevVersionNumIndex = i;
				}
			}
			if (previousState != null) { // 如果是修改数据
				currentState[prevVersionNumIndex] = previousState[versionNumIndex];
				currentState[versionNumIndex] = Long.parseLong(previousState[versionNumIndex].toString()) + 1;
			}
		} else if (entity instanceof AuditingEntity) {
			int mdByIndex = -1;
			int mdDtIndex = -1;
			for (int i = 0; i < propertyNames.length; i ++) {
				if ("modifiedBy".equals(propertyNames[i])) {
					mdByIndex = i;
				}
				if ("modifiedDate".equals(propertyNames[i])) {
					mdDtIndex = i;
				}
			}
			UserContext context = UserContextThreadLocal.get();
			if (context == null) {
				throw new AuditingException("No auditor info!");
			}
			currentState[mdByIndex] = context.getUserId();
			currentState[mdDtIndex] = new Date();
		}
		return true;
	}

	
}

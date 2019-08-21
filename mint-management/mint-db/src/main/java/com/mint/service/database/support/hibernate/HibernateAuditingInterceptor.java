package com.mint.service.database.support.hibernate;

import java.io.Serializable;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import com.mint.service.database.entity.SerialEntity;

public class HibernateAuditingInterceptor extends EmptyInterceptor {

	private static final long serialVersionUID = -3840176715234601768L;

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
		}
		return true;
	}

	
}

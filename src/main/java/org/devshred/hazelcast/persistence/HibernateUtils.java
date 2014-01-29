package org.devshred.hazelcast.persistence;

import org.hibernate.Session;
import org.hibernate.jpa.internal.EntityManagerImpl;

import javax.persistence.EntityManager;

public class HibernateUtils {
	public static Session getHibernateSession(EntityManager entityManager) {
		final Session session;
		if (entityManager.getDelegate() instanceof EntityManagerImpl) {
			EntityManagerImpl entityManagerImpl = (EntityManagerImpl) entityManager.getDelegate();
			session = entityManagerImpl.getSession();
		} else {
			session = (Session) entityManager.getDelegate();
		}

		return session;
	}
}

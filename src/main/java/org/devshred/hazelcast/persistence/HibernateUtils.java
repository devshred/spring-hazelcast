package org.devshred.hazelcast.persistence;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.jpa.internal.EntityManagerImpl;


class HibernateUtils {
	public static Session getHibernateSession(final EntityManager entityManager) {
		final Session session;
		if (entityManager.getDelegate() instanceof EntityManagerImpl) {
			final EntityManagerImpl entityManagerImpl = (EntityManagerImpl) entityManager.getDelegate();
			session = entityManagerImpl.getSession();
		} else {
			session = (Session) entityManager.getDelegate();
		}

		return session;
	}
}

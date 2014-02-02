package org.devshred.hazelcast.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


public abstract class AbstractTransactionalSpringUnitTester {
	@Autowired
	private PlatformTransactionManager transactionManager;

	private TransactionStatus transactionStatus;

	void beginTransaction() {
		transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
	}

	void commitTransaction() {
		if (transactionManager != null) {
			transactionManager.commit(transactionStatus);
		}
	}

	void rollBackTransaction() {
		if (transactionManager != null) {
			transactionManager.rollback(transactionStatus);
		}
	}
}

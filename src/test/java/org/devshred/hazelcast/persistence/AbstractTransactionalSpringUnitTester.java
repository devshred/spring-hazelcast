package org.devshred.hazelcast.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


public class AbstractTransactionalSpringUnitTester {
	@Autowired
	private PlatformTransactionManager transactionManager;

	private TransactionStatus transactionStatus;

	void beginTransaction() throws Exception {
		transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
	}

	void commitTransaction() throws Exception {
		if (transactionManager != null) {
			transactionManager.commit(transactionStatus);
		}
	}

	void rollBackTransaction() throws Exception {
		if (transactionManager != null) {
			transactionManager.rollback(transactionStatus);
		}
	}
}

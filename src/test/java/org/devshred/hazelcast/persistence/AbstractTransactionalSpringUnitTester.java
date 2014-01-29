package org.devshred.hazelcast.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class AbstractTransactionalSpringUnitTester {

	@Autowired
	private PlatformTransactionManager transactionManager;

	private TransactionStatus transactionStatus;

	/**
	 * Create a transaction for use in the test. Typically called in the
	 * <tt>@Before</tt> method of the JUnit 4 test class.
	 *
	 * @throws Exception
	 */
	protected void beginTransaction() throws Exception {
		transactionStatus = transactionManager
				.getTransaction(new DefaultTransactionDefinition());
	}

	/**
	 * Rollback transaction after a test to avoid corrupting other tests
	 * Typically called in the <tt>@After</tt> method of the JUnit 4 test class.
	 *
	 * @throws Exception
	 */
	protected void commitTransaction() throws Exception {
		if (transactionManager != null)
			transactionManager.commit(transactionStatus);
	}

	/**
	 * Rollback transaction after a test to avoid corrupting other tests
	 * Typically called in the <tt>@After</tt> method of the JUnit 4 test class.
	 *
	 * @throws Exception
	 */
	protected void rollBackTransaction() throws Exception {
		if (transactionManager != null)
			transactionManager.rollback(transactionStatus);
	}
}

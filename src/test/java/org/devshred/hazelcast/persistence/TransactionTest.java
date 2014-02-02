package org.devshred.hazelcast.persistence;

import org.devshred.hazelcast.config.DatabaseTestConfiguration;
import org.devshred.hazelcast.config.JpaHibernateConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.devshred.hazelcast.config.Profiles.EHCACHE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@ActiveProfiles(EHCACHE)
@ContextConfiguration(classes = {DatabaseTestConfiguration.class, JpaHibernateConfiguration.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class TransactionTest extends AbstractTransactionalSpringUnitTester {
	private static final String MIC = "ABC";

	@Autowired
	ShareRepository repository;

	@Test
	public void transactionHandling() throws Exception {
		beginTransaction();
		repository.save(new Share(MIC));
		rollBackTransaction();

		assertThat(repository.findAll(), hasSize(0));

		beginTransaction();
		repository.save(new Share(MIC));
		commitTransaction();

		assertThat(repository.findAll(), hasSize(1));
	}
}

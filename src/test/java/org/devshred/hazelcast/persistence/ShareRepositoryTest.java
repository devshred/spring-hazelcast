package org.devshred.hazelcast.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.devshred.hazelcast.config.DatabaseTestConfiguration;
import org.devshred.hazelcast.config.JpaHibernateConfiguration;

import org.hibernate.Session;
import org.hibernate.stat.SessionStatistics;
import org.hibernate.stat.Statistics;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.devshred.hazelcast.config.Profiles.EHCACHE;
import static org.devshred.hazelcast.persistence.HibernateUtils.getHibernateSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;


@ActiveProfiles(EHCACHE)
@ContextConfiguration(classes = { DatabaseTestConfiguration.class, JpaHibernateConfiguration.class })
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class ShareRepositoryTest extends AbstractTransactionalSpringUnitTester {
	private static final String MIC_1 = "ABC";
	private static final String MIC_2 = "DEF";
	private static final String MIC_3 = "GHI";
	private static final String NAME = "ABC Corp.";

	@Autowired
	ShareRepository repository;

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	public void findAll() {
		repository.save(new Share(MIC_1));
		repository.save(new Share(MIC_2));
		repository.save(new Share(MIC_3));
		assertThat(repository.findAll(), hasSize(3));
	}

	@Test
	public void findByMic() {
		repository.save(new Share(MIC_1, NAME, 10));
		assertThat(repository.findByMic(MIC_1).getName(), is(NAME));
	}

	@Test
	public void collectStatistics() throws Exception {
		final Session session = getHibernateSession(entityManager);
		final SessionStatistics sessionStats = session.getStatistics();
		final Statistics statistics = session.getSessionFactory().getStatistics();
		long entityInsertCount = statistics.getEntityInsertCount();
		long queryExecutionCount = statistics.getQueryExecutionCount();

		repository.save(new Share(MIC_1));
		repository.save(new Share(MIC_2));

		entityInsertCount = statistics.getEntityInsertCount() - entityInsertCount;

		assertThat(entityInsertCount, is(2L));
		assertThat(sessionStats.getEntityCount(), is(2));

		repository.findByMic(MIC_1);

		queryExecutionCount = statistics.getQueryExecutionCount() - queryExecutionCount;
		assertThat(queryExecutionCount, is(1L));
	}
}

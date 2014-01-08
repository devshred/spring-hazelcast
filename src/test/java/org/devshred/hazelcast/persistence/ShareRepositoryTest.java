package org.devshred.hazelcast.persistence;

import org.devshred.hazelcast.config.DatabaseTestConfiguration;
import org.devshred.hazelcast.config.JpaHibernateConfiguration;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;


@ContextConfiguration(classes = { DatabaseTestConfiguration.class, JpaHibernateConfiguration.class })
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class ShareRepositoryTest {
	private static final String MIC_1 = "ABC";
	private static final String MIC_2 = "DEF";
	private static final String MIC_3 = "GHI";
	private static final String NAME = "ABC Corp.";

	@Autowired
	ShareRepository repository;

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
}

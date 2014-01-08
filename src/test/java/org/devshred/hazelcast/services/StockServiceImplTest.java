package org.devshred.hazelcast.services;

import java.util.Collections;

import org.devshred.hazelcast.persistence.Share;
import org.devshred.hazelcast.persistence.ShareRepository;
import org.devshred.hazelcast.services.impl.StockServiceImpl;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class StockServiceImplTest {
	@Mock
	private ShareRepository repository;
	@InjectMocks
	private StockServiceImpl service;

	private final String mic = "ABC";
	private final Share share = mock(Share.class);

	@Test
	public void findAllDelegatesToRepository() {
		when(repository.findAll()).thenReturn(Collections.<Share>emptyList());
		service.findAll();
		verify(repository).findAll();
	}

	@Test
	public void findByMicDelegatesToRepository() {
		when(repository.findByMic(mic)).thenReturn(share);
		assertThat(service.findByMic(mic), is(share));
	}

	@Test
	public void findsShareThatAlreadyExists() {
		when(repository.findByMic(mic)).thenReturn(share);
		assertThat(service.findOrCreate(mic), is(share));
	}

	@Test
	public void createsNewShare() {
		when(repository.findByMic(mic)).thenReturn(null);
		when(repository.save(any(Share.class))).thenReturn(share);
		assertThat(service.findOrCreate(mic), notNullValue());
		verify(repository).save(any(Share.class));
	}

	@Test
	public void saveDelegatesToRepository() {
		service.save(new Share(mic));
		verify(repository).save(any(Share.class));
	}

	@Test
	public void updateQuote() {
		final int quote = 10;
		when(repository.findByMic(mic)).thenReturn(share);
		service.updateQuote(mic, quote);
		verify(share).setQuote(quote);
		verify(repository).save(share);
	}

	@Test
	public void initDb() {
		service.initDb();
		verify(repository, times(5)).save(any(Share.class));
	}
}

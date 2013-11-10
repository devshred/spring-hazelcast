package org.devshred.hazelcast.services;

import org.devshred.hazelcast.persistence.Share;
import org.devshred.hazelcast.persistence.ShareRepository;
import org.devshred.hazelcast.services.impl.StockServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheFactoryBean;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.devshred.hazelcast.services.impl.StockServiceImpl.SHARE_CACHE;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@ActiveProfiles("StockServiceCachingTest")
@ContextConfiguration(classes = {StockServiceCachingTest.TestConfiguration.class})
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
public class StockServiceCachingTest {
    private static final String MIC = "ABC";
    private static final Share SHARE = new Share(MIC);

    @Autowired
    private ShareRepository repository;

    @Autowired
    private StockService service;

    @Configuration
    @EnableCaching
    @Profile("StockServiceCachingTest")
    public static class TestConfiguration {
        @Bean
        public SimpleCacheManager cacheManager() {
            final SimpleCacheManager cacheManager = new SimpleCacheManager();
            final List<Cache> caches = new ArrayList<>();
            caches.add(shareCacheBean().getObject());
            cacheManager.setCaches(caches);
            return cacheManager;
        }

        @Bean
        public ConcurrentMapCacheFactoryBean shareCacheBean() {
            final ConcurrentMapCacheFactoryBean cacheFactoryBean = new ConcurrentMapCacheFactoryBean();
            cacheFactoryBean.setName(SHARE_CACHE);
            return cacheFactoryBean;
        }

        @Bean
        public ShareRepository shareRepository() {
            return mock(ShareRepository.class);
        }

        @Bean
        public StockService stockService() {
            return new StockServiceImpl(shareRepository());
        }
    }

    @Test
    public void serviceCachesRepositoryCalls() {
        service.findByMic(MIC);
        service.findByMic(MIC);

        verify(repository).findByMic(MIC);
    }

    @Test
    public void saveEvictsEntryFromCache() {
        when(repository.findByMic(MIC)).thenReturn(SHARE);

        service.findByMic(MIC);
        service.save(new Share(MIC));
        service.findByMic(MIC);

        verify(repository, times(2)).findByMic(MIC);
    }
}
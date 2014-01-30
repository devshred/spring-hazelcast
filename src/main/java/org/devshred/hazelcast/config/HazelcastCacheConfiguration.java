package org.devshred.hazelcast.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.config.InterfacesConfig;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.NearCacheConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.TcpIpConfig;

import com.hazelcast.core.HazelcastInstance;

import com.hazelcast.instance.HazelcastInstanceFactory;

import com.hazelcast.spring.cache.HazelcastCacheManager;

import javax.annotation.Resource;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import static org.apache.commons.lang3.StringUtils.split;


@Configuration
@EnableCaching
@Profile(Profiles.HAZELCAST)
@PropertySource("classpath:application.properties")
public class HazelcastCacheConfiguration {
	private static final String PROPERTY_NAME_HAZELCAST_INTERFACES = "hazelcast.interfaces";

	@Resource
	private Environment env;

	@Bean
	public CacheManager cacheManager() {
		return new HazelcastCacheManager(instance());
	}

	@Bean
	public HazelcastInstance instance() {
		final Config hazelcastConfig = new Config();

		hazelcastConfig.setGroupConfig(new GroupConfig("mygroup", "password"));

		final String[] interfaces = split(env.getRequiredProperty(PROPERTY_NAME_HAZELCAST_INTERFACES), ",");

		final NetworkConfig network = hazelcastConfig.getNetworkConfig();
		network.setPort(5700);
		network.setPortAutoIncrement(false);

		final InterfacesConfig nwInterfaces = network.getInterfaces();
		nwInterfaces.setEnabled(true);
		for (final String member : interfaces) {
			nwInterfaces.addInterface(member);
		}

		final JoinConfig join = network.getJoin();
		join.getMulticastConfig().setEnabled(false);

		final TcpIpConfig tcpIpConfig = join.getTcpIpConfig();
		tcpIpConfig.setEnabled(true);
		for (final String member : interfaces) {
			tcpIpConfig.addMember(member);
		}

		final MapConfig mapCfg = new MapConfig();
		mapCfg.setName("default");
		mapCfg.setBackupCount(2);
		mapCfg.getMaxSizeConfig().setSize(10000);
		mapCfg.setTimeToLiveSeconds(300);

		final NearCacheConfig nearCacheConfig = new NearCacheConfig();
		nearCacheConfig.setMaxSize(1000).setMaxIdleSeconds(120).setTimeToLiveSeconds(300);
		mapCfg.setNearCacheConfig(nearCacheConfig);

		hazelcastConfig.addMapConfig(mapCfg);

		return HazelcastInstanceFactory.newHazelcastInstance(hazelcastConfig);
	}
}

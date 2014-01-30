package org.devshred.hazelcast.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;


@ComponentScan(
	{
		"org.devshred.hazelcast.persistence",
		"org.devshred.hazelcast.services",
		"org.devshred.hazelcast.controller"
	}
)
@Configuration
@EnableWebMvc
@Import(
	{
		DatabaseConfiguration.class,
		JpaHibernateConfiguration.class,
		HazelcastCacheConfiguration.class,
		EhcacheCacheConfiguration.class
	}
)
public class WebAppConfig extends WebMvcConfigurerAdapter {
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**").addResourceLocations("/images/");
	}

	@Bean
	public UrlBasedViewResolver setupViewResolver() {
		final UrlBasedViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/pages/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
}
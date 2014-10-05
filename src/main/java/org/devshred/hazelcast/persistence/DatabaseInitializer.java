package org.devshred.hazelcast.persistence;

import org.devshred.hazelcast.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    StockService service;

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent contextRefreshedEvent) {
        service.initDb();
    }
}

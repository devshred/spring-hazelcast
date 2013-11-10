package org.devshred.hazelcast.services;

import org.devshred.hazelcast.persistence.Share;

import java.util.List;

public interface StockService {
    List<Share> findAll();

    Share findByMic(String stock);

    Share findOrCreate(String mic);

    Share save(Share share);

    Share updateQuote(String mic, int quote);

    void initDb();
}
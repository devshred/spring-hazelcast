package org.devshred.hazelcast.services.impl;

import org.devshred.hazelcast.persistence.Share;
import org.devshred.hazelcast.persistence.ShareRepository;
import org.devshred.hazelcast.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class StockServiceImpl implements StockService {
    public static final String SHARE_CACHE = "share";
    private final ShareRepository shareRepository;

    @Autowired
    public StockServiceImpl(final ShareRepository shareRepository) {
        this.shareRepository = shareRepository;
    }

    @Override
    public List<Share> findAll() {
        return shareRepository.findAll();
    }

    @Cacheable("share")
    @Override
    public Share findByMic(final String mic) {
        return shareRepository.findByMic(mic);
    }

    @Override
    public Share findOrCreate(final String mic) {
        final Share share = shareRepository.findByMic(mic);
        if (share != null) {
            return share;
        }
        return shareRepository.save(new Share(mic));
    }

    @Override
    @CacheEvict(value = SHARE_CACHE, key = "#share.mic")
    public Share save(final Share share) {
        return shareRepository.save(share);
    }

    @Override
    @CacheEvict(value = "share", key = "#mic")
    public Share updateQuote(final String mic, final int quote) {
        final Share share = shareRepository.findByMic(mic);
        share.setQuote(quote);
        return shareRepository.save(share);
    }

    @Override
    public void initDb() {
        shareRepository.save(new Share("AAPL", "Apple Inc.", 520));
        shareRepository.save(new Share("MSFT", "Microsoft Corporation", 38));
        shareRepository.save(new Share("GOOG", "Google Inc.", 1016));
        shareRepository.save(new Share("YHOO", "Yahoo! Inc.", 33));
        shareRepository.save(new Share("TWTR", "Twitter, Inc.", 32));
    }
}
package org.zyamileva.twitter.dao.Inmemory;

import org.zyamileva.twitter.dao.RetweetDao;
import org.zyamileva.twitter.entities.Retweet;

import java.util.Optional;
import java.util.UUID;

public class RetweetInMemoryDao implements RetweetDao {

    @Override
    public Retweet save(Retweet entity) {
        return Storage.putRetweet(entity);
    }

    @Override
    public Optional<Retweet> findById(UUID id) {
        Retweet retweet = Storage.getByRetweetId(id);
        if (retweet == null) {
            return Optional.empty();
        } else {
            return Optional.of(retweet);
        }
    }

    @Override
    public Iterable findAll() {
        return Storage.findAllRetweets();
    }

    @Override
    public void delete(Retweet entity) {
        Storage.deleteRetweet(entity);
    }
}
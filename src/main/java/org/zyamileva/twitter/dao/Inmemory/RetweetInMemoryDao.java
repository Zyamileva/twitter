package org.zyamileva.twitter.dao.Inmemory;

import org.zyamileva.twitter.dao.RetweetDao;
import org.zyamileva.twitter.entities.Retweet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RetweetInMemoryDao implements RetweetDao {

    @Override
    public Retweet save(Retweet entity) {
        return Storage.getInstance().putRetweet(entity);
    }

    @Override
    public Optional<Retweet> findById(UUID id) {
        Retweet retweet = Storage.getInstance().getByRetweetId(id);
        return Optional.ofNullable(retweet);
    }

    @Override
    public List<Retweet> findAll() {
        return Storage.getInstance().findAllRetweets();
    }

    @Override
    public void delete(Retweet entity) {
        Storage.getInstance().deleteRetweet(entity);
    }
}
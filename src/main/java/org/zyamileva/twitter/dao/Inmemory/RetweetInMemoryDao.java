package org.zyamileva.twitter.dao.Inmemory;

import org.zyamileva.twitter.dao.RetweetDao;
import org.zyamileva.twitter.entities.Retweet;
import org.zyamileva.twitter.entities.Tweet;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Override
    public Set<Retweet> findRetweetsByUserId(UUID userId) {
        return Storage.getInstance().findAllRetweets()
                .stream()
                .filter(retweet -> retweet.getUserId().equals(userId))
                .collect(Collectors.toSet());
    }
}
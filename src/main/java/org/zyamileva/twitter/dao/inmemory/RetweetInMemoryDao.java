package org.zyamileva.twitter.dao.inmemory;

import org.zyamileva.twitter.dao.RetweetDao;
import org.zyamileva.twitter.entities.Retweet;

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
    public Object delete(Retweet entity) {
        Storage.getInstance().deleteRetweet(entity);
        return null;
    }

    @Override
    public Set<Retweet> findRetweetsByUserId(UUID userId) {
        return Storage.getInstance().findAllRetweets()
                .stream()
                .filter(retweet -> retweet.getUserId().equals(userId))
                .collect(Collectors.toSet());
    }

    @Override
    public long countRetweets(UUID tweetId) {
        return Storage.getInstance().findAllRetweets()
                .stream()
                .filter(retweet -> retweet.getTweetId().equals(tweetId))
                .count();
    }

    @Override
    public void delete(UUID userId, UUID tweetId) {
        Optional<Retweet> retweetOptional = Storage.getInstance().findAllRetweets()
                .stream()
                .filter(retweetStream -> retweetStream.getUserId().equals(userId) && retweetStream.getTweetId().equals(tweetId))
                .findFirst();

        retweetOptional.ifPresent(retweet -> Storage.getInstance().deleteRetweet(retweet));
    }

    @Override
    public void deleteAllByTweetId(UUID tweetId) {
        Set<Retweet> retweetSet = Storage.getInstance().findAllRetweets()
                .stream()
                .filter(retweet -> retweet.getTweetId().equals(tweetId))
                .collect(Collectors.toSet());

        retweetSet.forEach(retweet -> Storage.getInstance().deleteRetweet(retweet));
    }

    @Override
    public boolean retweetExists(UUID userId, UUID tweetId) {
        return Storage.getInstance().findAllRetweets()
                .stream()
                .anyMatch(retweetStream -> retweetStream.getUserId().equals(userId) && retweetStream.getTweetId().equals(tweetId));
    }
}
package org.zyamileva.twitter.dao.Inmemory;

import org.zyamileva.twitter.dao.TweetDao;
import org.zyamileva.twitter.entities.Tweet;

import java.util.Optional;
import java.util.UUID;

public class TweetInMemoryDao implements TweetDao {
    @Override
    public Tweet save(Tweet entity) {
        return Storage.putTweet(entity);
    }

    @Override
    public Optional<Tweet> findById(UUID id) {
        Tweet tweet = Storage.getByTweetId(id);
        if (tweet == null) {
            return Optional.empty();
        } else {
            return Optional.of(tweet);
        }
    }

    @Override
    public Iterable<Tweet> findAll() {
        return Storage.findAllTweets();
    }

    @Override
    public void delete(Tweet entity) {
        Storage.deleteTweet(entity);
    }
}
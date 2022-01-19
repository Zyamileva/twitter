package org.zyamileva.twitter.dao.Inmemory;

import org.zyamileva.twitter.dao.TweetDao;
import org.zyamileva.twitter.entities.Tweet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TweetInMemoryDao implements TweetDao {
    @Override
    public Tweet save(Tweet entity) {
        return Storage.getInstance().putTweet(entity);
    }

    @Override
    public Optional<Tweet> findById(UUID id) {
        Tweet tweet = Storage.getInstance().getByTweetId(id);
        return Optional.ofNullable(tweet);
    }

    @Override
    public List<Tweet> findAll() {
        return Storage.getInstance().findAllTweets();
    }

    @Override
    public void delete(Tweet entity) {
        Storage.getInstance().deleteTweet(entity);
    }
}
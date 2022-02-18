package org.zyamileva.twitter.dao.inmemory;

import org.zyamileva.twitter.configuration.options.Context;
import org.zyamileva.twitter.dao.RetweetDao;
import org.zyamileva.twitter.dao.TweetDao;
import org.zyamileva.twitter.entities.Retweet;
import org.zyamileva.twitter.entities.Tweet;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class TweetInMemoryDao implements TweetDao {
    private final RetweetDao retweetDao = Context.getInstance().getRetweetDao();

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

    @Override
    public Set<Tweet> findFollowingTweets(Set<UUID> userIds) {
        return Storage.getInstance().findAllTweets()
                .stream()
                .filter(tweet ->userIds.contains(tweet.getUserId()))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Tweet> findTweetsByUserId(UUID userId) {
        return Storage.getInstance().findAllTweets()
                .stream()
                .filter(tweet -> tweet.getUserId().equals(userId))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Tweet> findRetweetsByUserId(UUID userId) {
        Set<UUID> requiredTweetIds = retweetDao.findRetweetsByUserId(userId)
                .stream()
                .map(Retweet::getTweetId)
                .collect(Collectors.toSet());
        return Storage.getInstance().findAllTweets()
                .stream()
                .filter(tweet -> requiredTweetIds.contains(tweet.getId()))
                .collect(Collectors.toSet());
    }
}
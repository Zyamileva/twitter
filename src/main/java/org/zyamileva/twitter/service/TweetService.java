package org.zyamileva.twitter.service;

import org.zyamileva.twitter.entities.Tweet;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface TweetService {
    Optional<Tweet> saveTweet(Tweet tweet);

    Optional<Tweet> findById(UUID tweetId);

    Set<Tweet> findFollowingTweets(Set<UUID> userIds);

    Set<Tweet> findTweetsByUserId(UUID userId);

    Set<Tweet> findRetweetsByUserId(UUID userId);

    boolean like(UUID userId, UUID tweetID);

    boolean retweet(UUID userId, UUID tweetId);

    List<Tweet> findAllTweet();

    void delete(Tweet tweet);

    void deleteLike(UUID userId, UUID tweetId);

    void deleteRetweet(UUID userId, UUID tweetId);

    long countLikes(UUID tweetId);

    long countRetweets(UUID tweetId);
}
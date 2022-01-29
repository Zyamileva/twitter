package org.zyamileva.twitter.dao;

import org.zyamileva.twitter.entities.Retweet;
import org.zyamileva.twitter.entities.Tweet;

import java.util.Set;
import java.util.UUID;

public interface RetweetDao extends GenericDao<Retweet> {

    Set<Retweet> findRetweetsByUserId(UUID userId);

    long countRetweets(UUID tweetId);

    void delete(UUID userId, UUID tweetId);

    void deleteAllByTweetId(UUID tweetId);

    boolean retweetExists(UUID userId, UUID tweetId);
}
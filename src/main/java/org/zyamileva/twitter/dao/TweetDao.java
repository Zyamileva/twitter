package org.zyamileva.twitter.dao;

import org.zyamileva.twitter.entities.Tweet;

import java.util.Set;
import java.util.UUID;

public interface TweetDao extends GenericDao<Tweet> {
    Set<Tweet> findFollowingTweets(Set<UUID> userIds);

    Set<Tweet> findTweetsByUserId(UUID userId);

    Set<Tweet> findRetweetsByUserId(UUID userId);
}
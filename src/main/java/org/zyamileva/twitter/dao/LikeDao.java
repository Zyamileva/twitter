package org.zyamileva.twitter.dao;

import org.zyamileva.twitter.entities.Like;

import java.util.UUID;

public interface LikeDao extends GenericDao<Like> {
    long countLikes(UUID tweetId);

    void delete(UUID userId, UUID tweetId);

    void deleteAllByTweetId(UUID tweetId);

    boolean likeExists(UUID userId, UUID tweetId);
}
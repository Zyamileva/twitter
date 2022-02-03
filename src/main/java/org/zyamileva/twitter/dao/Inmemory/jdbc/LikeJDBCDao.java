package org.zyamileva.twitter.dao.Inmemory.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zyamileva.twitter.dao.Inmemory.jdbc.mapper.TweetResultSetMapper;
import org.zyamileva.twitter.dao.LikeDao;
import org.zyamileva.twitter.entities.Like;
import org.zyamileva.twitter.service.TweetService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class LikeJDBCDao implements LikeDao {
    private static final Logger log = LogManager.getLogger(TweetService.class);
    //private static final TweetResultSetMapper tweetResultSetMapper = new TweetResultSetMapper();


    @Override
    public Like save(Like entity) {
        return null;
    }

    @Override
    public Optional<Like> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public void delete(Like entity) {

    }

    @Override
    public long countLikes(UUID tweetId) {
        return 0;
    }

    @Override
    public void delete(UUID userId, UUID tweetId) {

    }

    @Override
    public void deleteAllByTweetId(UUID tweetId) {

    }

    @Override
    public boolean likeExists(UUID userId, UUID tweetId) {
        return false;
    }

}
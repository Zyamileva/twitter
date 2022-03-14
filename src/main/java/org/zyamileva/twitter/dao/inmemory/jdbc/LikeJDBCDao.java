package org.zyamileva.twitter.dao.inmemory.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zyamileva.twitter.dao.inmemory.jdbc.mapper.LikeResultSetMapper;
import org.zyamileva.twitter.dao.LikeDao;
import org.zyamileva.twitter.entities.Like;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.zyamileva.twitter.dao.inmemory.jdbc.H2Properties.H2_URL;

public class LikeJDBCDao implements LikeDao {
    private static final Logger log = LogManager.getLogger(LikeJDBCDao.class);
    private static final LikeResultSetMapper likeResultSetMapper = new LikeResultSetMapper();

    @Override
    public Like save(Like entity) {
        final String createLikeQuery = """
                insert into likes(id, user_id, tweet_id, date_posted)
                               values (?, ?, ?, ?)
                """;
        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            Like savedLike = entity.clone();
            savedLike.setId(UUID.randomUUID());
            savedLike.setDatePosted(LocalDateTime.now());
            PreparedStatement pr = connection.prepareStatement(createLikeQuery);
            pr.setObject(1, savedLike.getId());
            pr.setObject(2, savedLike.getUserId());
            pr.setObject(3, savedLike.getTweetId());
            pr.setTimestamp(4, Timestamp.valueOf(savedLike.getDatePosted()));
            pr.execute();
            connection.commit();
            return savedLike;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Like update error");
            return null;
        }
    }

    @Override
    public Optional<Like> findById(UUID id) {
        final String findByIdQuery = """
                select *
                from likes
                where id = ?
                 """;
        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            PreparedStatement ps = connection.prepareStatement(findByIdQuery);
            ps.setObject(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return Optional.of(likeResultSetMapper.map(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error during find like by id " + id);
        }
        return Optional.empty();
    }

    @Override
    public List findAll() {
        final String findAllLikesQuery = """
                select *
                from likes
                 """;
        List<Like> likes = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(findAllLikesQuery);
            while (resultSet.next()) {
                likes.add(likeResultSetMapper.map(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error during find all likes");
        }
        return likes;
    }

    @Override
    public Like delete(Like entity) {
        final String deleteQuery = """
                delete from likes
                where id = ?
                """;
        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            PreparedStatement ps = connection.prepareStatement(deleteQuery);
            ps.setObject(1, entity.getId());
            ps.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error during delete like " + entity);
        }
        return entity;
    }

    @Override
    public long countLikes(UUID tweetId) {
        final String countLikesQuery = """
                select count(id) as number_of_likes
                from likes
                where tweet_id = ?
                """;
        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            PreparedStatement ps = connection.prepareStatement(countLikesQuery);
            ps.setObject(1, tweetId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("number_of_likes");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error during count likes for tweet: " + tweetId);
        }
        return 0;
    }

    @Override
    public void delete(UUID userId, UUID tweetId) {
        final String deleteQuery = """
                delete from likes
                where user_id = ? and tweet_id = ?
                """;
        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            PreparedStatement ps = connection.prepareStatement(deleteQuery);
            ps.setObject(1, userId);
            ps.setObject(2, tweetId);
            ps.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error during delete like for tweet: " + tweetId + " , user: " + userId);
        }
    }

    @Override
    public void deleteAllByTweetId(UUID tweetId) {
        final String deleteAllByTweetIdQuery = """
                delete from likes
                where tweet_id = ?
                """;
        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            PreparedStatement ps = connection.prepareStatement(deleteAllByTweetIdQuery);
            ps.setObject(1, tweetId);
            ps.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error during delete likes for tweet: " + tweetId);
        }
    }

    @Override
    public boolean likeExists(UUID userId, UUID tweetId) {
        final String likeExists = """
                select *
                from likes
                where user_id = ? and tweet_id = ?
                """;
        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            PreparedStatement ps = connection.prepareStatement(likeExists);
            ps.setObject(1, userId);
            ps.setObject(2, tweetId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error during likeExists userId " + userId + " tweetId " + tweetId);
        }
        return false;
    }
}
package org.zyamileva.twitter.dao.inmemory.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zyamileva.twitter.dao.inmemory.jdbc.mapper.RetweetResultSetMapper;
import org.zyamileva.twitter.dao.RetweetDao;
import org.zyamileva.twitter.entities.Retweet;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import static org.zyamileva.twitter.dao.inmemory.jdbc.H2Properties.H2_URL;

public class RetweetJDBCDao implements RetweetDao {
    private static final Logger log = LogManager.getLogger(RetweetJDBCDao.class);
    private static final RetweetResultSetMapper retweetResultSetMapper = new RetweetResultSetMapper();

    @Override
    public Retweet save(Retweet entity) {
        final String createRetweetQuery = """
                insert into retweets(id, user_id, tweet_id, date_posted)
                               values (?, ?, ?, ?)
                """;
        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            Retweet savedRetweet = entity.clone();
            savedRetweet.setId(UUID.randomUUID());
            savedRetweet.setDatePosted(LocalDateTime.now());
            PreparedStatement pr = connection.prepareStatement(createRetweetQuery);
            pr.setObject(1, savedRetweet.getId());
            pr.setObject(2, savedRetweet.getUserId());
            pr.setObject(3, savedRetweet.getTweetId());
            pr.setTimestamp(4, Timestamp.valueOf(savedRetweet.getDatePosted()));
            pr.execute();
            connection.commit();
            return savedRetweet;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Retweet update error");
            return null;
        }
    }

    @Override
    public Optional<Retweet> findById(UUID id) {
        final String findByIdQuery = """
                select *
                from retweet
                where id = ?
                 """;
        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            PreparedStatement ps = connection.prepareStatement(findByIdQuery);
            ps.setObject(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return Optional.of(retweetResultSetMapper.map(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error during find retweet by id " + id);
        }
        return Optional.empty();
    }

    @Override
    public List<Retweet> findAll() {
        final String findAllRetweetsQuery = """
                select *
                from retweets
                 """;
        List<Retweet> retweets = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(findAllRetweetsQuery);
            while (resultSet.next()) {
                retweets.add(retweetResultSetMapper.map(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error during find all retweets");
        }
        return retweets;
    }

    @Override
    public Retweet delete(Retweet entity) {
        final String deleteQuery = """
                delete from retweets
                where id = ?
                """;
        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            PreparedStatement ps = connection.prepareStatement(deleteQuery);
            ps.setObject(1, entity.getId());
            ps.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error during delete retweet " + entity);
            return null;
        }
        return entity;
    }

    @Override
    public Set<Retweet> findRetweetsByUserId(UUID userId) {
        Set<Retweet> retweets = new HashSet<>();
        final String findRetweetsByUserIdQuery = """
                select *
                from retweet
                where user_id = ?
                 """;
        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            PreparedStatement ps = connection.prepareStatement(findRetweetsByUserIdQuery);
            ps.setObject(1, userId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                retweets.add(retweetResultSetMapper.map(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error during find retweets by userId " + userId);
        }
        return retweets;
    }

    @Override
    public long countRetweets(UUID tweetId) {
        final String countRetweetsQuery = """
                select count(id) as number_of_retweets
                from retweets
                where tweet_id = ?
                """;
        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            PreparedStatement ps = connection.prepareStatement(countRetweetsQuery);
            ps.setObject(1, tweetId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("number_of_retweets");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error during count retweets for tweet: " + tweetId);
        }
        return 0;
    }

    @Override
    public void delete(UUID userId, UUID tweetId) {
        final String deleteQuery = """
                delete from retweets
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
            log.error("Error during delete retweet for tweet: " + tweetId + " , user: " + userId);
        }
    }

    @Override
    public void deleteAllByTweetId(UUID tweetId) {
        final String deleteAllByTweetIdQuery = """
                delete from retweets
                where tweet_id = ?
                """;
        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            PreparedStatement ps = connection.prepareStatement(deleteAllByTweetIdQuery);
            ps.setObject(1, tweetId);
            ps.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error during delete retweets for tweet: " + tweetId);
        }
    }

    @Override
    public boolean retweetExists(UUID userId, UUID tweetId) {
        final String retweetsExists = """
                select *
                from retweets
                where user_id = ? and tweet_id = ?
                """;
        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            PreparedStatement ps = connection.prepareStatement(retweetsExists);
            ps.setObject(1, userId);
            ps.setObject(2, tweetId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error during retweetsExists userId " + userId + " tweetId " + tweetId);
        }
        return false;
    }
}
package org.zyamileva.twitter.dao.inmemory.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zyamileva.twitter.dao.inmemory.jdbc.mapper.TweetResultSetMapper;
import org.zyamileva.twitter.dao.TweetDao;
import org.zyamileva.twitter.entities.Tweet;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.zyamileva.twitter.dao.inmemory.jdbc.H2Properties.H2_URL;
import static org.zyamileva.twitter.dao.inmemory.jdbc.H2Properties.UUID_TYPE;

public class TweetJDBCDao implements TweetDao {
    private static final Logger log = LogManager.getLogger(TweetJDBCDao.class);
    private static final TweetResultSetMapper tweetResultSetMapper = new TweetResultSetMapper();

    @Override
    public Tweet save(Tweet entity) {
        final String createTweetQuery = """
                insert into tweets(id, user_id, reply_tweet_id, date_posted, content, mentioned_user_ids)
                               values (?, ?, ?, ?, ?, ?)
                """;
        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            Tweet savedTweet = entity.clone();
            savedTweet.setId(UUID.randomUUID());
            savedTweet.setDatePosted(LocalDateTime.now());
            PreparedStatement pr = connection.prepareStatement(createTweetQuery);
            pr.setObject(1, savedTweet.getId());
            pr.setObject(2, savedTweet.getUserId());
            pr.setObject(3, savedTweet.getReplyTweetId());
            pr.setTimestamp(4, Timestamp.valueOf(savedTweet.getDatePosted()));
            pr.setObject(5, savedTweet.getContent());
            pr.setArray(6, connection.createArrayOf(UUID_TYPE, savedTweet.getMentionedUserIds().toArray()));
            pr.execute();
            connection.commit();
            return savedTweet;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Tweet update error");
            return null;
        }
    }

    @Override
    public Optional<Tweet> findById(UUID id) {
        final String findByIdQuery = """
                select *
                from tweets
                where id = ?
                 """;
        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            PreparedStatement ps = connection.prepareStatement(findByIdQuery);
            ps.setObject(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return Optional.of(tweetResultSetMapper.map(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error during find tweet by id " + id);
        }
        return Optional.empty();
    }

    @Override
    public List findAll() {
        final String findALLTweetsQuery = """
                select *
                from tweets
                 """;
        List<Tweet> tweets = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(findALLTweetsQuery);
            while (resultSet.next()) {
                tweets.add(tweetResultSetMapper.map(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error during find all tweets");
        }
        return tweets;
    }


    @Override
    public Object delete(Tweet entity) {
        final String deleteQuery = """
                delete from tweets
                where id = ?
                """;
        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            PreparedStatement ps = connection.prepareStatement(deleteQuery);
            ps.setObject(1, entity.getId());
            ps.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error during delete tweet " + entity);
        }
        return null;
    }

    @Override
    public Set<Tweet> findFollowingTweets(Set<UUID> userIds) {
        String findFollowingTweetsQuery = """
                select *
                from tweets
                where user_id IN (?)
                """;
        Set<Tweet> tweets = new HashSet<>();

        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            String inClause = userIds.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining("', '", "'", "'"));
            findFollowingTweetsQuery = findFollowingTweetsQuery.replace("?", inClause);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(findFollowingTweetsQuery);
            while (resultSet.next()) {
                tweets.add(tweetResultSetMapper.map(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error during fetching tweets");
        }
        return tweets;
    }

    @Override
    public Set<Tweet> findTweetsByUserId(UUID userId) {
        final String findTweetsByUserIdQuery = """
                select *
                from tweets
                where user_id = ?
                 """;
        Set<Tweet> tweets = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            PreparedStatement ps = connection.prepareStatement(findTweetsByUserIdQuery);
            ps.setObject(1, userId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                tweets.add(tweetResultSetMapper.map(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error during find tweets by userId " + userId);
        }
        return tweets;
    }

    @Override
    public Set<Tweet> findRetweetsByUserId(UUID userId) {
        final String findRetweetsByUserIdQuery = """
                select *
                from tweets a
                join retweets b on a.id = b.tweet_id
                where b.user_id = ?
                 """;
        Set<Tweet> tweets = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            PreparedStatement ps = connection.prepareStatement(findRetweetsByUserIdQuery);
            ps.setObject(1, userId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                tweets.add(tweetResultSetMapper.map(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error during find retweets by userId " + userId);
        }
        return tweets;
    }
}
package org.zyamileva.twitter.dao.Inmemory.jdbc.mapper;

import org.zyamileva.twitter.entities.Tweet;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TweetResultSetMapper implements ResultSetMapper<Tweet> {

    @Override
    public Tweet map(ResultSet resultSet) throws SQLException {
        Tweet tweet = new Tweet();
        tweet.setId(id(resultSet));
        tweet.setUserId(uuid(resultSet, "user_id"));
        tweet.setReplyTweetId(uuidNullable(resultSet, "reply_tweet_id"));
        tweet.setDatePosted(localDateTime(resultSet, "date_posted"));
        tweet.setContent(resultSet.getString("content"));
        tweet.setMentionedUserIds(uuids(resultSet, "mentioned_user_ids"));
        return tweet;
    }
}
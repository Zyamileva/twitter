package org.zyamileva.twitter.dao.inmemory.jdbc.mapper;

import org.zyamileva.twitter.entities.Retweet;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RetweetResultSetMapper implements ResultSetMapper<Retweet> {

    @Override
    public Retweet map(ResultSet resultSet) throws SQLException {
        Retweet retweet = new Retweet(uuid(resultSet, "user_id"), uuid(resultSet, "tweet_id"));
        retweet.setId(id(resultSet));
        retweet.setDatePosted(localDateTime(resultSet, "date_posted"));
        return retweet;
    }
}
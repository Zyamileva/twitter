package org.zyamileva.twitter.dao.Inmemory.jdbc.mapper;

import org.zyamileva.twitter.entities.Like;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LikeResultSetMapper implements ResultSetMapper<Like>{

    @Override
    public Like map(ResultSet resultSet) throws SQLException {
       Like like = new Like(uuid(resultSet, "user_id"), uuid(resultSet, "tweet_id"));
        like.setId(id(resultSet));
        like.setDatePosted(localDateTime(resultSet, "data_posted"));
        return like;
    }
}
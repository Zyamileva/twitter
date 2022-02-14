package org.zyamileva.twitter.dao.Inmemory.jdbc.mapper;

import org.zyamileva.twitter.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserResultSetMapper implements ResultSetMapper<User> {

    public User map(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(UUID.fromString(resultSet.getString("id")));
        user.setUsername(resultSet.getString("username"));
        user.setLogin(resultSet.getString("login"));
        user.setAbout(resultSet.getString("about"));
        user.setLocation(resultSet.getString("location"));
        user.setRegisteredSince(resultSet.getTimestamp("registered_since").toLocalDateTime());
        user.setFollowingIds(uuids(resultSet, "following_ids"));
        user.setFollowerIds(uuids(resultSet, "follower_ids"));
        user.setOfficialAccount(resultSet.getBoolean("official_account"));
        return user;
    }
}
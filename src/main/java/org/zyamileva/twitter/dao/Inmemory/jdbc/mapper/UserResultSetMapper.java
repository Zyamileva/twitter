package org.zyamileva.twitter.dao.Inmemory.jdbc.mapper;

import org.zyamileva.twitter.entities.PersistentEntity;
import org.zyamileva.twitter.entities.User;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserResultSetMapper implements ResultSetMapper<User> {

    public User map(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(UUID.fromString(resultSet.getString("id")));
        user.setUsername(resultSet.getString("username"));
        user.setLogin(resultSet.getString("login"));
        user.setAbout(resultSet.getString("about"));
        user.setLocation(resultSet.getString("location"));
        user.setRegisteredSince(resultSet.getTimestamp("registered_since").toLocalDateTime());

        Object[] followingIdsArray = (Object[]) resultSet.getArray("following_ids").getArray();
        Set<UUID> followingIds = Arrays.stream(followingIdsArray)
                .map(id -> UUID.fromString(id.toString()))
                .collect(Collectors.toSet());
        user.setFollowingIds(followingIds);

        Object[] followerIdsArray = (Object[]) resultSet.getArray("follower_ids").getArray();
        Set<UUID> followerIds = Arrays.stream(followerIdsArray)
                .map(id -> UUID.fromString(id.toString()))
                .collect(Collectors.toSet());
        user.setFollowerIds(followerIds);

        user.setOfficialAccount(resultSet.getBoolean("official_account"));
        return user;
    }
}
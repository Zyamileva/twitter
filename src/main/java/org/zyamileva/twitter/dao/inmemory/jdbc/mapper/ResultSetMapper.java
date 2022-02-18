package org.zyamileva.twitter.dao.inmemory.jdbc.mapper;

import org.zyamileva.twitter.entities.PersistentEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public interface ResultSetMapper<T extends PersistentEntity> {
    T map(ResultSet resultSet) throws SQLException;

    default UUID id(ResultSet resultSet) throws SQLException {
        return uuid(resultSet, "id");
    }

    default UUID uuid(ResultSet resultSet, String column) throws SQLException {
        return UUID.fromString(resultSet.getString(column));
    }

    default UUID uuidNullable(ResultSet resultSet, String column) throws SQLException {
        String uuid = resultSet.getString(column);
        return uuid == null ? null : UUID.fromString(uuid);
    }

    default LocalDateTime localDateTime(ResultSet resultSet, String column) throws SQLException {
        return resultSet.getTimestamp(column).toLocalDateTime();
    }

    default Set<UUID> uuids(ResultSet resultSet, String column) throws SQLException {
        Object[] ids = (Object[]) resultSet.getArray(column).getArray();
        return Arrays.stream(ids)
                .map(id -> UUID.fromString(id.toString()))
                .collect(Collectors.toSet());
    }
}
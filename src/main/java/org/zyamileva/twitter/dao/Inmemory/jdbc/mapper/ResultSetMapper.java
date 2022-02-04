package org.zyamileva.twitter.dao.Inmemory.jdbc.mapper;

import org.zyamileva.twitter.entities.PersistentEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public interface ResultSetMapper<T extends PersistentEntity> {
    T map(ResultSet resultSet) throws SQLException;

    default Set<UUID> uuids(ResultSet resultSet, String column) throws SQLException {
        Object[] ids = (Object[]) resultSet.getArray(column).getArray();
        return Arrays.stream(ids)
                .map(id -> UUID.fromString(id.toString()))
                .collect(Collectors.toSet());
    }
}
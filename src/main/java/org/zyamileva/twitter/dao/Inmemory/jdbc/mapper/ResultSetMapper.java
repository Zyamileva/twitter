package org.zyamileva.twitter.dao.Inmemory.jdbc.mapper;

import org.zyamileva.twitter.entities.PersistentEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetMapper <T extends PersistentEntity> {
     T map(ResultSet resultSet) throws SQLException;
}
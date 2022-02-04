package org.zyamileva.twitter.dao.Inmemory.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zyamileva.twitter.dao.Inmemory.jdbc.mapper.ResultSetMapper;
import org.zyamileva.twitter.dao.Inmemory.jdbc.mapper.UserResultSetMapper;
import org.zyamileva.twitter.dao.UserDao;
import org.zyamileva.twitter.entities.PersistentEntity;
import org.zyamileva.twitter.entities.User;
import org.zyamileva.twitter.service.TweetService;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.zyamileva.twitter.dao.Inmemory.jdbc.H2Properties.H2_URL;

public class UserJDBCDao implements UserDao {
    private static final Logger log = LogManager.getLogger(TweetService.class);
    private final UserResultSetMapper userResultSetMapper = new UserResultSetMapper();

    @Override
    public User save(User entity) {
        if (entity.getId() == null) {
            return createUser(entity);
        } else {
            return updateUser(entity);
        }
    }

    private User updateUser(User entity) {
        final String updateUserQuery = """
                update users set
                   username = ?,
                   login = ?,
                   about = ?,
                   location = ?,
                   follower_ids = ?,
                   following_ids = ?,
                   official_account = ?
                where id = ?
                """;
        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            PreparedStatement ps = connection.prepareStatement(updateUserQuery);
            ps.setObject(1, entity.getUsername());
            ps.setObject(2, entity.getLogin());
            ps.setObject(3, entity.getAbout());
            ps.setObject(4, entity.getLocation());
            ps.setArray(5, connection.createArrayOf("UUID", entity.getFollowerIds().toArray()));
            ps.setObject(6, connection.createArrayOf("UUID", entity.getFollowingIds().toArray()));
            ps.setBoolean(7, entity.isOfficialAccount());
            ps.setObject(8, entity.getId());
            ps.execute();
            connection.commit();

            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error during user creation");
            return null;
        }

    }

    private User createUser(User entity) {
        final String createUserQuery = """
                insert into users(id, username, login, about, location, registered_since,follower_ids, following_ids, official_account)
                values (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            User savedUser = entity.clone();
            savedUser.setId(UUID.randomUUID());
            savedUser.setRegisteredSince(LocalDateTime.now());

            PreparedStatement ps = connection.prepareStatement(createUserQuery);
            ps.setObject(1, savedUser.getId());
            ps.setObject(2, savedUser.getUsername());
            ps.setObject(3, savedUser.getLogin());
            ps.setObject(4, savedUser.getAbout());
            ps.setObject(5, savedUser.getLocation());
            ps.setTimestamp(6, Timestamp.valueOf(savedUser.getRegisteredSince()));
            ps.setArray(7, connection.createArrayOf("UUID", savedUser.getFollowerIds().toArray()));
            ps.setArray(8, connection.createArrayOf("UUID", savedUser.getFollowingIds().toArray()));
            ps.setBoolean(9, savedUser.isOfficialAccount());
            ps.execute();
            connection.commit();

            return savedUser;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("User update error");
            return null;
        }
    }

    @Override
    public Optional<User> findById(UUID id) {
        final String findByIdQuery = """
                select *
                from users
                where id = ?
                 """;
        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            PreparedStatement ps = connection.prepareStatement(findByIdQuery);
            ps.setObject(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return Optional.of(userResultSetMapper.map(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error during find user by id " + id);
        }
        return Optional.empty();
    }

    @Override
    public List findAll() {
        final String findALLUsersQuery = """
                select *
                from users
                 """;
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(findALLUsersQuery);
            while (resultSet.next()) {
                users.add(userResultSetMapper.map(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error during find all users");
        }
        return users;
    }

    @Override
    public void delete(User entity) {
        final String deleteQuery = """
                delete from users
                where id = ?
                """;
        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            PreparedStatement ps = connection.prepareStatement(deleteQuery);
            ps.setObject(1, entity.getId());
            ps.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error during delete user " + entity);
        }

    }

    @Override
    public Optional<User> findByLogin(String login) {
        final String findByLoginQuery = """
                select *
                from users
                where login = ?
                 """;
        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            PreparedStatement ps = connection.prepareStatement(findByLoginQuery);
            ps.setObject(1, login);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return Optional.of(userResultSetMapper.map(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error during find user by login " + login);
        }
        return Optional.empty();
    }

    @Override
    public Set<User> findByIds(Set<UUID> ids) {
        String findByIdsQuery = """
                select *
                from users
                where id IN (?)
                """;
        Set<User> users = new HashSet<>();

        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            String inClause = ids.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining("', '", "'", "'"));
            findByIdsQuery = findByIdsQuery.replace("?", inClause);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(findByIdsQuery);
            while (resultSet.next()) {
                users.add(userResultSetMapper.map(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error during fetching users");
        }
        return users;
    }

    @Override
    public boolean existsById(UUID id) {
        final String existsByIdQuery = """
                select * 
                from users
                where id = ?
                """;
        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            PreparedStatement ps = connection.prepareStatement(existsByIdQuery);
            ps.setObject(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error during existsById id " + id);
        }
        return false;
    }
}
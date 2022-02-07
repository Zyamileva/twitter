package org.zyamileva.twitter.configuration.migrations;

import org.apache.commons.cli.CommandLine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zyamileva.twitter.configuration.options.Configuration;
import org.zyamileva.twitter.configuration.options.DaoTypeOption;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.zyamileva.twitter.dao.Inmemory.jdbc.H2Properties.H2_URL;

public class Migration {
    private static final Logger log = LogManager.getLogger(Migration.class);

    private static final String CREATE_TABLE_USERS_QUERY = """
            create table if not exists users(
            id uuid primary key,
            username varchar(20) not null,
            login varchar(14) not null unique,
            about varchar(140),
            location varchar(30),
            registered_since timestamp not null,
            follower_ids uuid array not null,
            following_ids uuid array not null,
            official_account boolean not null
            );
            """;
    private static final String CREATE_TABLE_TWEETS_QUERY = """
            create table if not exists tweets(
            id uuid primary key,
            user_id uuid not null,
            reply_tweet_id uuid,
            date_posted timestamp not null,
            content varchar(140) not null,
            mentioned_user_ids uuid array not null,
            foreign key (user_id) references users(id),
            foreign key (reply_tweet_id) references tweets(id)
            );
            """;
    private static final String CREATE_TABLE_LIKES_QUERY = """
            create table if not exists likes(
            user_id uuid not null,
            tweet_id uuid not null,
            date_posted timestamp not null,
            foreign key (user_id) references users(id),
            foreign key (tweet_id) references tweets(id)
            );
            """;
    private static final String CREATE_TABLE_RETWEETS_QUERY = """
            create table if not exists retweets(
            user_id uuid not null,
            tweet_id uuid not null,
            date_posted timestamp not null,
            foreign key (user_id) references users(id),
            foreign key (tweet_id) references tweets(id)
            );          
            """;

    public Migration() {
    }

    public static Migration getInstance() {
        return Migration.MigrationHolder.MIGRATION;
    }

    private static class MigrationHolder {
        private static final Migration MIGRATION = new Migration();
    }

    public void performMigration(Configuration configuration) {
        if (configuration.getDaoTypeOption() == DaoTypeOption.JDBC) {
            initDB();
            if (configuration.isPopulateDB()) {
                populateDB();
            }
        }
    }

    private static boolean dbInitialised = false;

    private void initDB() {
        if (!dbInitialised) {
            executeQuery(CREATE_TABLE_USERS_QUERY);
            executeQuery(CREATE_TABLE_TWEETS_QUERY);
            executeQuery(CREATE_TABLE_LIKES_QUERY);
            executeQuery(CREATE_TABLE_RETWEETS_QUERY);
            dbInitialised = true;
            log.debug("DB initialised.");
        } else {
            log.warn("DB ha been initialised earlier.");
        }
    }

    private boolean executeQuery(String query) {
        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            Statement statement = connection.createStatement();
            statement.execute(query);
            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error during find all users");
        }
        ;
        return false;
    }

    private static final String INSERT_INTO_TABBLE_USERS_QUERY = """
        insert into USERS(id, username, login, about, location, registered_since,follower_ids, following_ids, official_account)
        values ('e0988b42-6b87-42ff-ab63-61ad5b621f8d',	'Kate Zyamileva',	'@kate',	'Girl',	'Odessa', '2009-08-02 22:19:12', array[]	,	array['ea8649fa-4feb-46df-9289-9bd34a289687', '03dccf47-6d9b-4835-847b-fcd4dff93c8e'],	'true')          
         """;

    private void populateDB() {
        executeQuery(INSERT_INTO_TABBLE_USERS_QUERY);

    }
}
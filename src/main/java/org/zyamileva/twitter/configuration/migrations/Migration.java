package org.zyamileva.twitter.configuration.migrations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zyamileva.twitter.configuration.options.Configuration;
import org.zyamileva.twitter.configuration.options.Context;
import org.zyamileva.twitter.configuration.options.DaoTypeOption;
import org.zyamileva.twitter.entities.Tweet;
import org.zyamileva.twitter.entities.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.zyamileva.twitter.dao.inmemory.jdbc.H2Properties.H2_URL;

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
            id uuid primary key,
            user_id uuid not null,
            tweet_id uuid not null,
            date_posted timestamp not null,
            foreign key (user_id) references users(id),
            foreign key (tweet_id) references tweets(id),
            constraint pair_like unique (user_id, tweet_id)
            );
            """;
    private static final String CREATE_TABLE_RETWEETS_QUERY = """
            create table if not exists retweets(
            id uuid primary key,
            user_id uuid not null,
            tweet_id uuid not null,
            date_posted timestamp not null,
            foreign key (user_id) references users(id),
            foreign key (tweet_id) references tweets(id),
            constraint pair_retweet unique (user_id, tweet_id)
            );          
            """;
    private static final String INSERT_INTO_TABLE_USERS_QUERY = """
            insert into USERS(id, username, login, about, location, registered_since,follower_ids, following_ids, official_account)
            values
            ('e0988b42-6b87-42ff-ab63-61ad5b621f8d',	'Kate Zyamileva',	'@kate',	'Girl',	'Odessa', '2022-01-07 22:19:12', array[],	array['ea8649fa-4feb-46df-9289-9bd34a289687', '03dccf47-6d9b-4835-847b-fcd4dff93c8e'],	'true'),
            ('03dccf47-6d9b-4835-847b-fcd4dff93c8e',	'Anna Zyamileva',	'@anna',	'Girl',	'DE, Delaware', '2022-01-28 22:19:12', array['e0988b42-6b87-42ff-ab63-61ad5b621f8d', 'ea8649fa-4feb-46df-9289-9bd34a289687'],	array[],	'true'),    
            ('ea8649fa-4feb-46df-9289-9bd34a289687',	'Nikita Ivanov',	'@nikita_ivanov',	'Boy',	'DE, Delaware', '2022-02-05 22:19:12', array['e0988b42-6b87-42ff-ab63-61ad5b621f8d'],	array['03dccf47-6d9b-4835-847b-fcd4dff93c8e'],	'true')         
            """;
    private static final String INSERT_INTO_TABLE_TWEETS_QUERY = """
            insert into TWEETS(id, user_id, reply_tweet_id, date_posted, content, mentioned_user_ids)
            values 
            ('834494ac-08dd-4601-ae6e-d195fb435378', '03dccf47-6d9b-4835-847b-fcd4dff93c8e', null, '2022-02-05 18:55:20', 'Hello @nikita_ivanov !', array['ea8649fa-4feb-46df-9289-9bd34a289687']),
            ('a2ab06fc-b1eb-4559-8886-24a200a481dc', 'e0988b42-6b87-42ff-ab63-61ad5b621f8d', null, '2022-02-06 18:55:20', 'Hello', array[]),
            ('abad2161-da52-443b-aa3a-33556fcd18db', 'ea8649fa-4feb-46df-9289-9bd34a289687', null, '2022-02-07 18:55:20', 'Hello my friends', array[]),
            ('70caa93e-7a1c-43e9-ad09-10a81a20ea7c', '03dccf47-6d9b-4835-847b-fcd4dff93c8e', 'abad2161-da52-443b-aa3a-33556fcd18db' , '2022-02-08 18:55:20', 'Hello, Nikita @nikita_ivanov', array['ea8649fa-4feb-46df-9289-9bd34a289687'])
            """;
    private static final String INSERT_INTO_TABLE_LIKES_QUERY = """
            insert into LIKES(id, user_id, tweet_id, date_posted)
            values
            ('ccb4fc2a-0974-4212-9e30-65aa6e4848ce', 'e0988b42-6b87-42ff-ab63-61ad5b621f8d', 'abad2161-da52-443b-aa3a-33556fcd18db', '2022-02-09 18:55:20')
            """;
    private static final String INSERT_INTO_TABLE_RETWEETS_QUERY = """
            insert into RETWEETS(id, user_id, tweet_id, date_posted)
            values
            ('ccd1fa2f-38f0-484f-9383-f5b793208730', '03dccf47-6d9b-4835-847b-fcd4dff93c8e', 'abad2161-da52-443b-aa3a-33556fcd18db', '2022-02-10 18:55:20')
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
        } else {
            if (configuration.isPopulateDB()) {
                populateStorage();
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
            log.error("Error during execute query");
        }
        return false;
    }

    private void deletingInvalidData() {
        try (Connection connection = DriverManager.getConnection(H2_URL)) {
            Statement statement = connection.createStatement();
            statement.execute("delete from likes");
            statement.execute("delete from retweets");
            statement.execute("delete from tweets");
            statement.execute("delete from users");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error during deleting invalid data query");
        }
    }

    private void populateDB() {
        if (!executeQuery(INSERT_INTO_TABLE_USERS_QUERY) || !executeQuery(INSERT_INTO_TABLE_TWEETS_QUERY)
                || !executeQuery(INSERT_INTO_TABLE_LIKES_QUERY) || !executeQuery(INSERT_INTO_TABLE_RETWEETS_QUERY)) {
            deletingInvalidData();
        }
    }

    private void populateStorage() {
        User nikita = Context.getInstance().getUserService().saveUser(new User("Nikita Ivanov", "@nikita_ivanov")).orElseThrow();
        User anna = Context.getInstance().getUserService().saveUser(new User("Anna Zyamileva", "@anna")).orElseThrow();
        User kate = Context.getInstance().getUserService().saveUser(new User("Kate Zyamileva", "@kate_zyam")).orElseThrow();

        Tweet tweetNikita = Context.getInstance().getTweetService().saveTweet(new Tweet(nikita.getId(), "Hello my friends")).orElseThrow();
        Tweet tweetAnna = Context.getInstance().getTweetService().saveTweet(new Tweet(anna.getId(), "Hello my friends and @nikita_ivanov")).orElseThrow();
        Tweet tweetKate = Context.getInstance().getTweetService().saveTweet(new Tweet(kate.getId(), "Hello!")).orElseThrow();

        Context.getInstance().getTweetService().retweet(anna.getId(), tweetKate.getId());
        Context.getInstance().getTweetService().retweet(anna.getId(), tweetNikita.getId());
        Context.getInstance().getTweetService().like(anna.getId(), tweetKate.getId());
        Context.getInstance().getTweetService().like(kate.getId(), tweetAnna.getId());

        Context.getInstance().getUserService().subscribe(anna.getId(), kate.getId());
        Context.getInstance().getUserService().subscribe(kate.getId(), anna.getId());

        log.debug("Storage initialised.");
    }
}
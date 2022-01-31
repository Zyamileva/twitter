package org.zyamileva.twitter;

import com.sun.source.doctree.SeeTree;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.zyamileva.twitter.Feed.HomeFeed;
import org.zyamileva.twitter.Feed.ReplyFeed;
import org.zyamileva.twitter.Feed.UserFeed;
import org.zyamileva.twitter.dao.Inmemory.jdbc.UserJDBCDao;
import org.zyamileva.twitter.entities.Like;
import org.zyamileva.twitter.entities.Retweet;
import org.zyamileva.twitter.entities.Tweet;
import org.zyamileva.twitter.entities.User;
import org.zyamileva.twitter.service.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class Twitter {
    private static final UserService userService = new UserServiceImpl();
    private static final TweetService tweetService = new TweetServiceImpl();
    private static final FeedService feedService = new FeedServiceImpl();
    private static final UserJDBCDao userJDBCDao = new UserJDBCDao();

    public static void main(String[] args) {
        User kate = userService.findByLogin("@kate").orElseThrow();
        //User nikita = new User("Nikita Ivanov", "@nikita_ivanov");

        // userService.saveUser(nikita);

        //User nikita = userService.findByLogin("@nikita_ivanov").orElseThrow();
        //userService.saveUser(kate);

        //kate.setLocation("Odessa");
        //userService.saveUser(kate);

        //System.out.println(userService.findById(kate.getId()).orElseThrow());

        //  userService.delete(nikita);

        //  userJDBCDao.findAll().forEach(user -> System.out.println(user));

        Set<UUID> userSet = new HashSet<>();
        userSet.add(UUID.fromString("e0988b42-6b87-42ff-ab63-61ad5b621f8d"));
        userSet.add(UUID.fromString("03dccf47-6d9b-4835-847b-fcd4dff93c8e"));
        System.out.println(userService.findByIds(userSet));

        System.out.println(userService.existById(UUID.fromString("03dccf47-6d9b-4835-847b-fcd4dff93c8e")));

//        User kate = new User("Kate Zyamileva", "@kate_zyam");
//        User anna = new User("Anna Zyamileva", "@___j");
//        User nikita = new User("Nikita Ivanov", "@nikita_ivanov");
//
//        kate.setOfficialAccount(true);
//        anna.setOfficialAccount(true);
//        nikita.setOfficialAccount(true);
//
//        kate = userService.saveUser(kate).orElseThrow();
//        anna = userService.saveUser(anna).orElseThrow();
//        nikita = userService.saveUser(nikita).orElseThrow();
//
//        log.info(kate);
//
//        User ann = new User("Anna Zyamikleva", "@___j");
//        //  ann = userService.saveUser(ann).orElseThrow();
//
       // Tweet tweetAnna = new Tweet(anna.getId(), "Hello @nikita_ivanov !");
        Tweet tweetKate = new Tweet(kate.getId(), "Hello!");
        //Tweet tweetNikita = new Tweet(nikita.getId(), "Hello my friends");
        tweetKate = tweetService.saveTweet(tweetKate).orElseThrow();
       // tweetAnna = tweetService.saveTweet(tweetAnna).orElseThrow();
        //tweetNikita = tweetService.saveTweet(tweetNikita).orElseThrow();

        //tweetService.retweet(kate.getId(), tweetAnna.getId());
        tweetService.like(kate.getId(), tweetKate.getId());

        UserFeed userFeed = feedService.buildUserFeed(kate.getId());
        System.out.println((userFeed));
//
//        userService.subscribe(nikita.getId(), anna.getId());
//        userService.subscribe(nikita.getId(), kate.getId());
//
//        HomeFeed homeFeed = feedService.buildHomeFeed(nikita.getId());
//        log.info(homeFeed);
//
//        Tweet tweetAnnaForNikita = new Tweet(anna.getId(), "Good");
//        Tweet tweetKateForNikite = new Tweet(kate.getId(), "Grest!");
//        tweetAnnaForNikita = tweetService.saveTweet(tweetAnnaForNikita).orElseThrow();
//        tweetKateForNikite = tweetService.saveTweet(tweetKateForNikite).orElseThrow();
//
//        tweetAnnaForNikita.setReplyTweetId(tweetNikita.getId());
//        tweetKateForNikite.setReplyTweetId(tweetNikita.getId());
//        tweetAnnaForNikita = tweetService.saveTweet(tweetAnnaForNikita).orElseThrow();
//        tweetKateForNikite = tweetService.saveTweet(tweetKateForNikite).orElseThrow();
//
//        ReplyFeed replayFeed = feedService.buildReplyFeed(tweetNikita.getId());
//        System.out.println((replayFeed));
    }
}
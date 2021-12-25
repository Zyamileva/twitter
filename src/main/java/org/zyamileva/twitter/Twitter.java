package org.zyamileva.twitter;

import org.zyamileva.twitter.Feed.UserFeed;
import org.zyamileva.twitter.dao.Inmemory.LikeInMemoryDao;
import org.zyamileva.twitter.dao.Inmemory.RetweetInMemoryDao;
import org.zyamileva.twitter.dao.Inmemory.TweetInMemoryDao;
import org.zyamileva.twitter.dao.Inmemory.UserInMemoryDao;
import org.zyamileva.twitter.dao.LikeDao;
import org.zyamileva.twitter.dao.RetweetDao;
import org.zyamileva.twitter.dao.TweetDao;
import org.zyamileva.twitter.dao.UserDao;
import org.zyamileva.twitter.entities.Like;
import org.zyamileva.twitter.entities.Retweet;
import org.zyamileva.twitter.entities.Tweet;
import org.zyamileva.twitter.entities.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Twitter {
    public static final UserDao userDao = new UserInMemoryDao();
    public static final TweetDao tweetDao = new TweetInMemoryDao();
    public static final LikeDao likeDao = new LikeInMemoryDao();
    public static final RetweetDao retweetDao = new RetweetInMemoryDao();

    public static void main(String[] args) {
        User kate = new User("Kate Zyamileva", "@kate_zyamileva");
        User anna = new User("Anna Zyamileva", "@anna_zyamileva");
        kate.setOfficialAccount(true);
        anna.setOfficialAccount(true);


        /*System.out.println(kate);
        kate = userDao.save(kate);
        System.out.println(kate);

        User kateFromDB = userDao.findById(kate.getId()).orElseThrow();
        System.out.println(kateFromDB);
        System.out.println();

        //System.out.println(userDao.findAll());

        kateFromDB.setAbout("Hello");
        userDao.save(kateFromDB);

        System.out.println(kateFromDB);
        System.out.println();

        User anotherKateFromDB = userDao.findById(kate.getId()).orElseThrow();
        System.out.println(anotherKateFromDB);
        System.out.println(); */

        anna = userDao.save(anna);
        kate = userDao.save(kate);

        System.out.println(userDao.findAll());
        userDao.delete(kate);
        System.out.println(userDao.findAll());

        System.out.println();
        System.out.println(userDao.findByLogin("@anna_zyamileva").orElseThrow());

        Tweet firstAnnaTweet = new Tweet(anna.getId(), "Hi!!!", new HashSet<>());
        Tweet firstKateTweet = new Tweet(kate.getId(), "Good day.", new HashSet<>());

        System.out.println(firstAnnaTweet);
        firstAnnaTweet = tweetDao.save(firstAnnaTweet);
        firstKateTweet = tweetDao.save(firstKateTweet);

        System.out.println();
        Tweet anotherTweet = tweetDao.findById(firstAnnaTweet.getId()).orElseThrow();
        System.out.println(anotherTweet);
        System.out.println();

        System.out.println("hhh" + tweetDao.findAll());

        System.out.println();
        tweetDao.delete(firstAnnaTweet);
        System.out.println(tweetDao.findAll());
        System.out.println();

        Like annaLikesKateTweet = new Like(anna.getId(), firstKateTweet.getId());
        System.out.println(annaLikesKateTweet);
        annaLikesKateTweet = likeDao.save(annaLikesKateTweet);
        System.out.println(annaLikesKateTweet);
        System.out.println();

        firstKateTweet.getLikeIds().add(annaLikesKateTweet.getId());
        firstKateTweet = tweetDao.save(firstKateTweet);
        System.out.println(firstKateTweet);
        System.out.println();

        Retweet annaRetweetKateTweet = new Retweet(anna.getId(), firstKateTweet.getId());
        System.out.println(annaRetweetKateTweet);
        annaRetweetKateTweet = retweetDao.save(annaRetweetKateTweet);
        System.out.println(annaRetweetKateTweet);

        firstKateTweet.getRetweetIds().add(annaRetweetKateTweet.getId());
        firstKateTweet = tweetDao.save(firstKateTweet);
        System.out.println(firstKateTweet);

        /*likeDao.delete(annaLikesKateTweet);
        retweetDao.delete(annaRetweetKateTweet);
        System.out.println("jjj");
        System.out.println(likeDao.findAll());
        System.out.println(retweetDao.findAll()); */

        /*
        System.out.println("(Users: ");
        System.out.println(kate);
        System.out.println(anna);
        System.out.println();

       Tweet firstKateTweet = new Tweet(kate.getId(), "Hello!!!", new HashSet<>());
        Set<UUID> mentionedUsersOnAnnaTweet = new HashSet<>();
        mentionedUsersOnAnnaTweet.add(kate.getId());
        Tweet firstAnnaTweet = new Tweet(anna.getId(), "Hi all and @kate_zyamileva !!!", mentionedUsersOnAnnaTweet);

        System.out.println("Tweets: ");
        System.out.println(firstAnnaTweet);
        System.out.println(firstKateTweet);

        Like annaLikesKateTweet = new Like(anna.getId(), firstKateTweet.getId());
        System.out.println("Anna likes Kate tweet");
        System.out.println(annaLikesKateTweet);
        System.out.println();

        firstKateTweet.getLikeIds().add(annaLikesKateTweet.getId());
        System.out.println("Kate tweet update: ");
        System.out.println(firstKateTweet);
        System.out.println();

        anna.getFollowerIds().add(kate.getId());
        kate.getFollowingIds().add(anna.getId());
        System.out.println("Kate started follow Anna: ");
        System.out.println();
        System.out.println(anna);
        System.out.println(kate);
        System.out.println();

        Tweet secondKateTweet = new Tweet(kate.getId(), "Hello, I'm with you again.", new HashSet<>());
        System.out.println("Kate posted second tweet: ");
        System.out.println(secondKateTweet);
        System.out.println();

        UserFeed KateFeed = new UserFeed(kate, Set.of(firstKateTweet, secondKateTweet));
        System.out.println("Kate feed: ");
        System.out.println(KateFeed);
        System.out.println();

        Tweet secondAnnaTweet = new Tweet(anna.getId(), "Hi, ......", new HashSet<>());
        System.out.println("Anna posted second tweet: ");
        System.out.println(secondAnnaTweet);
        System.out.println();

        UserFeed AnnaFeed = new UserFeed(anna, Set.of(firstAnnaTweet, secondAnnaTweet));
        System.out.println("Anna feed: ");
        System.out.println(AnnaFeed);
        System.out.println(); */
    }
}
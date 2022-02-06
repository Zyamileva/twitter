package org.zyamileva.twitter;

import com.sun.source.doctree.SeeTree;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.zyamileva.twitter.Feed.HomeFeed;
import org.zyamileva.twitter.Feed.ReplyFeed;
import org.zyamileva.twitter.Feed.UserFeed;
import org.zyamileva.twitter.dao.Inmemory.jdbc.LikeJDBCDao;
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
    private static final LikeJDBCDao likeJDBCDao = new LikeJDBCDao();

    public static void main(String[] args) {
//        User kate = new User("Kate Zyamileva", "@kate_zyam");
//        User anna = new User("Anna Zyamileva", "@___j");
//        User nikita = new User("Nikita Ivanov", "@nikita_ivanov");

//        kate.setOfficialAccount(true);
//        anna.setOfficialAccount(true);
//        nikita.setOfficialAccount(true);

        System.out.println(feedService.buildHomeFeed(userService.findByLogin("@nikita_ivanov").orElseThrow().getId()));
        System.out.println(feedService.buildUserFeed(userService.findByLogin("@anna").orElseThrow().getId()));
        System.out.println(feedService.buildReplyFeed(UUID.fromString("abad2161-da52-443b-aa3a-33556fcd18db")));

//        nikita = userService.saveUser(nikita).orElseThrow();

        User kate = userService.findByLogin("@kate").orElseThrow();
        User anna = userService.findByLogin("@anna").orElseThrow();
        User nikita = userService.findByLogin("@nikita_ivanov").orElseThrow();
        //  tweetService.retweet(userService.findByLogin("@anna").orElseThrow().getId(), UUID.fromString("abad2161-da52-443b-aa3a-33556fcd18db"));

//        Tweet tweetKate = new Tweet(kate.getId(), "Hello!");
//        tweetKate = tweetService.saveTweet(tweetKate).orElseThrow();
//        Tweet tweetAnna = new Tweet(anna.getId(), "Hello @nikita_ivanov !");
//        tweetAnna = tweetService.saveTweet(tweetAnna).orElseThrow();
//        Tweet tweetNikita = new Tweet(nikita.getId(), "Hello my friends");
//        tweetNikita = tweetService.saveTweet(tweetNikita).orElseThrow();
//         Set <UUID> followingKate = new HashSet<>();
//         followingKate.add(nikita.getId());
//         followingKate.add(anna.getId());
//         kate.setFollowingIds(followingKate);
//        kate = userService.saveUser(kate).orElseThrow();
//        Set<UUID> followingNikita = new HashSet<>();
//        followingNikita.add(anna.getId());
//        nikita.setFollowingIds(followingNikita);
//        nikita = userService.saveUser(nikita).orElseThrow();
//
//        Set<UUID> followerNikita = new HashSet<>();
//        followerNikita.add(kate.getId());
//        nikita.setFollowerIds(followerNikita);
//        nikita = userService.saveUser(nikita).orElseThrow();

//        Set<UUID> followerAnna = new HashSet<>();
//        followerAnna.add(nikita.getId());
//        anna.setFollowerIds(followerAnna);
//        anna = userService.saveUser(anna).orElseThrow();
//
//        Set<UUID> follower = new HashSet<>();
//        follower.add(kate.getId());
//        follower.add(nikita.getId());
//
//        anna.setFollowerIds(follower);
//        anna = userService.saveUser(anna).orElseThrow();

//        tweetService.like(kate.getId(),UUID.fromString("abad2161-da52-443b-aa3a-33556fcd18db"));
//
//        Tweet tweetAnnaForNikita = new Tweet(anna.getId(), "Hello, Nikita @nikita_ivanov");
//        tweetAnnaForNikita = tweetService.saveTweet(tweetAnnaForNikita).orElseThrow();
//        tweetService.saveTweet(annaTweet);
//
//        tweetService.delete(tweetService.findById(UUID.fromString("bd635dab-dcdf-4bec-a1b7-bd30f4ce4e92")).orElseThrow());
//        User t = userService.findById(UUID.fromString("03dccf47-6d9b-4835-847b-fcd4dff93c8e")).orElseThrow();
//        Set<UUID> userSet = new HashSet<>();
//        userSet.add(t.getId());
//        System.out.println(tweetService.findFollowingTweets(userSet));

//        System.out.println(tweetService.findTweetsByUserId(t.getId()));
        //(UUID.fromString("7c76d022-0836-4944-8316-dfc6d239d708")));

//       System.out.println( tweetService.countLikes(UUID.fromString("abad2161-da52-443b-aa3a-33556fcd18db")));

//        tweetService.like(anna.getId(),UUID.fromString("abad2161-da52-443b-aa3a-33556fcd18db"));

//        tweetService.deleteLike(anna.getId(),UUID.fromString("abad2161-da52-443b-aa3a-33556fcd18db"));

//        Tweet tweetAnnaForNikita = new Tweet(anna.getId(), "Hello, Nikita @nikita_ivanov");
//        tweetAnnaForNikita = tweetService.saveTweet(tweetAnnaForNikita).orElseThrow();
//        tweetService.like(UUID.fromString("03dccf47-6d9b-4835-847b-fcd4dff93c8e"), UUID.fromString("365814ca-3f03-4520-9341-c916666444a2"));
//        likeJDBCDao.deleteAllByTweetId(UUID.fromString("365814ca-3f03-4520-9341-c916666444a2"));
//        System.out.println( likeJDBCDao.findById(UUID.fromString("ccb4fc2a-0974-4212-9e30-65aa6e4848ce")));

    }

}
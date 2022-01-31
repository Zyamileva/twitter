package org.zyamileva.twitter.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zyamileva.twitter.Feed.HomeFeed;
import org.zyamileva.twitter.Feed.ReplyFeed;
import org.zyamileva.twitter.Feed.UserFeed;
import org.zyamileva.twitter.entities.PersistentEntity;
import org.zyamileva.twitter.entities.Tweet;
import org.zyamileva.twitter.entities.User;
import org.zyamileva.twitter.model.TweetProjection;

import java.util.*;
import java.util.stream.Collectors;

public class FeedServiceImpl implements FeedService {
    private final UserService userService = new UserServiceImpl();
    private final TweetService tweetService = new TweetServiceImpl();
    private static final Logger log = LogManager.getLogger(TweetService.class);

    private TreeSet<TweetProjection> getTweetProjections(Set<Tweet> tweets, Map<UUID, User> authorIdToAuthor) {
        return tweets
                .stream()
                .map(tweet -> new TweetProjection(
                        authorIdToAuthor.get(tweet.getUserId()).getUsername(),
                        authorIdToAuthor.get(tweet.getUserId()).getLogin(),
                        tweet.getDataPosted(),
                        tweet.getContent(),
                        0,
                        0
                ))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public UserFeed buildUserFeed(UUID userId) {
        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isEmpty()) {
            log.error("User not found by id: " + userId);
            return null;
        }
        User user = userOptional.get();

        Set<Tweet> tweets = tweetService.findTweetsByUserId(userId);
        Set<Tweet> retweets = tweetService.findRetweetsByUserId(userId);
        tweets.addAll(retweets);

        Map<UUID, User> authorIdToAutor = tweets
                .stream()
                .collect(Collectors.toMap(tweet -> tweet.getUserId(), tweet -> userService.findById(tweet.getUserId()).get()));

        TreeSet tweetProjections = getTweetProjections(tweets, authorIdToAutor);
        return new UserFeed(user, tweetProjections);
    }

    @Override
    public HomeFeed buildHomeFeed(UUID userId) {
        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isEmpty()) {
            log.error("User not found by id: " + userId);
            return null;
        }
        User user = userOptional.get();

        Set<Tweet> followingTweets = tweetService.findFollowingTweets(user.getFollowingIds());

        Map<UUID, User> authorIdToAutor = userService.findByIds(user.getFollowingIds())
                .stream()
                .collect(Collectors.toMap(PersistentEntity::getId, author -> author));

        TreeSet tweetProjections = getTweetProjections(followingTweets, authorIdToAutor);
        return new HomeFeed(user, tweetProjections);
    }

    @Override
    public ReplyFeed buildReplyFeed(UUID tweetId) {
        Optional<Tweet> tweetOptional = tweetService.findById(tweetId);
        if (tweetOptional.isEmpty()) {
            log.error("Tweet not found by id: " + tweetId);
            return null;
        }
        Tweet tweet = tweetOptional.get();
        List<Tweet> tweets = tweetService.findAllTweet()
                .stream()
                .filter(tweetList -> (tweetList.getReplyTweetId() != null) && (tweetList.getReplyTweetId().equals(tweet.getId())))
                .collect(Collectors.toList());

        Map<UUID, User> authorIdToAutor = tweets
                .stream()
                .collect(Collectors.toMap(tweetStream -> tweetStream.getUserId(), tweetStream -> userService.findById(tweetStream.getUserId()).get()));

        Set tweetsSet = new HashSet<>(tweets);
        TreeSet tweetProjections = getTweetProjections(tweetsSet, authorIdToAutor);
        return new ReplyFeed(tweet, tweetProjections);
    }
}
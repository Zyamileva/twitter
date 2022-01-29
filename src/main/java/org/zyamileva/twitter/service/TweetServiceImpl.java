package org.zyamileva.twitter.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import java.util.*;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TweetServiceImpl implements TweetService {
    private static final Logger log = LogManager.getLogger(TweetService.class);
    private final TweetDao tweetDao = new TweetInMemoryDao();
    private final UserDao userDao = new UserInMemoryDao();
    private final RetweetDao retweetDao = new RetweetInMemoryDao();
    private final LikeDao likeDao = new LikeInMemoryDao();
    private final UserService userService = new UserServiceImpl();
    private static final int MAX_LENGTH = 140;

    @Override
    public Optional<Tweet> saveTweet(Tweet tweet) {
        Set<String> validateErrors = validateTweet(tweet);
        if (validateErrors.isEmpty()) {
            return Optional.of(tweetDao.save(tweet));
        } else {
            log.error(validateErrors);
            return Optional.empty();
        }
    }

    private Set<String> validateTweet(Tweet tweet) {
        Set<String> errors = new HashSet<>();
        if (tweet.getContent().isBlank() || tweet.getContent().isEmpty()) {
            errors.add("Tweet content can't be blank or empty");
        }
        if (tweet.getContent().length() > MAX_LENGTH) {
            errors.add("Maximum tweet length is " + MAX_LENGTH + " characters.");
        }
        String regex = "@[a-z_]+\\b";
        Pattern pat = Pattern.compile(regex);
        Matcher mat = pat.matcher(tweet.getContent());
        Set<UUID> uuids = new HashSet<>();

        while (mat.find()) {
            if (!UserServiceImpl.validateLogin(tweet.getContent().substring(mat.start(), mat.end()))) {
                errors.add("The tweet contains a link - " + tweet.getContent().substring(mat.start(), mat.end()) + " to an invalid login.");
            } else {
                uuids.add(userDao.findByLogin(tweet.getContent().substring(mat.start(), mat.end())).get().getId());
            }
        }
        if (errors.isEmpty()) {
            tweet.setMentionedUserIds(uuids);
            return errors;
        }
        return errors;
    }

    @Override
    public Optional<Tweet> findById(UUID tweetId) {
        return tweetDao.findById(tweetId);
    }

    @Override
    public Set<Tweet> findFollowingTweets(Set<UUID> userIds) {
        return tweetDao.findFollowingTweets(userIds);
    }

    @Override
    public Set<Tweet> findTweetsByUserId(UUID userId) {
        return tweetDao.findTweetsByUserId(userId);
    }

    @Override
    public Set<Tweet> findRetweetsByUserId(UUID userId) {
        return tweetDao.findRetweetsByUserId(userId);
    }

    @Override
    public boolean like(UUID userId, UUID tweetId) {
        BiConsumer<UUID, UUID> likeConsumer = (userIdConsumer, tweetIdConsumer) -> {
            Like like = new Like(userIdConsumer, tweetIdConsumer);
            like = likeDao.save(like);
            tweetDao.save(tweetDao.findById(tweetId).get());
        };
        return actionWithTweet(userId, tweetId, likeConsumer);
    }

    @Override
    public boolean retweet(UUID userId, UUID tweetId) {
        BiConsumer<UUID, UUID> retweetConsumer = (userIdConsumer, tweetIdConsumer) -> {
            Retweet retweet = new Retweet(userIdConsumer, tweetIdConsumer);
            retweet = retweetDao.save(retweet);
            tweetDao.save(tweetDao.findById(tweetId).get());
        };
        return actionWithTweet(userId, tweetId, retweetConsumer);
    }

    @Override
    public List<Tweet> findAllTweet() {
        return tweetDao.findAll();
    }

    private boolean actionWithTweet(UUID userId, UUID tweetId, BiConsumer<UUID, UUID> action) {
        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isEmpty()) {
            log.error("User not found by id: " + userId);
            return false;
        }
        Optional<Tweet> tweetOptional = tweetDao.findById(tweetId);
        if (tweetOptional.isEmpty()) {
            log.error("Tweet not found by id: " + tweetId);
            return false;
        }
        action.accept(userId, tweetId);
        return true;
    }
}
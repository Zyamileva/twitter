package org.zyamileva.twitter.dao.Inmemory;

import org.zyamileva.twitter.entities.Like;
import org.zyamileva.twitter.entities.Retweet;
import org.zyamileva.twitter.entities.Tweet;
import org.zyamileva.twitter.entities.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Storage {
    private static final HashMap<UUID, User> USERS = new HashMap<>();
    private static final HashMap<UUID, Tweet> TWEETS = new HashMap<>();
    private static final HashMap<UUID, Like> LIKES = new HashMap<>();
    private static final HashMap<UUID, Retweet> RETWEET = new HashMap<>();

    protected static User putUser(User user) {
        if (user == null) {
            return null;
        }
        User clone = user.clone();
        if (clone.getId() == null) {
            clone.setId(UUID.randomUUID());
            clone.setRegisteredSince(LocalDateTime.now());
        }
        USERS.put(clone.getId(), clone);
        return getByUserId(clone.getId());
    }

    protected static User getByUserId(UUID id) {
        return USERS.get(id).clone();
    }

    protected static Iterable<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        for (User user : USERS.values()) {
            users.add(user.clone());
        }
        return users;
    }

    protected static void deleteUser(User entity) {
        if (entity != null && entity.getId() != null) {
            USERS.remove(entity.getId());
        }
    }

    protected static Tweet putTweet(Tweet entity) {
        if (entity == null) {
            return null;
        }
        Tweet clone = entity.clone();
        if (clone.getId() == null) {
            clone.setId(UUID.randomUUID());
            clone.setDataPosted(LocalDateTime.now());
        }
        TWEETS.put(clone.getId(), clone);
        return getByTweetId(clone.getId());
    }

    protected static Tweet getByTweetId(UUID id) {
        return TWEETS.get(id).clone();
    }

    protected static Iterable<Tweet> findAllTweets() {
        List<Tweet> tweets = new ArrayList<>();
        for (Tweet value : TWEETS.values()) {
            tweets.add(value.clone());
        }
        return tweets;
    }

    protected static void deleteTweet(Tweet entity) {
        if (entity != null && entity.getId() != null) {
            TWEETS.remove(entity.getId());
        }
    }

    protected static Like putLike(Like like) {
        if (like == null) {
            return null;
        }
        Like clone = like.clone();
        if (clone.getId() == null) {
            clone.setId(UUID.randomUUID());
            clone.setDatePosted(LocalDateTime.now());
        }
        LIKES.put(clone.getId(), clone);
        return getByLikeId(clone.getId());
    }

    protected static Like getByLikeId(UUID id) {
        return LIKES.get(id).clone();
    }

    protected static Iterable<Like> findAllLikes() {
        List<Like> likes = new ArrayList<>();
        for (Like like : LIKES.values()) {
            likes.add(like.clone());
        }
        return likes;
    }

    protected static void deleteLike(Like like) {
        if (like != null && like.getId() != null) {
            LIKES.remove(like.getId());
        }
    }

    protected static Retweet putRetweet(Retweet retweet) {
        if (retweet == null) {
            return null;
        }
        Retweet clone = retweet.clone();
        if (clone.getId() == null) {
            clone.setId(UUID.randomUUID());
            clone.setDatePosted(LocalDateTime.now());
        }
        RETWEET.put(clone.getId(), clone);
        return getByRetweetId(clone.getId());
    }

    protected static Retweet getByRetweetId(UUID id) {
        return RETWEET.get(id).clone();
    }

    protected static Iterable<Retweet> findAllRetweets() {
        List<Retweet> retweets = new ArrayList<>();
        for (Retweet retweet : RETWEET.values()) {
            retweets.add(retweet.clone());
        }
        return retweets;
    }

    protected static void deleteRetweet(Retweet retweet) {
        if (retweet != null && retweet.getId() != null) {
            RETWEET.remove(retweet.getId());
        }
    }
}
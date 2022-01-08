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
    private static class StorageHolder {
        private static final Storage INSTANCE = new Storage();
    }

    public static Storage getInstance() {
        return StorageHolder.INSTANCE;
    }

    private final HashMap<UUID, User> USERS = new HashMap<>();
    private final HashMap<UUID, Tweet> TWEETS = new HashMap<>();
    private final HashMap<UUID, Like> LIKES = new HashMap<>();
    private final HashMap<UUID, Retweet> RETWEET = new HashMap<>();

    protected User putUser(User user) {
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

    protected User getByUserId(UUID id) {
        User user = USERS.get(id);
        if (user != null) {
            return user.clone();
        } else {
            return null;
        }
    }

    protected Iterable<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        for (User user : USERS.values()) {
            users.add(user.clone());
        }
        return users;
    }

    protected void deleteUser(User entity) {
        if (entity != null && entity.getId() != null) {
            USERS.remove(entity.getId());
        }
    }

    protected Tweet putTweet(Tweet entity) {
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

    protected Tweet getByTweetId(UUID id) {
        Tweet tweet = TWEETS.get(id);
        if (tweet != null) {
            return tweet.clone();
        } else {
            return null;
        }
    }

    protected Iterable<Tweet> findAllTweets() {
        List<Tweet> tweets = new ArrayList<>();
        for (Tweet value : TWEETS.values()) {
            tweets.add(value.clone());
        }
        return tweets;
    }

    protected void deleteTweet(Tweet entity) {
        if (entity != null && entity.getId() != null) {
            TWEETS.remove(entity.getId());
        }
    }

    protected Like putLike(Like like) {
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

    protected Like getByLikeId(UUID id) {
        return LIKES.get(id).clone();
    }

    protected Iterable<Like> findAllLikes() {
        List<Like> likes = new ArrayList<>();
        for (Like like : LIKES.values()) {
            likes.add(like.clone());
        }
        return likes;
    }

    protected void deleteLike(Like like) {
        if (like != null && like.getId() != null) {
            LIKES.remove(like.getId());
        }
    }

    protected Retweet putRetweet(Retweet retweet) {
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

    protected Retweet getByRetweetId(UUID id) {
        Retweet retweet = RETWEET.get(id);
        if (retweet != null) {
            return retweet.clone();
        } else {
            return null;
        }
    }

    protected Iterable<Retweet> findAllRetweets() {
        List<Retweet> retweets = new ArrayList<>();
        for (Retweet retweet : RETWEET.values()) {
            retweets.add(retweet.clone());
        }
        return retweets;
    }

    protected void deleteRetweet(Retweet retweet) {
        if (retweet != null && retweet.getId() != null) {
            RETWEET.remove(retweet.getId());
        }
    }

    private Storage() {
    }
}
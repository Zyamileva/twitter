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
import java.util.stream.Collectors;

class Storage {
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

    protected List<User> findAllUsers() {
        return this.USERS.values()
                .stream()
                .map(User::clone)
                .collect(Collectors.toList());
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

    protected List<Tweet> findAllTweets() {
        return this.TWEETS.values()
                .stream()
                .map(Tweet::clone)
                .collect(Collectors.toList());
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

    protected List<Like> findAllLikes() {
        return this.LIKES.values()
                .stream()
                .map(Like::clone)
                .collect(Collectors.toList());
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

    protected List<Retweet> findAllRetweets() {
        return this.RETWEET.values()
                .stream()
                .map(Retweet::clone)
                .collect(Collectors.toList());
    }

    protected void deleteRetweet(Retweet retweet) {
        if (retweet != null && retweet.getId() != null) {
            RETWEET.remove(retweet.getId());
        }
    }

    private Storage() {
    }
}
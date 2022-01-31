package org.zyamileva.twitter.Feed;

import org.zyamileva.twitter.dao.Inmemory.UserInMemoryDao;
import org.zyamileva.twitter.dao.UserDao;
import org.zyamileva.twitter.entities.Tweet;
import org.zyamileva.twitter.model.TweetProjection;
import org.zyamileva.twitter.service.UserService;
import org.zyamileva.twitter.service.UserServiceImpl;

import java.util.Set;
import java.util.TreeSet;

public class ReplyFeed extends Feed {
    private final UserService userService = new UserServiceImpl();

    private Tweet tweet;

    public ReplyFeed(Tweet tweet, TreeSet<TweetProjection> tweetProjections) {
        super(tweetProjections);
        this.tweet = tweet;
    }

    public Tweet getTweet() {
        return tweet;
    }

    @Override
    public String toString() {
        StringBuilder feed = new StringBuilder();
        feed
                .append(NEW_LINE)
                .append(SEPARATOR)
                .append(NEW_LINE)
                .append("Replay feed for ")
                .append(userService.findById(tweet.getUserId()).get().getLogin())
                .append(NEW_LINE)
                .append(SEPARATOR)
                .append(NEW_LINE)
                .append(new TweetProjection(
                        userService.findById(tweet.getUserId()).get().getUsername(),
                        userService.findById(tweet.getUserId()).get().getLogin(),
                        tweet.getDataPosted(),
                        tweet.getContent(),
                        0,
                        0
                ))
                .append(NEW_LINE);

        tweetProjections.forEach(tweetForFeed -> feed.append(SEPARATOR).append(NEW_LINE).append(tweetForFeed).append(NEW_LINE));
        feed.append(SEPARATOR).append(NEW_LINE);
        return feed.toString();
    }
}
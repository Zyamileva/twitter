package org.zyamileva.twitter.Feed;

import org.zyamileva.twitter.entities.Tweet;
import org.zyamileva.twitter.entities.User;
import org.zyamileva.twitter.model.TweetProjection;

import java.util.Set;
import java.util.TreeSet;

import static org.zyamileva.twitter.utils.StringUtils.NEW_LINE;
import static org.zyamileva.twitter.utils.StringUtils.SEPARATOR;

public class HomeFeed extends Feed {

    private  User user;

    public HomeFeed(User user, TreeSet<TweetProjection> tweetProjections) {
        super(tweetProjections);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        StringBuilder feed = new StringBuilder();
        feed
                .append(SEPARATOR)
                .append(NEW_LINE)
                .append("Home feed for ")
                .append(user.getLogin())
                .append(NEW_LINE);
        tweetProjections.forEach(tweetForFeed -> feed.append(SEPARATOR).append(NEW_LINE).append(tweetForFeed).append(NEW_LINE));
        feed.append(SEPARATOR).append(NEW_LINE);
        return feed.toString();
    }
}
package org.zyamileva.twitter.Feed;

import org.zyamileva.twitter.entities.Tweet;

import java.util.Set;

public abstract class Feed {
    protected Set<Tweet> tweets;

    public Feed(Set<Tweet> tweets) {
        this.tweets = tweets;
    }

    public Set<Tweet> getTweets() {
        return tweets;
    }

    public abstract Feed buildFeed();

    @Override
    public String toString() {
        return "Feed{" +
                "tweets=" + tweets +
                '}';
    }
}
package org.zyamileva.twitter.feed;

import org.zyamileva.twitter.model.TweetProjection;

import java.util.TreeSet;

public abstract class Feed {
    protected TreeSet<TweetProjection> tweetProjections;

    public Feed(TreeSet<TweetProjection> tweetProjections) {
        this.tweetProjections = tweetProjections;
    }
}
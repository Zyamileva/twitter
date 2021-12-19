package org.zyamileva.twitter.Feed;

import org.zyamileva.twitter.entities.Tweet;

import java.util.Set;

public class ReplyFeed extends Feed {

    private Tweet tweet;

    public ReplyFeed(Tweet tweet, Set<Tweet> tweets) {
        super(tweets);
        this.tweet = tweet;
    }

    public Tweet getTweet() {
        return tweet;
    }

    @Override
    public Feed buildFeed() {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("---------->ReplyFeed\n").append(tweet).append("\n");
        for (Tweet tweet : tweets) {
            sb.append(tweet).append("\n");
        }
        sb.append("*************************");
        return sb.toString();
    }
}
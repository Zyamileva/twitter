package org.zyamileva.twitter.Feed;

import org.zyamileva.twitter.entities.Tweet;
import org.zyamileva.twitter.entities.User;

import java.util.Set;

public class HomeFeed extends Feed {

    private User user;

    public HomeFeed(User user, Set<Tweet> tweets) {
        super(tweets);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Feed buildFeed() {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("---------->HomeFeed\n").append(user).append("\n");
        for (Tweet tweet : tweets) {
            sb.append(tweet).append("\n");
        }
        sb.append("*************************");
        return sb.toString();
    }
}
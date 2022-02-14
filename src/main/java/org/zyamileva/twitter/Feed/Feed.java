package org.zyamileva.twitter.Feed;

import org.zyamileva.twitter.entities.Tweet;
import org.zyamileva.twitter.model.TweetProjection;
import javax.naming.ldap.PagedResultsControl;
import java.util.Set;
import java.util.TreeSet;

public abstract class Feed {
    protected TreeSet<TweetProjection> tweetProjections;

    public Feed(TreeSet<TweetProjection> tweetProjections) {
        this.tweetProjections = tweetProjections;
    }
}
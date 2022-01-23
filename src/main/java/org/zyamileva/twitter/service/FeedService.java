package org.zyamileva.twitter.service;

import org.zyamileva.twitter.Feed.HomeFeed;
import org.zyamileva.twitter.Feed.ReplyFeed;
import org.zyamileva.twitter.Feed.UserFeed;

import java.util.UUID;

public interface FeedService {
    HomeFeed buildHomeFeed(UUID userId);

    UserFeed buildUserFeed(UUID userId);

    ReplyFeed buildReplyFeed(UUID tweetId);
}

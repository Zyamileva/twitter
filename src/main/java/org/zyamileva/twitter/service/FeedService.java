package org.zyamileva.twitter.service;

import org.zyamileva.twitter.feed.HomeFeed;
import org.zyamileva.twitter.feed.ReplyFeed;
import org.zyamileva.twitter.feed.UserFeed;

import java.util.UUID;

public interface FeedService {
    HomeFeed buildHomeFeed(UUID userId);

    UserFeed buildUserFeed(UUID userId);

    ReplyFeed buildReplyFeed(UUID tweetId);
}

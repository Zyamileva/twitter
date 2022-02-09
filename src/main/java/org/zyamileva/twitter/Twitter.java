package org.zyamileva.twitter;

import org.zyamileva.twitter.Feed.HomeFeed;
import org.zyamileva.twitter.configuration.ApplicationRunner;
import org.zyamileva.twitter.configuration.options.Context;

import java.util.UUID;

public class Twitter {
    public static void main(String[] args) {
        ApplicationRunner.run(args);

        System.out.println(Context.getInstance().getFeedService().buildReplyFeed(UUID.fromString("abad2161-da52-443b-aa3a-33556fcd18db")));
        System.out.println(Context.getInstance().getFeedService().buildHomeFeed(UUID.fromString("e0988b42-6b87-42ff-ab63-61ad5b621f8d")));
        System.out.println(Context.getInstance().getFeedService().buildUserFeed(UUID.fromString("03dccf47-6d9b-4835-847b-fcd4dff93c8e")));
//        System.out.println(Context.getInstance().getFeedService().buildHomeFeed(Context.getInstance().getUserService().findByLogin("@anna").orElseThrow().getId()));
//        System.out.println(Context.getInstance().getFeedService().buildUserFeed(Context.getInstance().getUserService().findByLogin("@anna").orElseThrow().getId()));
    }
}
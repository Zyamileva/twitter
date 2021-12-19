package org.zyamileva.twitter;

import org.zyamileva.twitter.Feed.UserFeed;
import org.zyamileva.twitter.entities.Like;
import org.zyamileva.twitter.entities.Tweet;
import org.zyamileva.twitter.entities.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Twitter {
    public static void main(String[] args) {
        User kate = new User("Kate Zyamileva", "@kate_zyamileva");
        User anna = new User("Anna Zyamileva", "@anna_zyamileva");
        kate.setOfficialAccount(true);
        anna.setOfficialAccount(true);
        System.out.println("(Users: ");
        System.out.println(kate);
        System.out.println(anna);
        System.out.println();

        Tweet firstKateTweet = new Tweet(kate.getId(), "Hello!!!", new HashSet<>());
        Set<UUID> mentionedUsersOnAnnaTweet = new HashSet<>();
        mentionedUsersOnAnnaTweet.add(kate.getId());
        Tweet firstAnnaTweet = new Tweet(anna.getId(), "Hi all!!!", mentionedUsersOnAnnaTweet);

        System.out.println("Tweets: ");
        System.out.println(firstAnnaTweet);
        System.out.println(firstKateTweet);

        Like annaLikesKateTweet = new Like(anna.getId(), firstKateTweet.getId());
        System.out.println("Anna likes Kate tweet");
        System.out.println(annaLikesKateTweet);
        ;
        System.out.println();

        firstKateTweet.getLikeIds().add(annaLikesKateTweet.getId());
        System.out.println("Kate tweet update: ");
        System.out.println(firstKateTweet);
        System.out.println();

        anna.getFollowerIds().add(kate.getId());
        kate.getFollowingIds().add(anna.getId());
        System.out.println("Kate started follow Anna: ");
        System.out.println();
        System.out.println(anna);
        System.out.println(kate);
        System.out.println();

        Tweet secondKateTweet = new Tweet(kate.getId(), "Hello, I'm with you again.", new HashSet<>());
        System.out.println("Kate posted second tweet: ");
        System.out.println(secondKateTweet);
        System.out.println();

        UserFeed KateFeed = new UserFeed(kate, Set.of(firstKateTweet, secondKateTweet));
        System.out.println("Kate feed: ");
        System.out.println(KateFeed);
        System.out.println();

        Tweet secondAnnaTweet = new Tweet(anna.getId(), "Hi, ......", new HashSet<>());
        System.out.println("Anna posted second tweet: ");
        System.out.println(secondAnnaTweet);
        System.out.println();

        UserFeed AnnaFeed = new UserFeed(anna, Set.of(firstAnnaTweet, secondAnnaTweet));
        System.out.println("Anna feed: ");
        System.out.println(AnnaFeed);
        System.out.println();
    }
}
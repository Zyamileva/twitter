package org.zyamileva.twitter;

import com.sun.source.doctree.SeeTree;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.zyamileva.twitter.entities.Like;
import org.zyamileva.twitter.entities.Retweet;
import org.zyamileva.twitter.entities.Tweet;
import org.zyamileva.twitter.entities.User;
import org.zyamileva.twitter.service.TweetService;
import org.zyamileva.twitter.service.TweetServiceImpl;
import org.zyamileva.twitter.service.UserService;
import org.zyamileva.twitter.service.UserServiceImpl;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Twitter {
    private static final UserService userService = new UserServiceImpl();
    private static final TweetService tweetService = new TweetServiceImpl();
    private static final Logger log = LogManager.getLogger(Twitter.class);

    public static void main(String[] args) {
        User kate = new User("Kate Zyamileva", "@kate_zyam");
        User anna = new User("Anna Zyamileva", "@___j");
        User nikita = new User("Nikita Ivanov", "@nikita_ivanov");

        kate.setOfficialAccount(true);
        anna.setOfficialAccount(true);
        nikita.setOfficialAccount(true);

        kate = userService.saveUser(kate).orElseThrow();
        anna = userService.saveUser(anna).orElseThrow();
        nikita = userService.saveUser(nikita).orElseThrow();

        log.info(kate);
        log.info(userService.existById(UUID.randomUUID()));
        Set<UUID> n = new HashSet<>();
        n.add(kate.getId());
        n.add(anna.getId());
        log.info(userService.findByIds(n));

        User ann = new User("Anna Zyamikleva", "@___j");

        // ann = userService.saveUser(ann).orElseThrow();

        Tweet tweetKate = new Tweet(kate.getId(), "Hello @___j and @nikita_ivanov !");
        tweetKate = tweetService.saveTweet(tweetKate).orElseThrow();
        log.info(tweetKate);

        tweetService.like(kate.getId(),tweetKate.getId());
        tweetService.retweet(nikita.getId(),tweetKate.getId());
        log.info(tweetKate);
    }
}
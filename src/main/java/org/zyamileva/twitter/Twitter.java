package org.zyamileva.twitter;

import org.zyamileva.twitter.Feed.UserFeed;
import org.zyamileva.twitter.dao.Inmemory.LikeInMemoryDao;
import org.zyamileva.twitter.dao.Inmemory.RetweetInMemoryDao;
import org.zyamileva.twitter.dao.Inmemory.TweetInMemoryDao;
import org.zyamileva.twitter.dao.Inmemory.UserInMemoryDao;
import org.zyamileva.twitter.dao.LikeDao;
import org.zyamileva.twitter.dao.RetweetDao;
import org.zyamileva.twitter.dao.TweetDao;
import org.zyamileva.twitter.dao.UserDao;
import org.zyamileva.twitter.entities.Like;
import org.zyamileva.twitter.entities.Retweet;
import org.zyamileva.twitter.entities.Tweet;
import org.zyamileva.twitter.entities.User;
import org.zyamileva.twitter.service.UserService;
import org.zyamileva.twitter.service.UserServiceImpl;

import java.util.*;

public class Twitter {
    private static final UserService userService = new UserServiceImpl();

    public static void main(String[] args) {
        User kate = new User("Kate Zyamileva", "@kate_zyam");
        User anna = new User("Anna Zyamileva", "@a__z_");
        User nikita = new User("Nikita Ivanov", "@nikita_ivanov");

        kate.setOfficialAccount(true);
        anna.setOfficialAccount(true);
        nikita.setOfficialAccount(true);

        kate = userService.saveUser(kate).orElseThrow();
        anna = userService.saveUser(anna).orElseThrow();
        nikita = userService.saveUser(nikita).orElseThrow();

        userService.subscribe(kate.getId(), anna.getId());
        userService.subscribe(nikita.getId(), anna.getId());

        kate = userService.findById(kate.getId()).orElseThrow();
        anna = userService.findById(anna.getId()).orElseThrow();
        nikita = userService.findById(nikita.getId()).orElseThrow();

        System.out.println(kate);
        System.out.println(anna);
        System.out.println(nikita);

        userService.unsubscribe(nikita.getId(), anna.getId());
        anna = userService.findById(anna.getId()).orElseThrow();
        nikita = userService.findById(nikita.getId()).orElseThrow();

        System.out.println(kate);
        System.out.println(anna);
        System.out.println(nikita);

        System.out.println(userService.findById(UUID.randomUUID()));
        System.out.println(userService.findById(anna.getId()));
    }
}
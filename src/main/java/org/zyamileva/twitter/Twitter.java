package org.zyamileva.twitter;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.zyamileva.twitter.entities.User;
import org.zyamileva.twitter.service.UserService;
import org.zyamileva.twitter.service.UserServiceImpl;

public class Twitter {
    private static final UserService userService = new UserServiceImpl();
    private static final Logger log = LogManager.getLogger(Twitter.class);

    public static void main(String[] args) {
        User kate = new User("Kate Zyamileva", "@kate_zyam");
        User anna = new User("Anna Zyamileva", "@_");
        User nikita = new User("Nikita Ivanov", "@nikita_ivanov");

        kate.setOfficialAccount(true);
        anna.setOfficialAccount(true);
        nikita.setOfficialAccount(true);

        kate = userService.saveUser(kate).orElseThrow();
        anna = userService.saveUser(anna).orElseThrow();
        nikita = userService.saveUser(nikita).orElseThrow();

        log.info(kate);
        /*
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
        System.out.println(userService.findById(anna.getId())); */
    }
}
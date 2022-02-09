package org.zyamileva.twitter.configuration.options;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zyamileva.twitter.dao.Inmemory.LikeInMemoryDao;
import org.zyamileva.twitter.dao.Inmemory.RetweetInMemoryDao;
import org.zyamileva.twitter.dao.Inmemory.TweetInMemoryDao;
import org.zyamileva.twitter.dao.Inmemory.UserInMemoryDao;
import org.zyamileva.twitter.dao.Inmemory.jdbc.LikeJDBCDao;
import org.zyamileva.twitter.dao.Inmemory.jdbc.RetweetJDBCDao;
import org.zyamileva.twitter.dao.Inmemory.jdbc.TweetJDBCDao;
import org.zyamileva.twitter.dao.Inmemory.jdbc.UserJDBCDao;
import org.zyamileva.twitter.dao.LikeDao;
import org.zyamileva.twitter.dao.RetweetDao;
import org.zyamileva.twitter.dao.TweetDao;
import org.zyamileva.twitter.dao.UserDao;
import org.zyamileva.twitter.service.*;

public class Context {
    private static final Logger log = LogManager.getLogger(Context.class);

    private UserDao userDao;
    private RetweetDao retweetDao;
    private TweetDao tweetDao;
    private LikeDao likeDao;

    private UserService userService;
    private TweetService tweetService;
    private FeedService feedService;

    private Context() {
    }

    public static Context getInstance() {
        return ContextHolder.CONTEXT;
    }

    private static class ContextHolder {
        private static final Context CONTEXT = new Context();
    }

    private boolean contextInitialised = false;

    public void initContext(Configuration configuration) {
        if (!contextInitialised) {
            initDao(configuration);
            initServices();
            contextInitialised = true;
            log.debug("Context initialised.");
        } else {
            log.warn("Context has been initialised earlier.");
        }
    }

    private void initDao(Configuration configuration) {
        if (configuration.getDaoTypeOption() == DaoTypeOption.IN_MEMORY) {
            userDao = new UserInMemoryDao();
            retweetDao = new RetweetInMemoryDao();
            tweetDao = new TweetInMemoryDao();
            likeDao = new LikeInMemoryDao();
        } else {
            userDao = new UserJDBCDao();
            tweetDao = new TweetJDBCDao();
            likeDao = new LikeJDBCDao();
            retweetDao = new RetweetJDBCDao();
        }
    }

    private void initServices() {
        userService = new UserServiceImpl();
        tweetService = new TweetServiceImpl();
        feedService = new FeedServiceImpl();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public TweetDao getTweetDao() {
        return tweetDao;
    }

    public LikeDao getLikeDao() {
        return likeDao;
    }

    public RetweetDao getRetweetDao() {
        return retweetDao;
    }

    public UserService getUserService() {
        return userService;
    }

    public TweetService getTweetService() {
        return tweetService;
    }

    public FeedService getFeedService() {
        return feedService;
    }
}
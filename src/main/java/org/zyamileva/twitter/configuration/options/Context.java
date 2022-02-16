package org.zyamileva.twitter.configuration.options;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zyamileva.twitter.dao.Factory.DaoFactory;
import org.zyamileva.twitter.dao.Factory.JDBCFactoty;
import org.zyamileva.twitter.dao.Factory.StorageFactory;
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
        DaoFactory daoFactory;
        if (configuration.getDaoTypeOption() == DaoTypeOption.IN_MEMORY) {
            daoFactory = new StorageFactory();
        } else {
            daoFactory = new JDBCFactoty();
        }
        this.userDao = daoFactory.createUserDao();
        this.tweetDao = daoFactory.createTweetDao();
        this.likeDao = daoFactory.createLikeDao();
        this.retweetDao = daoFactory.createRetweetDao();
    }

    private void initServices() {
        userService = new UserServiceImpl();
        tweetService = new TweetServiceImpl();
        feedService = new FeedServiceImpl();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public RetweetDao getRetweetDao() {
        return retweetDao;
    }

    public TweetDao getTweetDao() {
        return tweetDao;
    }

    public LikeDao getLikeDao() {
        return likeDao;
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
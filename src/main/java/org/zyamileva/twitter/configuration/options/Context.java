package org.zyamileva.twitter.configuration.options;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zyamileva.twitter.dao.Factory.DaoFactory;
import org.zyamileva.twitter.dao.Factory.JDBCFactoty;
import org.zyamileva.twitter.dao.Factory.StorageFactory;
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

    private DaoFactory daoFactory;
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
            daoFactory = new StorageFactory();
        } else {
            daoFactory = new JDBCFactoty();
        }
    }

    private void initServices() {
        userService = new UserServiceImpl();
        tweetService = new TweetServiceImpl();
        feedService = new FeedServiceImpl();
    }

    public DaoFactory getDaoFactory() {
        return daoFactory;
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
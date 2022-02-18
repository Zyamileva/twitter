package org.zyamileva.twitter.dao.factory;

import org.zyamileva.twitter.dao.*;
import org.zyamileva.twitter.dao.inmemory.LikeInMemoryDao;
import org.zyamileva.twitter.dao.inmemory.RetweetInMemoryDao;
import org.zyamileva.twitter.dao.inmemory.TweetInMemoryDao;
import org.zyamileva.twitter.dao.inmemory.UserInMemoryDao;

public class StorageFactory implements DaoFactory {

    @Override
    public LikeDao createLikeDao() {
        return new LikeInMemoryDao();
    }

    @Override
    public RetweetDao createRetweetDao() {
        return new RetweetInMemoryDao();
    }

    @Override
    public UserDao createUserDao() {
        return new UserInMemoryDao();
    }

    @Override
    public TweetDao createTweetDao() {
        return new TweetInMemoryDao();
    }
}
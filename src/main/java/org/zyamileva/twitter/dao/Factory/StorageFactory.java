package org.zyamileva.twitter.dao.Factory;

import org.zyamileva.twitter.dao.*;
import org.zyamileva.twitter.dao.Inmemory.LikeInMemoryDao;
import org.zyamileva.twitter.dao.Inmemory.RetweetInMemoryDao;
import org.zyamileva.twitter.dao.Inmemory.TweetInMemoryDao;
import org.zyamileva.twitter.dao.Inmemory.UserInMemoryDao;

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
package org.zyamileva.twitter.dao.Factory;

import org.zyamileva.twitter.dao.Inmemory.jdbc.LikeJDBCDao;
import org.zyamileva.twitter.dao.Inmemory.jdbc.RetweetJDBCDao;
import org.zyamileva.twitter.dao.Inmemory.jdbc.TweetJDBCDao;
import org.zyamileva.twitter.dao.Inmemory.jdbc.UserJDBCDao;
import org.zyamileva.twitter.dao.LikeDao;
import org.zyamileva.twitter.dao.RetweetDao;
import org.zyamileva.twitter.dao.TweetDao;
import org.zyamileva.twitter.dao.UserDao;

public class JDBCFactoty implements DaoFactory{
    @Override
    public LikeDao createLikeDao() {
        return new LikeJDBCDao();
    }

    @Override
    public RetweetDao createRetweetDao() {
        return new RetweetJDBCDao();
    }

    @Override
    public UserDao createUserDao() {
        return new UserJDBCDao();
    }

    @Override
    public TweetDao createTweetDao() {
        return new TweetJDBCDao();
    }
}
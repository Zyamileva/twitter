package org.zyamileva.twitter.dao.factory;

import org.zyamileva.twitter.dao.LikeDao;
import org.zyamileva.twitter.dao.RetweetDao;
import org.zyamileva.twitter.dao.TweetDao;
import org.zyamileva.twitter.dao.UserDao;

public interface DaoFactory {
    LikeDao createLikeDao();
    RetweetDao createRetweetDao();
    UserDao createUserDao();
    TweetDao createTweetDao();
}
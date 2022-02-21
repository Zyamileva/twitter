package service;

import org.zyamileva.twitter.configuration.options.Context;
import org.zyamileva.twitter.dao.LikeDao;
import org.zyamileva.twitter.dao.RetweetDao;
import org.zyamileva.twitter.dao.TweetDao;
import org.zyamileva.twitter.dao.UserDao;
import org.zyamileva.twitter.service.FeedService;
import org.zyamileva.twitter.service.TweetService;
import org.zyamileva.twitter.service.UserService;

import static org.mockito.Mockito.*;

public class AbsrtactTest {
    protected static final UserDao userDaoMock = mock(UserDao.class);
    protected static final TweetDao tweetDaoMock = mock(TweetDao.class);
    protected static final LikeDao likeDaoMock = mock(LikeDao.class);
    protected static final RetweetDao retweetDaoMock = mock(RetweetDao.class);
    protected static final UserService userServiceMock = mock(UserService.class);
    protected static final TweetService tweetServiceMock = mock(TweetService.class);
    protected static final FeedService feedServiceMock = mock(FeedService.class);

    static {
        Context.getInstance()
                .initContext(userDaoMock, tweetDaoMock, likeDaoMock, retweetDaoMock,
                        userServiceMock, tweetServiceMock, feedServiceMock);
    }
}
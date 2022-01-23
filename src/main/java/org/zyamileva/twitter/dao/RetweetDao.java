package org.zyamileva.twitter.dao;

import org.zyamileva.twitter.entities.Retweet;
import org.zyamileva.twitter.entities.Tweet;

import java.util.Set;
import java.util.UUID;

public interface RetweetDao extends GenericDao<Retweet> {
    Set<Retweet> findRetweetsByUserId(UUID userId);
}
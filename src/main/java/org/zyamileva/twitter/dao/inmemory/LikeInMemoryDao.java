package org.zyamileva.twitter.dao.inmemory;

import org.zyamileva.twitter.dao.LikeDao;
import org.zyamileva.twitter.entities.Like;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class LikeInMemoryDao implements LikeDao {

    @Override
    public Like save(Like entity) {
        return Storage.getInstance().putLike(entity);
    }

    @Override
    public Optional<Like> findById(UUID id) {
        Like like = Storage.getInstance().getByLikeId(id);
        return Optional.ofNullable(like);
    }

    @Override
    public List<Like> findAll() {
        return Storage.getInstance().findAllLikes();
    }

    @Override
    public Like delete(Like entity) {
        Storage.getInstance().deleteLike(entity);
        return entity;
    }

    @Override
    public long countLikes(UUID tweetId) {
        return Storage.getInstance().findAllLikes()
                .stream()
                .filter(like -> like.getTweetId().equals(tweetId))
                .count();
    }

    @Override
    public void delete(UUID userId, UUID tweetId) {
        Optional<Like> retweetOptional = Storage.getInstance().findAllLikes()
                .stream()
                .filter(likeStream -> likeStream.getUserId().equals(userId) && likeStream.getTweetId().equals(tweetId))
                .findFirst();

        retweetOptional.ifPresent(like -> Storage.getInstance().deleteLike(like));
    }

    @Override
    public void deleteAllByTweetId(UUID tweetId) {
        Set<Like> likeSet = Storage.getInstance().findAllLikes()
                .stream()
                .filter(like -> like.getTweetId().equals(tweetId))
                .collect(Collectors.toSet());

        likeSet.forEach(like -> Storage.getInstance().deleteLike(like));
    }
    @Override
    public boolean likeExists(UUID userId, UUID tweetId) {
        return Storage.getInstance().findAllLikes()
                .stream()
                .anyMatch(likeStream -> likeStream.getUserId().equals(userId) && likeStream.getTweetId().equals(tweetId));
    }
}
package org.zyamileva.twitter.dao.Inmemory;

import org.zyamileva.twitter.dao.LikeDao;
import org.zyamileva.twitter.entities.Like;

import java.util.Optional;
import java.util.UUID;

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
    public Iterable findAll() {
        return Storage.getInstance().findAllLikes();
    }

    @Override
    public void delete(Like entity) {
        Storage.getInstance().deleteLike(entity);
    }
}
package org.zyamileva.twitter.dao.Inmemory;

import org.zyamileva.twitter.dao.LikeDao;
import org.zyamileva.twitter.entities.Like;

import java.util.Optional;
import java.util.UUID;

public class LikeInMemoryDao implements LikeDao {

    @Override
    public Like save(Like entity) {
        return Storage.putLike(entity);
    }

    @Override
    public Optional<Like> findById(UUID id) {
        Like like = Storage.getByLikeId(id);
        if (like == null) {
            return Optional.empty();
        } else {
            return Optional.of(like);
        }
    }

    @Override
    public Iterable findAll() {
        return Storage.findAllLikes();
    }

    @Override
    public void delete(Like entity) {
        Storage.deleteLike(entity);
    }
}
package org.zyamileva.twitter.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class Like {
    private UUID id;
    private UUID userId;
    private UUID tweetId;
    private LocalDateTime datePosted;

    public Like(UUID userId, UUID tweetId) {
        this.userId = userId;
        this.tweetId = tweetId;
        this.id = UUID.randomUUID();
        this.datePosted = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getTweetId() {
        return tweetId;
    }

    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Like like = (Like) o;

        if (!userId.equals(like.userId)) return false;
        return tweetId.equals(like.tweetId);
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + tweetId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Like{" +
                "id=" + id +
                ", userId=" + userId +
                ", tweetId=" + tweetId +
                ", datePosted=" + datePosted +
                '}';
    }
}
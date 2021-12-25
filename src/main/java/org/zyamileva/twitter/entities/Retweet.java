package org.zyamileva.twitter.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class Retweet extends PersistentEntity implements Cloneable {
    private UUID userId;
    private UUID tweetId;
    private LocalDateTime datePosted;

    public Retweet(UUID userId, UUID tweetId) {
        this.userId = userId;
        this.tweetId = tweetId;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getTweetId() {
        return tweetId;
    }

    public void setDatePosted(LocalDateTime datePosted) {
        this.datePosted = datePosted;
    }

    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Retweet like = (Retweet) o;

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
        return "Retweet{" +
                "id=" + id +
                ", userId=" + userId +
                ", tweetId=" + tweetId +
                ", datePosted=" + datePosted +
                '}';
    }

    @Override
    public Retweet clone() {
        try {
            Retweet clone = (Retweet) super.clone();
            clone.userId = this.userId;
            clone.tweetId = this.tweetId;
            clone.datePosted = this.datePosted;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
package org.zyamileva.twitter.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Tweet extends PersistentEntity implements Cloneable {
    private UUID userId;
    private UUID replyTweetId;
    private LocalDateTime dataPosted;
    private String content;
    private Set<UUID> mentionedUserIds = new HashSet<>();
    private Set<UUID> likeIds;
    private Set<UUID> retweetIds;

    public Tweet() {
        this.likeIds = new HashSet<>();
        this.retweetIds = new HashSet<>();
    }

    public Tweet(UUID userId, String content) {
        this.likeIds = new HashSet<>();
        this.retweetIds = new HashSet<>();
        this.userId = userId;
        this.content = content;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getReplyTweetId() {
        return replyTweetId;
    }

    public void setReplyTweetId(UUID replyTweetId) {
        this.replyTweetId = replyTweetId;
    }

    public LocalDateTime getDataPosted() {
        return dataPosted;
    }

    public void setDataPosted(LocalDateTime dataPosted) {
        this.dataPosted = dataPosted;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<UUID> getMentionedUserIds() {
        return mentionedUserIds;
    }

    public void setMentionedUserIds(Set<UUID> mentionedUserIds) {
        this.mentionedUserIds = mentionedUserIds;
    }

    public Set<UUID> getLikeIds() {
        return likeIds;
    }

    public void setLikeIds(Set<UUID> likeIds) {
        this.likeIds = likeIds;
    }

    public Set<UUID> getRetweetIds() {
        return retweetIds;
    }

    public void setRetweetIds(Set<UUID> retweetIds) {
        this.retweetIds = retweetIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tweet tweet = (Tweet) o;

        if (!Objects.equals(id, tweet.id)) return false;
        if (!userId.equals(tweet.userId)) return false;
        if (!Objects.equals(replyTweetId, tweet.replyTweetId)) return false;
        if (!Objects.equals(dataPosted, tweet.dataPosted)) return false;
        if (!content.equals(tweet.content)) return false;
        if (!Objects.equals(mentionedUserIds, tweet.mentionedUserIds))
            return false;
        if (!Objects.equals(likeIds, tweet.likeIds)) return false;
        return Objects.equals(retweetIds, tweet.retweetIds);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + userId.hashCode();
        result = 31 * result + (replyTweetId != null ? replyTweetId.hashCode() : 0);
        result = 31 * result + (dataPosted != null ? dataPosted.hashCode() : 0);
        result = 31 * result + content.hashCode();
        result = 31 * result + (mentionedUserIds != null ? mentionedUserIds.hashCode() : 0);
        result = 31 * result + (likeIds != null ? likeIds.hashCode() : 0);
        result = 31 * result + (retweetIds != null ? retweetIds.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "id=" + id +
                ", userId=" + userId +
                ", replyTweetId=" + replyTweetId +
                ", dataPosted=" + dataPosted +
                ", content='" + content + '\'' +
                ", mentionedUserIds=" + mentionedUserIds +
                ", likeIds=" + likeIds.size() +
                ", retweetIds=" + retweetIds.size() +
                '}';
    }

    @Override
    public Tweet clone() {
        try {
            Tweet clone = (Tweet) super.clone();
            clone.id = this.id;
            clone.userId = this.userId;
            clone.replyTweetId = this.replyTweetId;
            clone.dataPosted = this.dataPosted;
            clone.content = this.content;
            clone.mentionedUserIds = this.mentionedUserIds;
            clone.likeIds = this.likeIds;
            clone.retweetIds = this.retweetIds;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
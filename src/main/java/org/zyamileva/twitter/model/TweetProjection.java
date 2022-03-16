package org.zyamileva.twitter.model;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.zyamileva.twitter.utils.StringUtils.*;

public class TweetProjection implements Comparable<TweetProjection> {
    private String userName;
    private String userLogin;
    private LocalDateTime datePosted;
    private String content;
    private long likes;
    private long retweets;

    public TweetProjection(String userName, String userLogin, LocalDateTime datePosted, String content, long likes, long retweets) {
        this.userName = userName;
        this.userLogin = userLogin;
        this.datePosted = datePosted;
        this.content = content;
        this.likes = likes;
        this.retweets = retweets;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    public String getContent() {
        return content;
    }

    public long getLikes() {
        return likes;
    }

    public long getRetweets() {
        return retweets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TweetProjection that = (TweetProjection) o;

        if (likes != that.likes) return false;
        if (retweets != that.retweets) return false;
        if (!Objects.equals(userName, that.userName)) return false;
        if (!Objects.equals(userLogin, that.userLogin)) return false;
        if (!Objects.equals(datePosted, that.datePosted)) return false;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (userLogin != null ? userLogin.hashCode() : 0);
        result = 31 * result + (datePosted != null ? datePosted.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (int) (likes ^ (likes >>> 32));
        result = 31 * result + (int) (retweets ^ (retweets >>> 32));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String header = String.format("%1$-20s %2$-14s %3$ta %3$tb %3$td %3$tT", userName, userLogin, datePosted);
        String body = String.format("%-140s", content);
        String footage = String.format("%1$-20s %2$-20s", LIKE_EMOJI + likes, RETWEET_EMOJI + retweets);
        builder
                .append(header)
                .append(NEW_LINE)
                .append(body)
                .append(NEW_LINE)
                .append(footage);
        return builder.toString();
    }

    @Override
    public int compareTo(TweetProjection o) {
        return o.getDatePosted().compareTo(datePosted);

    }
}
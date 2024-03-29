package org.zyamileva.twitter.entities;

import org.zyamileva.twitter.utils.StringUtils;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static org.zyamileva.twitter.utils.StringUtils.*;

public class User extends PersistentEntity implements Cloneable {
    private String username;
    private String login;
    private String about;
    private String location;
    private LocalDateTime registeredSince;
    private Set<UUID> followerIds;
    private Set<UUID> followingIds;
    private boolean officialAccount;

    public User() {
        this.followerIds = new HashSet<>();
        this.followingIds = new HashSet<>();
        this.officialAccount = false;
    }

    public User(String username, String login) {
        this.username = username;
        this.login = login;
        this.followerIds = new HashSet<>();
        this.followingIds = new HashSet<>();
        this.officialAccount = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getRegisteredSince() {
        return registeredSince;
    }

    public void setRegisteredSince(LocalDateTime registeredSince) {
        this.registeredSince = registeredSince;
    }

    public Set<UUID> getFollowerIds() {
        return followerIds;
    }

    public void setFollowerIds(Set<UUID> followerIds) {
        this.followerIds = followerIds;
    }

    public Set<UUID> getFollowingIds() {
        return followingIds;
    }

    public void setFollowingIds(Set<UUID> followingIds) {
        this.followingIds = followingIds;
    }

    public boolean isOfficialAccount() {
        return officialAccount;
    }

    public void setOfficialAccount(boolean officialAccount) {
        this.officialAccount = officialAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (officialAccount != user.officialAccount) return false;
        if (!Objects.equals(id, user.id)) return false;
        if (!username.equals(user.username)) return false;
        if (!login.equals(user.login)) return false;
        if (!Objects.equals(about, user.about)) return false;
        if (!Objects.equals(location, user.location)) return false;
        if (!Objects.equals(registeredSince, user.registeredSince))
            return false;
        if (!Objects.equals(followerIds, user.followerIds)) return false;
        return Objects.equals(followingIds, user.followingIds);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + username.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + (about != null ? about.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (registeredSince != null ? registeredSince.hashCode() : 0);
        result = 31 * result + (followerIds != null ? followerIds.hashCode() : 0);
        result = 31 * result + (followingIds != null ? followingIds.hashCode() : 0);
        result = 31 * result + (officialAccount ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String header = String.format("%1$-20S", username);
        if(officialAccount) {
            header +=" " + OFFICIAL_EMOJI;
        }
        String registeredSinceString = String.format("%1$ta %1$tb %1$td %1$tT", registeredSince);
        String footer = followerIds.size() + " follows " + followerIds.size() + " followers";

        stringBuilder
                .append(header).append(NEW_LINE)
                .append(login).append(NEW_LINE);
        if (about != null) {
            stringBuilder.append(about).append(NEW_LINE);
        }
        if (location != null) {
            stringBuilder.append(LOCATION_EMOJI).append(SPACE).append(location).append(NEW_LINE);
        }
        stringBuilder.append(CALENDAR_EMOJI).append(SPACE).append(registeredSinceString).append(NEW_LINE);
        stringBuilder.append(footer);
        return stringBuilder.toString();
    }

    @Override
    public User clone() {
        try {
            User clone = (User) super.clone();
            clone.id = this.id;
            clone.username = this.username;
            clone.login = this.login;
            clone.about = this.about;
            clone.location = this.location;
            clone.registeredSince = this.registeredSince;
            clone.followerIds = new HashSet<>(this.followerIds);
            clone.followingIds = new HashSet<>(this.followingIds);
            clone.officialAccount = this.officialAccount;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
package com.gamersclubfinder.gamersclubfinder.database.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tb_players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", length = 36, columnDefinition = "UUID")
    private UUID id;
    @Column(name = "persona_name", length = 255)
    private String personaName;
    @Column(name = "steam_id", length = 70)
    private String steamId;
    @Column(name = "profile_url", length = 255)
    private String profileUrl;
    @Column(name = "gamersclub_url", length = 255)
    private String gamersclubUrl;
    @Column(name = "avatar_url", length = 255)
    private String avatarUrl;
    @Column(name = "playtime")
    private Long playtime;
    @Column(name = "playtime_2weeks")
    private Long playtime_2weeks;
    @Column(name = "community_visibility")
    private Integer communityVisibility;
    @Column(name = "banned_friends")
    private Integer bannedFriends;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Player() {
    }

    public Player(String personaName, String steamId, String profileUrl, String gamersclubUrl, String avatarUrl, Long playtime, Long playtime_2weeks, Integer communityVisibility, Integer bannedFriends) {
        this.personaName = personaName;
        this.steamId = steamId;
        this.profileUrl = profileUrl;
        this.avatarUrl = avatarUrl;
        this.gamersclubUrl = gamersclubUrl;
        this.playtime = playtime;
        this.playtime_2weeks = playtime_2weeks;
        this.communityVisibility = communityVisibility;
        this.bannedFriends = bannedFriends;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPersonaName() {
        return personaName;
    }

    public void setPersonaName(String personaName) {
        this.personaName = personaName;
    }

    public String getSteamId() {
        return steamId;
    }

    public void setSteamId(String steamId) {
        this.steamId = steamId;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getGamersclubUrl() {
        return gamersclubUrl;
    }

    public void setGamersclubUrl(String gamersclubUrl) {
        this.gamersclubUrl = gamersclubUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Long getPlaytime() {
        return playtime;
    }

    public void setPlaytime(Long playtime) {
        this.playtime = playtime;
    }

    public Long getPlaytime_2weeks() {
        return playtime_2weeks;
    }

    public void setPlaytime_2weeks(Long playtime_2weeks) {
        this.playtime_2weeks = playtime_2weeks;
    }

    public Integer getCommunityVisibility() {
        return communityVisibility;
    }

    public void setCommunityVisibility(Integer communityVisibility) {
        this.communityVisibility = communityVisibility;
    }

    public Integer getBannedFriends() {
        return bannedFriends;
    }

    public void setBannedFriends(Integer bannedFriends) {
        this.bannedFriends = bannedFriends;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Player{");
        sb.append("id=").append(id);
        sb.append(", personaName='").append(personaName).append('\'');
        sb.append(", steamId='").append(steamId).append('\'');
        sb.append(", profileUrl='").append(profileUrl).append('\'');
        sb.append(", gamersclubUrl='").append(gamersclubUrl).append('\'');
        sb.append(", avatarUrl='").append(avatarUrl).append('\'');
        sb.append(", playtime=").append(playtime);
        sb.append(", playtime_2weeks=").append(playtime_2weeks);
        sb.append(", communityVisibility=").append(communityVisibility);
        sb.append(", bannedFriends=").append(bannedFriends);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append('}');
        return sb.toString();
    }
}
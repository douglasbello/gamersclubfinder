package com.gamersclubfinder.gamersclubfinder.dtos.players;

public class PlayerResponse {
    String steamId;
    String gamersclubUrl;
    String personaName;
    String profileUrl;
    String avatar;
    Integer bannedFriends;
    Long totalPlayedTime;
    String createdAt;
    Double KDR;

    public PlayerResponse() {
    }

    public PlayerResponse(String steamId, String gamersclubUrl) {
        this.steamId = steamId;
        this.gamersclubUrl = gamersclubUrl;
    }

    public PlayerResponse(String steamId, String gamersclubUrl, String personaName, String profileUrl, String avatar, Integer bannedFriends, Long totalPlayedTime, String createdAt, Double KDR) {
        this.steamId = steamId;
        this.gamersclubUrl = gamersclubUrl;
        this.personaName = personaName;
        this.profileUrl = profileUrl;
        this.avatar = avatar;
        this.bannedFriends = bannedFriends;
        this.totalPlayedTime = totalPlayedTime;
        this.createdAt = createdAt;
        this.KDR = KDR;
    }

    public String getSteamId() {
        return steamId;
    }

    public void setSteamId(String steamId) {
        this.steamId = steamId;
    }

    public String getGamersclubUrl() {
        return gamersclubUrl;
    }

    public void setGamersclubUrl(String gamersclubUrl) {
        this.gamersclubUrl = gamersclubUrl;
    }

    public String getPersonaName() {
        return personaName;
    }

    public void setPersonaName(String personaName) {
        this.personaName = personaName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getBannedFriends() {
        return bannedFriends;
    }

    public void setBannedFriends(Integer bannedFriends) {
        this.bannedFriends = bannedFriends;
    }

    public Long getTotalPlayedTime() {
        return totalPlayedTime;
    }

    public void setTotalPlayedTime(Long totalPlayedTime) {
        this.totalPlayedTime = totalPlayedTime;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Double getKDR() {
        return KDR;
    }

    public void setKDR(Double KDR) {
        this.KDR = KDR;
    }
}
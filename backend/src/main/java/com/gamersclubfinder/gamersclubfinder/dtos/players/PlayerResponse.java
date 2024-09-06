package com.gamersclubfinder.gamersclubfinder.dtos.players;

public record PlayerResponse(
        String steamId,
        String gamersclubUrl,
        String personaName,
        String profileUrl,
        String avatar,
        Integer bannedFriends,
        Long totalPlayedTime,
        String createdAt,
        Double KDR
) {
}
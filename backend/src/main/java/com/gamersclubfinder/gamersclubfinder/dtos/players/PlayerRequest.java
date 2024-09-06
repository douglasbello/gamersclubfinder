package com.gamersclubfinder.gamersclubfinder.dtos.players;

public record PlayerRequest(
        String steamId,
        String gamersclubUrl
) {
}
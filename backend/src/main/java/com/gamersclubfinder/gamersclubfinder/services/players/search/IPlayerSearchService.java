package com.gamersclubfinder.gamersclubfinder.services.players.search;

import com.gamersclubfinder.gamersclubfinder.dtos.players.PlayerResponse;

public interface IPlayerSearchService {
    PlayerResponse findBySteamId(String steamId);
}
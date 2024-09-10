package com.gamersclubfinder.gamersclubfinder.services.players.search;

import com.gamersclubfinder.gamersclubfinder.dtos.players.PlayerResponse;
import com.gamersclubfinder.gamersclubfinder.handler.exceptions.NotFoundException;

public interface IPlayerSearchService {
    PlayerResponse findBySteamId(String steamId) throws NotFoundException;
}
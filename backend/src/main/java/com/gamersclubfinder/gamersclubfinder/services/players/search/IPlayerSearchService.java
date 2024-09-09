package com.gamersclubfinder.gamersclubfinder.services.players.search;

import com.gamersclubfinder.gamersclubfinder.dtos.games.OwnedGamesResponse;
import com.gamersclubfinder.gamersclubfinder.dtos.players.PlayerResponse;
import com.gamersclubfinder.gamersclubfinder.dtos.players.details.Details;

import java.util.concurrent.atomic.AtomicInteger;

public interface IPlayerSearchService {
    PlayerResponse findBySteamId(String steamId);
}
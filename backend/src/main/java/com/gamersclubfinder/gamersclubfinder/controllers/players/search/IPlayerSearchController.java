package com.gamersclubfinder.gamersclubfinder.controllers.players.search;

import com.gamersclubfinder.gamersclubfinder.dtos.players.PlayerResponse;
import com.gamersclubfinder.gamersclubfinder.dtos.players.SearchPlayerRequest;
import org.springframework.http.ResponseEntity;

public interface IPlayerSearchController {
    ResponseEntity<PlayerResponse> findBySteamId(SearchPlayerRequest steamId);
}
package com.gamersclubfinder.gamersclubfinder.controllers.players.search;

import com.gamersclubfinder.gamersclubfinder.dtos.players.PlayerResponse;
import org.springframework.http.ResponseEntity;

public interface IPlayerSearchController {
    ResponseEntity<PlayerResponse> findBySteamId(String steamId);
}
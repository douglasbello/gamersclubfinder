package com.gamersclubfinder.gamersclubfinder.controllers.players.search.impl;

import com.gamersclubfinder.gamersclubfinder.controllers.players.search.IPlayerSearchController;
import com.gamersclubfinder.gamersclubfinder.dtos.players.PlayerResponse;
import com.gamersclubfinder.gamersclubfinder.dtos.players.SearchPlayerRequest;
import com.gamersclubfinder.gamersclubfinder.services.players.search.IPlayerSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/players")
public class PlayerSearchControllerImpl implements IPlayerSearchController {
    private final IPlayerSearchService playerSearchService;

    public PlayerSearchControllerImpl(IPlayerSearchService playerSearchService) {
        this.playerSearchService = playerSearchService;
    }

    @GetMapping
    public ResponseEntity<PlayerResponse> findBySteamId(@RequestParam SearchPlayerRequest steamId) {
        return ResponseEntity.ok().body(playerSearchService.findBySteamId(steamId.steamId()));
    }
}
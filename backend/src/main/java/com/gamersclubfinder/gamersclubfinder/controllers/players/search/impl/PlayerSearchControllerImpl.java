package com.gamersclubfinder.gamersclubfinder.controllers.players.search.impl;

import com.gamersclubfinder.gamersclubfinder.controllers.players.search.IPlayerSearchController;
import com.gamersclubfinder.gamersclubfinder.dtos.players.PlayerResponse;
import com.gamersclubfinder.gamersclubfinder.services.players.search.IPlayerSearchService;
import com.gamersclubfinder.gamersclubfinder.util.SteamKeys;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/players")
public class PlayerSearchControllerImpl implements IPlayerSearchController {
    private final IPlayerSearchService playerSearchService;

    public PlayerSearchControllerImpl(IPlayerSearchService playerSearchService) {
        this.playerSearchService = playerSearchService;
    }

    @GetMapping("/{steamId}")
    public ResponseEntity<PlayerResponse> findBySteamId(@PathVariable String steamId) {
        return ResponseEntity.ok().body(playerSearchService.findBySteamId(SteamKeys.BASE_STEAM_URL + steamId));
    }
}
package com.gamersclubfinder.gamersclubfinder.services.players.search.impl;

import com.gamersclubfinder.gamersclubfinder.clients.SteamAPI;
import com.gamersclubfinder.gamersclubfinder.database.models.Player;
import com.gamersclubfinder.gamersclubfinder.database.repositories.PlayerRepository;
import com.gamersclubfinder.gamersclubfinder.dtos.client.steamid.SteamId;
import com.gamersclubfinder.gamersclubfinder.dtos.players.PlayerResponse;
import com.gamersclubfinder.gamersclubfinder.handler.exceptions.NotFoundException;
import com.gamersclubfinder.gamersclubfinder.services.players.facade.PlayerSteamDetailsFacade;
import com.gamersclubfinder.gamersclubfinder.services.players.search.IPlayerSearchService;
import com.gamersclubfinder.gamersclubfinder.util.SteamKeys;
import org.springframework.stereotype.Service;

@Service
public class PlayerSearchServiceImpl implements IPlayerSearchService {
    private final PlayerRepository playerRepository;
    private final SteamAPI steamAPI;
    private final PlayerSteamDetailsFacade playerFacade;

    public PlayerSearchServiceImpl(PlayerRepository playerRepository, SteamAPI steamAPI, PlayerSteamDetailsFacade playerFacade) {
        this.playerRepository = playerRepository;
        this.steamAPI = steamAPI;
        this.playerFacade = playerFacade;
    }

    @Override
    public PlayerResponse findBySteamId(String steamId) throws NotFoundException {
        steamId = extractSteamId(steamId);

        final String finalSteamId = steamId;
        Player player = playerRepository.findBySteamId(steamId).orElseThrow(() -> new NotFoundException("Steam account", finalSteamId));

        return playerFacade.fetchSteam(player, steamId);
    }

    private String extractSteamId(String steamId) throws NotFoundException {
        final String http = "http://steamcommunity.com/id/";
        final String https = "https://steamcommunity.com/id/";

        if (steamId.contains(http)) {
            steamId = steamId.replace("http://steamcommunity.com/id/", "");
            steamId = steamId.replace("/", "");
            SteamId response = steamAPI.getPlayerSteamIdByNickname(SteamKeys.STEAM_KEY, steamId.replace("http://steamcommunity.com/id/", ""));

            if (response == null)
                throw new NotFoundException("Steam account", steamId);

            steamId = response.response().steamid();
        }

        if (steamId.contains(https)) {
            steamId = steamId.replace("https://steamcommunity.com/id/", "");
            steamId = steamId.replace("/", "");
            SteamId response = steamAPI.getPlayerSteamIdByNickname(SteamKeys.STEAM_KEY, steamId.replace("https://steamcommunity.com/id/", ""));

            if (response == null)
                throw new NotFoundException("Steam account", steamId);

            steamId = response.response().steamid();
        }

        steamId = steamId.replace("http://steamcommunity.com/profiles/", "");
        steamId = steamId.replace("https://steamcommunity.com/profiles/", "");
        return steamId.replace("/", "");
    }
}
package com.gamersclubfinder.gamersclubfinder.services.players.search.impl;

import com.gamersclubfinder.gamersclubfinder.clients.SteamAPI;
import com.gamersclubfinder.gamersclubfinder.database.models.Player;
import com.gamersclubfinder.gamersclubfinder.database.repositories.PlayerRepository;
import com.gamersclubfinder.gamersclubfinder.dtos.client.friendlist.FriendsList;
import com.gamersclubfinder.gamersclubfinder.dtos.client.playerbans.PlayerBanResponse;
import com.gamersclubfinder.gamersclubfinder.dtos.client.playerstats.PlayerStatsResponse;
import com.gamersclubfinder.gamersclubfinder.dtos.client.playerstats.Stats;
import com.gamersclubfinder.gamersclubfinder.dtos.client.steamid.SteamId;
import com.gamersclubfinder.gamersclubfinder.dtos.games.Games;
import com.gamersclubfinder.gamersclubfinder.dtos.games.OwnedGamesResponse;
import com.gamersclubfinder.gamersclubfinder.dtos.players.PlayerResponse;
import com.gamersclubfinder.gamersclubfinder.dtos.players.details.Details;
import com.gamersclubfinder.gamersclubfinder.dtos.players.details.PlayersDetails;
import com.gamersclubfinder.gamersclubfinder.services.players.search.IPlayerSearchService;
import com.gamersclubfinder.gamersclubfinder.util.DateUtil;
import com.gamersclubfinder.gamersclubfinder.util.SteamKeys;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PlayerSearchServiceImpl implements IPlayerSearchService {
    private final PlayerRepository playerRepository;
    private final SteamAPI steamAPI;

    public PlayerSearchServiceImpl(PlayerRepository playerRepository, SteamAPI steamAPI) {
        this.playerRepository = playerRepository;
        this.steamAPI = steamAPI;
    }

    // todo: throw not found
    @Override
    public PlayerResponse findBySteamId(String steamId) {
        steamId = extractSteamId(steamId);

        Player player = playerRepository.findBySteamId(steamId);

        return fetchSteam(player, steamId);
    }

    private PlayerResponse fetchSteam(Player player, String steamId) {
        PlayerResponse response = new PlayerResponse(steamId, player.getGamersclubUrl());

        setPlayerDetails(response, steamId);
        setBannedFriends(response, steamId);
        setPlayedHours(response, steamId);
        setPlayerCsKdr(response, steamId);

        return response;
    }

    private String extractSteamId(String steamId) {
        final String http = "http://steamcommunity.com/id/";
        final String https = "https://steamcommunity.com/id/";

        if (steamId.contains(http)) {
            steamId = steamId.replace("http://steamcommunity.com/id/", "");
            steamId = steamId.replace("/", "");
            SteamId response = steamAPI.getPlayerSteamIdByNickname(SteamKeys.STEAM_KEY, steamId.replace("http://steamcommunity.com/id/", ""));

//            if (response == null) {
//                throw exception;
//            }

            steamId = response.response().steamid();
        }

        if (steamId.contains(https)) {
            steamId = steamId.replace("https://steamcommunity.com/id/", "");
            steamId = steamId.replace("/", "");
            SteamId response = steamAPI.getPlayerSteamIdByNickname(SteamKeys.STEAM_KEY, steamId.replace("https://steamcommunity.com/id/", ""));

//            if (response == null) {
//                throw exception;
//            }

            steamId = response.response().steamid();
        }

        steamId = steamId.replace("http://steamcommunity.com/profiles/", "");
        steamId = steamId.replace("https://steamcommunity.com/profiles/", "");
        return steamId.replace("/", "");
    }

    private void setBannedFriends(PlayerResponse response, String steamId) {
        FriendsList friends = steamAPI.getPlayerFriendsList(SteamKeys.STEAM_KEY, steamId, "friend");
        AtomicInteger bannedFriends;
        if (friends != null && friends.friends() != null) {
            bannedFriends = new AtomicInteger();
            friends.friends().forEach(f -> {
                PlayerBanResponse bans = steamAPI.getPlayerBans(SteamKeys.STEAM_KEY, List.of(steamId));
                bans.players().forEach(b -> {
                    if (b.VACBanned()) bannedFriends.getAndIncrement();
                });
            });
        } else {
            bannedFriends = null;
        }

        response.setBannedFriends((bannedFriends != null) ? bannedFriends.get() :  null);
    }

    private void setPlayerDetails(PlayerResponse response, String steamId) {
        Details details = steamAPI.getPlayerDetails(SteamKeys.STEAM_KEY, List.of(steamId));
        PlayersDetails playersDetails = details.response().players().stream().findFirst().get();

        response.setPersonaName(playersDetails.personaname());
        response.setAvatar(playersDetails.avatar());
        response.setProfileUrl(playersDetails.profileurl());
        response.setCreatedAt(DateUtil.secondsEpochToDate(playersDetails.timecreated()));
    }

    private void setPlayedHours(PlayerResponse response, String steamId) {
        OwnedGamesResponse ownedGames = steamAPI.getOwnedGames(SteamKeys.STEAM_KEY, steamId);

        var wrapperPlayedHours = new Object(){ String value = ""; };
        if (ownedGames != null && ownedGames.response() != null && ownedGames.response().games() != null) {
            Optional<Games> cs2 = ownedGames.response().games().stream().filter(g -> g.appid().equals("730")).findFirst();
            cs2.ifPresent(games -> wrapperPlayedHours.value += DateUtil.minutesToHours(games.playtime_forever()));
        }

        response.setTotalPlayedTime((!Objects.equals(wrapperPlayedHours.value, "")) ? Long.parseLong(wrapperPlayedHours.value) : 0);
    }

    private void setPlayerCsKdr(PlayerResponse response, String steamId) {
        PlayerStatsResponse cs2Stats = steamAPI.getPlayerStatsForGame(SteamKeys.STEAM_KEY, steamId, "730");

        double kdr;
        if (cs2Stats != null) {
            Optional<Stats> kills = cs2Stats.playerstats().stats().stream().filter(s -> s.name().equals("total_kills")).findFirst();
            Optional<Stats> deaths = cs2Stats.playerstats().stats().stream().filter(s -> s.name().equals("total_deaths")).findFirst();

            kdr =  (double) kills.get().value() / deaths.get().value();
        } else
            kdr = 0;

        response.setKDR(kdr);
    }
}
package com.gamersclubfinder.gamersclubfinder.services.players.search.impl;

import com.gamersclubfinder.gamersclubfinder.clients.SteamAPI;
import com.gamersclubfinder.gamersclubfinder.database.models.Player;
import com.gamersclubfinder.gamersclubfinder.database.repositories.PlayerRepository;
import com.gamersclubfinder.gamersclubfinder.dtos.client.friendlist.FriendsList;
import com.gamersclubfinder.gamersclubfinder.dtos.client.playerbans.PlayerBanResponse;
import com.gamersclubfinder.gamersclubfinder.dtos.games.OwnedGamesResponse;
import com.gamersclubfinder.gamersclubfinder.dtos.players.PlayerResponse;
import com.gamersclubfinder.gamersclubfinder.dtos.players.details.Details;
import com.gamersclubfinder.gamersclubfinder.dtos.players.details.PlayersDetails;
import com.gamersclubfinder.gamersclubfinder.services.players.search.IPlayerSearchService;
import com.gamersclubfinder.gamersclubfinder.util.DateUtil;
import com.gamersclubfinder.gamersclubfinder.util.SteamKeys;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PlayerSearchServiceImpl implements IPlayerSearchService {
    private final PlayerRepository playerRepository;
    private final SteamAPI steamAPI;

    public PlayerSearchServiceImpl(PlayerRepository playerRepository, SteamAPI steamAPI) {
        this.playerRepository = playerRepository;
        this.steamAPI = steamAPI;
    }

    @Override
    public PlayerResponse findBySteamId(String steamId) {
        Player player = playerRepository.findBySteamId(steamId);
        if (player == null) return null;

        return fetchSteam(player, steamId);
    }

    private PlayerResponse fetchSteam(Player player, String steamId) {
        FriendsList friends = steamAPI.getPlayerFriendsList(SteamKeys.STEAM_KEY, steamId, "friend");
        AtomicInteger bannedFriends = new AtomicInteger();
        friends.friends().forEach(f -> {
            PlayerBanResponse bans = steamAPI.getPlayerBans(SteamKeys.STEAM_KEY, List.of(steamId));
            bans.players().forEach(b -> {
                if (b.VACBanned()) bannedFriends.getAndIncrement();
            });
        });

        Details details = steamAPI.getPlayerDetails(SteamKeys.STEAM_KEY, List.of(steamId));
        PlayersDetails playersDetails = details.response().players().stream().findFirst().get();
        String personaName = playersDetails.personaname();
        String avatar = playersDetails.avatar();
        String profileUrl = playersDetails.profileUrl();

        OwnedGamesResponse ownedGames = steamAPI.getOwnedGames(SteamKeys.STEAM_KEY, steamId);

        var wrapperPlayedHours = new Object(){ String value = ""; };
        ownedGames.response().games().forEach(g -> {
            if (g.appid().equals("720")) wrapperPlayedHours.value += DateUtil.secondsEpochToHours(g.playtime_forever());
        });

        return new PlayerResponse(
                steamId,
                player.getGamersclubUrl(),
                personaName,
                profileUrl,
                avatar,
                bannedFriends.get(),
                Long.parseLong(wrapperPlayedHours.value),
                DateUtil.secondsEpochToDate(playersDetails.timecreated()),
                0.0
        );
    }
}
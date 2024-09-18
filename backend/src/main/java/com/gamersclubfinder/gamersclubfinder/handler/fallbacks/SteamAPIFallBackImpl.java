package com.gamersclubfinder.gamersclubfinder.handler.fallbacks;

import com.gamersclubfinder.gamersclubfinder.clients.SteamAPI;
import com.gamersclubfinder.gamersclubfinder.dtos.client.friendlist.FriendsList;
import com.gamersclubfinder.gamersclubfinder.dtos.client.friendlist.FriendsResponse;
import com.gamersclubfinder.gamersclubfinder.dtos.client.playerbans.PlayerBanResponse;
import com.gamersclubfinder.gamersclubfinder.dtos.client.playerstats.PlayerStatsResponse;
import com.gamersclubfinder.gamersclubfinder.dtos.client.steamid.SteamId;
import com.gamersclubfinder.gamersclubfinder.dtos.games.OwnedGamesResponse;
import com.gamersclubfinder.gamersclubfinder.dtos.players.details.Details;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SteamAPIFallBackImpl implements SteamAPI {

    @Override
    public SteamId getPlayerSteamIdByNickname(String key, String vanityurl) {
        return null;
    }

    @Override
    public FriendsResponse getPlayerFriendsList(String key, String steamid, String relationship) {
        return null;
    }

    @Override
    public PlayerBanResponse getPlayerBans(String key, List<String> steamIds) {
        return null;
    }

    @Override
    public PlayerStatsResponse getPlayerStatsForGame(String key, String steamId, String appId) {
        return null;
    }

    @Override
    public OwnedGamesResponse getOwnedGames(String key, String steamId) {
        return null;
    }

    @Override
    public Details getPlayerDetails(String key, List<String> steamIds) {
        return null;
    }
}
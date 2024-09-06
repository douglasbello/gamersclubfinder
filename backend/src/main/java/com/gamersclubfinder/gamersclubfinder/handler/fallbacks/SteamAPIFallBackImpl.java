package com.gamersclubfinder.gamersclubfinder.handler.fallbacks;

import com.gamersclubfinder.gamersclubfinder.clients.SteamAPI;
import com.gamersclubfinder.gamersclubfinder.dtos.client.friendlist.FriendsList;
import com.gamersclubfinder.gamersclubfinder.dtos.client.playerbans.PlayerBanResponse;
import com.gamersclubfinder.gamersclubfinder.dtos.client.steamid.SteamId;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SteamAPIFallBackImpl implements SteamAPI {

    @Override
    public SteamId getPlayerSteamIdByNickname(String key, String vanityurl) {
        return null;
    }

    @Override
    public FriendsList getPlayerFriendsList(String key, String steamid, String relationship) {
        return null;
    }

    @Override
    public PlayerBanResponse getPlayerBans(String key, List<String> steamIds) {
        return null;
    }
}
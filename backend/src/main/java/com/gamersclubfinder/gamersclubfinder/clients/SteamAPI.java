package com.gamersclubfinder.gamersclubfinder.clients;

import com.gamersclubfinder.gamersclubfinder.dtos.client.friendlist.FriendsList;
import com.gamersclubfinder.gamersclubfinder.dtos.client.friendlist.FriendsResponse;
import com.gamersclubfinder.gamersclubfinder.dtos.client.playerbans.PlayerBanResponse;
import com.gamersclubfinder.gamersclubfinder.dtos.client.playerstats.PlayerStatsResponse;
import com.gamersclubfinder.gamersclubfinder.dtos.client.steamid.SteamId;
import com.gamersclubfinder.gamersclubfinder.dtos.games.OwnedGamesResponse;
import com.gamersclubfinder.gamersclubfinder.dtos.players.details.Details;
import com.gamersclubfinder.gamersclubfinder.handler.fallbacks.SteamAPIFallBackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "steamapi", url = "https://api.steampowered.com/", fallback = SteamAPIFallBackImpl.class)
public interface SteamAPI {

    @RequestMapping(method = RequestMethod.GET, value = "/ISteamUser/ResolveVanityURL/v0001/")
    SteamId getPlayerSteamIdByNickname(@RequestParam String key, @RequestParam String vanityurl);

    @RequestMapping(method = RequestMethod.GET, value = "/ISteamUser/GetFriendList/v0001/")
    FriendsResponse getPlayerFriendsList(@RequestParam String key, @RequestParam String steamid, @RequestParam String relationship);

    @RequestMapping(method = RequestMethod.GET, value = "/ISteamUser/GetPlayerBans/v1/")
    PlayerBanResponse getPlayerBans(@RequestParam String key, @RequestParam(name = "steamids") List<String> steamIds);

    @RequestMapping(method = RequestMethod.GET, value = "/ISteamUserStats/GetUserStatsForGame/v0002/")
    PlayerStatsResponse getPlayerStatsForGame(@RequestParam String key, @RequestParam(name = "steamid") String steamId, @RequestParam(name = "appid") String appId);

    @RequestMapping(method = RequestMethod.GET, value = "/IPlayerService/GetOwnedGames/v1/")
    OwnedGamesResponse getOwnedGames(@RequestParam String key, @RequestParam(name = "steamid") String steamId);

    @RequestMapping(method = RequestMethod.GET, value = "/ISteamUser/GetPlayerSummaries/v2/")
    Details getPlayerDetails(@RequestParam String key, @RequestParam(name = "steamids") List<String> steamIds);
}
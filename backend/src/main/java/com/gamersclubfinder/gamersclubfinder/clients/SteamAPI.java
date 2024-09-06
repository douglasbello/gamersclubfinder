package com.gamersclubfinder.gamersclubfinder.clients;

import com.gamersclubfinder.gamersclubfinder.dtos.client.SteamId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "steamapi", url = "https://api.steampowered.com/")
public interface SteamAPI {

    @RequestMapping(method = RequestMethod.GET, value = "/ISteamUser/ResolveVanityURL/v0001/")
    SteamId getPlayerSteamIdByNickname(@RequestParam String key, @RequestParam String vanityurl);
}
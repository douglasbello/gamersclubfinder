package com.gamersclubfinder.gamersclubfinder;

import com.gamersclubfinder.gamersclubfinder.clients.SteamAPI;
import com.gamersclubfinder.gamersclubfinder.dtos.client.friendlist.FriendsList;
import com.gamersclubfinder.gamersclubfinder.dtos.client.playerbans.PlayerBanResponse;
import com.gamersclubfinder.gamersclubfinder.dtos.client.playerstats.PlayerStatsResponse;
import com.gamersclubfinder.gamersclubfinder.dtos.client.steamid.SteamId;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.List;

@SpringBootApplication
@EnableFeignClients
public class GamersclubfinderApplication implements CommandLineRunner {
	private final SteamAPI api;

	public GamersclubfinderApplication(SteamAPI api) {
		this.api = api;
	}

	private final String STEAM_KEY = "28F7BCC590CB51D81413B2ADCCF7ACF6";

	public static void main(String[] args) {
		SpringApplication.run(GamersclubfinderApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		SteamId response = api.getPlayerSteamIdByNickname("28F7BCC590CB51D81413B2ADCCF7ACF6", "napsterofpuppets");
		System.out.println(response);

		PlayerBanResponse bans = api.getPlayerBans(STEAM_KEY, List.of("76561199330735142"));
		System.out.println(bans);

		PlayerStatsResponse stats = api.getPlayerStatsForGame(STEAM_KEY, "76561199330735142", "730");
		System.out.println(stats);

		FriendsList friendsList = api.getPlayerFriendsList(STEAM_KEY, "76561199330735142", "friend");
		System.out.println(friendsList);
	}
}

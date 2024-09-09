package com.gamersclubfinder.gamersclubfinder;

import com.gamersclubfinder.gamersclubfinder.clients.SteamAPI;
import com.gamersclubfinder.gamersclubfinder.dtos.client.friendlist.FriendsList;
import com.gamersclubfinder.gamersclubfinder.dtos.client.playerbans.PlayerBanResponse;
import com.gamersclubfinder.gamersclubfinder.dtos.client.playerstats.PlayerStatsResponse;
import com.gamersclubfinder.gamersclubfinder.dtos.client.steamid.SteamId;
import com.gamersclubfinder.gamersclubfinder.dtos.games.OwnedGamesResponse;
import com.gamersclubfinder.gamersclubfinder.dtos.players.details.Details;
import com.gamersclubfinder.gamersclubfinder.services.players.search.impl.PlayerSearchServiceImpl;
import com.gamersclubfinder.gamersclubfinder.util.DateUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootApplication
@EnableFeignClients
public class GamersclubfinderApplication implements CommandLineRunner {
	private final SteamAPI api;
	private final PlayerSearchServiceImpl playerSearchServiceImpl;

	public GamersclubfinderApplication(SteamAPI api, PlayerSearchServiceImpl playerSearchServiceImpl) {
		this.api = api;
		this.playerSearchServiceImpl = playerSearchServiceImpl;
	}

	private final String STEAM_KEY = "28F7BCC590CB51D81413B2ADCCF7ACF6";

	public static void main(String[] args) {
		SpringApplication.run(GamersclubfinderApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		SteamId response = api.getPlayerSteamIdByNickname("28F7BCC590CB51D81413B2ADCCF7ACF6", "napsterofpuppets");
		System.out.println("Get steam id by vanity url - " + response);

		PlayerBanResponse bans = api.getPlayerBans(STEAM_KEY, List.of("76561198014127592"));
		System.out.println("Player bans - " + bans);

		PlayerStatsResponse stats = api.getPlayerStatsForGame(STEAM_KEY, "76561198014127592", "730");
		System.out.println("CS2 player stats - " + stats);

		FriendsList friendsList = api.getPlayerFriendsList(STEAM_KEY, "76561198014127592", "friend");
		System.out.println("Friends list - " + friendsList);

		Instant instant = Instant.ofEpochSecond(1654973577);
		LocalDateTime created = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));

		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String formattedDate = created.format(fmt);

		System.out.println("Created account at - " + formattedDate);

		String formattedHours = DateUtil.minutesToHours(1375151L);
		System.out.println("Played hours - " + formattedHours);

		OwnedGamesResponse ownedGamesResponse = api.getOwnedGames(STEAM_KEY, "76561198014127592");
		System.out.println("Owned games - " + ownedGamesResponse);

		Details napsterDetails = api.getPlayerDetails(STEAM_KEY, List.of("76561198014127592"));
		System.out.println("Player details - " + napsterDetails);
	}
}

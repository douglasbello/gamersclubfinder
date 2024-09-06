package com.gamersclubfinder.gamersclubfinder;

import com.gamersclubfinder.gamersclubfinder.clients.SteamAPI;
import com.gamersclubfinder.gamersclubfinder.dtos.client.SteamId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GamersclubfinderApplication implements CommandLineRunner {
	@Autowired
	private SteamAPI api;

	public static void main(String[] args) {
		SpringApplication.run(GamersclubfinderApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		SteamId response = api.getPlayerSteamIdByNickname("28F7BCC590CB51D81413B2ADCCF7ACF6", "napsterofpuppets");

		System.out.println(response);
	}
}

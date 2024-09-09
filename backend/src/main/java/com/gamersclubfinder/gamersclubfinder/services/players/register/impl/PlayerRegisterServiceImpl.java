package com.gamersclubfinder.gamersclubfinder.services.players.register.impl;

import com.gamersclubfinder.gamersclubfinder.database.models.Player;
import com.gamersclubfinder.gamersclubfinder.database.repositories.PlayerRepository;
import com.gamersclubfinder.gamersclubfinder.dtos.players.PlayerRequest;
import com.gamersclubfinder.gamersclubfinder.services.players.register.IPlayerRegisterService;
import org.springframework.stereotype.Service;

@Service
public class PlayerRegisterServiceImpl implements IPlayerRegisterService {
    private final PlayerRepository playerRepository;

    public PlayerRegisterServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public void register(PlayerRequest request) {
        Player player = new Player(request.steamId(), request.gamersclubUrl());

        if (player.getSteamId().contains("http://steamcommunity.com/profiles/"))
            player.setSteamId(player.getSteamId().replace("http://steamcommunity.com/profiles/", ""));

        playerRepository.save(player);
    }
}
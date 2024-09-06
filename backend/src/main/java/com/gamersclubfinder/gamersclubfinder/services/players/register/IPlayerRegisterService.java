package com.gamersclubfinder.gamersclubfinder.services.players.register;

import com.gamersclubfinder.gamersclubfinder.dtos.players.PlayerRequest;

public interface IPlayerRegisterService {
    void register(PlayerRequest request);
}
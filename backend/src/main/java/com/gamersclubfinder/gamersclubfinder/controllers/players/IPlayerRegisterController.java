package com.gamersclubfinder.gamersclubfinder.controllers.players;

import com.gamersclubfinder.gamersclubfinder.dtos.players.PlayerRequest;
import org.springframework.http.ResponseEntity;

public interface IPlayerRegisterController {
    ResponseEntity<Void> register(PlayerRequest request);
}
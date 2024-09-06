package com.gamersclubfinder.gamersclubfinder.controllers.players.impl;

import com.gamersclubfinder.gamersclubfinder.controllers.players.IPlayerRegisterController;
import com.gamersclubfinder.gamersclubfinder.dtos.players.PlayerRequest;
import com.gamersclubfinder.gamersclubfinder.services.players.register.IPlayerRegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/players")
public class IPlayerRegisterControllerImpl implements IPlayerRegisterController {
    private final IPlayerRegisterService playerRegisterService;

    public IPlayerRegisterControllerImpl(IPlayerRegisterService playerRegisterService) {
        this.playerRegisterService = playerRegisterService;
    }

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody PlayerRequest request) {
        playerRegisterService.register(request);

        return ResponseEntity.noContent().build();
    }
}
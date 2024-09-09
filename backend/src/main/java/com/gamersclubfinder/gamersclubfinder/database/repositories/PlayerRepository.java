package com.gamersclubfinder.gamersclubfinder.database.repositories;

import com.gamersclubfinder.gamersclubfinder.database.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlayerRepository extends JpaRepository<Player, UUID> {
    Player findBySteamId(String steamId);
}
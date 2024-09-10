package com.gamersclubfinder.gamersclubfinder.database.repositories;

import com.gamersclubfinder.gamersclubfinder.database.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PlayerRepository extends JpaRepository<Player, UUID> {
    Optional<Player> findBySteamId(String steamId);
}
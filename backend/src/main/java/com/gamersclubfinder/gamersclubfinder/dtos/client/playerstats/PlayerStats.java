package com.gamersclubfinder.gamersclubfinder.dtos.client.playerstats;

import java.util.List;

public record PlayerStats(String steamID, List<Stats> stats) {
}
package com.gamersclubfinder.gamersclubfinder.services.players.facade;

import com.gamersclubfinder.gamersclubfinder.clients.SteamAPI;
import com.gamersclubfinder.gamersclubfinder.database.models.Player;
import com.gamersclubfinder.gamersclubfinder.database.repositories.PlayerRepository;
import com.gamersclubfinder.gamersclubfinder.dtos.client.friendlist.Friend;
import com.gamersclubfinder.gamersclubfinder.dtos.client.friendlist.FriendsList;
import com.gamersclubfinder.gamersclubfinder.dtos.client.friendlist.FriendsResponse;
import com.gamersclubfinder.gamersclubfinder.dtos.client.playerbans.PlayerBanResponse;
import com.gamersclubfinder.gamersclubfinder.dtos.client.playerstats.PlayerStatsResponse;
import com.gamersclubfinder.gamersclubfinder.dtos.client.playerstats.Stats;
import com.gamersclubfinder.gamersclubfinder.dtos.games.Games;
import com.gamersclubfinder.gamersclubfinder.dtos.games.OwnedGamesResponse;
import com.gamersclubfinder.gamersclubfinder.dtos.players.PlayerResponse;
import com.gamersclubfinder.gamersclubfinder.dtos.players.details.Details;
import com.gamersclubfinder.gamersclubfinder.dtos.players.details.PlayersDetails;
import com.gamersclubfinder.gamersclubfinder.util.DateUtil;
import com.gamersclubfinder.gamersclubfinder.util.SteamKeys;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class PlayerSteamDetailsFacade {
    private final SteamAPI steamAPI;
    private static final ExecutorService executor = Executors.newFixedThreadPool(20);


    public PlayerSteamDetailsFacade(SteamAPI steamAPI) {
        this.steamAPI = steamAPI;
    }

    public PlayerResponse fetchSteam(Player player, String steamId) {
        PlayerResponse response = new PlayerResponse(steamId, player.getGamersclubUrl());

        CompletableFuture<Void> detailsFuture = CompletableFuture.runAsync(() -> setPlayerDetails(response, steamId));
        CompletableFuture<Void> bannedFriendsFuture = CompletableFuture.runAsync(() -> setBannedFriends(response, steamId));
        CompletableFuture<Void> playedHoursFuture = CompletableFuture.runAsync(() -> setPlayedHours(response, steamId));
        CompletableFuture<Void> kdrFuture = CompletableFuture.runAsync(() -> setPlayerCsKdr(response, steamId));

        CompletableFuture<Void> allTasks = CompletableFuture.allOf(detailsFuture, bannedFriendsFuture, playedHoursFuture, kdrFuture);

        try {
            allTasks.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return response;
    }
    private void setBannedFriends(PlayerResponse response, String steamId) {
        FriendsResponse friends = steamAPI.getPlayerFriendsList(SteamKeys.STEAM_KEY, steamId, "friend");
        AtomicInteger bannedFriends = new AtomicInteger();

        if (friends != null && friends.friendslist() != null && friends.friendslist().friends() != null) {
            List<Friend> friendList = friends.friendslist().friends();

            // Para cada amigo, cria uma tarefa assíncrona para verificar se está banido
            List<CompletableFuture<Void>> futures = friendList.stream()
                    .map(friend -> CompletableFuture.runAsync(() -> {
                        PlayerBanResponse bans = steamAPI.getPlayerBans(SteamKeys.STEAM_KEY, List.of(friend.steamid()));
                        bans.players().forEach(ban -> {
                            if (ban.VACBanned()) bannedFriends.getAndIncrement();
                        });
                    }, executor))
                    .toList(); // Converte para uma lista de CompletableFutures

            CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            try {
                allOf.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        response.setBannedFriends(bannedFriends.get());
        shutdown();
    }

    public void shutdown() {
        executor.shutdown();
    }

    private void setPlayerDetails(PlayerResponse response, String steamId) {
        Details details = steamAPI.getPlayerDetails(SteamKeys.STEAM_KEY, List.of(steamId));
        PlayersDetails playersDetails = details.response().players().stream().findFirst().get();

        response.setPersonaName(playersDetails.personaname());
        response.setAvatar(playersDetails.avatar());
        response.setProfileUrl(playersDetails.profileurl());
        response.setCreatedAt(playersDetails.timecreated() != null ? DateUtil.secondsEpochToDate(playersDetails.timecreated()) : null);
    }

    private void setPlayedHours(PlayerResponse response, String steamId) {
        OwnedGamesResponse ownedGames = steamAPI.getOwnedGames(SteamKeys.STEAM_KEY, steamId);
        System.out.println(ownedGames);

        var wrapperPlayedHours = new Object(){ String value = ""; };
        if (ownedGames != null && ownedGames.response() != null && ownedGames.response().games() != null) {
            Optional<Games> cs2 = ownedGames.response().games().stream().filter(g -> g.appid().equals("730")).findFirst();
            cs2.ifPresent(games -> wrapperPlayedHours.value += DateUtil.minutesToHours(games.playtime_forever()));
        }

        response.setTotalPlayedTime((!Objects.equals(wrapperPlayedHours.value, "")) ? Long.parseLong(wrapperPlayedHours.value) : 0);
    }

    private void setPlayerCsKdr(PlayerResponse response, String steamId) {
        PlayerStatsResponse cs2Stats = steamAPI.getPlayerStatsForGame(SteamKeys.STEAM_KEY, steamId, "730");

        double kdr = 0;
        if (cs2Stats != null) {
            Optional<Stats> kills = cs2Stats.playerstats().stats().stream().filter(s -> s.name().equals("total_kills")).findFirst();
            Optional<Stats> deaths = cs2Stats.playerstats().stats().stream().filter(s -> s.name().equals("total_deaths")).findFirst();

            kdr = (double) kills.get().value() / deaths.get().value();
        }

        response.setKDR(kdr);
    }
}
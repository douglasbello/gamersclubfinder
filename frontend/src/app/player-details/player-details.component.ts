import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { catchError, Observable, of } from "rxjs";
import { Players } from "../players/players";
import { PlayerService } from "../services/player.service";
import { AsyncPipe, NgIf } from "@angular/common";

@Component({
  selector: 'app-player-details',
  standalone: true,
  imports: [
    NgIf,
    AsyncPipe
  ],
  templateUrl: './player-details.component.html',
  styleUrl: './player-details.component.scss'
})
export class PlayerDetailsComponent {
  player$: Observable<Players>;

  constructor(private route: ActivatedRoute, private playersService: PlayerService) {
    const playerId: string | null = this.route.snapshot.paramMap.get('playerId');
    const id = (playerId ? playerId : 'undefined');

    this.player$ = this.playersService.findBySteamId(id)
      .pipe(
        catchError(error => {
          console.log(error);
          return of({
            steamId: "",
            gamersclubUrl: "",
            personaName: "",
            profileUrl: "",
            avatar: "",
            bannedFriends: 0,
            totalPlayedTime: 0,
            createdAt: "",
            KDR: 0
          })
        })
      )
  }
}

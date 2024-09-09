import { Component } from '@angular/core';
import { catchError, Observable, of } from "rxjs";
import { Players } from "../players/players";
import { PlayerService } from "../services/player.service";
import { AsyncPipe, NgIf } from "@angular/common";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    AsyncPipe,
    NgIf
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  players$: Observable<Players>;

  constructor(private playersService: PlayerService) {
    this.players$ = this.playersService.findBySteamId("https://steamcommunity.com/id/rGangster/")
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

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
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

  constructor(private route: ActivatedRoute, private playersService: PlayerService, private router: Router) {
    const playerId: string | null = this.route.snapshot.paramMap.get('playerId');
    const id: string = (playerId ? playerId : 'undefined');

    this.player$ = this.playersService.findBySteamId(id)
      .pipe(
        catchError(error => {
          if (error.status === 404)
            this.router.navigate(['/not-found']);

          return of(
            {
              steamId: "",
              gamersclubUrl: "",
              personaName: "",
              profileUrl: "",
              avatar: "",
              bannedFriends: 0,
              totalPlayedTime: 0,
              createdAt: "",
              KDR: 0
            }
          );
        })
      )
  }
}

import { Routes } from '@angular/router';
import { PlayerDetailsComponent } from "./player-details/player-details.component";

export const routes: Routes = [
  { path: "player/:playerId", component: PlayerDetailsComponent }
];

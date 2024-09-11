import { Routes } from '@angular/router';
import { PlayerDetailsComponent } from "./player-details/player-details.component";
import { Form } from "./form/form";
import { NotFoundComponent } from "./not-found/not-found.component";

export const routes: Routes = [
  { path: '', component: Form },
  { path: 'player/:playerId', component: PlayerDetailsComponent },
  { path: 'not-found', component: NotFoundComponent }
];

import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Players } from "../players/players";

@Injectable({
  providedIn: 'root'
})
export class PlayerService {
  private readonly API: string = 'http://localhost:8080/players';
  private readonly HTTP: string = "http://steamcommunity.com/id/"
  private readonly HTTPS: string = "https://steamcommunity.com/id/"

  constructor(private httpClient: HttpClient) { }

  findBySteamId(req: string): Observable<Players> {
    req = this.subString(req);

    return this.httpClient.get<Players>(this.API, { params: { steamId: req } });
  }

  private subString(req: string): string {
    if (req.includes(this.HTTP))
      req.replace(this.HTTP, "");

    if (req.includes(this.HTTPS))
      req.replace(this.HTTPS, "");

    if (req.includes("http://steamcommunity.com/profiles/"))
      req.replace("http://steamcommunity.com/profiles/", "");

    if (req.includes("https://steamcommunity.com/profiles/"))
      req.replace("https://steamcommunity.com/profiles/", "");

    if (req.endsWith("/"))
      req.replace("/", "");

    return req;
  }
}

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Player } from '../../shared/models/player.model';
import { Ranking } from '../../shared/models/ranking.model';


@Injectable({
  providedIn: 'root'
})
export class PlayerService {

  private apiUrl = '/api/players';

  constructor(private http: HttpClient) {}

  getPlayerById(id: number): Observable<Player> {
    return this.http.get<Player>(`${this.apiUrl}/${id}`);
  }

  getAllPlayers(): Observable<Player[]> {
    return this.http.get<Player[]>(this.apiUrl);
  }

  createPlayer(player: { username: string; email: string }): Observable<Player> {
  return this.http.post<Player>(this.apiUrl, player);
  }

  getRankings(): Observable<Ranking[]> {
  return this.http.get<Ranking[]>('/api/rankings');
  }


}

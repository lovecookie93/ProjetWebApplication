import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GameApiService {
  private http = inject(HttpClient);
  
  // L'URL de ton backend Spring Boot
  private apiUrl = 'http://localhost:8080/api/games'; 

  // 1. Envoie les données au @PostMapping
  saveScore(scoreData: any): Observable<any> {
    return this.http.post(this.apiUrl, scoreData);
  }

  // 2. RÉCUPÈRE les données (C'est ce qu'il manquait pour le profil !)
  // Cette méthode appelle ton @GetMapping("/player/{playerId}") côté Java
  getGamesByPlayer(playerId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/player/${playerId}`);
  }
}
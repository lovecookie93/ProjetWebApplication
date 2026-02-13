import { Component, OnInit } from '@angular/core';
import { NgFor } from '@angular/common';
import { PlayerService } from '../../core/services/player.service';
import { Player } from '../../shared/models/player.model';
import { Ranking } from '../../shared/models/ranking.model';
import { Router } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-home',
  imports: [NgFor],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  // 🔹 Jeux affichés sur la page
games = [
  { name: 'Snake', description: 'Survis le plus longtemps possible.', route: '/games/snake' },
  { name: 'Tic-Tac-Toe', description: 'Affronte un adversaire.', route: '/games/tictactoe' },
  { name: 'Sudoku', description: 'Teste ta logique.', route: '/games/sudoku' },
  { name: 'Chifoumi', description: 'Pierre Feuille Ciseaux.', route: '/games/chifoumi' }
];


  // 🔹 Classement dynamique
  topPlayers: { username: string; points: number }[] = [];

  constructor(
  private playerService: PlayerService,
  private router: Router
) {}

  ngOnInit(): void {
    this.loadRanking();
  }

  goToGames(): void {
  this.router.navigate(['/games']);}
  
  openGame(route: string): void {
  this.router.navigate([route]);
}


  loadRanking(): void {
    this.playerService.getAllPlayers().subscribe((players: Player[]) => {

      this.playerService.getRankings().subscribe((rankings: Ranking[]) => {

        const sorted = rankings.sort((a, b) => b.points - a.points);

        this.topPlayers = sorted.slice(0, 3).map(r => {
          const player = players.find(p => p.id === r.playerId);
          return {
            username: player ? player.username : 'Inconnu',
            points: r.points
          };
        });

      });

    });
  }

}

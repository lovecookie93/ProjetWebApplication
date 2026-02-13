import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GameApiService } from '../../services/game-api.service';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  private gameApiService = inject(GameApiService);

  user = {
    username: 'Nova',
    globalScore: 0,
    gamesPlayed: 0,
    multiplayer: 0
  };

  gameHistory: any[] = [];

  ngOnInit() {
    this.loadStats();
  }

  loadStats() {
    // On récupère les parties du joueur 1
    this.gameApiService.getGamesByPlayer(1).subscribe({
      next: (data) => {
        this.gameHistory = data.reverse(); // Plus récents en premier
        this.user.gamesPlayed = data.length;
        // On calcule le score total
        this.user.globalScore = data.reduce((acc, game) => acc + (game.scorePlayer1 || 0), 0);
      },
      error: (err) => console.error("Erreur API :", err)
    });
  }
}
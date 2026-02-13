import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-games',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './games.component.html',
  styleUrl: './games.component.scss'
})
export class GamesComponent {
  private router = inject(Router);

  games = [
    { id: 'snake', name: 'Snake', description: 'Survis le plus longtemps possible.', icon: '🐍', color: '#00d2ff' },
    { id: 'tictactoe', name: 'Tic-Tac-Toe', description: 'Affronte un adversaire.', icon: '❌⭕', color: '#d946ef' },
    { id: 'sudoku', name: 'Sudoku', description: 'Teste ta logique.', icon: '🔢', color: '#fbbf24' },
    { id: 'chifoumi', name: 'Chifoumi', description: 'Pierre Feuille Ciseaux.', icon: '👊', color: '#10b981' }
  ];

  playGame(gameId: string) {
    this.router.navigate(['/games', gameId]);
  }
}
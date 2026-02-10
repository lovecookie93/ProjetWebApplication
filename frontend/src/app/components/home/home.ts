import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class HomeComponent {
  // Liste des jeux pour la grille
  games = [
    { id: 'tictactoe', name: 'Tic-Tac-Toe', icon: '🏁' },
    { id: 'snake', name: 'Snake', icon: '🐍' },
    { id: 'sudoku', name: 'Sudoku', icon: '🔢' },
    { id: 'chifoumi', name: 'Chifoumi', icon: '✊' }
  ];

  // Données simulées pour le Top Joueurs
  topPlayers = [
    { name: 'Nove', score: '15583pts' },
    { name: 'Kal', score: '11850pts' },
    { name: 'Ray', score: '922pts' }
  ];
}
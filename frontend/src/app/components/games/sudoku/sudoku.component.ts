import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GameApiService } from '../../../services/game-api.service';

@Component({
  selector: 'app-sudoku',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './sudoku.component.html',
  styleUrl: './sudoku.component.css'
})
export class SudokuComponent {
  private gameApiService = inject(GameApiService);

  // La grille affichée (0 représente une case vide)
  grid = [
    [1, 0, 3, 0],
    [0, 0, 0, 2],
    [2, 0, 0, 0],
    [0, 4, 0, 0]
  ];

  // La solution unique que le code attend pour valider
  solution = [
    [1, 2, 3, 4],
    [4, 3, 1, 2],
    [2, 1, 4, 3],
    [3, 4, 2, 1]
  ];

  score = 0;
  message = "Complétez la grille (Chiffres de 1 à 4) !";

  updateCell(row: number, col: number, event: any) {
    const input = event.target as HTMLInputElement;
    const value = parseInt(input.value);

    // Si on efface le chiffre
    if (!value) {
      this.grid[row][col] = 0;
      return;
    }

    // Comparaison avec la solution
    if (value === this.solution[row][col]) {
      this.grid[row][col] = value; 
      this.message = "Correct !";
      input.classList.remove('error'); // On enlève le rouge si c'était faux avant
      this.checkWin();
    } else {
      this.message = "Chiffre incorrect pour cette position...";
      input.classList.add('error'); // Ajoute la classe pour le CSS rouge
      
      // On efface après un petit délai pour laisser l'utilisateur voir
      setTimeout(() => { 
        input.value = ''; 
        input.classList.remove('error');
      }, 800);
    }
  }

  checkWin() {
    let complete = true;
    
    // On vérifie chaque case une par une
    for (let i = 0; i < 4; i++) {
      for (let j = 0; j < 4; j++) {
        if (this.grid[i][j] !== this.solution[i][j]) {
          complete = false;
          break;
        }
      }
    }

    if (complete) {
      this.message = "🔥 FÉLICITATIONS ! Grille complétée.";
      this.score = 100;
      this.sendScore();
    }
  }

  sendScore() {
    const payload = {
      player1Id: 1, // ID de test
      gameTypeId: 3, // ID correspondant au Sudoku dans ta BDD
      scorePlayer1: this.score,
      status: 'FINISHED',
      startTime: new Date().toISOString()
    };

    this.gameApiService.saveScore(payload).subscribe({
      next: (res) => console.log('Score Sudoku envoyé !', res),
      error: (err) => console.error('Erreur envoi score', err)
    });
  }

  resetGame() {
    window.location.reload();
  }
}
import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GameApiService } from '../../../services/game-api.service';

@Component({
  selector: 'app-chifoumi',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './chifoumi.component.html',
  styleUrl: './chifoumi.component.scss'
})
export class ChifoumiComponent {
  private gameApiService = inject(GameApiService);

  choices = [
    { name: 'pierre', emoji: '👊', color: '#ef4444' },
    { name: 'feuille', emoji: '✋', color: '#3b82f6' },
    { name: 'ciseaux', emoji: '✌️', color: '#10b981' }
  ];

  isTwoPlayers = false; // Mode par défaut : IA
  player1Choice: any = null;
  player2Choice: any = null;
  turn: number = 1; // Qui doit jouer ?
  resultMessage: string = 'Choisissez votre arme !';
  scoreP1 = 0;
  scoreP2 = 0;

  setMode(twoPlayers: boolean) {
    this.isTwoPlayers = twoPlayers;
    this.reset();
  }

  play(choice: any) {
    if (!this.isTwoPlayers) {
      // MODE IA
      this.player1Choice = choice;
      this.player2Choice = this.choices[Math.floor(Math.random() * 3)];
      this.determineWinner();
    } else {
      // MODE 2 JOUEURS
      if (this.turn === 1) {
        this.player1Choice = choice;
        this.turn = 2;
        this.resultMessage = "Au tour du Joueur 2 ! (Le choix 1 est caché)";
      } else {
        this.player2Choice = choice;
        this.determineWinner();
      }
    }
  }

  determineWinner() {
    const p1 = this.player1Choice.name;
    const p2 = this.player2Choice.name;

    if (p1 === p2) {
      this.resultMessage = "ÉGALITÉ !";
    } else if (
      (p1 === 'pierre' && p2 === 'ciseaux') ||
      (p1 === 'feuille' && p2 === 'pierre') ||
      (p1 === 'ciseaux' && p2 === 'feuille')
    ) {
      this.resultMessage = this.isTwoPlayers ? "JOUEUR 1 GAGNE !" : "VOUS AVEZ GAGNÉ !";
      this.scoreP1++;
    } else {
      this.resultMessage = this.isTwoPlayers ? "JOUEUR 2 GAGNE !" : "L'IA A GAGNÉ !";
      this.scoreP2++;
    }
    this.sendResultToBackend();
  }

  sendResultToBackend() {
    const payload = {
      player1Id: 1, 
      player2Id: this.isTwoPlayers ? 2 : null, // On remplit le player2 si 2 joueurs
      gameTypeId: 4, 
      scorePlayer1: this.scoreP1,
      scorePlayer2: this.scoreP2,
      startTime: new Date().toISOString()
    };
    this.gameApiService.saveScore(payload).subscribe();
  }

  reset() {
    this.player1Choice = null;
    this.player2Choice = null;
    this.turn = 1;
    this.resultMessage = 'Prêt ?';
  }
}
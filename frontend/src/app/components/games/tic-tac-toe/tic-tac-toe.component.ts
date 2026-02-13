import { Component, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GameApiService } from '../../../services/game-api.service';

@Component({
  selector: 'app-tic-tac-toe',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './tic-tac-toe.component.html',
  styleUrl: './tic-tac-toe.component.scss'
})
export class TicTacToeComponent {
  private gameApiService = inject(GameApiService);
  private cdr = inject(ChangeDetectorRef); // Pour forcer la mise à jour de l'affichage

  board: string[] = Array(9).fill('');
  playerTurn: string = 'X';
  message: string = "Choisissez un mode pour commencer";
  
  gameMode: 'SOLO' | 'DUO' | null = null;
  isGameOver = false;

  selectMode(mode: 'SOLO' | 'DUO') {
    this.gameMode = mode;
    this.resetGame();
    this.message = mode === 'SOLO' ? "Mode Solo : À vous de jouer (X)" : "Mode Duel : Joueur X commence";
  }

  makeMove(index: number) {
    if (!this.board[index] && !this.isGameOver && this.gameMode) {
      this.board[index] = this.playerTurn;
      
      if (this.checkWin()) {
        this.endGame(this.playerTurn + " GAGNE !");
      } else if (this.board.every(cell => cell !== '')) {
        this.endGame("ÉGALITÉ !");
      } else {
        this.playerTurn = this.playerTurn === 'X' ? 'O' : 'X';
        
        if (this.gameMode === 'SOLO' && this.playerTurn === 'O') {
          this.message = "Vortex Bot réfléchit...";
          setTimeout(() => this.aiMove(), 600);
        } else {
          this.message = `C'est au tour de : ${this.playerTurn}`;
        }
      }
    }
  }

  aiMove() {
    if (this.isGameOver) return;

    let move = this.findBestMove();
    this.board[move] = 'O';

    if (this.checkWin()) {
      this.endGame("VORTEX BOT GAGNE !");
    } else if (this.board.every(cell => cell !== '')) {
      this.endGame("ÉGALITÉ !");
    } else {
      this.playerTurn = 'X';
      this.message = "À vous de jouer (X)";
    }

    // Indispensable pour voir le 'O' apparaître immédiatement
    this.cdr.detectChanges(); 
  }

  findBestMove(): number {
    const lines = [[0,1,2],[3,4,5],[6,7,8],[0,3,6],[1,4,7],[2,5,8],[0,4,8],[2,4,6]];
    
    // 1. Gagner si possible
    for (let l of lines) {
      const vals = l.map(i => this.board[i]);
      if (vals.filter(v => v === 'O').length === 2 && vals.includes('')) return l[vals.indexOf('')];
    }
    // 2. Bloquer le joueur X
    for (let l of lines) {
      const vals = l.map(i => this.board[i]);
      if (vals.filter(v => v === 'X').length === 2 && vals.includes('')) return l[vals.indexOf('')];
    }
    // 3. Prendre le centre ou aléatoire
    if (this.board[4] === '') return 4;
    const emptyIndices = this.board.map((v, i) => v === '' ? i : null).filter(v => v !== null) as number[];
    return emptyIndices[Math.floor(Math.random() * emptyIndices.length)];
  }

  checkWin(): boolean {
    const lines = [[0,1,2],[3,4,5],[6,7,8],[0,3,6],[1,4,7],[2,5,8],[0,4,8],[2,4,6]];
    return lines.some(([a, b, c]) => 
      this.board[a] && this.board[a] === this.board[b] && this.board[a] === this.board[c]
    );
  }

  endGame(resultMsg: string) {
    this.isGameOver = true;
    this.message = resultMsg;
    let points = resultMsg.includes('X GAGNE') || (this.gameMode === 'DUO' && resultMsg.includes('GAGNE')) ? 50 : (resultMsg.includes('ÉGALITÉ') ? 10 : 0);
    this.sendScore(points);
  }

  sendScore(points: number) {
    const payload = {
      player1Id: 1,
      gameTypeId: 2, 
      scorePlayer1: points,
      status: 'FINISHED'
    };
    this.gameApiService.saveScore(payload).subscribe();
  }

  resetGame() {
    this.board = Array(9).fill('');
    this.playerTurn = 'X';
    this.isGameOver = false;
    if (this.gameMode) this.message = this.gameMode === 'SOLO' ? "À vous de jouer (X)" : "Joueur X commence";
    this.cdr.detectChanges();
  }
}
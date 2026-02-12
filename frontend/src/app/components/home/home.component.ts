import { Component } from '@angular/core';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [NgFor],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {

  games = [
    { name: 'Snake', description: 'Survis le plus longtemps possible.' },
    { name: 'Tic-Tac-Toe', description: 'Affronte un adversaire.' },
    { name: 'Sudoku', description: 'Teste ta logique.' },
    { name: 'Chifoumi', description: 'Pierre Feuille Ciseaux.' }
  ];

  topPlayers = [
    { name: 'Nova', score: 1580 },
    { name: 'Kai', score: 1505 },
    { name: 'Ray', score: 1322 }
  ];

}

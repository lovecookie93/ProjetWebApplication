import { Component } from '@angular/core';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [NgFor],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent {

  user = {
    username: 'PlayerOne',
    globalScore: 2450,
    gamesPlayed: 120,
    multiplayer: 35
  };

  stats = [
    { name: 'Tic-Tac-Toe', score: 800 },
    { name: 'Snake', score: 950 },
    { name: 'Sudoku', score: 700 }
  ];

}

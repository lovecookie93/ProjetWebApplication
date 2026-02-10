import { Component } from '@angular/core';

@Component({
  selector: 'app-profile',
  imports: [],
  templateUrl: './profile.html',
  styleUrl: './profile.css',
})
export class Profile {

}

export class ProfileComponent {
  user = {
    pseudo: 'Joueur_Alpha',
    avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=Felix' 
  };

  matchHistory = [
    { gameName: 'Snake', result: 'Victoire', date: '09/02/2026' },
    { gameName: 'Sudoku', result: 'Échec', date: '08/02/2026' },
    { gameName: 'Morpion', result: 'Victoire', date: '07/02/2026' }
  ]
}

import { Component } from '@angular/core';

@Component({
  selector: 'app-games',
  standalone: true,
  template: `
    <div class="container">
      <h1>Liste des Jeux</h1>
      <p>Sélectionne un jeu pour commencer.</p>
    </div>
  `
})
export class GamesComponent {}

import { Routes } from '@angular/router';
//import { Home } from './components/home/home';

import { HomeComponent } from './components/home/home';
import { ProfileComponent } from './components/profile/profile';
import { ForumComponent } from './components/forum/forum';
import { GamesComponent } from './components/games/games';

export const routes: Routes = [
  { path: '', component: HomeComponent },             // Page d'accueil par défaut
  { path: 'profile', component: ProfileComponent },   // Page utilisateur
  { path: 'forum', component: ForumComponent },       // Page forum
  { path: 'games', component: GamesComponent },   // Page de sélection des jeux
  { path: '**', redirectTo: '' }                      // Redirection si erreur d'URL
];


import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { ProfileComponent } from './components/profile/profile.component';
import { GamesComponent } from './components/games/games.component';
import { ForumComponent } from './components/forum/forum.component';
import { ForumDetailComponent } from './components/forum-detail/forum-detail.component';
import { SnakeComponent } from './components/games/snake/snake.component';
import { ChifoumiComponent } from './components/games/chifoumi/chifoumi.component';
import { SudokuComponent } from './components/games/sudoku/sudoku.component'; 
import { TicTacToeComponent } from './components/games/tic-tac-toe/tic-tac-toe.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'games', component: GamesComponent },
  { path: 'forum', component: ForumComponent },
  { path: 'forum/:id', component: ForumDetailComponent },
  { path: 'games/snake', component: SnakeComponent }, 
  { path: 'games/chifoumi', component: ChifoumiComponent },
  { path: 'games/sudoku', component: SudokuComponent },
  { path: 'games/tictactoe', component: TicTacToeComponent },
  { path: '**', redirectTo: '' } // TOUJOURS EN DERNIER
];
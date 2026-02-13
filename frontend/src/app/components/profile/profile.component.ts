import { Component, OnInit } from '@angular/core';
import { NgIf, NgFor } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PlayerService } from '../../core/services/player.service';
import { Player } from '../../shared/models/player.model';

@Component({
  standalone: true,
  selector: 'app-profile',
  imports: [NgIf, NgFor, FormsModule],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  players: Player[] = [];
  selectedPlayer?: Player;

  // Form fields
  newUsername: string = '';
  newEmail: string = '';

  constructor(private playerService: PlayerService) {}

  ngOnInit(): void {
    this.loadPlayers();
  }

  loadPlayers(): void {
    this.playerService.getAllPlayers().subscribe({
      next: (data: Player[]) => {
        this.players = data;
      },
      error: (err: any) => {
        console.error(err);
      }
    });
  }

  selectPlayer(player: Player): void {
    this.selectedPlayer = player;
  }

  addPlayer(): void {
    if (!this.newUsername || !this.newEmail) return;

    const newPlayer = {
      username: this.newUsername,
      email: this.newEmail
    };

    this.playerService.createPlayer(newPlayer).subscribe({
      next: () => {
        this.newUsername = '';
        this.newEmail = '';
        this.loadPlayers(); // refresh list
      },
      error: (err: any) => {
        console.error('Erreur création joueur:', err);
      }
    });
  }
}

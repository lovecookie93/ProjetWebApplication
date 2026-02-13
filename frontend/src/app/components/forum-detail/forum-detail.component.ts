import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';

@Component({
  selector: 'app-forum-detail',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './forum-detail.component.html',
  styles: [`
    .chat-container { background: rgba(13, 17, 23, 0.95); min-height: 80vh; padding: 20px; border-radius: 15px; border: 1px solid #30363d; }
    .msg { margin-bottom: 15px; padding: 15px; border-radius: 10px; position: relative; }
    .msg-author { color: #00d2ff; font-weight: bold; font-size: 0.9em; margin-bottom: 5px; display: block; }
    .msg-content { color: #e6edf3; line-height: 1.5; }
    .msg-date { font-size: 0.7em; color: #888; float: right; }
    .input-zone { margin-top: 20px; display: flex; gap: 10px; }
    input { flex: 1; padding: 15px; background: #0d1117; border: 1px solid #30363d; color: white; border-radius: 5px; }
    button { background: #00d2ff; color: black; border: none; padding: 0 20px; font-weight: bold; cursor: pointer; border-radius: 5px; }
  `]
})
export class ForumDetailComponent implements OnInit {
  private route = inject(ActivatedRoute);

  forumId: string | null = '';
  topicName: string = 'Chargement...';
  messages: any[] = [];

  private allMessages: { [key: string]: any[] } = {
    '1': [
      { author: 'Nova', text: 'Bienvenue sur le forum général !', date: '10:00' },
      { author: 'Admin', text: 'Respectez les règles svp.', date: '10:05' }
    ],
    '2': [
      { author: 'Kai', text: 'Mon record au Snake est de 500 !', date: '14:20' },
      { author: 'Ray', text: 'Je n\'arrive pas à dépasser 100...', date: '15:10' }
    ],
    '3': [
      { author: 'TechSupport', text: 'Un problème avec AG-Grid ?', date: '09:00' }
    ]
  };

  ngOnInit() {
    this.forumId = this.route.snapshot.paramMap.get('id');
    
    if (this.forumId) {
      this.messages = this.allMessages[this.forumId] || [
        { author: 'Système', text: 'Aucun message dans ce forum pour le moment.', date: 'Maintenant' }
      ];

      if (this.forumId === '1') this.topicName = 'Discussion Générale';
      else if (this.forumId === '2') this.topicName = 'Tournois Snake';
      else if (this.forumId === '3') this.topicName = 'Support Technique';
      else this.topicName = 'Discussion #' + this.forumId;
    }
  }
} 
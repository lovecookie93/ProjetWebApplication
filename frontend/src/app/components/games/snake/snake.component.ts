import { Component, ElementRef, HostListener, OnInit, ViewChild, inject } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-snake',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './snake.component.html', // Le ./ signifie "dans le même dossier"
  styleUrl: './snake.component.scss'
})
export class SnakeComponent implements OnInit {
  @ViewChild('gameCanvas', { static: true }) canvas!: ElementRef<HTMLCanvasElement>;
  private ctx!: CanvasRenderingContext2D;

  // État du jeu
  snake = [{ x: 10, y: 10 }];
  food = { x: 5, y: 5 };
  direction = { x: 1, y: 0 };
  score = 0;
  gameOver = false;
  
  // Configuration
  gridSize = 20;
  tileCount = 20;

  ngOnInit() {
    this.ctx = this.canvas.nativeElement.getContext('2d')!;
    this.resetGame();
    // Lance la boucle de rendu (10 images par seconde pour le côté rétro)
    setInterval(() => this.update(), 100);
  }

  // Écoute les touches du clavier
  @HostListener('window:keydown', ['$event'])
  onKeydown(event: KeyboardEvent) {
    if (this.gameOver && event.key === 'Enter') this.resetGame();
    
    switch (event.key) {
      case 'ArrowUp': if (this.direction.y === 0) this.direction = { x: 0, y: -1 }; break;
      case 'ArrowDown': if (this.direction.y === 0) this.direction = { x: 0, y: 1 }; break;
      case 'ArrowLeft': if (this.direction.x === 0) this.direction = { x: -1, y: 0 }; break;
      case 'ArrowRight': if (this.direction.x === 0) this.direction = { x: 1, y: 0 }; break;
    }
  }

  update() {
    if (this.gameOver) return;

    // Calcul de la nouvelle position de la tête
    const head = { 
      x: this.snake[0].x + this.direction.x, 
      y: this.snake[0].y + this.direction.y 
    };

    // Collision murs
    if (head.x < 0 || head.x >= this.tileCount || head.y < 0 || head.y >= this.tileCount) {
      return this.endGame();
    }

    // Collision corps
    if (this.snake.some(segment => segment.x === head.x && segment.y === head.y)) {
      return this.endGame();
    }

    this.snake.unshift(head);

    // Manger la pomme
    if (head.x === this.food.x && head.y === this.food.y) {
      this.score += 10;
      this.generateFood();
    } else {
      this.snake.pop();
    }

    this.draw();
  }

  draw() {
    // Fond du canvas
    this.ctx.fillStyle = '#0f172a';
    this.ctx.fillRect(0, 0, 400, 400);

    // Dessin du serpent (Dégradé Cyan)
    this.ctx.fillStyle = '#00d2ff';
    this.snake.forEach(seg => this.ctx.fillRect(seg.x * 20, seg.y * 20, 19, 19));

    // Dessin de la nourriture (Rouge néon)
    this.ctx.fillStyle = '#ff4444';
    this.ctx.shadowBlur = 10;
    this.ctx.shadowColor = '#ff4444';
    this.ctx.fillRect(this.food.x * 20, this.food.y * 20, 19, 19);
    this.ctx.shadowBlur = 0;
  }

  endGame() {
    this.gameOver = true;
    console.log("Fin de partie ! Score final :", this.score);
    // On appellera le service backend ici pour save()
  }

  resetGame() {
    this.snake = [{ x: 10, y: 10 }];
    this.direction = { x: 1, y: 0 };
    this.score = 0;
    this.gameOver = false;
    this.generateFood();
  }

  generateFood() {
    this.food = {
      x: Math.floor(Math.random() * this.tileCount),
      y: Math.floor(Math.random() * this.tileCount)
    };
  }
}
import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AgGridAngular } from 'ag-grid-angular';
import { ColDef, ModuleRegistry, ClientSideRowModelModule } from 'ag-grid-community';

// Activation du module de recherche
ModuleRegistry.registerModules([ClientSideRowModelModule]);

@Component({
  selector: 'app-forum',
  standalone: true,
  imports: [CommonModule, AgGridAngular],
  templateUrl: './forum.component.html',
  styleUrl: './forum.component.scss'
})
export class ForumComponent {
  private router = inject(Router);
  
  // Variable simple pour la recherche
  searchText: string = '';

  colDefs: ColDef[] = [
    { 
      field: 'name', headerName: 'SUJET', flex: 3, 
      cellRenderer: (params: any) => `
          <div style="display: flex; align-items: center; gap: 15px;">
            <span style="font-size: 1.2em;">📂</span>
            <b style="color: #00d2ff; font-size: 1.1em; text-shadow: 0 0 5px rgba(0, 210, 255, 0.3);">${params.value}</b>
          </div>`
    },
    { 
      field: 'creator', headerName: 'AUTEUR', width: 150,
      // Jaune Or (#FFD700) forcé pour la visibilité
      cellRenderer: (params: any) => `
        <span style="color: #FFD700 !important; font-weight: bold; text-shadow: 0 0 8px rgba(255, 215, 0, 0.5);">
          @${params.value}
        </span>`
    },
    { field: 'lastUpdate', headerName: 'DERNIER MSG', width: 150, cellStyle: { color: '#cccccc' } },
    { 
      headerName: 'ACTION', width: 120, 
      cellRenderer: () => `<button style="background: rgba(0, 210, 255, 0.15); border: 1px solid #00d2ff; color: #00d2ff; border-radius: 4px; padding: 4px 12px; cursor: pointer; font-weight: bold;">VOIR</button>` 
    }
  ];

  forumList = [
    { id: 1, name: 'Discussion Générale', creator: 'Nova', lastUpdate: '16:24:54' },
    { id: 2, name: 'Tournois Snake', creator: 'Kai', lastUpdate: '14:10:22' },
    { id: 3, name: 'Support Technique', creator: 'Ray', lastUpdate: 'Hier' },
    { id: 4, name: 'Astuces Tic-Tac-Toe', creator: 'Nova', lastUpdate: '06/06/2022' },
    { id: 5, name: 'Mises à jour Vortex', creator: 'Admin', lastUpdate: '07/06/2020' }
  ];

  // Mise à jour de la variable de recherche
  onSearch(event: any) {
    this.searchText = event.target.value;
  }

  onRowClicked(event: any) {
    this.router.navigate(['/forum', event.data.id]);
  }
}

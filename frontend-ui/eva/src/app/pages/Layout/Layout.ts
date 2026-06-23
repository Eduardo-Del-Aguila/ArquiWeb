import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Sidebar } from '../../shared/components/sidebar/sidebar';
import { RouterOutlet } from '@angular/router';
import { JamendoService } from '../../core/services/jamendo.service';
import { CommonModule } from '@angular/common'; // Asegúrate de tener esto

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [Sidebar, RouterOutlet , CommonModule],
  templateUrl: './Layout.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Layout implements OnInit {
  
  // Aquí guardaremos las canciones
pistasMusicales: any[] = [];
  constructor(
    private jamendoService: JamendoService,
    private cdr: ChangeDetectorRef // <-- Súper importante por usar OnPush
  ) {}

  ngOnInit(): void {
    this.cargarMusica();
  }

  cargarMusica() {
    this.jamendoService.getMusicaRelajante().subscribe({
      next: (respuesta) => {
        console.log('Música de Jamendo:', respuesta);
        this.pistasMusicales = respuesta.results;
        console.log('Primera pista:', this.pistasMusicales[0]); // <--- Agrega esto
        // Como usamos OnPush, le avisamos a Angular que redibuje la pantalla con las canciones
        this.cdr.markForCheck(); 
      },
      error: (error) => {
        console.error('Error al cargar la música:', error);
      }
    });
  }
}
import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Sidebar } from '../../shared/components/sidebar/sidebar';
import { RouterOutlet } from '@angular/router';
import { JamendoService } from '../../core/services/jamendo.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [Sidebar, RouterOutlet , CommonModule],
  templateUrl: './Layout.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Layout implements OnInit {
  
  pistasMusicales: any[] = [];
  indiceActual: number = 0;

  constructor(
    private jamendoService: JamendoService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.cargarMusica();
  }

  cargarMusica() {
    this.jamendoService.getMusicaRelajante().subscribe({
      next: (respuesta) => {
        if (respuesta.results && respuesta.results.length > 0) {
          this.pistasMusicales = respuesta.results;
          this.cdr.markForCheck(); // Le avisamos a la vista que hay datos
        }
      },
      error: (error) => console.error('Error al cargar la música:', error)
    });
  }

  // Recibimos el elemento HTML del reproductor para manipularlo
  siguienteCancion(reproductor: HTMLAudioElement) {
    if (this.indiceActual < this.pistasMusicales.length - 1) {
      this.indiceActual++;
    } else {
      this.indiceActual = 0;
    }
    this.forzarReproduccion(reproductor);
  }

  cancionAnterior(reproductor: HTMLAudioElement) {
    if (this.indiceActual > 0) {
      this.indiceActual--;
    } else {
      this.indiceActual = this.pistasMusicales.length - 1;
    }
    this.forzarReproduccion(reproductor);
  }

  forzarReproduccion(reproductor: HTMLAudioElement) {
    this.cdr.markForCheck(); // Actualiza el HTML con la nueva URL
    
    // Le damos un respiro minúsculo a Angular para que cambie el [src] y luego forzamos el play
    setTimeout(() => {
      reproductor.load(); // Obliga al navegador a descargar el nuevo archivo
      reproductor.play(); // Le da play automáticamente
    }, 50);
  }
}
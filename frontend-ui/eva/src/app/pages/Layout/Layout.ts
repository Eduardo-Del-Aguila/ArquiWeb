import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit, signal } from '@angular/core';
import { Sidebar } from '../../shared/components/sidebar/sidebar';
import { RouterOutlet } from '@angular/router';
import { JamendoService } from '../../core/services/jamendo.service';
import { CommonModule } from '@angular/common';
import { MatIcon } from '@angular/material/icon';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [Sidebar, RouterOutlet , CommonModule, MatIcon],
  templateUrl: './Layout.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Layout implements OnInit {

  pistasMusicales: any[] = [];
  indiceActual: number = 0;
  playerAbierto = signal<boolean>(false);

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
          this.cdr.markForCheck();
        }
      },
      error: (error) => console.error('Error al cargar la música:', error)
    });
  }

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
    this.cdr.markForCheck();

    setTimeout(() => {
      reproductor.load();
      reproductor.play();
    }, 50);
  }
}

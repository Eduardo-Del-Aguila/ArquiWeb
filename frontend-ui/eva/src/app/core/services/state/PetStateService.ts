import { Injectable, signal } from '@angular/core';
import { EvaPetShow } from '../../interfaces/pet.interface';

@Injectable({
  providedIn: 'root',
})
export class PetStateService {
  recargar = signal<boolean>(false);

  notificarRecarga() {
    this.recargar.set(!this.recargar());
  }
}

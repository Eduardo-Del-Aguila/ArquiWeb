import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class MedicationStateService {

  recargar = signal<boolean>(false);

  notificarRecarga() {
    this.recargar.set(!this.recargar());
  }

}
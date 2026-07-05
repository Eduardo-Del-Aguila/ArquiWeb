import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class MedicalHistoryStateService {
  recargar = signal<boolean>(false);

  notificarRecarga() {
    this.recargar.set(!this.recargar());
  }
}

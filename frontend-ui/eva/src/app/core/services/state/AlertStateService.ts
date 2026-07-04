import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AlertStateService {
  recargar = signal<boolean>(false);

  notificarRecarga() {
    this.recargar.set(!this.recargar());
  }
}

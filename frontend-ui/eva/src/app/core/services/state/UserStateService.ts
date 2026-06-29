import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class UserStateService {
  recargar = signal<boolean>(false);

  notificarRecarga() {
    this.recargar.set(!this.recargar());
  }
}

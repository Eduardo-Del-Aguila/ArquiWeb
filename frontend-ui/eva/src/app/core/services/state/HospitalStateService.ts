import { Injectable, signal } from "@angular/core";

@Injectable({ providedIn: 'root' })
export class HospitalStateService {
  recargar = signal<boolean>(false);

  notificarRecarga() {
    this.recargar.set(!this.recargar());
  }
}

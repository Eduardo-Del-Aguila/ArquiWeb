import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn:'root'
})
export class PrescriptionMedicationStateService{

  private refresh = signal(0);

  recargar(){
    return this.refresh();
  }

  notificarRecarga(){
    this.refresh.update(v=>v+1);
  }

}
import { Injectable, signal } from "@angular/core";

@Injectable({
  providedIn:'root'
})

export class PrescriptionStateService{

  private actualizar=signal(0);

  recargar(){
      return this.actualizar();
  }

  notificarRecarga(){
      this.actualizar.update(x=>x+1);
  }

}
import { ChangeDetectionStrategy, Component, effect, inject, OnInit, signal } from '@angular/core';
import { EvaPetService } from '../../core/services/evaService';
import { EvaPetShow } from '../../core/interfaces/pet.interface';
import { TableAction, TableColumn } from '../../core/interfaces/table';
import { GenericTable } from "../../core/components/generic-table/generic-table";
import { PetForm } from "./PetForm/PetForm";
import { PetStateService } from '../../core/services/state/PetStateService';
import { MatDialog } from '@angular/material/dialog';
import { PetEditDialog } from './PetEditDialog/PetEditDialog';
import { ConfirmDialog } from '../../core/components/ConfirmDialog/ConfirmDialog';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { StatusPetColorPipe } from '../../shared/pipes/status-pet-color-pipe';
import { LoginService } from '../../core/services/auth/Login.service';

@Component({
  selector: 'app-pet-page',
  imports: [ PetForm, MatCardModule, MatButtonModule, MatIconModule, MatTooltipModule, StatusPetColorPipe],
  templateUrl: './pet-page.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PetPage implements OnInit {
  //
  private petService = inject(EvaPetService);
  private stateService = inject(PetStateService);
  private dialog = inject(MatDialog);
  private loginService = inject(LoginService);

  role = signal<string>('');
  mail = signal<string>('');

  pets = signal<EvaPetShow[]>([]);


  columns: TableColumn[] = []

  actions: TableAction<EvaPetShow>[] = [
    { label: 'Editar', icon: 'edit', action: (row) => this.editar(row) },
    { label: 'Eliminar', icon: 'delete', action: (row) => this.eliminar(row) }
  ];

  ngOnInit() {
    if (this.loginService.verificar()) {
      this.role.set(this.loginService.showRole() ?? '');
      this.mail.set(this.loginService.showMail() ?? '');
    }
    this.cargar();
   }

  private recargarEffect = effect(() => {
    this.stateService.recargar();
    this.cargar();
  });
  //TOOD: no olvides dessuscribirte a todas las suscrpciones que hagas
  cargar() {
    if(this.role() === 'ADMIN'){

    this.petService.listar().subscribe({
      next: data => {
        console.log('DATA DE PETS',data);
        if (data.length > 0) {
          this.columns = Object.keys(data[0]).map(key => ({
            key,
            label: key
          }));
        }
        this.pets.set(data);
      },
      error: err => console.error(err)
    });
    } else {
      this.petService.listByUserId('rodriguez%40gmail.com').subscribe({
      next: data => {
        console.log('DATA DE PETS',data);
        if (data.length > 0) {
          this.columns = Object.keys(data[0]).map(key => ({
            key,
            label: key
          }));
        }
        this.pets.set(data);
      },
      error: err => console.error(err)
    });

    }



  }

  editar(row: EvaPetShow) {
    const ref = this.dialog.open(PetEditDialog, {
      width: '400px',
      data: row
    });

    ref.afterClosed().subscribe(actualizado => {
      if (actualizado) this.stateService.notificarRecarga();
    });
  }


  eliminar(row: EvaPetShow) {
    const ref = this.dialog.open(ConfirmDialog, {
      width: '350px',
      data: { titulo: `¿Deseas eliminar el rol ${row.name}?`, mensaje: 'Esta acción no se puede deshacer.' }

    });

    ref.afterClosed().subscribe(confirmado => {
      if (confirmado) {
        this.petService.eliminar(row.idEva).subscribe({
          next: () => this.stateService.notificarRecarga(),
          error: err => console.error(err)
        });
      }
    });
  }
}

import { ChangeDetectionStrategy, Component, effect, inject, OnInit, signal } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { RolService } from '../../core/services/rolService';
import { RolShowDTO } from '../../core/interfaces/rol';
import { TableAction, TableColumn } from '../../core/interfaces/table';
import { ConfirmDialog } from '../../core/components/ConfirmDialog/ConfirmDialog';
import { RolEditDialog } from './RolEditDialog/RolEditDialog';
import { RolStateService } from '../../core/services/state/RolStateService';
import { RolForm } from "./RolForm/RolForm";
import { GenericTable } from "../../core/components/generic-table/generic-table";

@Component({
  selector: 'app-rol-page',
  imports: [RolForm, GenericTable],
  templateUrl: './RolPage.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class RolPage implements OnInit {
  private rolService = inject(RolService);
  private dialog = inject(MatDialog);
  private stateService = inject(RolStateService);

  roles = signal<RolShowDTO[]>([]);
  columns: TableColumn[] = [];

  actions: TableAction<RolShowDTO>[] = [
    { label: 'Editar', icon: 'edit', action: (row) => this.editar(row) },
    { label: 'Eliminar', icon: 'delete', action: (row) => this.eliminar(row) }
  ];

  ngOnInit() { this.cargar(); }

  private recargarEffect = effect(() => {
    this.stateService.recargar();
    this.cargar();
  });


  cargar() {
    this.rolService.listar().subscribe({
      next: data => {
        if (data.length > 0) {

          this.columns = Object.keys(data[0]).map(key => ({ key, label: key }));
        }
        this.roles.set(data);
      },
      error: err => console.error(err)
    });
  }

  editar(row: RolShowDTO) {
    const ref = this.dialog.open(RolEditDialog, {
      width: '400px',
      data: row
    });
    ref.afterClosed().subscribe(actualizado => {
      if (actualizado) this.stateService.notificarRecarga();
    });
  }

  eliminar(row: RolShowDTO) {
    const ref = this.dialog.open(ConfirmDialog, {
      width: '350px',
      data: { titulo: `¿Deseas eliminar el rol ${row.nameRol}?`, mensaje: 'Esta acción no se puede deshacer.' }
    });
    ref.afterClosed().subscribe(confirmado => {
      if (confirmado) {
        this.rolService.eliminar(row.idRol).subscribe({
          next: () => this.stateService.notificarRecarga(),
          error: err => console.error(err)
        });
      }
    });
  }
}

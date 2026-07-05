import { ChangeDetectionStrategy, Component, computed, effect, inject, signal } from '@angular/core';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { HospitalService } from '../../core/services/hospitalService';
import { HospitalStateService } from '../../core/services/state/HospitalStateService';
import { HospitalShowDTO } from '../../core/interfaces/hospital.interface';
import { TableAction, TableColumn } from '../../core/interfaces/table';
import { GenericTable } from '../../core/components/generic-table/generic-table';
import { HospitalForm } from './HospitalForm/HospitalForm';
import { ConfirmDialog } from '../../core/components/ConfirmDialog/ConfirmDialog';
import { HospitalEditDialog } from './HospitalEditDialog/HospitalEditDialog';

@Component({
  selector: 'app-hospitales-page',
 imports: [HospitalForm, GenericTable, MatDialogModule],
  templateUrl: './hospitales-page.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HospitalesPage {
  private hospitalService = inject(HospitalService);
  private dialog = inject(MatDialog);
  private stateService = inject(HospitalStateService);

  hospitals = signal<HospitalShowDTO[]>([]);
  columns: TableColumn[] = [];

  actions: TableAction<HospitalShowDTO>[] = [
    { label: 'Editar', icon: 'edit', action: (row) => this.editar(row) },
    { label: 'Eliminar', icon: 'delete', action: (row) => this.eliminar(row) }
  ];

  private recargarEffect = effect(() => {
    this.stateService.recargar();
    this.cargar();
  });

  cargar() {
    this.hospitalService.listar().subscribe({
      next: data => {
        console.log('Hospitales Data: ', data);
        if (data.length > 0) {
          this.columns = Object.keys(data[0]).map(key => ({ key, label: key }));
        }
        this.hospitals.set(data);
      },
      error: err => console.error(err)
    });
  }

  editar(row: HospitalShowDTO) {
    console.log('editar', row);
    const ref = this.dialog.open(HospitalEditDialog, {
      width: '400px',
      data: row
    });
    ref.afterClosed().subscribe(actualizado => {
      if (actualizado) this.stateService.notificarRecarga();
    });
  }

  eliminar(row: HospitalShowDTO) {
    const ref = this.dialog.open(ConfirmDialog, {
      width: '350px',
      data: {
        titulo: `¿Deseas eliminar ${row.name}?`,
        mensaje: 'Esta acción no se puede deshacer.'
      }
    });
    ref.afterClosed().subscribe(confirmado => {
      if (confirmado) {
        this.hospitalService.eliminar(row.idHospital).subscribe({
          next: () => this.stateService.notificarRecarga(),
          error: err => console.error(err)
        });
      }
    });
    console.log('editar', row);
  }
}

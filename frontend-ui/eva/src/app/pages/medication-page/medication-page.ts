import { ChangeDetectionStrategy, Component, effect, inject, OnInit, signal } from '@angular/core';
import { MedicationShow } from '../../core/interfaces/medication.interface';
import { TableAction, TableColumn } from '../../core/interfaces/table';
import { GenericTable } from "../../core/components/generic-table/generic-table";
import { Medication } from '../../core/services/medication';
import { MedicationForm } from './medication-form/medication-form';
import { MedicationStateService } from '../../core/services/state/MedicationStateService';
import { ConfirmDialog } from '../../core/components/ConfirmDialog/ConfirmDialog';
import { MatDialog } from '@angular/material/dialog';
import { MedicationEditDialog } from './medication-edit-dialog/medication-edit-dialog';
@Component({
  selector: 'app-medication-page',
  imports: [GenericTable, MedicationForm],
  templateUrl: './medication-page.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class MedicationPage implements OnInit {

  private medicationService = inject(Medication);
  private medicationState = inject(MedicationStateService);
  private dialog = inject(MatDialog);
  medications = signal<MedicationShow[]>([]);

  columns: TableColumn[] = [];

  actions: TableAction<MedicationShow>[] = [
    {
    label: 'Editar',
    icon: 'edit',
    action: (row) => this.editar(row)
  },
  {
    label: 'Eliminar',
    icon: 'delete',
    action: (row) => this.eliminar(row)
  }
  ];


  ngOnInit() {
    this.cargar();
  }

  private recargarEffect = effect(() => {

  this.medicationState.recargar();

  this.cargar();

});

  cargar() {
    this.medicationService.listar().subscribe({
      next: data => {

        if (data.length > 0) {
          this.columns = Object.keys(data[0]).map(key => ({
            key,
            label: key
          }));
        }

        this.medications.set(data);
      },

      error: err => console.error(err)
    });
  }

  eliminar(row: MedicationShow) {
    console.log(row);

  const ref = this.dialog.open(ConfirmDialog, {
    width: '350px',
    data: {
      titulo: `¿Deseas eliminar el medicamento ${row.name}?`,
      mensaje: 'Esta acción no se puede deshacer.'
    }
  });

  ref.afterClosed().subscribe(confirmado => {

    if (confirmado) {

      this.medicationService.eliminar(row.idMedication).subscribe({

        next: () => {
          this.medicationState.notificarRecarga();
        },

        error: err => console.error(err)

      });

    }

  });

}

editar(row: MedicationShow) {

  const ref = this.dialog.open(MedicationEditDialog, {

    width: '400px',

    data: row

  });

  ref.afterClosed().subscribe(actualizado => {

    if (actualizado) {

      this.medicationState.notificarRecarga();

    }

  });

}

}
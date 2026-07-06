import { ChangeDetectionStrategy, Component, effect, inject, OnInit, signal } from '@angular/core';

import { GenericTable } from '../../core/components/generic-table/generic-table';
import { TableAction, TableColumn } from '../../core/interfaces/table';

import { PrescriptionMedicationShow } from '../../core/interfaces/prescription-medication.interface';
import { PrescriptionMedication } from '../../core/services/prescription-medication';
import { PrescriptionMedicationStateService } from '../../core/services/state/PrescriptionMedicationStateService';

import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialog } from '../../core/components/ConfirmDialog/ConfirmDialog';
import { PrescriptionMedicationsEditDialog } from './prescription-medications-edit-dialog/prescription-medications-edit-dialog';
import { PrescriptionMedicationsForm } from './prescription-medications-form/prescription-medications-form';

@Component({
  selector: 'app-prescription-medication-page',
  imports: [GenericTable, PrescriptionMedicationsForm],
  templateUrl: './prescription-medications-page.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PrescriptionMedicationsPage implements OnInit {
  private service = inject(PrescriptionMedication);

  private stateService = inject(PrescriptionMedicationStateService);

  private dialog = inject(MatDialog);

  prescriptionMedications = signal<PrescriptionMedicationShow[]>([]);

  columns: TableColumn[] = [];

  actions: TableAction<PrescriptionMedicationShow>[] = [
    {
      label: 'Editar',
      icon: 'edit',
      action: (row) => this.editar(row),
    },

    {
      label: 'Eliminar',
      icon: 'delete',
      action: (row) => this.eliminar(row),
    },
  ];

  ngOnInit() {
    this.cargar();
  }

  private recargarEffect = effect(() => {
    this.stateService.recargar();

    this.cargar();
  });

  cargar() {
    this.service.listar().subscribe({
      next: (data) => {
        if (data.length > 0) {
          this.columns = Object.keys(data[0]).map((key) => ({
            key,

            label: key,
          }));
        }

        this.prescriptionMedications.set(data);
      },

      error: (err) => console.error(err),
    });
  }

  editar(row: PrescriptionMedicationShow) {
    const ref = this.dialog.open(PrescriptionMedicationsEditDialog, {
      width: '450px',

      data: row,
    });

    ref.afterClosed().subscribe((actualizado) => {
      if (actualizado) {
        this.stateService.notificarRecarga();
      }
    });
  }

  eliminar(row: PrescriptionMedicationShow) {
    const ref = this.dialog.open(ConfirmDialog, {
      width: '350px',

      data: {
        titulo: `¿Deseas eliminar este registro?`,

        mensaje: 'Esta acción no se puede deshacer.',
      },
    });

    ref.afterClosed().subscribe((confirmado) => {
      if (confirmado) {
        this.service.eliminar(row.idPrescriptionMedications).subscribe({
          next: () => this.stateService.notificarRecarga(),

          error: (err) => console.error(err),
        });
      }
    });
  }
}

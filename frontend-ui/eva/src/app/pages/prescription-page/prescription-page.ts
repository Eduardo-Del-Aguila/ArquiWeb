import { ChangeDetectionStrategy, Component, effect, inject, OnInit, signal } from '@angular/core';
import { PrescriptionForm } from './prescription-form/prescription-form';
import { GenericTable } from '../../core/components/generic-table/generic-table';
import { Prescription } from '../../core/services/prescription';
import { PrescriptionStateService } from '../../core/services/state/PrescriptionStateService';
import { MatDialog } from '@angular/material/dialog';
import { PrescriptionShow } from '../../core/interfaces/prescription.interface';
import { TableAction } from '../../core/interfaces/table';
import { TableColumn } from '../../core/interfaces/table';
import { ConfirmDialog } from '../../core/components/ConfirmDialog/ConfirmDialog';
import { PrescriptionEditDialog } from './prescription-edit-dialog/prescription-edit-dialog';

@Component({
  selector: 'app-prescription-page',
  imports: [GenericTable, PrescriptionForm],
  templateUrl: './prescription-page.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PrescriptionPage implements OnInit {
  private prescriptionService = inject(Prescription);
  private prescriptionState = inject(PrescriptionStateService);
  private dialog = inject(MatDialog);
  prescriptions = signal<PrescriptionShow[]>([]);

  columns: TableColumn[] = [];

  actions: TableAction<PrescriptionShow>[] = [
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
    this.prescriptionState.recargar();

    this.cargar();
  });

  cargar() {
    this.prescriptionService.listar().subscribe({
      next: (data) => {
        console.log(Object.keys(data[0]));
        if (data.length > 0) {
          this.columns = Object.keys(data[0]).map((key) => ({
            key,
            label: key,
          }));
        }

        this.prescriptions.set(data);
      },

      error: (err) => console.error(err),
    });
  }

  eliminar(row: PrescriptionShow) {
    console.log(row);

    const ref = this.dialog.open(ConfirmDialog, {
      width: '350px',
      data: {
        titulo: `¿Deseas eliminar la prescripción con ID ${row.idPrescription}?`,
        mensaje: 'Esta acción no se puede deshacer.',
      },
    });

    ref.afterClosed().subscribe((confirmado) => {
      if (confirmado) {
        this.prescriptionService.eliminar(row.idPrescription).subscribe({
          next: () => {
            this.prescriptionState.notificarRecarga();
          },

          error: (err) => console.error(err),
        });
      }
    });
  }

  editar(row: PrescriptionShow) {
    const ref = this.dialog.open(PrescriptionEditDialog, {
      width: '400px',

      data: row,
    });

    ref.afterClosed().subscribe((actualizado) => {
      if (actualizado) {
        this.prescriptionState.notificarRecarga();
      }
    });
  }
}

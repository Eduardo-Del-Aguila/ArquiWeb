import { ChangeDetectionStrategy, Component, inject, OnInit, signal } from '@angular/core';

import { GenericTable } from '../../core/components/generic-table/generic-table';

import { MedicationUse } from '../../core/interfaces/medication-use.interface';

import { TableColumn } from '../../core/interfaces/table';
import { PrescriptionMedication } from '../../core/services/prescription-medication';

@Component({
  selector: 'app-medications-use-page',
  imports: [GenericTable],
  templateUrl: './medications-use-page.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class MedicationsUsePage implements OnInit {
  private service = inject(PrescriptionMedication);

  medications = signal<MedicationUse[]>([]);

  columns: TableColumn[] = [];

  ngOnInit() {
    this.cargar();
  }

  cargar() {
    this.service.mostUsedMedications().subscribe({
      next: (data) => {
        if (data.length > 0) {
          this.columns = Object.keys(data[0]).map((key) => ({
            key,

            label: key,
          }));
        }

        this.medications.set(data);
      },

      error: (err) => console.error(err),
    });
  }
}

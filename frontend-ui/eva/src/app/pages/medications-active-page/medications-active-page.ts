import { ChangeDetectionStrategy, Component, OnInit, inject, signal } from '@angular/core';

import { GenericTable } from '../../core/components/generic-table/generic-table';

import { TableAction, TableColumn } from '../../core/interfaces/table';

import { MedicationShow } from '../../core/interfaces/medication.interface';

import { Medication } from '../../core/services/medication';

@Component({
  selector: 'app-medications-active-page',
  imports: [GenericTable],
  templateUrl: './medications-active-page.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class MedicationsActivePage implements OnInit {
  private medicationService = inject(Medication);

  medications = signal<MedicationShow[]>([]);

  columns: TableColumn[] = [];

  actions: TableAction<MedicationShow>[] = [];

  ngOnInit() {
    this.cargar();
  }

  cargar() {
    this.medicationService.listarActivos().subscribe({
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

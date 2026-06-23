import { ChangeDetectionStrategy, Component, inject, OnInit, signal } from '@angular/core';
import { HospitalService } from '../../core/services/hospitalService';
import { HospitalShow } from '../../core/interfaces/hospital.interface';
import { TableAction, TableColumn } from '../../core/interfaces/table';
import { GenericTable } from "../../core/components/generic-table/generic-table";

@Component({
  selector: 'app-hospital-page',
  imports: [GenericTable],
  templateUrl: './hospital-page.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HospitalPage implements OnInit {

  private hospitalService = inject(HospitalService);

  hospitals = signal<HospitalShow[]>([]);

  columns: TableColumn[] = [];

  actions: TableAction<HospitalShow>[] = [
    { label: 'Editar', icon: 'edit', action: (row) => this.editar(row) },
    { label: 'Eliminar', icon: 'delete', action: (row) => this.eliminar(row) }
  ];

  ngOnInit() {
    this.cargar();
  }

  cargar() {
  console.log("ENTRÓ A CARGAR");

  this.hospitalService.listar().subscribe({
    next: data => {
      console.log("DATOS RECIBIDOS:", data);

      if (data.length > 0) {
        this.columns = Object.keys(data[0]).map(key => ({
          key,
          label: key
        }));
      }

      this.hospitals.set(data);
    },
    error: err => {
      console.error("ERROR:", err);
    }
  });
}

  editar(row: HospitalShow) {
    console.log('editar', row);
  }

  eliminar(row: HospitalShow) {
    console.log('eliminar', row);
  }
}
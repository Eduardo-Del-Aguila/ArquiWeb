import { ChangeDetectionStrategy, Component, inject, OnInit, signal } from '@angular/core';
import { Symptom, SymptomInsertDTO, Severity } from '../../core/interfaces/symptom.interface';
import { SymptomService } from '../../core/services/symptomService';
import { TableAction, TableColumn } from '../../core/interfaces/table';
import { GenericTable } from '../../core/components/generic-table/generic-table';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-symptom-page',
  imports: [GenericTable, FormsModule, MatFormFieldModule, MatInputModule, MatSelectModule, MatButtonModule],
  templateUrl: './symptom-page.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SymptomPage implements OnInit {
  private symptomService = inject(SymptomService);

  symptoms = signal<Symptom[]>([]);
  columns: TableColumn[] = [];
  severityOptions = Object.values(Severity);

  // Formulario de inserción
  nuevoSintoma: SymptomInsertDTO = { name: '', severity: Severity.LOW };

  actions: TableAction<Symptom>[] = [
    { label: 'Eliminar', icon: 'delete', action: (row) => this.eliminar(row) }
  ];

  ngOnInit() { this.cargar(); }

  cargar() {
    this.symptomService.listarSintomas().subscribe({
      next: data => {
        if (data.length > 0) {
          this.columns = Object.keys(data[0]).map(key => ({ key, label: key }));
        }
        this.symptoms.set(data);
      },
      error: err => console.error('Error al listar síntomas:', err)
    });
  }

  insertar() {
    if (!this.nuevoSintoma.name || !this.nuevoSintoma.severity) return;

    this.symptomService.insertarSintoma(this.nuevoSintoma).subscribe({
      next: () => {
        this.nuevoSintoma = { name: '', severity: Severity.LOW };
        this.cargar(); // recarga la tabla
      },
      error: err => console.error('Error al insertar síntoma:', err)
    });
  }

eliminar(row: any) {
  console.log('row a eliminar:', JSON.stringify(row));
  this.symptomService.eliminarSintoma(row.idSymptom).subscribe({
    next: () => this.cargar(),
    error: err => console.error('Error al eliminar síntoma:', err)
  });
}
}
import { ChangeDetectionStrategy, Component, inject, OnInit, signal } from '@angular/core';
import { EvaPetService } from '../../core/services/evaService';
import { EvaPetShow } from '../../core/interfaces/pet.interface';
import { TableAction, TableColumn } from '../../core/interfaces/table';
import { GenericTable } from "../../core/components/generic-table/generic-table";

@Component({
  selector: 'app-pet-page',
  imports: [GenericTable],
  templateUrl: './pet-page.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PetPage implements OnInit {
  private petService = inject(EvaPetService);

  pets = signal<EvaPetShow[]>([]);


  columns: TableColumn[] = []

  actions: TableAction<EvaPetShow>[] = [
    { label: 'Editar', icon: 'edit', action: (row) => this.editar(row) },
    { label: 'Eliminar', icon: 'delete', action: (row) => this.eliminar(row) }
  ];

  ngOnInit() { this.cargar(); }

  cargar() {
    this.petService.listar().subscribe({
      next: data => {
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

  editar(row: EvaPetShow) { console.log('editar', row); }
  eliminar(row: EvaPetShow) { console.log('eliminar', row); }
}

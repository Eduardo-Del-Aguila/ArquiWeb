import { ChangeDetectionStrategy, Component, input } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { TableAction, TableColumn } from '../../interfaces/table';

@Component({
  selector: 'app-generic-table',
  imports: [MatTableModule, MatButtonModule, MatIconModule, MatPaginatorModule],
  templateUrl: './generic-table.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
// Si o si la clase tiene que llevar el generic para poder ser capaz de usarse dentro de la clase
export class GenericTable<T> {
  data = input<T[]>([]);
  columns = input<TableColumn[]>([]);
  actions = input<TableAction<T>[]>([]);

  get displayedColumns(): string[] {
    return [...this.columns().map(c => c.key), 'acciones'];
  }
}

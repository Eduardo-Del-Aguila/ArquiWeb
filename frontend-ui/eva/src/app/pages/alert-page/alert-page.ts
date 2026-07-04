import { ChangeDetectionStrategy, Component, effect, inject, OnInit, signal } from '@angular/core';
import { AlertService } from '../../core/services/alertService'; // Ajusta la ruta a tu servicio
import { AlertsInsertDTO } from '../../core/interfaces/alert.interface'; // Ajusta la ruta a tu interface
import { TableAction, TableColumn } from '../../core/interfaces/table';
import { GenericTable } from "../../core/components/generic-table/generic-table";
import { AlertForm } from "./AlertForm/AlertForm";
import { AlertStateService } from '../../core/services/state/AlertStateService'; // Necesitarás crear este servicio
import { MatDialog } from '@angular/material/dialog';
import { AlertEditDialog } from './AlertEditDialog/AlertEditDialog'; // Asegúrate de tener un componente para editar alertas
import { ConfirmDialog } from '../../core/components/ConfirmDialog/ConfirmDialog';

@Component({
  selector: 'app-alert-page',
  imports: [GenericTable, AlertForm],
  templateUrl: './alert-page.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AlertPage implements OnInit {
  
  private alertService = inject(AlertService);
  private stateService = inject(AlertStateService); // Espejo de tu PetStateService
  private dialog = inject(MatDialog);

  // Usamos AlertsInsertDTO porque tu GET /listar en Java devuelve eso
  alerts = signal<AlertsInsertDTO[]>([]);

  columns: TableColumn[] = [];

  actions: TableAction<AlertsInsertDTO>[] = [
    { label: 'Editar', icon: 'edit', action: (row) => this.editar(row) },
    { label: 'Eliminar', icon: 'delete', action: (row) => this.eliminar(row) }
  ];

  ngOnInit() { this.cargar(); }

  private recargarEffect = effect(() => {
    this.stateService.recargar();
    this.cargar();
  });

  cargar() {
    this.alertService.listar().subscribe({
      next: data => {
        if (data.length > 0) {
          // Genera las columnas dinámicamente basado en las llaves del DTO
          this.columns = Object.keys(data[0]).map(key => ({
            key,
            label: key
          }));
        }
        this.alerts.set(data);
      },
      error: err => console.error('Error al cargar alertas:', err)
    });
  }

  editar(row: AlertsInsertDTO) {
    const ref = this.dialog.open(AlertEditDialog, {
      width: '400px',
      data: row
    });

    ref.afterClosed().subscribe(actualizado => {
      if (actualizado) this.stateService.notificarRecarga();
    });
  }

  eliminar(row: AlertsInsertDTO) {
    const ref = this.dialog.open(ConfirmDialog, {
      width: '350px',
      // row.idAlert asume que ese es el nombre de la llave primaria en tu DTO
      data: { titulo: `¿Deseas eliminar esta alerta?`, mensaje: 'Esta acción no se puede deshacer.' }
    });

    ref.afterClosed().subscribe(confirmado => {
      if (confirmado && row.idAlert) {
        this.alertService.eliminar(row.idAlert).subscribe({
          next: () => this.stateService.notificarRecarga(),
          error: err => console.error('Error al eliminar:', err)
        });
      }
    });
  }
}
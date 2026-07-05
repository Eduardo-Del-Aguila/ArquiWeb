import { ChangeDetectionStrategy, Component, effect, inject, signal } from '@angular/core';
import { MedicalHistoryService } from '../../core/services/medicalHistoryService';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MedicalHistoryStateService } from '../../core/services/state/MedicalHistoryStateService';
import { MedicalHistoryShowDTO, MedicalStatus } from '../../core/interfaces/medicalHistory';
import { ConfirmDialog } from '../../core/components/ConfirmDialog/ConfirmDialog';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatChipsModule } from '@angular/material/chips';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { DatePipe, SlicePipe } from '@angular/common';
import { MedicalHistoryEditDialog } from './MedicalHistoryEditDialog/MedicalHistoryEditDialog';

@Component({
  selector: 'app-medical-history',
  imports: [SlicePipe, MatExpansionModule, MatChipsModule, MatButtonModule, MatIconModule, MatDialogModule, MatTooltipModule, DatePipe],
  templateUrl: './MedicalHistory-page.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class MedicalHistory {
  private medicalService = inject(MedicalHistoryService);
  private dialog = inject(MatDialog);
  private stateService = inject(MedicalHistoryStateService);

  historiales = signal<MedicalHistoryShowDTO[]>([]);

  private recargarEffect = effect(() => {
    this.stateService.recargar();
    this.cargar();
  });

  cargar() {
    this.medicalService.listarTodos().subscribe({
      next: data => this.historiales.set(data),
      error: err => console.error(err)
    });
  }

  colorStatus(status: MedicalStatus): string {
    const map: Record<MedicalStatus, string> = {
      PENDING: 'warn',
      REVIEWED: 'primary',
      CLOSED: 'accent'
    };
    return map[status];
  }

  editar(historial: MedicalHistoryShowDTO) {
    const ref = this.dialog.open(MedicalHistoryEditDialog, {
      width: '500px',
      data: historial
    });
    ref.afterClosed().subscribe(actualizado => {
      if (actualizado) this.stateService.notificarRecarga();
    });
  }

  eliminar(historial: MedicalHistoryShowDTO) {
    const ref = this.dialog.open(ConfirmDialog, {
      width: '350px',
      data: {
        titulo: `¿Deseas eliminar este historial?`,
        mensaje: 'Esta acción no se puede deshacer.'
      }
    });
    ref.afterClosed().subscribe(confirmado => {
      if (confirmado) {
        this.medicalService.eliminar(historial.id).subscribe({
          next: () => this.stateService.notificarRecarga(),
          error: err => console.error(err)
        });
      }
    });
  }
}

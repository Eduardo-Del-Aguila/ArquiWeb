import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { EvaPetShow } from '../../interfaces/pet.interface';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-confirm-dialog',
  imports: [MatButtonModule, MatDialogModule],
  templateUrl: './ConfirmDialog.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ConfirmDialog {
  private dialogRef = inject(MatDialogRef<ConfirmDialog>);
  private data = inject<{ titulo: string, mensaje: string }>(MAT_DIALOG_DATA);

  titulo = this.data.titulo;
  mensaje = this.data.mensaje;

  confirmar() { this.dialogRef.close(true); }
  cancelar() { this.dialogRef.close(false); }



}

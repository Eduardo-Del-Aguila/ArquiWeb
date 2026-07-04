import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AlertService } from '../../../core/services/alertService'; // Ajusta tu ruta
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { AlertsInsertDTO } from '../../../core/interfaces/alert.interface'; // Ajusta tu ruta
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
  selector: 'app-alert-edit-dialog',
  imports: [ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatSelectModule, MatDialogModule],
  templateUrl: './AlertEditDialog.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AlertEditDialog {
  private fb = inject(FormBuilder);
  private alertService = inject(AlertService);
  private dialogRef = inject(MatDialogRef<AlertEditDialog>);
  private data = inject<AlertsInsertDTO>(MAT_DIALOG_DATA);

  alertTypes: string[] = ['INFO', 'WARNING', 'CRITICAL'];

  form = this.fb.group({
    type: [this.data.type, Validators.required],
    message: [this.data.message, [Validators.required, Validators.maxLength(255)]]
  });

  guardar() {
    if (this.form.invalid) return;
    
    // Combinamos los datos originales con los nuevos valores del formulario
    // para enviar un DTO completo sin perder los IDs de paciente/mascota
    const dto = {
      ...this.data,
      ...this.form.value
    } as AlertsInsertDTO;

    // Asumimos que el identificador en tu interface es 'idAlert'
    this.alertService.actualizar(this.data.idAlert!, dto).subscribe({
      next: () => this.dialogRef.close(true),
      error: err => console.error('Error al actualizar la alerta:', err)
    });
  }

  cancelar() { this.dialogRef.close(false); }
}
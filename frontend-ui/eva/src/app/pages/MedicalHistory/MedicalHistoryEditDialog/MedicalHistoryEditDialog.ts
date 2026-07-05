import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MedicalHistoryService } from '../../../core/services/medicalHistoryService';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MedicalHistoryShowDTO, MedicalStatus } from '../../../core/interfaces/medicalHistory';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-medical-history-edit-dialog',
  imports: [ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatSelectModule, MatDialogModule],
  templateUrl: './MedicalHistoryEditDialog.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class MedicalHistoryEditDialog  {
  private fb = inject(FormBuilder);
  private medicalService = inject(MedicalHistoryService);
  private dialogRef = inject(MatDialogRef<MedicalHistoryEditDialog>);
  private data = inject<MedicalHistoryShowDTO>(MAT_DIALOG_DATA);

  statuses: MedicalStatus[] = ['PENDING', 'REVIEWED', 'CLOSED'];

  form = this.fb.group({
    reason: [this.data.reason, Validators.required],
    treatment: [this.data.treatment],
    observations: [this.data.observations],
    diagnostics: [this.data.diagnostics],
    status: [this.data.status as MedicalStatus, Validators.required]
  });

  guardar() {
    if (this.form.invalid) return;
    const dto = this.form.value as MedicalHistoryShowDTO;
    this.medicalService.actualizar(this.data.id, dto).subscribe({
      next: () => this.dialogRef.close(true),
      error: err => console.error(err)
    });
  }

  cancelar() { this.dialogRef.close(false); }
}

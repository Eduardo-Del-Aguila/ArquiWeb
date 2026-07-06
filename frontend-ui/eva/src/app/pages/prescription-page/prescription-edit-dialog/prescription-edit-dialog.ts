import { Component, inject } from '@angular/core';
import { Prescription } from '../../../core/services/prescription';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { PrescriptionInsert, PrescriptionShow } from '../../../core/interfaces/prescription.interface';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';

@Component({
  selector: 'app-prescription-edit-dialog',
  imports: [ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatDialogModule],
  templateUrl: './prescription-edit-dialog.html',
  styleUrl: './prescription-edit-dialog.css',
})
export class PrescriptionEditDialog {
  private fb = inject(FormBuilder);

  private prescriptionService = inject(Prescription);

  private dialogRef = inject(MatDialogRef<PrescriptionEditDialog>);

  private data = inject<PrescriptionShow>(MAT_DIALOG_DATA);

  form = this.fb.group({
  idUserPatient: [this.data.idUserPatient, [Validators.required, Validators.min(1)]],
  idEva: [this.data.idEva, [Validators.required, Validators.min(1)]],
  diagnosis: [this.data.diagnosis, [Validators.required, Validators.maxLength(100)]],
  date: [this.data.date, Validators.required]
});

  guardar() {
    if (this.form.invalid) return;

    const dto: PrescriptionInsert = {
      idPrescription: this.data.idPrescription,

      idUserPatient: this.form.value.idUserPatient!,

      idEva: this.form.value.idEva!,

      diagnosis: this.form.value.diagnosis!,

      date: this.form.value.date!,
    };

    this.prescriptionService.actualizar(this.data.idPrescription, dto).subscribe({
      next: () => this.dialogRef.close(true),
      error: (err) => console.error(err),
    });
  }

  cancelar() {
    this.dialogRef.close(false);
  }
}


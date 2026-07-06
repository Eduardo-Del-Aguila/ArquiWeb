import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';

import { Medication } from '../../../core/services/medication';

import { MedicationInsert, MedicationShow } from '../../../core/interfaces/medication.interface';

import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
  selector: 'app-medication-edit-dialog',
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatDialogModule,
  ],
  templateUrl: './medication-edit-dialog.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class MedicationEditDialog {
  private fb = inject(FormBuilder);

  private medicationService = inject(Medication);

  private dialogRef = inject(MatDialogRef<MedicationEditDialog>);

  private data = inject<MedicationShow>(MAT_DIALOG_DATA);

  form = this.fb.group({
    name: [this.data.name, [Validators.required]],

    description: [this.data.description, [Validators.required]],

    active: [this.data.active, Validators.required],
  });

  guardar() {
    if (this.form.invalid) return;

    const dto: MedicationInsert = {
      idMedication: this.data.idMedication,

      name: this.form.value.name!,

      description: this.form.value.description!,

      active: this.form.value.active!,
    };

    this.medicationService.actualizar(this.data.idMedication, dto).subscribe({
      next: () => this.dialogRef.close(true),
      error: (err) => console.error(err),
    });
  }

  cancelar() {
    this.dialogRef.close(false);
  }
}

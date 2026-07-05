import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

import {
  MAT_DIALOG_DATA,
  MatDialogModule,
  MatDialogRef
} from '@angular/material/dialog';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

import {
  PrescriptionMedicationInsert,
  PrescriptionMedicationShow
} from '../../../core/interfaces/prescription-medication.interface';

import { PrescriptionMedication } from '../../../core/services/prescription-medication';

@Component({
  selector: 'app-prescription-medications-edit-dialog',
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatDialogModule
  ],
  templateUrl: './prescription-medications-edit-dialog.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PrescriptionMedicationsEditDialog {

  private fb = inject(FormBuilder);

  private service = inject(PrescriptionMedication);

  private dialogRef = inject(MatDialogRef<PrescriptionMedicationsEditDialog>);

  private data = inject<PrescriptionMedicationShow>(MAT_DIALOG_DATA);

  form = this.fb.group({

    dose: [
      this.data.dose,
      [Validators.required, Validators.min(1)]
    ],

    frequency: [
      this.data.frequency,
      [Validators.required, Validators.min(1)]
    ],

    duration: [
      this.data.duration,
      [Validators.required, Validators.min(1)]
    ],

    idPrescription: [
      this.data.idPrescription,
      [Validators.required]
    ],

    idMedication: [
      this.data.idMedication,
      [Validators.required]
    ]

  });

  guardar() {

    if (this.form.invalid) return;

    const dto: PrescriptionMedicationInsert = {

      idPrescriptionMedications: this.data.idPrescriptionMedications,

      dose: this.form.value.dose!,

      frequency: this.form.value.frequency!,

      duration: this.form.value.duration!,

      idPrescription: this.form.value.idPrescription!,

      idMedication: this.form.value.idMedication!

    };

    this.service.actualizar(
      this.data.idPrescriptionMedications,
      dto
    ).subscribe({

      next: () => this.dialogRef.close(true),

      error: err => console.error(err)

    });

  }

  cancelar() {

    this.dialogRef.close(false);

  }

}
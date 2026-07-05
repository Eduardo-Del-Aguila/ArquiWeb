import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

import { PrescriptionMedication } from '../../../core/services/prescription-medication';
import { PrescriptionMedicationStateService } from '../../../core/services/state/PrescriptionMedicationStateService';
import { PrescriptionMedicationInsert } from '../../../core/interfaces/prescription-medication.interface';

@Component({
  selector: 'app-prescription-medications-form',
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './prescription-medications-form.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PrescriptionMedicationsForm {

  private fb = inject(FormBuilder);

  private prescriptionMedicationService = inject(PrescriptionMedication);

  private stateService = inject(PrescriptionMedicationStateService);

  form = this.fb.group({

    dose: [1, [Validators.required, Validators.min(1)]],

    frequency: [1, [Validators.required, Validators.min(1)]],

    duration: [1, [Validators.required, Validators.min(1)]],

    idPrescription: [1, [Validators.required, Validators.min(1)]],

    idMedication: [1, [Validators.required, Validators.min(1)]]

  });

  guardar() {

    if (this.form.invalid) return;

    const dto = this.form.value as PrescriptionMedicationInsert;

    this.prescriptionMedicationService.insertar(dto).subscribe({

      next: () => {

        this.form.reset({
          dose: 1,
          frequency: 1,
          duration: 1,
          idPrescription: 1,
          idMedication: 1
        });

        this.stateService.notificarRecarga();

      },

      error: err => console.error(err)

    });

  }

}
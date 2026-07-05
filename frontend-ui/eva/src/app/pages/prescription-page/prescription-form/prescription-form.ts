import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { PrescriptionStateService } from '../../../core/services/state/PrescriptionStateService';
import { Prescription } from '../../../core/services/prescription';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { PrescriptionInsert } from '../../../core/interfaces/prescription.interface';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';

@Component({
  selector: 'app-prescription-form',
  imports: [ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule],
  templateUrl: './prescription-form.html',
  styleUrl: './prescription-form.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PrescriptionForm {

  private fb = inject(FormBuilder);

  private prescriptionService = inject(Prescription);
  private prescriptionState = inject(PrescriptionStateService);

  form = this.fb.group({
    idUserPatient: [0, Validators.required],

    idEva: [0, Validators.required],

    diagnosis: ['', Validators.required],

    date: [null, Validators.required]
  });

  guardar() {
    if (this.form.invalid) return;

    const dto: PrescriptionInsert = {
      idUserPatient: this.form.value.idUserPatient!,

      idEva: this.form.value.idEva!,

      diagnosis: this.form.value.diagnosis!,

      date: this.form.value.date!
    };

    this.prescriptionService.insertar(dto).subscribe({
      next: () => {
        this.form.reset();

        this.prescriptionState.notificarRecarga();
      },

      error: (err) => console.error(err),
    });

  if (this.form.invalid) {
    console.log(this.form.value);
    console.log(this.form.errors);
    return;
  }
  }
}

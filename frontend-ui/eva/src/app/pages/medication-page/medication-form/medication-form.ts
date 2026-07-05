import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Medication } from '../../../core/services/medication';
import { MedicationStateService } from '../../../core/services/state/MedicationStateService';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MedicationInsert } from '../../../core/interfaces/medication.interface';

@Component({
  selector: 'app-medication-form',
  imports: [
    MatSelectModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
  ],
  templateUrl: './medication-form.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  styleUrl: './medication-form.css',
})
export class MedicationForm {
  private fb = inject(FormBuilder);

  private medicationService = inject(Medication);
  private medicationState = inject(MedicationStateService);

  form = this.fb.group({
    name: ['', Validators.required],

    description: ['', Validators.required],

    active: [true],
  });

  guardar() {
    if (this.form.invalid) return;

    const dto: MedicationInsert = {
      name: this.form.value.name!,
      description: this.form.value.description!,
      active: this.form.value.active!,
    };

    this.medicationService.insertar(dto).subscribe({
      next: () => {
        this.form.reset({
          active: true,
        });

        this.medicationState.notificarRecarga();
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

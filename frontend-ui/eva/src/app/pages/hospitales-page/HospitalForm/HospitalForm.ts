import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { HospitalService } from '../../../core/services/hospitalService';
import { HospitalStateService } from '../../../core/services/state/HospitalStateService';
import { HospitalInsertDTO } from '../../../core/interfaces/hospital.interface';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
  selector: 'app-hospital-form',
  imports: [ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule],
  templateUrl: './HospitalForm.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HospitalForm {
  private fb = inject(FormBuilder);
  private hospitalService = inject(HospitalService);
  private stateService = inject(HospitalStateService);

  form = this.fb.group({
    name: ['', Validators.required],
    direction: ['', Validators.required],
    phone: ['', Validators.required],
    city: ['', Validators.required]
  });

  guardar() {
    if (this.form.invalid) return;
    const dto = this.form.value as HospitalInsertDTO;
    this.hospitalService.insertar(dto).subscribe({
      next: () => {
        this.form.reset();
        this.stateService.notificarRecarga();
      },
      error: err => console.error(err)
    });
  }
}

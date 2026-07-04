import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { RolService } from '../../../core/services/rolService';
import { RolStateService } from '../../../core/services/state/RolStateService';
import { RolInsertDTO, UserRol } from '../../../core/interfaces/rol';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
  selector: 'app-rol-form',
  imports: [ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatSelectModule],
  templateUrl: './RolForm.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class RolForm {
  private fb = inject(FormBuilder);
  private rolService = inject(RolService);
  private stateService = inject(RolStateService);

  roles: UserRol[] = ['PATIENT', 'DOCTOR', 'FAMILY', 'ADMIN'];

  form = this.fb.group({
    nameRol: ['PATIENT' as UserRol, Validators.required],
    descriptionRol: ['', [Validators.required, Validators.maxLength(200)]]
  });

  guardar() {
    if (this.form.invalid) return;
    const dto = this.form.value as RolInsertDTO;
    this.rolService.insertar(dto).subscribe({
      next: () => {
        this.form.reset({ nameRol: 'PATIENT' as UserRol });
        this.stateService.notificarRecarga();
      },
      error: err => console.error(err)
    });
  }
}

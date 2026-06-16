import { ChangeDetectionStrategy, Component, effect, inject, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { EvaPetService } from '../../../core/services/evaService';
import { PetStateService } from '../../../core/services/state/PetStateService';
import { EvaPetInsert, EvaPetShow, StatusPet } from '../../../core/interfaces/pet.interface';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-pet-form',
  imports: [ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatSelectModule],
  templateUrl: './PetForm.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PetForm {
  private fb = inject(FormBuilder);
  private petService = inject(EvaPetService);
  protected stateService = inject(PetStateService);

  statuses: StatusPet[] = ['HAPPY', 'SAD', 'ANGRY', 'MELANCOLIC'];

  form = this.fb.group({
    name: ['', [Validators.required, Validators.maxLength(25)]],
    description: ['', [Validators.required, Validators.maxLength(200)]],
    status: ['HAPPY' as StatusPet, Validators.required]
  });

  guardar() {
    if (this.form.invalid) return;
    const dto = this.form.value as EvaPetInsert;
    this.petService.insertar(dto).subscribe({
      next: () => {
        this.form.reset({ status: 'HAPPY' as StatusPet });
        this.stateService.notificarRecarga();
      },
      error: err => console.error(err)
    });
  }
}

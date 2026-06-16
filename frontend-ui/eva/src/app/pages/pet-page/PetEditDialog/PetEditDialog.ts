import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { EvaPetService } from '../../../core/services/evaService';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { EvaPetInsert, EvaPetShow, StatusPet } from '../../../core/interfaces/pet.interface';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
  selector: 'app-pet-edit-dialog',
  imports: [ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatSelectModule, MatDialogModule],
  templateUrl: './PetEditDialog.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PetEditDialog {
  private fb = inject(FormBuilder);
  private petService = inject(EvaPetService);
  private dialogRef = inject(MatDialogRef<PetEditDialog>);
  private data = inject<EvaPetShow>(MAT_DIALOG_DATA);

  statuses: StatusPet[] = ['HAPPY', 'SAD', 'ANGRY', 'MELANCOLIC'];

  form = this.fb.group({
    name: [this.data.name, [Validators.required, Validators.maxLength(25)]],
    description: [this.data.description, [Validators.required, Validators.maxLength(200)]],
    status: [this.data.status as StatusPet, Validators.required]
  });

  guardar() {
    if (this.form.invalid) return;
    const dto = this.form.value as EvaPetInsert;
    this.petService.actualizar(this.data.idEva, dto).subscribe({
      next: () => this.dialogRef.close(true),
      error: err => console.error(err)
    });
  }

  cancelar() { this.dialogRef.close(false); }
}

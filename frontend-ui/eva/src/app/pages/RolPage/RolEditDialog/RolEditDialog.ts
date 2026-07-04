import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { RolInsertDTO, RolShowDTO, UserRol } from '../../../core/interfaces/rol';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { RolService } from '../../../core/services/rolService';

@Component({
  selector: 'app-rol-edit-dialog',
  imports: [ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatSelectModule, MatDialogModule],
  templateUrl: './RolEditDialog.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class RolEditDialog {
  private fb = inject(FormBuilder);
  private rolService = inject(RolService);
  private dialogRef = inject(MatDialogRef<RolEditDialog>);
  private data = inject<RolShowDTO>(MAT_DIALOG_DATA);

  roles: UserRol[] = ['PATIENT', 'DOCTOR', 'FAMILY', 'ADMIN'];

  form = this.fb.group({
    nameRol: [this.data.nameRol as UserRol, Validators.required],
    descriptionRol: [this.data.descriptionRol, [Validators.required, Validators.maxLength(200)]]
  });

  guardar() {
    if (this.form.invalid) return;
    const dto = this.form.value as RolInsertDTO;
    this.rolService.actualizar(this.data.idRol, dto).subscribe({
      next: () => this.dialogRef.close(true),
      error: err => console.error(err)
    });
  }

  cancelar() { this.dialogRef.close(false); }
}

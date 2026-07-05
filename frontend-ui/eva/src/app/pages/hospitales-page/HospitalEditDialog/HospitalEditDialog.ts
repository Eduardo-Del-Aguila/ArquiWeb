import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { HospitalService } from '../../../core/services/hospitalService';
import { HospitalInsertDTO, HospitalShowDTO } from '../../../core/interfaces/hospital.interface';

@Component({
  selector: 'app-hospital-edit-dialog',
  imports: [ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatDialogModule],
  templateUrl: './HospitalEditDialog.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HospitalEditDialog {
  private fb = inject(FormBuilder);
  private hospitalService = inject(HospitalService);
  private dialogRef = inject(MatDialogRef<HospitalEditDialog>);
  private data = inject<HospitalShowDTO>(MAT_DIALOG_DATA);

  form = this.fb.group({
    name: [this.data.name, Validators.required],
    direction: [this.data.direction, Validators.required],
    phone: [this.data.phone, Validators.required],
    city: [this.data.city, Validators.required]
  });

  guardar() {
    if (this.form.invalid) return;
    const dto = this.form.value as HospitalInsertDTO;
    this.hospitalService.actualizar(this.data.idHospital, dto).subscribe({
      next: () => this.dialogRef.close(true),
      error: err => console.error(err)
    });
  }

  cancelar() { this.dialogRef.close(false); }
}

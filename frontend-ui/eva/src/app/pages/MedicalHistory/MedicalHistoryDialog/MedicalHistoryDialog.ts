import { ChangeDetectionStrategy, Component, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MedicalHistoryService } from '../../../core/services/medicalHistoryService';
import { HospitalService } from '../../../core/services/hospitalService';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { EvaPetShow } from '../../../core/interfaces/pet.interface';
import { HospitalShowDTO } from '../../../core/interfaces/hospital.interface';
import { MedicalHistoryInsertDTO } from '../../../core/interfaces/medicalHistory';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-medical-history-dialog',
  imports: [ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatSelectModule, MatDialogModule],
  templateUrl: './MedicalHistoryDialog.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class MedicalHistoryDialog implements OnInit {
  private fb = inject(FormBuilder);
  private medicalService = inject(MedicalHistoryService);
  private hospitalService = inject(HospitalService);
  private dialogRef = inject(MatDialogRef<MedicalHistoryDialog>);
  public data = inject<EvaPetShow>(MAT_DIALOG_DATA);

  hospitales = signal<HospitalShowDTO[]>([]);

  form = this.fb.group({
    reason: ['', Validators.required],
    hospitalId: [null as number | null, Validators.required]
  });

  ngOnInit() {
    this.hospitalService.listar().subscribe({
      next: data => this.hospitales.set(data),
      error: err => console.error(err)
    });
  }

  guardar() {
    if (this.form.invalid) return;
    const dto: MedicalHistoryInsertDTO = {
      reason: this.form.value.reason!
    };
    this.medicalService.insertar(this.data.idEva, this.form.value.hospitalId!, dto).subscribe({
      next: () => this.dialogRef.close(true),
      error: err => console.error(err)
    });
  }

  cancelar() { this.dialogRef.close(false); }
}

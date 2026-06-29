import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserService } from '../../../core/services/usersService';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { UserShowDTO } from '../../../core/interfaces/users.interface';
import { UserRol } from '../../../core/interfaces/rol';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-users-edit-dialog',
  imports: [ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatSelectModule, MatDialogModule],
  templateUrl: './UsersEditDialog.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class UsersEditDialog {
  private fb = inject(FormBuilder);
  private userService = inject(UserService);
  private dialogRef = inject(MatDialogRef<UsersEditDialog>);
  private data = inject<UserShowDTO>(MAT_DIALOG_DATA);

  roles: UserRol[] = ['PATIENT', 'DOCTOR', 'FAMILY', 'ADMIN'];

  archivoSeleccionado = signal<File | null>(null);
  previewUrl = signal<string | null>(this.data.image_url);

  form = this.fb.group({
    name: [this.data.name, Validators.required],
    lastName: [this.data.lastName, Validators.required],
    mail: [this.data.mail, [Validators.required, Validators.email]],
    phoneNumber: [this.data.phoneNumber, Validators.required],
    nameRol: [this.data.nameRol as UserRol, Validators.required]
  });

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    const file = input.files?.[0];
    if (!file) return;
    this.archivoSeleccionado.set(file);
    this.previewUrl.set(URL.createObjectURL(file));
  }

  guardar() {
    if (this.form.invalid) return;

    const formValue = this.form.value;
    const formData = new FormData();
    formData.append('name', formValue.name!);
    formData.append('lastName', formValue.lastName!);
    formData.append('mail', formValue.mail!);
    formData.append('phoneNumber', formValue.phoneNumber!);
    formData.append('nameRol', formValue.nameRol!);

    if (this.archivoSeleccionado()) {
      formData.append('imagen', this.archivoSeleccionado()!);
    }

    this.userService.actualizar(this.data.idUser, formData).subscribe({
      next: () => this.dialogRef.close(true),
      error: err => console.error(err)
    });
  }

  cancelar() { this.dialogRef.close(false); }
}

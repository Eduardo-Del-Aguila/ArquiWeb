import { ChangeDetectionStrategy, Component, inject, OnInit, signal } from '@angular/core';
import { AbstractControl, FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { UserService } from '../../../core/services/usersService';
import { RolService } from '../../../core/services/rolService';
import { UserStateService } from '../../../core/services/state/UserStateService';
import { RolShowDTO } from '../../../core/interfaces/rol';
import { UserInsertDTO } from '../../../core/interfaces/users.interface';
import { CloudinaryService } from '../../../core/services/cloudinaryService';
@Component({
  selector: 'app-user-form',
  imports: [ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatSelectModule, MatIconModule],
  templateUrl: './UserForm.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class UserForm implements OnInit {
  private fb = inject(FormBuilder);
  private userService = inject(UserService);
  private rolService = inject(RolService);
  private stateService = inject(UserStateService);
  private cloudinaryService = inject(CloudinaryService);

  roles = signal<RolShowDTO[]>([]);
  showPassword = signal(false);
  showConfirmPassword = signal(false);
  subiendoImagen = signal(false);
  previewUrl = signal<string | null>(null);

  form = this.fb.group({
    name: ['', Validators.required],
    lastName: ['', Validators.required],
    mail: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
    confirmPassword: ['', Validators.required],
    image_url: [''],
    phoneNumber: ['', Validators.required],
    rolId: [null as number | null, Validators.required]
  }, { validators: this.passwordsMatch });

  private passwordsMatch(group: AbstractControl) {
    const pass = group.get('password')?.value;
    const confirm = group.get('confirmPassword')?.value;
    return pass === confirm ? null : { passwordsMismatch: true };
  }

  ngOnInit() {
    this.rolService.listar().subscribe({
      next: data => this.roles.set(data),
      error: err => console.error(err)
    });
  }

  togglePassword() { this.showPassword.set(!this.showPassword()); }
  toggleConfirmPassword() { this.showConfirmPassword.set(!this.showConfirmPassword()); }

  archivoSeleccionado = signal<File | null>(null);

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
    formData.append('password', formValue.password!);
    formData.append('phoneNumber', formValue.phoneNumber!);
    formData.append('rolId', String(formValue.rolId));

    if (this.archivoSeleccionado()) {
      formData.append('imagen', this.archivoSeleccionado()!);
    }

    this.userService.insertar(formData).subscribe({
      next: () => {
        this.form.reset();
        this.previewUrl.set(null);
        this.archivoSeleccionado.set(null);
        this.stateService.notificarRecarga();
      },
      error: err => console.error(err)
    });
  }
}

import { ChangeDetectionStrategy, Component, inject, OnInit, signal } from '@angular/core';
import { AbstractControl, FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserService } from '../../../core/services/usersService';
import { RolService } from '../../../core/services/rolService';
import { Router, RouterLink } from '@angular/router';
import { RolShowDTO, UserRol } from '../../../core/interfaces/rol';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCard } from '@angular/material/card';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatSelectModule, MatIconModule, MatCheckboxModule, RouterLink, MatCard],
  templateUrl: './Register.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Register implements OnInit {
  private fb = inject(FormBuilder);
  private userService = inject(UserService);
  private rolService = inject(RolService);
  private router = inject(Router);

  roles = signal<RolShowDTO[]>([]);
  showPassword = signal(false);
  showConfirmPassword = signal(false);
  archivoSeleccionado = signal<File | null>(null);
  previewUrl = signal<string | null>(null);
  aceptaTerminos = signal(false);

  rolesPermitidos: UserRol[] = ['PATIENT', 'FAMILY', 'DOCTOR'];

  form = this.fb.group({
    name: ['', Validators.required],
    lastName: ['', Validators.required],
    mail: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
    confirmPassword: ['', Validators.required],
    phoneNumber: ['', Validators.required],
    image_url: [''],
    rolId: [null as number | null, Validators.required]
  }, { validators: this.passwordsMatch });

  private passwordsMatch(group: AbstractControl) {
    const pass = group.get('password')?.value;
    const confirm = group.get('confirmPassword')?.value;
    return pass === confirm ? null : { passwordsMismatch: true };
  }

  ngOnInit() {
    this.rolService.listar().subscribe({
      next: data => this.roles.set(data.filter(r => this.rolesPermitidos.includes(r.nameRol as UserRol))),
      error: err => console.error(err)
    });
  }

  togglePassword() { this.showPassword.set(!this.showPassword()); }
  toggleConfirmPassword() { this.showConfirmPassword.set(!this.showConfirmPassword()); }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    const file = input.files?.[0];
    if (!file) return;
    this.archivoSeleccionado.set(file);
    this.previewUrl.set(URL.createObjectURL(file));
  }

  registrar() {
    if (this.form.invalid || !this.aceptaTerminos()) return;

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
      next: () => this.router.navigate(['/login']),
      error: err => console.error(err)
    });
  }
}

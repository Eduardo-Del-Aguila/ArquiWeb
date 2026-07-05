import { ChangeDetectionStrategy, Component, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { AuthService } from '../../../core/services/AuthService';
import { MatSnackBar } from '@angular/material/snack-bar';
import { JwtRequestDTO } from '../../../core/models/JwtRequestDTO';
import { LoginService } from '../../../core/services/auth/Login.service';
import { MatIcon } from '@angular/material/icon';

@Component({
  selector: 'app-login',
  imports: [RouterLink, MatIcon,MatButtonModule, MatCardModule, MatInputModule, MatFormFieldModule, ReactiveFormsModule],
  templateUrl: './login.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Login {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);
  private snackBar = inject(MatSnackBar);
  private loginService = inject(LoginService);
  role = signal<string>('');



  showPassword = signal(false);
  togglePassword() { this.showPassword.set(!this.showPassword()); }

  form = this.fb.nonNullable.group({
    mail: ['eduardodelaguila10@gmail.com', [Validators.required, Validators.email]],
    password: ['hachi2010', Validators.required]
  });


  login() {
    const request = new JwtRequestDTO();
    request.mail = this.form.controls.mail.value;
    request.password = this.form.controls.password.value;
    console.log('Soy el rol:', this.role());
    this.loginService.login(request).subscribe({
      next: (data: any) => {
        sessionStorage.setItem('token', data.jwttoken);
        if (this.loginService.verificar()) {
          this.role.set(this.loginService.showRole() ?? '');
        }
        switch(this.role()){
          case 'ADMIN':
            this.router.navigate(['/layout/users']);
            break;
          case 'FAMILY':
            this.router.navigate(['/layout']);
            break;
            case 'PATIENT':
              this.router.navigate(['/layout/pets']);
              break;
            case 'DOCTOR':
              this.router.navigate(['/layout/medical-history']);
              break;
            default:
            this.router.navigate(['/layout']);
        }

      },
      error: (error) => {

        if (error.status === 401) {
          this.snackBar.open(
            'Usuario o contraseña incorrectos',
            'Cerrar',
            {
              duration: 3000,
              horizontalPosition: 'center',
              verticalPosition: 'top'
            }
          );
        } else {
          this.snackBar.open(
            'Ocurrió un error al iniciar sesión',
            'Cerrar',
            {
              duration: 3000,
              horizontalPosition: 'center',
              verticalPosition: 'top'
            }
          );
        }
      }
    });
  }
}

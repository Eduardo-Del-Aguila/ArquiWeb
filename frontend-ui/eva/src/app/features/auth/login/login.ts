import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { AuthService } from '../../../core/services/authService';

@Component({
  selector: 'app-login',
  imports: [MatButtonModule, MatCardModule, MatInputModule, MatFormFieldModule, ReactiveFormsModule],
  templateUrl: './login.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Login {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);

  form = this.fb.group({
    mail: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required]
  });

  login() {
  if (this.form.invalid) return;

  console.log("FORM:", this.form.value);

  this.authService.login(this.form.value as any).subscribe({
    next: (res) => {
      console.log("LOGIN OK", res);
      this.router.navigate(['/layout/pets']);
    },
    error: (err) => {
      console.log("LOGIN ERROR", err);
    }
  });
}
}

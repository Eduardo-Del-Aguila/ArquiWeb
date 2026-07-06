import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from "@angular/router";
import { LoginService } from '../../../core/services/auth/Login.service';
import { MatMenuModule } from '@angular/material/menu';
import { MatIcon } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-sidebar',
  imports: [RouterLink, RouterLinkActive, MatMenuModule, MatButtonModule, MatIconModule, MatMenuModule, MatIcon],
  templateUrl: './sidebar.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Sidebar {

  role = signal<string>('');
  usuario = signal<string>('');

  private loginService = inject(LoginService);
  private router = inject(Router)

  cerrar() {
    sessionStorage.clear();
    this.router.navigate(['/login']);
  }


  ngOnInit() {
    if (this.loginService.verificar()) {
      this.role.set(this.loginService.showRole() ?? '');
    }
  }

  isAdmin() {
    return this.role() === 'ADMIN';
  }
  isFamily() {
    return this.role() === 'FAMILY';
  }
  isPatient() {
    return this.role() === 'PATIENT';
  }
  isDoctor(){
    return this.role() === 'DOCTOR';
  }
}

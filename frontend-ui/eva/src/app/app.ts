import { Component, inject, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Login } from "./features/auth/login/login";
import { AuthService } from './core/services/AuthService';
import { JitsiFloatComponent } from './core/components/jitsi-float/jitsi-float';


@Component({
  selector: 'app-root',
  imports: [RouterOutlet,Login,JitsiFloatComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('eva');
  private authService = inject(AuthService);

  logout(){
    this.authService.logout();
  }

}

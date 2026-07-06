import { ChangeDetectionStrategy, Component, signal } from '@angular/core';
import { Navbar } from "../navbar/navbar";
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-header',
  imports: [ Navbar, RouterLink, RouterLinkActive],
  templateUrl: './header.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Header {
  public menuOpen = signal(false);

  openMenu(){
    this.menuOpen.set(true);
  }

  closeMenu(){
    this.menuOpen.set(false);
  }


}

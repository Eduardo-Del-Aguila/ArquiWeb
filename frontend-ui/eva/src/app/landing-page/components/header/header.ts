import { ChangeDetectionStrategy, Component, signal } from '@angular/core';
import { Navbar } from "../navbar/navbar";

@Component({
  selector: 'app-header',
  imports: [ Navbar],
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

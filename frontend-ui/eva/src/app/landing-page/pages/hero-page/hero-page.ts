import { ChangeDetectionStrategy, Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-hero-page',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './hero-page.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HeroPage {}

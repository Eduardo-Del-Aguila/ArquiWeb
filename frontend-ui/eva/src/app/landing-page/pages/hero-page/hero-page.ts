import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'app-hero-page',
  imports: [],
  templateUrl: './hero-page.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HeroPage {}

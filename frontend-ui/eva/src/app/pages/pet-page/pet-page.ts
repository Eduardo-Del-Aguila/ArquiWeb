import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'app-pet-page',
  imports: [],
  templateUrl: './pet-page.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PetPage {}

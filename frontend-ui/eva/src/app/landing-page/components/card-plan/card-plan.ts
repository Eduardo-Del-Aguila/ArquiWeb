import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'app-card-plan',
  imports: [],
  templateUrl: './card-plan.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CardPlan {
  list = [1,2,3,4,5]

}

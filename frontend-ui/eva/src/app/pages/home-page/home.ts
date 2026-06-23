import { ChangeDetectionStrategy, Component } from '@angular/core';
import { Sidebar } from "../../shared/components/sidebar/sidebar";

@Component({
  selector: 'app-home',
  imports: [Sidebar],
  templateUrl: './home.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HomePage {




}

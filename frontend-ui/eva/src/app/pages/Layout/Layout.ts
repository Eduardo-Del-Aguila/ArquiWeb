import { ChangeDetectionStrategy, Component } from '@angular/core';
import { Sidebar } from '../../shared/components/sidebar/sidebar';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-layout',
  imports: [Sidebar, RouterOutlet],
  templateUrl: './Layout.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Layout {}

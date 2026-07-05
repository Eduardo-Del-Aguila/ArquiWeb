import { ChangeDetectionStrategy, Component } from '@angular/core';
import { Header } from "./components/header/header";
import { HeroPage } from "./pages/hero-page/hero-page";
import { CardPlan } from "./components/card-plan/card-plan";
import { AboutEva } from "./pages/about-eva/about-eva";

@Component({
  selector: 'app-landing',
  imports: [Header, HeroPage, CardPlan, AboutEva],
  templateUrl: './landing.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Landing {


}

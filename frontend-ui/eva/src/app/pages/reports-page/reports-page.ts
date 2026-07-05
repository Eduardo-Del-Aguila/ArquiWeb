import { Component } from '@angular/core';
// Asegúrate de que la ruta apunte correctamente a donde creaste tu AlertReport
import { AlertReport } from '../alert-page/alert-report/AlertReport'; 

@Component({
  selector: 'app-reports-page',
  standalone: true,
  imports: [AlertReport], // <-- ¡Súper importante agregar esto!
  templateUrl: './reports-page.html'
})
export class ReportsPage {

}
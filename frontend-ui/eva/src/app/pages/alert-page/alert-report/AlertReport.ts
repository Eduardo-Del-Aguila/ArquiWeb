import { Component, OnInit, inject , ChangeDetectorRef} from '@angular/core';
import { BaseChartDirective } from 'ng2-charts';
import { ChartConfiguration, ChartType } from 'chart.js';
// Asegúrate de que la ruta coincida con la ubicación de tu servicio
import { AlertService } from '../../../core/services/alertService'; 
@Component({
  selector: 'app-alert-report',
  standalone: true,
  imports: [BaseChartDirective],
  templateUrl: './AlertReport.html'
})
export class AlertReport implements OnInit {
  private alertService = inject(AlertService);
  private cdr = inject(ChangeDetectorRef); // <-- 1. Inyectamos el detector de cambios
  public chartType: ChartType = 'doughnut';
  
  public chartData: ChartConfiguration['data'] = {
    labels: [], 
    datasets: [{
      data: [], 
      backgroundColor: ['#f59e0b', '#ef4444', '#3b82f6', '#10b981'],
      hoverBackgroundColor: ['#d97706', '#dc2626', '#2563eb', '#059669'],
      borderWidth: 0 // Le quitamos el borde para que se vea más moderno y limpio
    }]
  };

  // Opciones extra para que el gráfico sea responsivo y se vea bien junto a la tabla
  public chartOptions: ChartConfiguration['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        position: 'bottom',
        labels: { color: 'white' } // Letras blancas para tu tema oscuro
      }
    }
  };

  ngOnInit(): void {
    this.cargarDatos();
  }

  cargarDatos() {
    this.alertService.getReporteTipos().subscribe({
      next: (data: any[]) => {
        const etiquetas = data.map(item => item.tipo);
        const valores = data.map(item => item.cantidad);

        // Forzamos la actualización del objeto para que Angular redibuje el gráfico
        this.chartData = {
          labels: etiquetas,
          datasets: [{ 
            ...this.chartData.datasets[0], // Mantenemos los colores
            data: valores 
          }]
        };
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Error al cargar datos del gráfico', err)
    });
  }
}
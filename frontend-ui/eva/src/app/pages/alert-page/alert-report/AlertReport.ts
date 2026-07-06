import { Component, OnInit, inject , ChangeDetectorRef} from '@angular/core';
import { ChartConfiguration, ChartType } from 'chart.js';
// Asegúrate de que la ruta coincida con la ubicación de tu servicio
import { AlertService } from '../../../core/services/alertService';
import { BaseChartDirective } from 'ng2-charts';


@Component({
  selector: 'app-alert-report',
  imports: [BaseChartDirective],
  templateUrl: './AlertReport.html'
})
export class AlertReport implements OnInit {
  private alertService = inject(AlertService);
  private cdr = inject(ChangeDetectorRef);
  public chartType: ChartType = 'doughnut';

  public chartData: ChartConfiguration['data'] = {
    labels: [],
    datasets: [{
      data: [],
      backgroundColor: ['#f59e0b', '#ef4444', '#3b82f6', '#10b981'],
      hoverBackgroundColor: ['#d97706', '#dc2626', '#2563eb', '#059669'],
      borderWidth: 0
    }]
  };

  public chartOptions: ChartConfiguration['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        position: 'bottom',
        labels: { color: 'white' }
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


        this.chartData = {
          labels: etiquetas,
          datasets: [{
            ...this.chartData.datasets[0],
            data: valores
          }]
        };
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Error al cargar datos del gráfico', err)
    });
  }
}

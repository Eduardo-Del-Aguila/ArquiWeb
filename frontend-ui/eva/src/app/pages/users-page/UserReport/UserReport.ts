import { AfterViewInit, ChangeDetectionStrategy, Component, ElementRef, inject, OnDestroy, ViewChild } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import { UserService } from '../../../core/services/usersService';
import { UserRolReportDTO } from '../../../core/interfaces/users.interface';
Chart.register(...registerables);

@Component({
  selector: 'app-user-report',
  imports: [],
  templateUrl: './UserReport.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class UserReport  implements AfterViewInit, OnDestroy {
  private userService = inject(UserService);

  @ViewChild('chartCanvas') chartCanvas!: ElementRef<HTMLCanvasElement>;
  private chart?: Chart;

  ngAfterViewInit() {
    this.userService.reporteRoles().subscribe({
      next: data => this.crearGrafica(data),
      error: err => console.error(err)
    });
  }

  crearGrafica(data: UserRolReportDTO[]) {
    const labels = data.map(d => d.rol);
    const valores = data.map(d => d.cantidad);

    this.chart = new Chart(this.chartCanvas.nativeElement, {
      type: 'doughnut',
      data: {
        labels,
        datasets: [{
          label: 'Usuarios por rol',
          data: valores,
          backgroundColor: [
            'rgba(34, 197, 94, 0.6)',
            'rgba(59, 130, 246, 0.6)',
            'rgba(234, 179, 8, 0.6)'
          ],
          borderColor: [
            'rgba(34, 197, 94, 1)',
            'rgba(59, 130, 246, 1)',
            'rgba(234, 179, 8, 1)'
          ],
          borderWidth: 2
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false, 
        plugins: {
          legend: { position: 'bottom' },
          title: {
            display: true,
            text: 'Distribución de usuarios por rol'
          }
        }
      }
    });
  }

  ngOnDestroy() {
    this.chart?.destroy();
  }
}

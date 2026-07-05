import { Chart, registerables } from 'chart.js';
import { AfterViewInit, ChangeDetectionStrategy, Component, ElementRef, inject, OnDestroy, ViewChild, viewChild } from '@angular/core';
import { EvaPetService } from '../../../core/services/evaService';
import { EvaPetReportDTO } from '../../../core/interfaces/evaPetReport';
import { filter, map } from 'rxjs';
Chart.register(...registerables);
@Component({
  selector: 'app-eva-pet-report',
  imports: [],
  templateUrl: './EvaPetReport.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class EvaPetReport implements AfterViewInit, OnDestroy {
  private petService = inject(EvaPetService);

  @ViewChild('chartCanvas') chartCanvas!: ElementRef<HTMLCanvasElement>;

  // chartCanvas = viewChild<ElementRef>('chartCanvas')


  private chart?: Chart;

  ngAfterViewInit() {
    this.petService.reporteNivel()
    .pipe(
      map(d => d.slice(0,5) )
    )
    .subscribe({
      next: (data) =>
        {

          this.crearGrafica(data),
          console.log('data por niveles:',data);

        },
      error: err => console.error(err)
    });
  }

  crearGrafica(data: EvaPetReportDTO[]) {
    const labels = data.map(d => `${d.petName} (${d.ownerName})`);
    const valores = data.map(d => d.experience);

    this.chart = new Chart(this.chartCanvas.nativeElement, {
      type: 'bar',
      data: {
        labels,
        datasets: [{
          label: 'Nivel de mascota',
          data: valores,
          backgroundColor: 'rgba(34, 197, 94, 0.6)',
          borderColor: 'rgba(34, 197, 94, 1)',
          borderWidth: 2,
          borderRadius: 6
        }]
      },
      options: {
        responsive: true,
        plugins: {
          legend: { display: false },
          title: {
            display: true,
            text: 'Mascotas por experiencia'
          }
        },
        scales: {
          y: {
            beginAtZero: true,
            ticks: { stepSize: 20 }
          }
        }
      }
    });
  }

  ngOnDestroy() {
    this.chart?.destroy();
  }
}

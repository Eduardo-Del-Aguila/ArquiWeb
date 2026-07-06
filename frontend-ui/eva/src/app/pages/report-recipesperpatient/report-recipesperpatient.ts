import { Component, effect, ElementRef, OnInit, viewChild } from '@angular/core';
import { Prescription } from '../../core/services/prescription';
import { MatIconModule } from '@angular/material/icon';
import { BaseChartDirective } from 'ng2-charts';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);
@Component({
  selector: 'app-report-recipesperpatient',
  imports: [MatIconModule],
  templateUrl: './report-recipesperpatient.html',
  styleUrl: './report-recipesperpatient.css',
})
export class ReportRecipesperpatient implements OnInit {

  hasData = false;

  chart!: Chart;

  dataChart: any[] = [];

  canvas = viewChild<ElementRef<HTMLCanvasElement>>('chart');

  constructor(private pS: Prescription) {

    effect(() => {

      const canvas = this.canvas();

      if (!canvas || this.dataChart.length === 0) return;

      this.createChart(canvas.nativeElement);

    });

  }

  ngOnInit(): void {

    this.pS.recipesPerPatient().subscribe(data => {

      this.hasData = data.length > 0;

      if (!this.hasData) return;

      this.dataChart = data;

    });

  }

  createChart(canvas: HTMLCanvasElement) {

    this.chart?.destroy();

    this.chart = new Chart(canvas, {
      type: 'bar',
      data: {
        labels: this.dataChart.map(x => x.idUserPatient),
        datasets: [{
          label: 'Recetas por Paciente',
          data: this.dataChart.map(x => x.totalRecetas),
          backgroundColor: [
            '#2196F3',
            '#4CAF50',
            '#FFC107',
            '#F44336',
            '#9C27B0'
          ]
        }]
      },
      options: {
        responsive: true
      }
    });

  }

}

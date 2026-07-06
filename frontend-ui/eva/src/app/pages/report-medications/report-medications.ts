import { Component, effect, ElementRef, OnInit, viewChild } from '@angular/core';
import { ChartDataset, ChartOptions, ChartType } from 'chart.js';
import { MatIconModule } from '@angular/material/icon';
import { Medication } from '../../core/services/medication';
import { PrescriptionMedication } from '../../core/services/prescription-medication';
import { BaseChartDirective } from 'ng2-charts';

import { Chart, registerables } from 'chart.js';
Chart.register(...registerables);

@Component({
  selector: 'app-report-medications',
  imports: [MatIconModule],
  templateUrl: './report-medications.html',
  styleUrl: './report-medications.css',
})
export class ReportMedications implements OnInit {

  hasData = false;

  chart!: Chart;

  dataChart: any[] = [];

  canvas = viewChild<ElementRef<HTMLCanvasElement>>('chart');

  constructor(private mS: PrescriptionMedication) {

    effect(() => {

      const canvas = this.canvas();

      if (!canvas || this.dataChart.length === 0) return;

      this.createChart(canvas.nativeElement);

    });

  }

  ngOnInit(): void {

    this.mS.mostUsedMedications().subscribe(data => {

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
        labels: this.dataChart.map(x => x.name),
        datasets: [{
          label: 'Medicamentos más usados',
          data: this.dataChart.map(x => x.totalUso),
          backgroundColor: [
            '#2196F3',
            '#4CAF50',
            '#FF9800',
            '#E91E63',
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

import { Component, effect, ElementRef, OnInit, viewChild } from '@angular/core';
import { Medication } from '../../core/services/medication';
import { MatIconModule } from '@angular/material/icon';
import { Chart, registerables } from 'chart.js';
Chart.register(...registerables);

@Component({
  selector: 'app-report-medicationactive',
  imports: [MatIconModule],
  templateUrl: './report-medicationactive.html',
  styleUrl: './report-medicationactive.css',
})
export class ReportMedicationactive implements OnInit {

  hasData = false;

  chart!: Chart;

  dataChart: any[] = [];

  canvas = viewChild<ElementRef<HTMLCanvasElement>>('chart');

  constructor(private mS: Medication) {

    effect(() => {

      const canvas = this.canvas();

      if (!canvas || this.dataChart.length === 0) return;

      this.createChart(canvas.nativeElement);

    });

  }

  ngOnInit(): void {

    this.mS.getActiveMedications().subscribe(data => {
      console.log('Soy la dadta:',data);
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
          label: 'Medicamentos Activos',
          data: this.dataChart.map(x => x.isActive),
          backgroundColor: [
            '#4CAF50',
            '#2196F3',
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

import { Component, OnInit } from '@angular/core';
import { ChartDataset, ChartOptions, ChartType } from 'chart.js';
import { Medication } from '../../core/services/medication';


@Component({
  selector: 'app-report-medicationactive',
  imports: [],
  templateUrl: './report-medicationactive.html',
  styleUrl: './report-medicationactive.css',
})
export class ReportMedicationactive implements OnInit {
  hasData = false;

  barChartOptions: ChartOptions = {
    responsive: true,
  };

  barChartLegend = true;

  barChartLabels: string[] = [];

  barChartData: ChartDataset[] = [];

  barChartType: ChartType = 'bar';

  constructor(private mS: Medication) {}

  ngOnInit(): void {
    this.mS.getActiveMedications().subscribe((data) => {
      if (data.length > 0) {
        this.hasData = true;

        this.barChartLabels = data.map((item) => item.name);

        this.barChartData = [
          {
            data: data.map((item) => item.isActive),
            label: 'Medicamentos Activos',
            backgroundColor: ['#4CAF50', '#2196F3', '#FF9800', '#E91E63', '#9C27B0'],
          },
        ];
      } else {
        this.hasData = false;
      }
    });
  }
}

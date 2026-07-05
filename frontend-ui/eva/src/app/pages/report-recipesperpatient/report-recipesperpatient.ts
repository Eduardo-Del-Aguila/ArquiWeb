import { Component, OnInit } from '@angular/core';
import { Prescription } from '../../core/services/prescription';

@Component({
  selector: 'app-report-recipesperpatient',
  imports: [BaseChartDirective, MatIconModule],
  templateUrl: './report-recipesperpatient.html',
  styleUrl: './report-recipesperpatient.css',
})
export class ReportRecipesperpatient implements OnInit {

  hasData = false;

  barChartOptions: ChartOptions = {
    responsive: true,
  };

  barChartLegend = true;

  barChartLabels: string[] = [];

  barChartData: ChartDataset[] = [];

  barChartType: ChartType = 'bar';

  constructor(private pS: Prescription) {}

  ngOnInit(): void {

    this.pS.recipesPerPatient().subscribe((data) => {

      if (data.length > 0) {

        this.hasData = true;

        this.barChartLabels = data.map(item => item.idUserPatient);

        this.barChartData = [
          {
            data: data.map(item => item.totalRecetas),
            label: 'Recetas por Paciente',
            backgroundColor: [
              '#2196F3',
              '#4CAF50',
              '#FFC107',
              '#F44336',
              '#9C27B0'
            ]
          }
        ];

      } else {

        this.hasData = false;

      }

    });

  }

}
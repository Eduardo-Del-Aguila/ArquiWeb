import { Component, OnInit } from '@angular/core';
import { ChartDataset, ChartOptions, ChartType } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { MatIconModule } from '@angular/material/icon';
import { Medication } from '../../core/services/medication';
import { PrescriptionMedication } from '../../core/services/prescription-medication';



@Component({
  selector: 'app-report-medications',
  imports: [BaseChartDirective, MatIconModule],
  templateUrl: './report-medications.html',
  styleUrl: './report-medications.css',
})
export class ReportMedications implements OnInit {

    hasData = false;

    barChartOptions: ChartOptions = {
        responsive: true,
    };

    barChartLegend = true;

    barChartLabels: string[] = [];

    barChartData: ChartDataset[] = [];

    barChartType: ChartType = 'bar';

    constructor(private mS: PrescriptionMedication){}

    ngOnInit(): void {

        this.mS.mostUsedMedications().subscribe(data=>{

            if(data.length>0){

                this.hasData=true;

                this.barChartLabels=data.map(item=>item.name);

                this.barChartData=[
                    {
                        data:data.map(item=>item.totalUso),
                        label:'Medicamentos más usados',
                        backgroundColor:[
                            '#2196F3',
                            '#4CAF50',
                            '#FF9800',
                            '#E91E63',
                            '#9C27B0'
                        ]
                    }
                ];

            }else{

                this.hasData=false;

            }

        });

    }

}
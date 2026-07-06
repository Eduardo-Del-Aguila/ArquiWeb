import {
  ChangeDetectionStrategy,
  Component,
  OnInit,
  inject,
  signal
} from '@angular/core';

import { GenericTable } from '../../core/components/generic-table/generic-table';

import { Prescription } from '../../core/services/prescription';

import { RecipesPatient } from '../../core/interfaces/recipes-patient.interface';

import { TableColumn } from '../../core/interfaces/table';

@Component({
  selector: 'app-recipes-patient-page',
  imports: [GenericTable],
  templateUrl: './recipes-patient-page.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class RecipesPatientPage implements OnInit {

  private prescriptionService = inject(Prescription);

  recipes = signal<RecipesPatient[]>([]);

  columns: TableColumn[] = [];

  ngOnInit() {
    this.cargar();
  }

  cargar() {

    this.prescriptionService.recipesPerPatient().subscribe({

      next: data => {

        if(data.length>0){

          this.columns = Object.keys(data[0]).map(key=>({

            key,

            label:key

          }));

        }

        this.recipes.set(data);

      },

      error:err=>console.error(err)

    });

  }

}
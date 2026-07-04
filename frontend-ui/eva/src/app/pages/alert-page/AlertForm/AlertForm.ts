import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AlertService } from '../../../core/services/alertService'; // Ajusta tu ruta
import { AlertStateService } from '../../../core/services/state/AlertStateService'; // Ajusta tu ruta
import { AlertsInsertDTO } from '../../../core/interfaces/alert.interface'; // Ajusta tu ruta
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-alert-form',
  imports: [ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatSelectModule],
  templateUrl: './AlertForm.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AlertForm {
  private fb = inject(FormBuilder);
  private alertService = inject(AlertService);
  protected stateService = inject(AlertStateService);

  // Tipos de alerta (puedes cambiarlos según la lógica de EvaPet)
  alertTypes: string[] = ['INFO', 'WARNING', 'CRITICAL'];

  form = this.fb.group({
    type: ['INFO', Validators.required],
    message: ['', [Validators.required, Validators.maxLength(255)]],
    // Inicializamos en null para que el usuario deba ingresarlos o seleccionarlos
    idPatient: [null as number | null, [Validators.required, Validators.min(1)]],
    idEva: [null as number | null, [Validators.required, Validators.min(1)]]
  });

  guardar() {
    // Si el formulario es inválido, detenemos la ejecución
    if (this.form.invalid) return; 

    // Casteamos los valores del formulario a tu DTO
    const dto = this.form.value as AlertsInsertDTO;
    
    this.alertService.insertar(dto).subscribe({
      next: () => {
        // Reseteamos el formulario y dejamos 'INFO' por defecto
        this.form.reset({ type: 'INFO' }); 
        
        // Disparamos la recarga de la tabla en alert-page
        this.stateService.notificarRecarga();
      },
      error: err => console.error('Error al guardar la alerta:', err)
    });
  }
}
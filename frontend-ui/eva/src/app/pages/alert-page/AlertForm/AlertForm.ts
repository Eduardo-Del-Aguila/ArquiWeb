import { ChangeDetectionStrategy, Component, inject, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AlertService } from '../../../core/services/alertService';
import { AlertStateService } from '../../../core/services/state/AlertStateService';
import { AlertsInsertDTO } from '../../../core/interfaces/alert.interface';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { UserService } from '../../../core/services/usersService'; 
// Asegúrate de importar CommonModule si usas *ngFor en tu HTML, o quítalo si usas la sintaxis @for de Angular 17+
import { CommonModule } from '@angular/common'; 

@Component({
  selector: 'app-alert-form',
  imports: [CommonModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatSelectModule],
  templateUrl: './AlertForm.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AlertForm implements OnInit {
  private fb = inject(FormBuilder);
  private alertService = inject(AlertService);
  protected stateService = inject(AlertStateService);
  
  // NUEVO: Inyectamos el servicio de usuarios y el detector de cambios
  private userService = inject(UserService);
  private cdr = inject(ChangeDetectorRef);

  alertTypes: string[] = ['BEHAVIOR', 'INACTIVITY', 'MEDICATION', 'HEALTH'];
  pacientes: any[] = [];

  form = this.fb.group({
    type: ['BEHAVIOR', Validators.required],
    message: ['', [Validators.required, Validators.maxLength(255)]],
    idPatient: [null as number | null, [Validators.required, Validators.min(1)]]
    // ⚠️ idEva eliminado completamente de aquí
  });

  ngOnInit(): void {
    this.cargarPacientes();
  }

  cargarPacientes(): void {
    // ⚠️ Ojo: Cambia 'listar' por el nombre real que tenga tu método en UserService
    this.userService.listar().subscribe({
      next: (data) => {
        this.pacientes = data;
        // Como usas OnPush, le avisamos a Angular que ya llegaron los datos para que dibuje el mat-select
        this.cdr.markForCheck();
      },
      error: (err) => console.error('Error al cargar la lista de pacientes', err)
    });
  }
guardar() {
  if (this.form.invalid) return;

  const dto = this.form.value as AlertsInsertDTO;

  this.alertService.insertar(dto).subscribe({
    next: () => {
      this.form.reset({ type: 'BEHAVIOR' });
      this.stateService.notificarRecarga();
      alert('Alerta registrada correctamente');
    },
    error: (err) => {
      // Aquí capturamos el mensaje exacto que nos diste
      console.error('Detalle del error 400:', err.error);
      alert('Error: ' + (err.error || 'No se pudo guardar la alerta. Verifica que el paciente tenga una mascota asignada.'));
    }
  });
}
}
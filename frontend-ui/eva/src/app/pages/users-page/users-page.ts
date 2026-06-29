import { ChangeDetectionStrategy, Component, effect, inject, signal } from '@angular/core';
import { UserForm } from "./UserForm/UserForm";
import { UserService } from '../../core/services/usersService';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { UserStateService } from '../../core/services/state/UserStateService';
import { UserShowDTO } from '../../core/interfaces/users.interface';
import { TableAction } from '../../core/interfaces/table';
import { UsersEditDialog } from './UsersEditDialog/UsersEditDialog';
import { ConfirmDialog } from '../../core/components/ConfirmDialog/ConfirmDialog';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';


@Component({
  selector: 'app-users-page',
  imports: [UserForm, MatCardModule, MatButtonModule, MatIconModule, MatTooltipModule, MatDialogModule],
  templateUrl: './users-page.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class UsersPage {
  private userService = inject(UserService);
  private dialog = inject(MatDialog);
  private stateService = inject(UserStateService);

  users = signal<UserShowDTO[]>([]);

  actions: TableAction<UserShowDTO>[] = [
    { label: 'Editar', icon: 'edit', action: (row) => this.editar(row) },
    { label: 'Eliminar', icon: 'delete', action: (row) => this.eliminar(row) }
  ];

  private recargarEffect = effect(() => {
    this.stateService.recargar();
    this.cargar();
  });

  cargar() {
    this.userService.listar().subscribe({
      next: data => {
        this.users.set(data)
        console.log('Soy la data: ',data);
      },
      error: err => console.error(err)
    });
  }

  editar(row: UserShowDTO) {
    const ref = this.dialog.open(UsersEditDialog, {
      width: '450px',
      data: row
    });
    ref.afterClosed().subscribe(actualizado => {
      if (actualizado) this.stateService.notificarRecarga();
    });
  }

  eliminar(row: UserShowDTO) {
    const ref = this.dialog.open(ConfirmDialog, {
      width: '350px',
      data: {
        titulo: `¿Deseas eliminar a ${row.name} ${row.lastName}?`,
        mensaje: 'Esta acción no se puede deshacer.'
      }
    });
    ref.afterClosed().subscribe(confirmado => {
      if (confirmado) {
        this.userService.eliminar(row.idUser).subscribe({
          next: () => this.stateService.notificarRecarga(),
          error: err => console.error(err)
        });
      }
    });
  }
}

import { ChangeDetectionStrategy, Component, computed, effect, inject, signal } from '@angular/core';
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
import { RolService } from '../../core/services/rolService';
import { RolShowDTO } from '../../core/interfaces/rol';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatOption, MatSelect } from '@angular/material/select';


@Component({
  selector: 'app-users-page',
  imports: [UserForm, MatCardModule, MatButtonModule, MatIconModule, MatTooltipModule, MatDialogModule, MatFormFieldModule, MatSelect, MatOption],
  templateUrl: './users-page.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class UsersPage {
  private userService = inject(UserService);
  private dialog = inject(MatDialog);
  private stateService = inject(UserStateService);
  private rolService = inject(RolService);

  roles = signal<RolShowDTO[]>([]);
  filtroRol = signal<string>('TODOS');

  users = signal<UserShowDTO[]>([]);

  actions: TableAction<UserShowDTO>[] = [
    { label: 'Editar', icon: 'edit', action: (row) => this.editar(row) },
    { label: 'Eliminar', icon: 'delete', action: (row) => this.eliminar(row) }
  ];

  usersFiltrados = computed(() => {
    const filtro = this.filtroRol();
    if (filtro === 'TODOS') return this.users();
    return this.users().filter(u => u.nameRol === filtro);
  });

  private recargarEffect = effect(() => {
    this.stateService.recargar();
    this.cargar();
  });

  ngOnInit() {
    this.rolService.listar().subscribe({
      next: data => this.roles.set(data),
      error: err => console.error(err)
    });
  }

  cambiarFiltro(rol: string) {
    this.filtroRol.set(rol);
  }

  cargar() {
    this.userService.listar().subscribe({
      next: data => {
        this.users.set(data)
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

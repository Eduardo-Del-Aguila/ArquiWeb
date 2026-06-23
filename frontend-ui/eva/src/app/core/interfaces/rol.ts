export interface RolShowDTO {
  idRol: number;
  nameRol: string;
  descriptionRol: string;
}

export interface RolInsertDTO {
  nameRol: UserRol;
  descriptionRol: string;
}


export type UserRol = 'PATIENT' | 'DOCTOR' | 'FAMILY' | 'ADMIN';


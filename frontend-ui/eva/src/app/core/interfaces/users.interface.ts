export interface UserInsertDTO {
  name: string;
  lastName: string;
  mail: string;
  password: string;
  image_url: string;
  phoneNumber: string;
  rolId: number;
}

export interface UserShowDTO {
  idUser: number;
  name: string;
  lastName: string;
  mail: string;
  phoneNumber: string;
  image_url: string;
  nameRol: string;
}

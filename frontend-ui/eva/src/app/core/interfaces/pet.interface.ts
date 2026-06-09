export interface EvaPetShow {
  description: string;
  experiencie: number;
  level:       number;
  name:        string;
  status:      string;
}

export interface EvaPetInsert {
  name: string;
  description: string;
  status: StatusPet;
}

export enum StatusPet{
    PATIENT,
    DOCTOR,
    FAMILY,
    ADMIN
}

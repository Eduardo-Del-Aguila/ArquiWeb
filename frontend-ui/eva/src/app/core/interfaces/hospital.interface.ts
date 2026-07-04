export interface HospitalInsertDTO {
  name: string;
  direction: string;
  phone: string;
  city: string;
}

export interface HospitalShowDTO {
  idHospital: number;
  name: string;
  direction: string;
  phone: string;
  city: string;
}

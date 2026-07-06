export interface PrescriptionShow {
  idPrescription: number;
  idUserPatient: number;
  idEva: number;
  diagnosis: string;
  date: string;
}

export interface PrescriptionInsert {
  idPrescription?: number;
  idUserPatient: number;
  idEva: number;
  diagnosis: string;
  date: string;
}
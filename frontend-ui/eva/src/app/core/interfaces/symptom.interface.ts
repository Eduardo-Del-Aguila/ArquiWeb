export interface Symptom {
  idSymptom: number;
  name: string;
  severity: Severity;
  idMedicalHistory?: MedicalHistory;
}

export enum Severity {
  LOW = 'LOW',
  MEDIUM = 'MEDIUM',
  HIGH = 'HIGH'
}

export interface MedicalHistory {
  idMedicalHistory: number;
}

export interface SymptomInsertDTO {
  name: string;
  severity: Severity;
}
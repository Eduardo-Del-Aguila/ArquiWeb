export type MedicalStatus = 'PENDING' | 'REVIEWED' | 'CLOSED';

export interface MedicalHistoryInsertDTO {
  reason: string;
}

export interface MedicalHistoryShowDTO {
  id: number;
  reason: string;
  treatment: string;
  observations: string;
  diagnostics: string;
  status: MedicalStatus;
  evaName: string;
  patientName: string;
  doctorName: string;
  nameHospital: string;
  registerAt: string;
}

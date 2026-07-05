export interface PrescriptionMedicationShow {
  idPrescriptionMedications: number;
  dose: number;
  frequency: number;
  duration: number;
  idPrescription: number;
  idMedication: number;
}

export interface PrescriptionMedicationInsert {
  idPrescriptionMedications?: number;
  dose: number;
  frequency: number;
  duration: number;
  idPrescription: number;
  idMedication: number;
}
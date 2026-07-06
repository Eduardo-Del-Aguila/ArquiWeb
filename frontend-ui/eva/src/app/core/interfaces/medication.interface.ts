export interface MedicationShow {
  idMedication: number;
  name: string;
  description: string;
  active: boolean;
}

export interface MedicationInsert {
  idMedication?: number;
  name: string;
  description: string;
  active: boolean;
}
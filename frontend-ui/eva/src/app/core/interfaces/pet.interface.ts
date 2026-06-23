export interface EvaPetShow {
  idEva:       number;
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

export type StatusPet = 'SAD' | 'HAPPY' | 'ANGRY' | 'MELANCOLIC';

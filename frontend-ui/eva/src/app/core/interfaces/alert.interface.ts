export interface AlertsInsertDTO {
  idAlert?: number; // Opcional al insertar
  type: string;
  message: string;
  idPatient: number;
  idEva: number;
  isRead?: boolean;
  createdAt?: string; // Suele llegar como string ISO desde Java
}

export interface ShowAlertsDTO {
  idAlert: number;
  type: string;
  message: string;
  isRead: boolean;
  createdAt: string;
  // Agrega otros campos si tu ShowAlertsDTO de Java los tiene (ej. datos del paciente)
}
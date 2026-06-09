export interface LoginRequest {
  mail: string;
  password: string;
}

export interface LoginResponse {
  token: string;
}

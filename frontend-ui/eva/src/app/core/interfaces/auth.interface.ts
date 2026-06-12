export interface LoginRequest {
  mail: string;
  password: string;
}

export interface LoginResponse {
  jwttoken: string;
}

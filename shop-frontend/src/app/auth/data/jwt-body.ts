export interface JwtBody {
  exp: number;
  iat: number;
  jti: string;
  sub: string;
  usr?: string;
  rls?: string[];
}

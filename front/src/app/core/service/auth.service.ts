import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginRequest } from '../models/loginRequest.interface';
import { RegisterRequest } from '../models/registerRequest.interface';
import { SessionInformation } from 'src/app/core/models/sessionInformation.interface';

/**
 * AuthService - Gestion de l'authentification
 *
 * Service responsable de:
 * - Enregistrement d'un nouvel utilisateur
 * - Connexion d'un utilisateur existant
 * - Communication avec l'API d'authentification
 *
 * @injectable
 * @example
 * constructor(private authService: AuthService) { }
 * this.authService.login(credentials).subscribe(...);
 */
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private pathService = '/api/auth';

  constructor(private httpClient: HttpClient) { }

  public register(registerRequest: RegisterRequest): Observable<void> {
    return this.httpClient.post<void>(`${this.pathService}/register`, registerRequest);
  }

  public login(loginRequest: LoginRequest): Observable<SessionInformation> {
    return this.httpClient.post<SessionInformation>(`${this.pathService}/login`, loginRequest);
  }
}

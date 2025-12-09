// @ts-nocheck
/**
 * Tests unitaires pour `AuthService`
 * - Vérifie que les bonnes routes HTTP sont appelées
 * - Vérifie le mapping des réponses
 */
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { AuthService } from './auth.service';
import { LoginRequest } from '../models/loginRequest.interface';
import { SessionInformation } from '../models/sessionInformation.interface';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService],
    });

    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    // Vérifie qu'aucune requête n'est en attente
    httpMock.verify();
  });

  it('should call register endpoint with correct body', () => {
    const payload = { email: 'a@b.com', password: 'pwd', firstName: 'A', lastName: 'B' } as any;

    // Appel de la méthode
    service.register(payload).subscribe((res) => {
      // register retourne void, on vérifie juste qu'on reçoit une réponse
      expect(res).toBeUndefined();
    });

    // Vérifie la requête HTTP
    const req = httpMock.expectOne('/api/auth/register');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(payload);

    // Envoi d'une réponse simulée (void)
    req.flush(null);
  });

  it('should call login endpoint and return session information', () => {
    const credentials: LoginRequest = { email: 'a@b.com', password: 'pwd' } as LoginRequest;
    const mockSession: SessionInformation = {
      token: 't',
      type: 'Bearer',
      id: 1,
      username: 'user',
      firstName: 'First',
      lastName: 'Last',
      admin: false,
    } as SessionInformation;

    service.login(credentials).subscribe((res) => {
      expect(res).toEqual(mockSession);
    });

    const req = httpMock.expectOne('/api/auth/login');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(credentials);

    req.flush(mockSession);
  });
});

// @ts-nocheck
/**
 * Tests unitaires pour `SessionService`
 * - Vérifie que le BehaviorSubject initial émet `false`
 * - Vérifie que `logIn()` stocke sessionInformation et émet `true`
 * - Vérifie que `logOut()` supprime sessionInformation et émet `false`
 * - Vérifie que `$isLogged()` retourne un Observable
 */
import { TestBed } from '@angular/core/testing';

import { SessionService } from './session.service';
import { SessionInformation } from '../models/sessionInformation.interface';

describe('SessionService', () => {
  let service: SessionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should emit false initially when not logged in', (done) => {
    service.$isLogged().subscribe((isLogged) => {
      expect(isLogged).toBe(false);
      expect(service.sessionInformation).toBeUndefined();
      done();
    });
  });

  it('should emit true after logIn is called', (done) => {
    const mockSession: SessionInformation = {
      token: 'fake-token',
      type: 'Bearer',
      id: 1,
      username: 'user',
      firstName: 'John',
      lastName: 'Doe',
      admin: false,
    };

    service.logIn(mockSession);

    service.$isLogged().subscribe((isLogged) => {
      expect(isLogged).toBe(true);
      expect(service.sessionInformation).toEqual(mockSession);
      done();
    });
  });

  it('should emit false after logOut is called', (done) => {
    const mockSession: SessionInformation = {
      token: 'fake-token',
      type: 'Bearer',
      id: 2,
      username: 'admin',
      firstName: 'Admin',
      lastName: 'User',
      admin: true,
    };

    // Connexion puis déconnexion
    service.logIn(mockSession);
    service.logOut();

    service.$isLogged().subscribe((isLogged) => {
      expect(isLogged).toBe(false);
      expect(service.sessionInformation).toBeUndefined();
      done();
    });
  });

  it('should return an Observable from $isLogged()', () => {
    const observable = service.$isLogged();
    expect(observable).toBeDefined();
    expect(typeof observable.subscribe).toBe('function');
  });
});

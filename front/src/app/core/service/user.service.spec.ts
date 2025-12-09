// @ts-nocheck
/**
 * Tests unitaires pour `UserService`
 * - Vérifie GET /api/user/{id} (récupération d'un utilisateur)
 * - Vérifie DELETE /api/user/{id} (suppression d'un utilisateur)
 */
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { UserService } from './user.service';
import { User } from '../models/user.interface';

describe('UserService', () => {
  let service: UserService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UserService],
    });
    service = TestBed.inject(UserService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch user by id', () => {
    const mockUser: User = {
      id: 10,
      email: 'test@example.com',
      lastName: 'Test',
      firstName: 'User',
      admin: false,
      password: 'hashed',
      createdAt: new Date(),
      updatedAt: new Date(),
    };

    service.getById('10').subscribe((user) => {
      expect(user).toEqual(mockUser);
      expect(user.email).toBe('test@example.com');
    });

    const req = httpMock.expectOne('api/user/10');
    expect(req.request.method).toBe('GET');
    req.flush(mockUser);
  });

  it('should delete user by id', () => {
    service.delete('42').subscribe((res) => {
      expect(res).toBeUndefined();
    });

    const req = httpMock.expectOne('api/user/42');
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });
});

// @ts-nocheck
/**
 * Tests unitaires pour `SessionApiService`
 * - Vérifie GET /api/session (toutes les sessions)
 * - Vérifie GET /api/session/{id} (détail d'une session)
 * - Vérifie POST /api/session (création)
 * - Vérifie PUT /api/session/{id} (mise à jour)
 * - Vérifie DELETE /api/session/{id} (suppression)
 * - Vérifie POST /api/session/{id}/participate (participation)
 * - Vérifie DELETE /api/session/{id}/participate (annulation participation)
 */
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { SessionApiService } from './session-api.service';
import { Session } from '../models/session.interface';

describe('SessionApiService', () => {
  let service: SessionApiService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [SessionApiService],
    });
    service = TestBed.inject(SessionApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch all sessions', () => {
    const mockSessions: Session[] = [
      {
        id: 1,
        name: 'Session 1',
        description: 'Desc 1',
        date: new Date(),
        teacher_id: 1,
        users: [1, 2],
        createdAt: new Date(),
        updatedAt: new Date(),
      },
      {
        id: 2,
        name: 'Session 2',
        description: 'Desc 2',
        date: new Date(),
        teacher_id: 2,
        users: [],
        createdAt: new Date(),
        updatedAt: new Date(),
      },
    ];

    service.all().subscribe((sessions) => {
      expect(sessions).toEqual(mockSessions);
      expect(sessions.length).toBe(2);
    });

    const req = httpMock.expectOne('api/session');
    expect(req.request.method).toBe('GET');
    req.flush(mockSessions);
  });

  it('should fetch session detail by id', () => {
    const mockSession: Session = {
      id: 5,
      name: 'Yoga Advanced',
      description: 'Advanced yoga',
      date: new Date(),
      teacher_id: 3,
      users: [1],
      createdAt: new Date(),
      updatedAt: new Date(),
    };

    service.detail('5').subscribe((session) => {
      expect(session).toEqual(mockSession);
      expect(session.name).toBe('Yoga Advanced');
    });

    const req = httpMock.expectOne('api/session/5');
    expect(req.request.method).toBe('GET');
    req.flush(mockSession);
  });

  it('should create a session', () => {
    const newSession: Session = {
      name: 'New Session',
      description: 'New',
      date: new Date(),
      teacher_id: 1,
      users: [],
    } as Session;

    const createdSession: Session = { ...newSession, id: 10, createdAt: new Date(), updatedAt: new Date() };

    service.create(newSession).subscribe((session) => {
      expect(session).toEqual(createdSession);
      expect(session.id).toBe(10);
    });

    const req = httpMock.expectOne('api/session');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(newSession);
    req.flush(createdSession);
  });

  it('should update a session', () => {
    const updatedSession: Session = {
      id: 7,
      name: 'Updated Session',
      description: 'Updated',
      date: new Date(),
      teacher_id: 2,
      users: [1, 2],
      createdAt: new Date(),
      updatedAt: new Date(),
    };

    service.update('7', updatedSession).subscribe((session) => {
      expect(session).toEqual(updatedSession);
      expect(session.name).toBe('Updated Session');
    });

    const req = httpMock.expectOne('api/session/7');
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(updatedSession);
    req.flush(updatedSession);
  });

  it('should delete a session', () => {
    service.delete('3').subscribe((res) => {
      expect(res).toBeUndefined();
    });

    const req = httpMock.expectOne('api/session/3');
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });

  it('should participate in a session', () => {
    const sessionId = '8';
    const userId = '5';

    service.participate(sessionId, userId).subscribe((res) => {
      expect(res).toBeUndefined();
    });

    const req = httpMock.expectOne('api/session/8/participate/5');
    expect(req.request.method).toBe('POST');
    req.flush(null);
  });

  it('should unparticipate from a session', () => {
    const sessionId = '8';
    const userId = '5';

    service.unParticipate(sessionId, userId).subscribe((res) => {
      expect(res).toBeUndefined();
    });

    const req = httpMock.expectOne('api/session/8/participate/5');
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });
});

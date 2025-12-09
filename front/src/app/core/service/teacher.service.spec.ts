// @ts-nocheck
/**
 * Tests unitaires pour `TeacherService`
 * - Vérifie GET /api/teacher (tous les professeurs)
 * - Vérifie GET /api/teacher/{id} (détail d'un professeur)
 */
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { TeacherService } from './teacher.service';
import { Teacher } from '../models/teacher.interface';

describe('TeacherService', () => {
  let service: TeacherService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TeacherService],
    });
    service = TestBed.inject(TeacherService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch all teachers', () => {
    const mockTeachers: Teacher[] = [
      { id: 1, firstName: 'John', lastName: 'Doe', createdAt: new Date(), updatedAt: new Date() },
      { id: 2, firstName: 'Jane', lastName: 'Smith', createdAt: new Date(), updatedAt: new Date() },
    ];

    service.all().subscribe((teachers) => {
      expect(teachers).toEqual(mockTeachers);
      expect(teachers.length).toBe(2);
    });

    const req = httpMock.expectOne('api/teacher');
    expect(req.request.method).toBe('GET');
    req.flush(mockTeachers);
  });

  it('should fetch teacher detail by id', () => {
    const mockTeacher: Teacher = {
      id: 5,
      firstName: 'Alice',
      lastName: 'Wonder',
      createdAt: new Date(),
      updatedAt: new Date(),
    };

    service.detail('5').subscribe((teacher) => {
      expect(teacher).toEqual(mockTeacher);
      expect(teacher.id).toBe(5);
    });

    const req = httpMock.expectOne('api/teacher/5');
    expect(req.request.method).toBe('GET');
    req.flush(mockTeacher);
  });
});

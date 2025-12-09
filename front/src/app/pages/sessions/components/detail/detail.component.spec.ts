// @ts-nocheck
import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { RouterTestingModule } from '@angular/router/testing';
import { Router, ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DetailComponent } from './detail.component';
import { SessionService } from '../../../../core/service/session.service';
import { SessionApiService } from '../../../../core/service/session-api.service';
import { TeacherService } from '../../../../core/service/teacher.service';
import { Session } from '../../../../core/models/session.interface';
import { Teacher } from '../../../../core/models/teacher.interface';

describe('DetailComponent', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  let sessionService: any;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  let sessionApiService: any;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  let teacherService: any;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  let router: any;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  let matSnackBar: any;

  const mockSessionInformation = {
    token: 'fake-token',
    type: 'Bearer',
    id: 1,
    username: 'testuser',
    firstName: 'Test',
    lastName: 'User',
    admin: true,
  };

  const mockSession: Session = {
    id: 1,
    name: 'Yoga Session',
    description: 'Test session',
    date: new Date(),
    teacher_id: 1,
    users: [1, 2],
    createdAt: new Date(),
    updatedAt: new Date(),
  };

  const mockTeacher: Teacher = {
    id: 1,
    lastName: 'Doe',
    firstName: 'John',
    createdAt: new Date(),
    updatedAt: new Date(),
  };

  beforeEach(async () => {
    const sessionServiceMock = {
      logOut: jest.fn(),
      sessionInformation: mockSessionInformation,
    };
    const sessionApiServiceMock = {
      detail: jest.fn(),
      delete: jest.fn(),
      participate: jest.fn(),
      unParticipate: jest.fn(),
    };
    const teacherServiceMock = {
      detail: jest.fn(),
    };
    const routerMock = {
      navigate: jest.fn(),
    };
    const matSnackBarMock = {
      open: jest.fn(),
    };

    await TestBed.configureTestingModule({
      imports: [
        DetailComponent,
        RouterTestingModule,
        HttpClientModule,
        ReactiveFormsModule,
      ],
      providers: [
        { provide: SessionService, useValue: sessionServiceMock },
        { provide: SessionApiService, useValue: sessionApiServiceMock },
        { provide: TeacherService, useValue: teacherServiceMock },
        { provide: Router, useValue: routerMock },
        { provide: MatSnackBar, useValue: matSnackBarMock },
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: {
                get: () => '1',
              },
            },
          },
        },
      ],
    }).compileComponents();

    sessionService = TestBed.inject(SessionService);
    sessionApiService = TestBed.inject(SessionApiService);
    teacherService = TestBed.inject(TeacherService);
    router = TestBed.inject(Router);
    matSnackBar = TestBed.inject(MatSnackBar);

    fixture = TestBed.createComponent(DetailComponent);
    component = fixture.componentInstance;
  });

  describe('component initialization', () => {
    it('should create', () => {
      expect(component).toBeTruthy();
    });

    it('should have session id from route params', () => {
      expect(component.sessionId).toBe('1');
    });

    it('should set isAdmin based on session information', () => {
      expect(component.isAdmin).toBe(true);
    });

    it('should set userId from session information', () => {
      expect(component.userId).toBe('1');
    });
  });

  describe('ngOnInit', () => {
    it('should call fetchSession on init', () => {
      sessionApiService.detail.mockReturnValue(of(mockSession));
      teacherService.detail.mockReturnValue(of(mockTeacher));

      fixture.detectChanges();

      expect(sessionApiService.detail).toHaveBeenCalledWith('1');
    });
  });

  describe('back method', () => {
    it('should navigate to sessions', () => {
      component.back();

      expect(router.navigate).toHaveBeenCalledWith(['sessions']);
    });
  });

  describe('delete method', () => {
    beforeEach(() => {
      sessionApiService.detail.mockReturnValue(of(mockSession));
      teacherService.detail.mockReturnValue(of(mockTeacher));
    });

    it('should delete session and navigate on success', () => {
      sessionApiService.delete.mockReturnValue(of(null));
      fixture.detectChanges();

      component.delete();

      expect(sessionApiService.delete).toHaveBeenCalledWith('1');
      expect(router.navigate).toHaveBeenCalledWith(['sessions']);
    });
  });

  describe('participate method', () => {
    beforeEach(() => {
      sessionApiService.detail.mockReturnValue(of(mockSession));
      teacherService.detail.mockReturnValue(of(mockTeacher));
      fixture.detectChanges();
    });

    it('should call participate and refresh session', () => {
      sessionApiService.participate.mockReturnValue(of(null));
      sessionApiService.detail.mockReturnValue(of(mockSession));

      component.participate();

      expect(sessionApiService.participate).toHaveBeenCalledWith('1', '1');
      expect(sessionApiService.detail).toHaveBeenCalled();
    });
  });

  describe('unParticipate method', () => {
    beforeEach(() => {
      sessionApiService.detail.mockReturnValue(of(mockSession));
      teacherService.detail.mockReturnValue(of(mockTeacher));
      fixture.detectChanges();
    });

    it('should call unParticipate and refresh session', () => {
      sessionApiService.unParticipate.mockReturnValue(of(null));
      sessionApiService.detail.mockReturnValue(of(mockSession));

      component.unParticipate();

      expect(sessionApiService.unParticipate).toHaveBeenCalledWith('1', '1');
      expect(sessionApiService.detail).toHaveBeenCalled();
    });
  });

  describe('fetchSession method', () => {
    it('should fetch session and teacher', () => {
      sessionApiService.detail.mockReturnValue(of(mockSession));
      teacherService.detail.mockReturnValue(of(mockTeacher));

      component['fetchSession']();

      expect(sessionApiService.detail).toHaveBeenCalledWith('1');
      expect(teacherService.detail).toHaveBeenCalledWith('1');
      expect(component.session).toEqual(mockSession);
      expect(component.teacher).toEqual(mockTeacher);
    });

    it('should set isParticipate to true if user is in session', () => {
      sessionApiService.detail.mockReturnValue(of(mockSession));
      teacherService.detail.mockReturnValue(of(mockTeacher));

      component['fetchSession']();

      expect(component.isParticipate).toBe(true);
    });

    it('should set isParticipate to false if user is not in session', () => {
      const sessionWithoutUser: Session = { ...mockSession, users: [2, 3] };
      sessionApiService.detail.mockReturnValue(of(sessionWithoutUser));
      teacherService.detail.mockReturnValue(of(mockTeacher));

      component['fetchSession']();

      expect(component.isParticipate).toBe(false);
    });
  });

  describe('ngOnDestroy', () => {
    it('should complete destroy subject', () => {
      jest.spyOn(component['destroy$'], 'next');
      jest.spyOn(component['destroy$'], 'complete');

      component.ngOnDestroy();

      expect(component['destroy$'].next).toHaveBeenCalled();
      expect(component['destroy$'].complete).toHaveBeenCalled();
    });
  });
});
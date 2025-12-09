// @ts-nocheck
import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule, MatSnackBar } from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { Router, ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FormComponent } from './form.component';
import { SessionService } from '../../../../core/service/session.service';
import { SessionApiService } from '../../../../core/service/session-api.service';
import { TeacherService } from '../../../../core/service/teacher.service';
import { Session } from '../../../../core/models/session.interface';
import { Teacher } from '../../../../core/models/teacher.interface';

describe('FormComponent', () => {
  let component: FormComponent;
  let fixture: ComponentFixture<FormComponent>;
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

  const mockTeachers: Teacher[] = [
    {
      id: 1,
      lastName: 'Doe',
      firstName: 'John',
      createdAt: new Date(),
      updatedAt: new Date(),
    },
  ];

  const mockSession: Session = {
    id: 1,
    name: 'Yoga Session',
    description: 'Test session',
    date: new Date(),
    teacher_id: 1,
    users: [],
    createdAt: new Date(),
    updatedAt: new Date(),
  };

  beforeEach(async () => {
    const sessionServiceMock = {
      sessionInformation: mockSessionInformation,
    };
    const sessionApiServiceMock = {
      create: jest.fn(),
      update: jest.fn(),
      detail: jest.fn(),
    };
    const teacherServiceMock = {
      all: jest.fn(),
    };
    const routerMock = {
      navigate: jest.fn(),
      url: 'create',
    };
    const matSnackBarMock = {
      open: jest.fn(),
    };

    await TestBed.configureTestingModule({
      imports: [
        FormComponent,
        RouterTestingModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
        MatSnackBarModule,
        MatSelectModule,
        BrowserAnimationsModule,
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

    teacherService.all.mockReturnValue(of(mockTeachers));

    fixture = TestBed.createComponent(FormComponent);
    component = fixture.componentInstance;
  });

  describe('component initialization', () => {
    it('should create', () => {
      expect(component).toBeTruthy();
    });

    it('should have onUpdate set to false for create mode', () => {
      router.url = '/sessions/create';
      fixture.detectChanges();

      expect(component.onUpdate).toBe(false);
    });

    it('should have onUpdate set to true for update mode', () => {
      router.url = '/sessions/update/1';
      sessionApiService.detail.mockReturnValue(of(mockSession));

      fixture.detectChanges();

      expect(component.onUpdate).toBe(true);
    });

    it('should redirect to /sessions if user is not admin', () => {
      sessionService.sessionInformation = { ...mockSessionInformation, admin: false };

      fixture.detectChanges();

      expect(router.navigate).toHaveBeenCalledWith(['/sessions']);
    });
  });

  describe('form initialization', () => {
    it('should initialize form with empty values for create mode', () => {
      router.url = '/sessions/create';
      fixture.detectChanges();

      expect(component.sessionForm?.get('name')?.value).toBe('');
      expect(component.sessionForm?.get('description')?.value).toBe('');
    });

    it('should initialize form with session data for update mode', (done) => {
      router.url = '/sessions/update/1';
      sessionApiService.detail.mockReturnValue(of(mockSession));

      fixture.detectChanges();

      setTimeout(() => {
        expect(component.sessionForm?.get('name')?.value).toBe('Yoga Session');
        done();
      }, 100);
    });
  });

  describe('submit method - create mode', () => {
    beforeEach(() => {
      router.url = '/sessions/create';
      fixture.detectChanges();
    });

    it('should call sessionApiService.create with form values', () => {
      sessionApiService.create.mockReturnValue(of(mockSession));
      component.sessionForm?.patchValue({
        name: 'New Session',
        date: '2024-01-15',
        teacher_id: 1,
        description: 'New yoga session',
      });

      component.submit();

      expect(sessionApiService.create).toHaveBeenCalled();
    });

    it('should show snackbar and navigate on successful create', (done) => {
      sessionApiService.create.mockReturnValue(of(mockSession));
      component.sessionForm?.patchValue({
        name: 'New Session',
        date: '2024-01-15',
        teacher_id: 1,
        description: 'New yoga session',
      });

      component.submit();

      setTimeout(() => {
        expect(router.navigate).toHaveBeenCalledWith(['sessions']);
        done();
      }, 100);
    });
  });

  describe('submit method - update mode', () => {
    beforeEach(() => {
      router.url = '/sessions/update/1';
      sessionApiService.detail.mockReturnValue(of(mockSession));
      fixture.detectChanges();
    });

    it('should call sessionApiService.update with id and form values', (done) => {
      sessionApiService.update.mockReturnValue(of(mockSession));
      component.sessionForm?.patchValue({
        name: 'Updated Session',
        date: '2024-01-15',
        teacher_id: 1,
        description: 'Updated yoga session',
      });

      component.submit();

      setTimeout(() => {
        expect(sessionApiService.update).toHaveBeenCalled();
        done();
      }, 100);
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

import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, throwError } from 'rxjs';

import { LoginComponent } from './login.component';
import { SessionService } from 'src/app/core/service/session.service';
import { AuthService } from 'src/app/core/service/auth.service';
import { SessionInformation } from 'src/app/core/models/sessionInformation.interface';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  let authService: any;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  let sessionService: any;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  let router: any;

  const mockSessionInformation: SessionInformation = {
    token: 'fake-token',
    type: 'Bearer',
    id: 1,
    username: 'testuser',
    firstName: 'Test',
    lastName: 'User',
    admin: false,
  };

  beforeEach(async () => {
    const authServiceMock = {
      login: jest.fn(),
    };
    const sessionServiceMock = {
      logIn: jest.fn(),
    };
    const routerMock = {
      navigate: jest.fn(),
    };

    await TestBed.configureTestingModule({
      imports: [
        LoginComponent,
        RouterTestingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
      ],
      providers: [
        { provide: AuthService, useValue: authServiceMock },
        { provide: SessionService, useValue: sessionServiceMock },
        { provide: Router, useValue: routerMock },
      ],
    }).compileComponents();

    authService = TestBed.inject(AuthService);
    sessionService = TestBed.inject(SessionService);
    router = TestBed.inject(Router);

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
  });

  describe('component initialization', () => {
    it('should create', () => {
      expect(component).toBeTruthy();
    });

    it('should have form with email and password controls', () => {
      expect(component.form.get('email')).toBeDefined();
      expect(component.form.get('password')).toBeDefined();
    });

    it('should have hide property set to true initially', () => {
      expect(component.hide).toBe(true);
    });

    it('should have onError property set to false initially', () => {
      expect(component.onError).toBe(false);
    });
  });

  describe('form validation', () => {
    it('should invalidate form with empty email', () => {
      component.form.patchValue({
        email: '',
        password: 'password123',
      });

      expect(component.form.valid).toBe(false);
    });

    it('should invalidate form with invalid email', () => {
      component.form.patchValue({
        email: 'invalid-email',
        password: 'password123',
      });

      expect(component.form.valid).toBe(false);
    });

    it('should invalidate form with empty password', () => {
      component.form.patchValue({
        email: 'test@example.com',
        password: '',
      });

      expect(component.form.valid).toBe(false);
    });

    it('should validate form with valid email and password', () => {
      component.form.patchValue({
        email: 'test@example.com',
        password: 'password123',
      });

      expect(component.form.valid).toBe(true);
    });
  });

  describe('submit method', () => {
    beforeEach(() => {
      fixture.detectChanges();
    });

    it('should call authService.login with form values', () => {
      authService.login.mockReturnValue(of(mockSessionInformation));
      component.form.patchValue({
        email: 'test@example.com',
        password: 'password123',
      });

      component.submit();

      expect(authService.login).toHaveBeenCalledWith({
        email: 'test@example.com',
        password: 'password123',
      });
    });

    it('should call sessionService.logIn and router.navigate on successful login', () => {
      authService.login.mockReturnValue(of(mockSessionInformation));
      component.form.patchValue({
        email: 'test@example.com',
        password: 'password123',
      });

      component.submit();

      expect(sessionService.logIn).toHaveBeenCalledWith(mockSessionInformation);
      expect(router.navigate).toHaveBeenCalledWith(['/sessions']);
    });

    it('should set onError to true when login fails', () => {
      authService.login.mockReturnValue(throwError(() => ({ error: 'Unauthorized' })));
      component.form.patchValue({
        email: 'test@example.com',
        password: 'wrongpassword',
      });

      component.submit();

      expect(component.onError).toBe(true);
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

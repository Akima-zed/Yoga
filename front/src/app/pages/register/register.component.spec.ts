import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';

import { RegisterComponent } from './register.component';
import { AuthService } from 'src/app/core/service/auth.service';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  let authService: any;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  let router: any;

  beforeEach(async () => {
    const authServiceMock = {
      register: jest.fn(),
    };
    const routerMock = {
      navigate: jest.fn(),
    };

    await TestBed.configureTestingModule({
      imports: [
        RegisterComponent,
        BrowserAnimationsModule,
        HttpClientModule,
        ReactiveFormsModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
      ],
      providers: [
        { provide: AuthService, useValue: authServiceMock },
        { provide: Router, useValue: routerMock },
        FormBuilder,
      ],
    }).compileComponents();

    authService = TestBed.inject(AuthService);
    router = TestBed.inject(Router);

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
  });

  describe('component initialization', () => {
    it('should create', () => {
      expect(component).toBeTruthy();
    });

    it('should have form with all required fields', () => {
      expect(component.form.get('email')).toBeDefined();
      expect(component.form.get('firstName')).toBeDefined();
      expect(component.form.get('lastName')).toBeDefined();
      expect(component.form.get('password')).toBeDefined();
    });

    it('should have onError property set to false initially', () => {
      expect(component.onError).toBe(false);
    });
  });

  describe('form validation', () => {
    it('should invalidate form with empty fields', () => {
      component.form.patchValue({
        email: '',
        firstName: '',
        lastName: '',
        password: '',
      });

      expect(component.form.valid).toBe(false);
    });

    it('should invalidate form with invalid email', () => {
      component.form.patchValue({
        email: 'invalid-email',
        firstName: 'John',
        lastName: 'Doe',
        password: 'password123',
      });

      expect(component.form.valid).toBe(false);
    });

    it('should validate form with all valid fields', () => {
      component.form.patchValue({
        email: 'test@example.com',
        firstName: 'John',
        lastName: 'Doe',
        password: 'password123',
      });

      expect(component.form.valid).toBe(true);
    });
  });

  describe('submit method', () => {
    beforeEach(() => {
      fixture.detectChanges();
    });

    it('should call authService.register with form values', () => {
      authService.register.mockReturnValue(of(null));
      component.form.patchValue({
        email: 'test@example.com',
        firstName: 'John',
        lastName: 'Doe',
        password: 'password123',
      });

      component.submit();

      expect(authService.register).toHaveBeenCalledWith({
        email: 'test@example.com',
        firstName: 'John',
        lastName: 'Doe',
        password: 'password123',
      });
    });

    it('should navigate to login on successful registration', () => {
      authService.register.mockReturnValue(of(null));
      component.form.patchValue({
        email: 'test@example.com',
        firstName: 'John',
        lastName: 'Doe',
        password: 'password123',
      });

      component.submit();

      expect(router.navigate).toHaveBeenCalledWith(['/login']);
    });

    it('should set onError to true when registration fails', () => {
      authService.register.mockReturnValue(throwError(() => ({ error: 'Email already exists' })));
      component.form.patchValue({
        email: 'existing@example.com',
        firstName: 'John',
        lastName: 'Doe',
        password: 'password123',
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

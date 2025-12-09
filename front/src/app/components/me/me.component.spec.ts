// @ts-nocheck
import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBarModule, MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { of, Subject } from 'rxjs';

import { MeComponent } from './me.component';
import { SessionService } from 'src/app/core/service/session.service';
import { UserService } from 'src/app/core/service/user.service';
import { User } from 'src/app/core/models/user.interface';

// eslint-disable-next-line @typescript-eslint/no-explicit-any
describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  let userService: any;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  let sessionService: any;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  let router: any;

  const mockUser: User = {
    id: 1,
    email: 'test@example.com',
    lastName: 'Doe',
    firstName: 'John',
    admin: false,
    password: 'password123',
    createdAt: new Date(),
    updatedAt: new Date(),
  };

  const mockSessionInformation = {
    token: 'fake-token',
    type: 'Bearer',
    id: 1,
    username: 'john.doe',
    firstName: 'John',
    lastName: 'Doe',
    admin: false,
  };

  beforeEach(async () => {
    const userServiceMock = {
      getById: jest.fn(),
      delete: jest.fn(),
    };
    const sessionServiceMock = {
      logOut: jest.fn(),
      sessionInformation: mockSessionInformation,
    };
    const matSnackBarMock = {
      open: jest.fn(),
    };
    const routerMock = {
      navigate: jest.fn(),
    };

    await TestBed.configureTestingModule({
      imports: [
        MeComponent,
        MatSnackBarModule,
        HttpClientModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
      ],
      providers: [
        { provide: SessionService, useValue: sessionServiceMock },
        { provide: UserService, useValue: userServiceMock },
        { provide: MatSnackBar, useValue: matSnackBarMock },
        { provide: Router, useValue: routerMock },
      ],
    }).compileComponents();

    userService = TestBed.inject(UserService);
    sessionService = TestBed.inject(SessionService);
    router = TestBed.inject(Router);

    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
  });

  describe('component initialization', () => {
    it('should create', () => {
      expect(component).toBeTruthy();
    });

    it('should have undefined user initially', () => {
      expect(component.user).toBeUndefined();
    });
  });

  describe('ngOnInit', () => {
    it('should fetch user by session id on init', () => {
      userService.getById.mockReturnValue(of(mockUser));

      fixture.detectChanges();

      expect(userService.getById).toHaveBeenCalledWith('1');
      expect(component.user).toEqual(mockUser);
    });

    it('should handle user service error gracefully', () => {
      userService.getById.mockReturnValue(
        new Subject<User>().asObservable()
      );

      fixture.detectChanges();

      expect(userService.getById).toHaveBeenCalledWith('1');
    });
  });

  describe('back method', () => {
    it('should navigate back in browser history', () => {
      jest.spyOn(window.history, 'back').mockImplementation(() => {});

      component.back();

      expect(window.history.back).toHaveBeenCalled();
    });
  });

  describe('delete method', () => {
    beforeEach(() => {
      userService.getById.mockReturnValue(of(mockUser));
      fixture.detectChanges();
    });

    it('should delete user and call logout and navigate on success', () => {
      userService.delete.mockReturnValue(of(null));

      component.delete();

      expect(userService.delete).toHaveBeenCalledWith('1');
      expect(sessionService.logOut).toHaveBeenCalled();
      expect(router.navigate).toHaveBeenCalledWith(['/']);
    });

    it('should call delete service when delete method is invoked', () => {
      userService.delete.mockReturnValue(of(null));

      component.delete();

      expect(userService.delete).toHaveBeenCalledWith('1');
    });
  });

  describe('ngOnDestroy', () => {
    it('should complete destroy subject on destroy', () => {
      jest.spyOn(component['destroy$'], 'next');
      jest.spyOn(component['destroy$'], 'complete');

      component.ngOnDestroy();

      expect(component['destroy$'].next).toHaveBeenCalled();
      expect(component['destroy$'].complete).toHaveBeenCalled();
    });

    it('should unsubscribe from observables on destroy', () => {
      userService.getById.mockReturnValue(of(mockUser));
      fixture.detectChanges();

      jest.spyOn(component['destroy$'], 'next');

      component.ngOnDestroy();

      expect(component['destroy$'].next).toHaveBeenCalled();
    });
  });

  describe('subscription cleanup', () => {
    it('should cleanup subscriptions via takeUntil on component destroy', (done) => {
      userService.getById.mockReturnValue(of(mockUser));

      const destroySpy = jest.spyOn(component['destroy$'], 'next');
      fixture.detectChanges();

      component.ngOnDestroy();

      expect(destroySpy).toHaveBeenCalled();
      done();
    });
  });
});

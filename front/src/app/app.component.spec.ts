import { HttpClientModule } from '@angular/common/http';
// @ts-nocheck
import { TestBed } from '@angular/core/testing';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';

import { AppComponent } from './app.component';


describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatToolbarModule,
        AppComponent
      ],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it('should call $isLogged and return observable', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    const sessionService = TestBed.inject(app["sessionService"].constructor);
    jest.spyOn(sessionService, '$isLogged').mockReturnValue('observable' as any);
    expect(app.$isLogged()).toBe('observable');
  });

  it('should call logout and navigate', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    const sessionService = TestBed.inject(app["sessionService"].constructor);
    const router = TestBed.inject(app["router"].constructor);
    const logOutSpy = jest.spyOn(sessionService, 'logOut');
    const navigateSpy = jest.spyOn(router, 'navigate');
    app.logout();
    expect(logOutSpy).toHaveBeenCalled();
    expect(navigateSpy).toHaveBeenCalledWith(['']);
  });
});

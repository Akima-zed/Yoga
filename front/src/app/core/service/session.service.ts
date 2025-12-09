import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { SessionInformation } from '../models/sessionInformation.interface';

/**
 * SessionService - Gestion de la session utilisateur
 *
 * Service responsable de:
 * - Maintenir l'état de connexion/déconnexion
 * - Stocker les informations de session de l'utilisateur
 * - Émettre des observables sur l'état de la session
 * - Gérer la connexion et déconnexion
 *
 * @injectable
 * @example
 * constructor(private sessionService: SessionService) { }
 * this.sessionService.$isLogged().subscribe(...);
 * this.sessionService.logIn(user);
 * this.sessionService.logOut();
 */
@Injectable({
  providedIn: 'root'
})
export class SessionService {

  public isLogged = false;
  public sessionInformation: SessionInformation | undefined;

  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);

  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  public logIn(user: SessionInformation): void {
    this.sessionInformation = user;
    this.isLogged = true;
    this.next();
  }

  public logOut(): void {
    this.sessionInformation = undefined;
    this.isLogged = false;
    this.next();
  }

  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
  }
}

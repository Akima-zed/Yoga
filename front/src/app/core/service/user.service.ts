import { HttpClient } from '@angular/common/http';
import { inject } from '@angular/core';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../models/user.interface';

/**
 * UserService - Gestion des utilisateurs
 *
 * Service responsable de:
 * - Récupérer les informations d'un utilisateur
 * - Supprimer le compte d'un utilisateur
 * - Communication avec l'API utilisateurs
 *
 * @injectable
 * @example
 * constructor(private userService: UserService) { }
 * this.userService.getById('1').subscribe(...);
 */
@Injectable({
  providedIn: 'root'
})
export class UserService {
  private pathService = 'api/user';
  private httpClient = inject(HttpClient);

  public getById(id: string): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService}/${id}`);
  }

  public delete(id: string): Observable<unknown> {
    return this.httpClient.delete(`${this.pathService}/${id}`);
  }
}

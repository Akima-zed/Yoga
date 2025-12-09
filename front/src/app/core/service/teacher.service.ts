import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Teacher } from '../models/teacher.interface';

/**
 * TeacherService - Gestion des professeurs
 *
 * Service responsable de:
 * - Récupérer tous les professeurs
 * - Récupérer les détails d'un professeur
 * - Communication avec l'API professeurs
 *
 * @injectable
 * @example
 * constructor(private teacherService: TeacherService) { }
 * this.teacherService.all().subscribe(...);
 */
@Injectable({
  providedIn: 'root'
})
export class TeacherService {

  private pathService = 'api/teacher';

  constructor(private httpClient: HttpClient) { }

  public all(): Observable<Teacher[]> {
    return this.httpClient.get<Teacher[]>(this.pathService);
  }

  public detail(id: string): Observable<Teacher> {
    return this.httpClient.get<Teacher>(`${this.pathService}/${id}`);
  }
}

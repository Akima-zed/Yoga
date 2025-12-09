import { Component, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { SessionInformation } from '../../../../core/models/sessionInformation.interface';
import { SessionService } from '../../../../core/service/session.service';
import { Session } from '../../../../core/models/session.interface';
import { SessionApiService } from '../../../../core/service/session-api.service';
import { MaterialModule } from "../../../../shared/material.module";
import { CommonModule } from "@angular/common";
import { RouterModule } from "@angular/router";

/**
 * ListComponent - Liste des séances
 *
 * Affiche toutes les séances de yoga disponibles:
 * - Liste avec image, titre, date et description
 * - Lien pour consulter les détails d'une séance
 * - Lien pour créer une nouvelle séance (administrateur)
 * - Affiche l'utilisateur connecté en haut de la page
 *
 * @component
 * @standalone
 * @example
 * // Dans app.routes.ts
 * { path: 'sessions', component: ListComponent }
 */
@Component({
  selector: 'app-list',
   standalone: true,
  imports: [CommonModule, MaterialModule, RouterModule],
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent {
  private sessionApiService = inject(SessionApiService);
  private sessionService = inject(SessionService);

  // consommé directement dans le template via (sessions$ | async).
  public sessions$: Observable<Session[]> = this.sessionApiService.all();

  get user(): SessionInformation | undefined {
    return this.sessionService.sessionInformation;
  }
}

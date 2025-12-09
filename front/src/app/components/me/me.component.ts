import { Component, OnInit, inject, OnDestroy } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { User } from '../../core/models/user.interface';
import { SessionService } from '../../core/service/session.service';
import { UserService } from '../../core/service/user.service';
import { MaterialModule } from "../../shared/material.module";
import { CommonModule } from "@angular/common";
import { Subject, takeUntil } from 'rxjs';

/**
 * MeComponent - Profil utilisateur
 *
 * Affiche les informations de l'utilisateur connecté et permet:
 * - Consulter son profil
 * - Supprimer son compte
 * - Revenir à la page précédente
 *
 * @component
 * @standalone
 * @example
 * // Dans app.routes.ts
 * { path: 'me', component: MeComponent }
 */
@Component({
  selector: 'app-me',
  standalone: true,
  imports: [CommonModule, MaterialModule],
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit, OnDestroy {
  private router = inject(Router);
  private sessionService = inject(SessionService);
  private matSnackBar = inject(MatSnackBar);
  private userService = inject(UserService);
  public user: User | undefined;
  private destroy$ = new Subject<void>();


  ngOnInit(): void {
    // Récupère l'utilisateur depuis la session et subscribe
    // avec takeUntil pour nettoyer les subscriptions à la destruction
    this.userService
      .getById(this.sessionService.sessionInformation!.id.toString())
      .pipe(takeUntil(this.destroy$))
      .subscribe((user: User) => this.user = user);
  }

  /**
   * Revient à la page précédente dans l'historique du navigateur
   *
   * @returns void
   */
  public back(): void {
    window.history.back();
  }

  /**
   * Supprime le compte utilisateur et le déconnecte
   * Affiche un message de confirmation et redirige vers l'accueil
   *
   * @returns void
   */
  public delete(): void {
    this.userService
      .delete(this.sessionService.sessionInformation!.id.toString())
      .pipe(takeUntil(this.destroy$))
      .subscribe(() => {
        this.matSnackBar.open("Your account has been deleted !", 'Close', { duration: 3000 });
        this.sessionService.logOut();
        this.router.navigate(['/']);
      })
  }

  /**
   * Nettoie les ressources du composant
   * Complète le subject destroy$ pour déclencher takeUntil sur toutes les subscriptions
   *
   * @returns void
   */
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
    }

}

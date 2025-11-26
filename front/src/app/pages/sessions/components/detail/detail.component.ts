import { Component, OnInit, OnDestroy, inject } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject, of, forkJoin } from 'rxjs';
import { takeUntil, catchError } from 'rxjs/operators';

import { Teacher } from '../../../../core/models/teacher.interface';
import { Session } from '../../../../core/models/session.interface';
import { SessionService } from '../../../../core/service/session.service';
import { SessionApiService } from '../../../../core/service/session-api.service';
import { TeacherService } from '../../../../core/service/teacher.service';
import { MaterialModule } from "../../../../shared/material.module";
import { CommonModule } from "@angular/common";

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss'],
  imports: [CommonModule, MaterialModule],
  standalone: true
})
export class DetailComponent implements OnInit, OnDestroy {
  public session?: Session;
  public teacher?: Teacher;
  public isParticipate = false;
  public isAdmin = false;
  public sessionId: string;
  public userId: string;

  private destroy$ = new Subject<void>();

  private route = inject(ActivatedRoute);
  private fb = inject(FormBuilder);
  private sessionService = inject(SessionService);
  private sessionApiService = inject(SessionApiService);
  private teacherService = inject(TeacherService);
  private matSnackBar = inject(MatSnackBar);
  private router = inject(Router);

  constructor() {
    this.sessionId = this.route.snapshot.paramMap.get('id')!;
    const sessionInfo = this.sessionService.sessionInformation;
    this.isAdmin = sessionInfo?.admin ?? false;
    this.userId = sessionInfo?.id.toString() ?? '';
  }

  ngOnInit(): void {
    this.fetchSession();
  }

  public back(): void {
    this.router.navigate(['sessions']);
  }

  public delete(): void {
    this.sessionApiService.delete(this.sessionId)
      .pipe(
        takeUntil(this.destroy$),
        catchError(err => {
          this.matSnackBar.open('Erreur lors de la suppression de la session', 'Close', { duration: 3000 });
          return of(null);
        })
      )
      .subscribe(() => {
        this.matSnackBar.open('Session supprimée !', 'Close', { duration: 3000 });
        this.router.navigate(['sessions']);
      });
  }

  public participate(): void {
    this.sessionApiService.participate(this.sessionId, this.userId)
      .pipe(
        takeUntil(this.destroy$),
        catchError(err => {
          this.matSnackBar.open('Erreur lors de la participation', 'Close', { duration: 3000 });
          return of(null);
        })
      )
      .subscribe(() => this.fetchSession());
  }

  public unParticipate(): void {
    this.sessionApiService.unParticipate(this.sessionId, this.userId)
      .pipe(
        takeUntil(this.destroy$),
        catchError(err => {
          this.matSnackBar.open('Erreur lors du désabonnement', 'Close', { duration: 3000 });
          return of(null);
        })
      )
      .subscribe(() => this.fetchSession());
  }

  private fetchSession(): void {
    this.sessionApiService.detail(this.sessionId)
      .pipe(
        takeUntil(this.destroy$),
        catchError(err => {
          this.matSnackBar.open('Erreur lors du chargement de la session', 'Close', { duration: 3000 });
          return of(undefined);
        })
      )
      .subscribe(session => {
        if (!session) return;
        this.session = session;
        this.isParticipate = session.users.includes(this.sessionService.sessionInformation!.id);

        this.teacherService.detail(session.teacher_id.toString())
          .pipe(
            takeUntil(this.destroy$),
            catchError(err => {
              this.matSnackBar.open('Erreur lors du chargement du professeur', 'Close', { duration: 3000 });
              return of(undefined);
            })
          )
          .subscribe(teacher => {
            if (teacher) this.teacher = teacher;
          });
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}

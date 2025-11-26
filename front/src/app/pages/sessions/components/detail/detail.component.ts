import { Component, OnInit, inject, OnDestroy } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Teacher } from '../../../../core/models/teacher.interface';
import { SessionService } from '../../../../core/service/session.service';
import { TeacherService } from '../../../../core/service/teacher.service';
import { Session } from '../../../../core/models/session.interface';
import { SessionApiService } from '../../../../core/service/session-api.service';
import { MaterialModule } from "../../../../shared/material.module";
import { CommonModule } from "@angular/common";
import { Subject, forkJoin, takeUntil, catchError, of } from 'rxjs';

@Component({
  selector: 'app-detail',
  imports: [CommonModule, MaterialModule],
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit, OnDestroy {
  public session: Session | undefined;
  public teacher: Teacher | undefined;
  public isParticipate = false;
  public isAdmin = false;
  public sessionId: string;
  public userId: string;

  private route = inject(ActivatedRoute);
  private fb = inject(FormBuilder);
  private sessionService = inject(SessionService);
  private sessionApiService = inject(SessionApiService);
  private teacherService = inject(TeacherService);
  private matSnackBar = inject(MatSnackBar);
  private router = inject(Router);

  private destroy$ = new Subject<void>();

  constructor() {
    this.sessionId = this.route.snapshot.paramMap.get('id')!;
    this.isAdmin = this.sessionService.sessionInformation!.admin;
    this.userId = this.sessionService.sessionInformation!.id.toString();
  }

  ngOnInit(): void {
    this.fetchSession();
  }

  public back() {
    //window.history.back();
    this.router.navigate(['sessions']);
  }

  public delete(): void {
    this.sessionApiService
      .delete(this.sessionId)
      .pipe(takeUntil(this.destroy$),
            catchError(err => {
              this.matSnackBar.open('Erreur lors de la suppression de la session', 'Close', { duration: 3000 });
              return of(null);
            })
          )
      .subscribe((_: any) => {
          this.matSnackBar.open('Session deleted !', 'Close', { duration: 3000 });
          this.router.navigate(['sessions']);
        }
      );
  }

  public participate(): void {
    this.sessionApiService
      .participate(this.sessionId, this.userId)
      .pipe(takeUntil(this.destroy$),
            catchError(err => {
              this.matSnackBar.open('Erreur lors de la participation', 'Close', { duration: 3000 });
              return of(null);
           })
      )
      .subscribe(_ => this.fetchSession());
  }

  public unParticipate(): void {
    this.sessionApiService
    .unParticipate(this.sessionId, this.userId)
    .pipe(takeUntil(this.destroy$),
         catchError(err => {
           this.matSnackBar.open('Erreur lors de la participation', 'Close', { duration: 3000 });
           return of(undefined);
         })
       )
    .subscribe(_ => this.fetchSession());
  }

  private fetchSession(): void {
    this.sessionApiService
      .detail(this.sessionId)
      .pipe(takeUntil(this.destroy$),
            catchError(err => {
              this.matSnackBar.open('Erreur lors du désabonnement', 'Close', { duration: 3000 });
              return of(undefined);
            })
          )
      .subscribe((session: Session) => {
        if (!session) return;
        this.session = session;
        this.isParticipate = session.users.some(u => u === this.sessionService.sessionInformation!.id);
        this.teacherService
          .detail(session.teacher_id.toString())
          .pipe(takeUntil(this.destroy$),
               catchError(err => {
                this.matSnackBar.open('Erreur lors du chargement du professeur', 'Close', { duration: 3000 });
                return of(undefined);
          })
        )
          .subscribe(teacher => {
            if (teacher) this.teacher = teacher;
      });
  }

  ngOnDestroy(): void {
      this.destroy$.next();
      this.destroy$.complete();
  }

}


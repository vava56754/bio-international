import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  private notificationSubject = new Subject<{ message: string, type: string }>();

  notification$ = this.notificationSubject.asObservable();

  constructor() { }

  showNotification(message: string, type: string) {
    this.notificationSubject.next({message, type});
  }
}

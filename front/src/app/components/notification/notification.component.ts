import { Component, OnDestroy, OnInit } from '@angular/core';
import { NotificationService } from 'src/app/services/notification/notification.service';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.scss']
})
export class NotificationComponent implements OnInit {
  notification!: { message: string, type: string };

  constructor(private notificationService: NotificationService) { }

  ngOnInit() {
    this.notificationService.notification$.subscribe(notification => {
      this.notification = notification;
      setTimeout(() => this.notification = {"message":"","type":""}, 2000); // Efface la notification après 2 secondes
    });
  }

}

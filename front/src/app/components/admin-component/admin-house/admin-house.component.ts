import { CSP_NONCE, Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ConfirmDialogComponent } from 'src/app/components/confirm-dialog/confirm-dialog.component';
import { FIELD } from 'src/app/core/constants/constants';
import { LOG_MESSAGES } from 'src/app/core/constants/log-messages';
import { MESSAGE } from 'src/app/core/constants/messages';
import { House } from 'src/app/models/house/house.model';
import { FileService } from 'src/app/services/file/file.service';
import { HouseService } from 'src/app/services/house/house.service';
import { NotificationService } from 'src/app/services/notification/notification.service';

@Component({
  selector: 'app-admin-house',
  templateUrl: './admin-house.component.html',
  styleUrls: ['./admin-house.component.scss']
})
export class AdminHouseComponent implements OnInit, OnDestroy{
  houses: House[] = [];
  private subscription: Subscription = new Subscription();
  
  constructor(private houseService: HouseService, private fileService: FileService, private router: Router, public dialog: MatDialog, private notificationService: NotificationService) {}

  ngOnInit(): void {
    this.subscription.add(this.getHouses());
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  openDeleteConfirmationHouse(houseId: number): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent);
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleteHouse(houseId)
      }
    });
  }

  refreshPage() {
    location.reload();
  }

  goCreateHouse(): void {
    this.router.navigate(['/admin-house-create']);
  }

  goToHouseDetail(houseId: number): void {
    this.router.navigate(['/admin-house', houseId]);
  }

  getHouses(): Subscription{
    return this.houseService.getHouses().subscribe({
      next: (houses) => {
        this.houses = houses;
        this.houses.forEach(house => {
          this.fileService.getFile(house.houseLink).subscribe({
            next: (file) => {
              const reader = new FileReader();
              reader.onloadend = () => {
                house.houseImageUrl = reader.result as string;
              };
              reader.readAsDataURL(file);
            },
            error: (e) => {
              console.error(LOG_MESSAGES.fileRetrieval.error, e);
            }
          })
        })
      },
      error: (e) => {
        console.error(LOG_MESSAGES.houseRetrieval.error);
      }
    });
  }

  deleteHouse(id: number):void {
    this.houseService.deleteHouse(id).subscribe({
      next: () => {
        this.getHouses();
      },
      error: (e) => {
        console.error(LOG_MESSAGES.delete.error, e);
        this.notificationService.showNotification(MESSAGE.DELETE.FAIL, FIELD.RESULT.ERROR);
      },
      complete: () => {
        this.notificationService.showNotification(MESSAGE.DELETE.SUCCESS, FIELD.RESULT.SUCCESS);
      }
    });
    
  }
}

import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { LOG_MESSAGES } from 'src/app/core/constants/log-messages';
import { Procurement } from 'src/app/models/procurement/procurement.model';
import { FileService } from 'src/app/services/file/file.service';
import { NotificationService } from 'src/app/services/notification/notification.service';
import { ProcurementService } from 'src/app/services/procurement/procurement.service';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from 'src/app/components/confirm-dialog/confirm-dialog.component';
import { FIELD } from 'src/app/core/constants/constants';
import { MESSAGE } from 'src/app/core/constants/messages';

@Component({
  selector: 'app-admin-procurement',
  templateUrl: './admin-procurement.component.html',
  styleUrls: ['./admin-procurement.component.scss']
})
export class AdminProcurementComponent implements OnInit, OnDestroy  {
  validateProcurementList: Procurement[] = [];
  private subscription: Subscription = new Subscription();

  constructor(private procurementService: ProcurementService, private fileService: FileService, private router: Router, private notificationService: NotificationService, public dialog: MatDialog) {}

  ngOnInit(): void {
    this.subscription.add(this.getValidateProcurement());
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  getValidateProcurement(): Subscription {
    return this.procurementService.getAllValidateProcurement().subscribe({
      next: (procurements) => {
        this.validateProcurementList = procurements;
        this.validateProcurementList.forEach((procurement) => {
          procurement.lineProcurementList.forEach((line)=> {
            this.fileService.getFile(line.product.productLink).subscribe({
              next: (file) => {
                const reader = new FileReader();
                reader.onloadend = () => {
                  line.product.productImageUrl = reader.result as string;
                };
                reader.readAsDataURL(file);
              },
              error: (e) => {
                console.error(LOG_MESSAGES.fileRetrieval.error, e)
              }
            })
          })
        })
      },
      error: (e) => {
        console.error(LOG_MESSAGES.procurementRetrieval.error, e)
      }
    })
  }

  goToProcurementDetail(procurementId: number) {
    this.router.navigate(['/admin-procurement', procurementId]);
  }

  completeProcurement(procurement: Procurement) {
    this.procurementService.completeProcurement(procurement).subscribe({
      next: () => {
        this.getValidateProcurement();
      },
      error: (e) => {
        console.error(LOG_MESSAGES.delete.error, e);
        this.notificationService.showNotification(MESSAGE.UPDATE.FAIL, FIELD.RESULT.ERROR);
      },
      complete: () => {
        this.notificationService.showNotification(MESSAGE.UPDATE.SUCCESS, FIELD.RESULT.SUCCESS);
      }
    })
  }

  openDeleteConfirmationProcurement(procurementId: number): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent);
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleteProcurement(procurementId)
      }
    });
  }

  deleteProcurement(procurementId: number) {
    this.procurementService.deleteProcurement(procurementId).subscribe({
      next: () => {
        this.notificationService.showNotification(MESSAGE.DELETE.SUCCESS, FIELD.RESULT.SUCCESS);
      },
      error: (e) => {
        console.error(LOG_MESSAGES.delete.error, e);
        this.notificationService.showNotification(MESSAGE.DELETE.FAIL, FIELD.RESULT.ERROR);
      },
      complete: () => {
        this.getValidateProcurement();
      }
    })
  }

}

import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ConfirmDialogComponent } from 'src/app/components/confirm-dialog/confirm-dialog.component';
import { FIELD } from 'src/app/core/constants/constants';
import { LOG_MESSAGES } from 'src/app/core/constants/log-messages';
import { MESSAGE } from 'src/app/core/constants/messages';
import { LineProcurement } from 'src/app/models/procurement/line-procurement.model';
import { Procurement } from 'src/app/models/procurement/procurement.model';
import { FileService } from 'src/app/services/file/file.service';
import { NotificationService } from 'src/app/services/notification/notification.service';
import { ProcurementService } from 'src/app/services/procurement/procurement.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-admin-procurement-detail',
  templateUrl: './admin-procurement-detail.component.html',
  styleUrls: ['./admin-procurement-detail.component.scss']
})
export class AdminProcurementDetailComponent implements OnInit, OnDestroy{
  id: number = 0;
  procurement: Procurement = {} as Procurement;
  private subcription: Subscription = new Subscription();

  constructor(private procurementService: ProcurementService, private fileService: FileService ,private route: ActivatedRoute, private router: Router, private notificationService: NotificationService,  public dialog: MatDialog, private location: Location) {
  
  }

  ngOnInit(): void {
    this.route.params.subscribe(param => {
      this.id = Number.parseInt(this.route.snapshot.params['procurementId']);
      this.subcription.add(this.getCurrentProcurement(this.id));

    })
    
  }

  ngOnDestroy(): void {
    this.subcription.unsubscribe();
  }

  

  getCurrentProcurement(id: number): Subscription {
    return this.procurementService.getProcurement(id).subscribe({
      next: (procurement) => {
        this.procurement = procurement;
        this.procurement.lineProcurementList.forEach((line) => {
          this.fileService.getFile(line.product.productLink).subscribe({
            next: (file) => {
              const reader = new FileReader();
              reader.onloadend = () => {
                line.product.productImageUrl = reader.result as string;
              };
              reader.readAsDataURL(file);
            },
            error: (e) => console.error(LOG_MESSAGES.fileRetrieval.error, e)
          })
        })
      },
      error: (e) => console.error(LOG_MESSAGES.procurementRetrieval.error, e)
    })
  }

  

  

  completeProcurement(procurement: Procurement): void {
    this.procurementService.completeProcurement(procurement).subscribe({
      next: () => {
        this.notificationService.showNotification(MESSAGE.UPDATE.SUCCESS, FIELD.RESULT.SUCCESS);
      },
      error: (e) => {
        console.error(LOG_MESSAGES.delete.error, e);
        this.notificationService.showNotification(MESSAGE.UPDATE.FAIL, FIELD.RESULT.ERROR);
      },
      complete: () => {
        setTimeout(() => {
          this.router.navigate(["/admin-procurement"]);
        }, 2500);
      }
    })
      

  }

  updateLineProcurement(line: LineProcurement): void {
    if(line.lineQuantity != 0) {
      this.procurementService.updateLineProcurement(line).subscribe({
        next : () => {
          this.notificationService.showNotification(MESSAGE.UPDATE.SUCCESS, FIELD.RESULT.SUCCESS);
        },
        error: (e) => {
          console.error(LOG_MESSAGES.update.error, e);
          this.notificationService.showNotification(MESSAGE.UPDATE.FAIL, FIELD.RESULT.ERROR);
        },
        complete: () => {
          this.getCurrentProcurement(this.id);
        }
      })
    }else {
      this.deleteLineProcurement(line.lineId);
    }
    
  }

  openDeleteConfirmationLineProcurement(procurementId: number): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent);
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleteLineProcurement(procurementId)
      }
    });
  }

  openDeleteConfirmationProcurement(): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent);
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleteProcurement(this.id)
      }
    });
  }

  deleteLineProcurement(id: number): void {
    this.procurementService.deleteLineProcurement(id).subscribe({
      next: () => {
        this.notificationService.showNotification(MESSAGE.DELETE.SUCCESS, FIELD.RESULT.SUCCESS);
      },
      error: (e) => {
        console.error(LOG_MESSAGES.delete.error, e);
        this.notificationService.showNotification(MESSAGE.DELETE.FAIL, FIELD.RESULT.ERROR);
      },
      complete: () => {
        this.getCurrentProcurement(this.id);
      }
    })
  }

  deleteProcurement(procurementId: number): void {
    this.procurementService.deleteProcurement(procurementId).subscribe({
      next: () => {
        this.notificationService.showNotification(MESSAGE.DELETE.SUCCESS, FIELD.RESULT.SUCCESS);
      },
      error: (e) => {
        console.error(LOG_MESSAGES.delete.error, e);
        this.notificationService.showNotification(MESSAGE.DELETE.FAIL, FIELD.RESULT.ERROR);
      },
      complete: () => {
        setTimeout(() => {
          this.router.navigate(["/admin-procurement"]);
        }, 2500);
      }
    })
  }

  back(): void {
    this.location.back();
  }

  

}

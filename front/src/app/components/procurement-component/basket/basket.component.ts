import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
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

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrls: ['./basket.component.scss']
})
export class BasketComponent implements OnInit, OnDestroy{
  procurement: Procurement = {} as Procurement;
  pastProcurement: Procurement[] = [];
  private subcription: Subscription = new Subscription();
  loading: boolean = false;

  constructor(private procurementService: ProcurementService, private fileService: FileService, private router: Router, public dialog: MatDialog, private notificationService: NotificationService) {
  
  }

  ngOnInit(): void {
    this.subcription.add(this.getCurrentProcurement());
  }

  ngOnDestroy(): void {
    this.subcription.unsubscribe();
  }

  getCurrentProcurement(): Subscription {
    return this.procurementService.getCurrentProcurement().subscribe({
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

 

  validateProcurement() {
    this.loading = true;
    this.procurementService.validateProcurement(this.procurement).subscribe({
      next: (res) => {
        this.notificationService.showNotification(MESSAGE.PROCUREMENT.SUCCESS, FIELD.RESULT.SUCCESS)
      },
      error: (e) => {
        console.error(LOG_MESSAGES.create.error, e)
        this.notificationService.showNotification(MESSAGE.PROCUREMENT.FAIL, FIELD.RESULT.ERROR)
      },
      complete: () => {
        this.loading = false;
        setTimeout(() => {
          this.router.navigate(["/procurement"]);
        }, 1000);
      }, 
    })
  }

  updateLineProcurement(line: LineProcurement): void {
    if(line.lineQuantity > 0) {
      this.procurementService.updateLineProcurement(line).subscribe({
        next : () => {
          this.notificationService.showNotification(MESSAGE.UPDATE.SUCCESS, FIELD.RESULT.SUCCESS);
        },
        error: (e) => {
          console.error(LOG_MESSAGES.update.error, e);
          this.notificationService.showNotification(MESSAGE.UPDATE.FAIL, FIELD.RESULT.ERROR);
        },
        complete: () => {
          this.getCurrentProcurement();
        }
      })
    }else if(line.lineQuantity < 0) {
      this.notificationService.showNotification(MESSAGE.UPDATE.FAIL, FIELD.RESULT.ERROR);
    }
    else {
      this.deleteLineProcurement(line.lineId);
    }
    
  }

  goToProductDetail(productId: number): void {
    this.router.navigate(['/product', productId]);
  }

  openValideConfirmationProcurement(): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent);
    dialogRef.componentInstance.title = 'Confirmer la commande'
    dialogRef.componentInstance.message = 'Voulez-vous vraiment confirmer la commande ?'
    dialogRef.componentInstance.action = 'Confirmer'
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.validateProcurement()
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
        this.getCurrentProcurement();
      }
    })
  }
}

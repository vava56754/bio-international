import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { ConfirmDialogComponent } from 'src/app/components/confirm-dialog/confirm-dialog.component';
import { FIELD } from 'src/app/core/constants/constants';
import { LOG_MESSAGES } from 'src/app/core/constants/log-messages';
import { MESSAGE } from 'src/app/core/constants/messages';
import { Body } from 'src/app/models/body/body.model';
import { TypeProduct } from 'src/app/models/type/type.model';
import { BodyService } from 'src/app/services/body/body.service';
import { NotificationService } from 'src/app/services/notification/notification.service';
import { TypeService } from 'src/app/services/type/type.service';

@Component({
  selector: 'app-admin-type-body',
  templateUrl: './admin-type-body.component.html',
  styleUrls: ['./admin-type-body.component.scss']
})
export class AdminTypeBodyComponent implements OnInit, OnDestroy {
  typeProduct: TypeProduct[]= [];
  bodys: Body[]=[];
  bodyName: string="";
  typeName: string="";
  bodyNameUpdate: string="";
  typeNameUpdate: string="";
  displayTypeUpdate: boolean = false;
  displayBodyUpdate: boolean = false;
  selectedBodyIndex: number = -1;
  selectedTypeIndex: number = -1;
  private subscription: Subscription[] = [];
  
  constructor(private typeService: TypeService, private bodyService: BodyService,  private notificationService: NotificationService, public dialog: MatDialog) {}
  
  ngOnInit(): void {
    this.subscription.push(this.getBodys(), this.getTypes());
     
  }

  ngOnDestroy(): void {
      this.subscription.forEach(subscription => subscription.unsubscribe());
  }

  openDeleteConfirmationType(typeId: number): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent);
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleteType(typeId)
      }
    });
  }

  openDeleteConfirmationBody(bodyId: number): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent);
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleteBody(bodyId)
      }
    });
  }

  displayTypeInput(index: number): void {
    this.displayTypeUpdate = !this.displayTypeUpdate;
    this.selectedTypeIndex = index;
    this.typeNameUpdate = ""
  }

  displayBodyInput(index: number): void {
    this.displayBodyUpdate = !this.displayBodyUpdate;
    this.selectedBodyIndex = index;
    this.bodyNameUpdate = ""
  }

  createBody(name: string): void {
    if (!name || name =="") {
      this.notificationService.showNotification(MESSAGE.EMPTY_NAME, FIELD.RESULT.ERROR);
      return     
    }
    let body = new Body(name);
    this.bodyService.createBody(body).subscribe({
      next: () => {
        this.notificationService.showNotification(MESSAGE.CREATE.SUCCESS, FIELD.RESULT.SUCCESS)
      },
      error: (e) => {
        console.error(LOG_MESSAGES.create.error, e)
        this.notificationService.showNotification(MESSAGE.CREATE.FAIL, FIELD.RESULT.ERROR);
      },
      complete: () => {
        this.getBodys();
        this.bodyName = "";
      }
    });
  }

  createType(name: string): void {
    if (!name || name =="") {
      this.notificationService.showNotification(MESSAGE.EMPTY_NAME, FIELD.RESULT.ERROR);
      return     
    }
    let type = new TypeProduct(name);
    this.typeService.createType(type).subscribe({
      next: () => {
        this.notificationService.showNotification(MESSAGE.CREATE.SUCCESS, FIELD.RESULT.SUCCESS)
      },
      error: (e) => {
        console.error(LOG_MESSAGES.create.error, e)
        this.notificationService.showNotification(MESSAGE.CREATE.FAIL, FIELD.RESULT.ERROR);
      },
      complete: () => {
        this.getTypes();
        this.typeName = "";
      }
    });
  }

  updateBody(id:number, name: string, index: number): void {
    if (!name || name =="") {
      this.notificationService.showNotification(MESSAGE.EMPTY_NAME, FIELD.RESULT.ERROR);
      return     
    }
    let body = new Body(name);
    this.bodyService.updateBody(id,body).subscribe({
      next: () => {
        this.getBodys();
        this.displayBodyInput(index);
      },
      error: (e) => {
        console.error(LOG_MESSAGES.update.error, e)
        this.notificationService.showNotification(MESSAGE.UPDATE.FAIL, FIELD.RESULT.ERROR);
      },
      complete: () => {
        this.notificationService.showNotification(MESSAGE.UPDATE.SUCCESS, FIELD.RESULT.SUCCESS);
       
      }
    })
  }

  updateType(id: number, name: string, index: number): void {
    if (!name || name =="") {
      this.notificationService.showNotification(MESSAGE.EMPTY_NAME, FIELD.RESULT.ERROR);
      return     
    }
    let type = new TypeProduct(name);
    this.typeService.updateType(id, type).subscribe({
      next: () => {
        this.getTypes();
        this.displayTypeInput(index);
      },
      error: (e) => {
        console.error(LOG_MESSAGES.update.error, e)
        this.notificationService.showNotification(MESSAGE.UPDATE.FAIL, FIELD.RESULT.ERROR);
      },
      complete: () => {
        this.notificationService.showNotification(MESSAGE.UPDATE.SUCCESS, FIELD.RESULT.SUCCESS);
      }
    })
  }


  getTypes(): Subscription {
    return this.typeService.getTypes().subscribe({
      next : (types)=> {
        this.typeProduct = types;
      },
      error: (e) => {
        console.error(LOG_MESSAGES.typeRetrieval.error, e);
      }
    })
  
  }

  getBodys(): Subscription{
    return this.bodyService.getBodys().subscribe({
      next : (bodys)=>{
        this.bodys = bodys;
      },
      error : (e) => {
        console.error(LOG_MESSAGES.bodyRetrieval.error, e);
      }
    })
  }

  deleteType(id: number) {
    this.typeService.deleteType(id).subscribe({
      next: () => {
        this.getTypes();
      },
      error: (e) => {
        console.error(LOG_MESSAGES.delete.error, e)
        this.notificationService.showNotification(MESSAGE.DELETE.FAIL, FIELD.RESULT.ERROR);
      },
      complete: () => {
        this.notificationService.showNotification(MESSAGE.DELETE.SUCCESS, FIELD.RESULT.SUCCESS)
      }
    })
  }

  deleteBody(id: number) {
    this.bodyService.deleteBody(id).subscribe({
      next: () => {
        this.getBodys();
      },
      error: (e) => {
        console.error(LOG_MESSAGES.delete.error, e)
        this.notificationService.showNotification(MESSAGE.DELETE.FAIL, FIELD.RESULT.ERROR);
      },
      complete: () => {
        this.notificationService.showNotification(MESSAGE.DELETE.SUCCESS, FIELD.RESULT.SUCCESS)
      }
    })
  }

}

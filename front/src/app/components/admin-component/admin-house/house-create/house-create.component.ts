import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FIELD, FILE, HOUSE } from 'src/app/core/constants/constants';
import { LOG_MESSAGES } from 'src/app/core/constants/log-messages';
import { MESSAGE } from 'src/app/core/constants/messages';
import { House } from 'src/app/models/house/house.model';
import { HouseService } from 'src/app/services/house/house.service';
import { NotificationService } from 'src/app/services/notification/notification.service';

@Component({
  selector: 'app-house-create',
  templateUrl: './house-create.component.html',
  styleUrls: ['./house-create.component.scss']
})
export class HouseCreateComponent {
  houseCreateName = "";
  houseCreateDescription = "";
  selectedFile!: File;
  form?: HTMLFormElement
  constructor(private houseService: HouseService, private route: ActivatedRoute, private router: Router , private notificationService: NotificationService) {

  }

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
  }

  cancel(): void {
    this.router.navigate(["/admin-house"]);
  }

  createHouse(): void {
    let houseCreate:House =  this.checkHouse();

    let data = new FormData();
    data.append(FILE, this.selectedFile, this.selectedFile.name);
    data.append(HOUSE, JSON.stringify(houseCreate));

    this.houseService.createhouse(data).subscribe({
      next: ()=> {
        this.notificationService.showNotification(MESSAGE.CREATE.SUCCESS,FIELD.RESULT.SUCCESS);
      },
      error: (e) => {
        console.error(LOG_MESSAGES.create.error, e);
        this.notificationService.showNotification(MESSAGE.CREATE.FAIL,FIELD.RESULT.ERROR);
      },
      complete: () => {
        setTimeout(() => {
          this.router.navigate(["/admin-house"]);
        }, 2500);
      } 
    });
  }

  checkHouse(): House {
    let house: House = {} as House;
    house.houseName = this.houseCreateName;
    house.houseDescription = this.houseCreateDescription;

    if (!house.houseName || house.houseName.trim() === '') {
      this.notificationService.showNotification(MESSAGE.HOUSE.EMPTY_NAME, FIELD.RESULT.ERROR);
      throw null;
    }

    if (!house.houseDescription || house.houseDescription.trim() === '') {
      this.notificationService.showNotification(MESSAGE.HOUSE.EMPTY_DESC, FIELD.RESULT.ERROR);
      throw null;
    }
    return house;
  }
}

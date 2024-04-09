import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, switchMap } from 'rxjs';
import { FIELD, FILE, HOUSE } from 'src/app/core/constants/constants';
import { House } from 'src/app/models/house/house.model';
import { FileService } from 'src/app/services/file/file.service';
import { HouseService } from 'src/app/services/house/house.service';
import { NotificationService } from 'src/app/services/notification/notification.service';
import { LOG_MESSAGES } from 'src/app/core/constants/log-messages';
import { MESSAGE } from 'src/app/core/constants/messages';

@Component({
  selector: 'app-house-detail',
  templateUrl: './house-detail.component.html',
  styleUrls: ['./house-detail.component.scss']
})
export class HouseDetailComponent implements OnInit, OnDestroy {
  id: number = 0;
  house: House = {} as House;
  houseUpdateName = "";
  houseUpdateDescription = "";
  selectedFile!: File;
  defaultfile! :Blob;
  displayHouseName: boolean = false;
  displayHouseDescription: boolean = false;
  subscription: Subscription = new Subscription();

  constructor(private houseService: HouseService, private fileService: FileService, private route: ActivatedRoute, private router: Router, private notificationService: NotificationService) { }

  ngOnInit(): void {
    this.route.params.subscribe(param => {
      this.id = Number.parseInt(this.route.snapshot.params['houseId']);
      this.subscription.add(this.getHouseById(this.id));
    })
  }

  ngOnDestroy(): void {
      this.subscription.unsubscribe();
  }

  displayHouseNameEditButton() {
    this.displayHouseName = !this.displayHouseName;
  }

  displayHouseDescriptionEditButton() {
    this.displayHouseDescription = !this.displayHouseDescription;
  }

  setHouseName(id: string) {
    this.house.houseName = this.houseUpdateName;
    this.disableEditing(id)
  }

  resetHouseName() {
    this.houseUpdateName = this.house.houseName;
  }

  setHouseDescription(id: string) {
    this.house.houseDescription = this.houseUpdateDescription;
    this.disableEditing(id)
  }

  resetHouseDescription() {
    this.houseUpdateDescription = this.house.houseDescription;
  }

  setHouseProperty(id: string, property: string, value: any): void {
    if (Object.prototype.hasOwnProperty.call(this.house, property)) {
      (this.house as any)[property as keyof House] = value;
      this.disableEditing(id);
    } else {
      console.error(`La propriété ${property} n'existe pas dans l'objet House.`);
    }
  }

  resetHouseProperty(property: string) {
    (this as any)[`update${property.charAt(0).toUpperCase() + property.slice(1)}`] = (this.house as any)[property as keyof House];
  }

  enableEditing(id: string) {
    const inputElement = document.getElementById(id);
    if(inputElement != null) {
      inputElement.removeAttribute("readonly");
    }

  }

  disableEditing(id: string) {
    const inputElement = document.getElementById(id);
    if(inputElement != null) {
      inputElement.setAttribute("readonly", "true")
    }

  }

  getHouseById(id :number): Subscription {
    return this.houseService.getHouseById(id).subscribe({
      next: (house) => {
        this.house = house;
        this.fileService.getFile(house.houseLink).subscribe({
          next : (file) => {
            const reader = new FileReader();
            reader.onloadend = () => {
              this.house.houseImageUrl = reader.result as string;
            };
            reader.readAsDataURL(file);
            this.defaultfile = file;
          },
          error : (e) => console.log(LOG_MESSAGES.fileRetrieval.error, e)          
        })
      },
      error: (e) => console.error(LOG_MESSAGES.houseRetrieval.error, e)
    })
    
  }

  cancel() {
    this.router.navigate(["/admin-house"]);
  }

  updateHouse(id: number): void {
    let data = new FormData();
    if(this.selectedFile){
      data.append(FILE, this.selectedFile, this.selectedFile.name);
    } else {
      data.append(FILE, this.defaultfile);
    }
    
    data.append(HOUSE, JSON.stringify(this.checkHouse(this.house)));
    this.houseService.updateHouse(id, data).subscribe({
      next : () => {
        this.notificationService.showNotification(MESSAGE.UPDATE.SUCCESS, FIELD.RESULT.SUCCESS);
      },
      error : (e) => {
        console.error(LOG_MESSAGES.update.error, e);
        this.notificationService.showNotification(MESSAGE.UPDATE.FAIL, FIELD.RESULT.SUCCESS);
      },complete: () => {
        setTimeout(() => {
          this.router.navigate(["/admin-house"]);
        }, 2500);
      },
    })
  }

  checkHouse(house: House): House { 
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

  onFileSelected(event: any): void {
    if (event.target.files.length > 0) {
      this.selectedFile = event.target.files[0];
    }
  }


}

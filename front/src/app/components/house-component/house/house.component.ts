import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { LOG_MESSAGES } from 'src/app/core/constants/log-messages';
import { House } from 'src/app/models/house/house.model';
import { FileService } from 'src/app/services/file/file.service';
import { HouseService } from 'src/app/services/house/house.service';

@Component({
  selector: 'app-house',
  templateUrl: './house.component.html',
  styleUrls: ['./house.component.scss']
})
export class HouseComponent implements OnInit, OnDestroy{
  houses: House[] = [];
  private subscription: Subscription = new Subscription();
  
  constructor(private houseService: HouseService, private fileService: FileService) {}

  ngOnInit(): void {
    this.subscription.add(this.getHouses());
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
  
  getHouses(): Subscription {
    return this.houseService.getHouses().subscribe({
      next: (houses) => {
        this.houses = houses
        this.houses.forEach(house => {
          this.fileService.getFile(house.houseLink).subscribe({
            next: (file) => {
              const reader = new FileReader();
              reader.onloadend = () => {
                house.houseImageUrl = reader.result as string;
              };
               reader.readAsDataURL(file);
            },error: (e) => console.error(LOG_MESSAGES.houseRetrieval.error, e)
          })
        })
      },
      error: (e) => console.error(LOG_MESSAGES.productRetrieval.error, e)
    });
  } 
  
}

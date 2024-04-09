import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { LOG_MESSAGES } from 'src/app/core/constants/log-messages';
import { ProcurementState } from 'src/app/enums/procrument-state.enum';
import { LineProcurement } from 'src/app/models/procurement/line-procurement.model';
import { Procurement } from 'src/app/models/procurement/procurement.model';
import { FileService } from 'src/app/services/file/file.service';
import { ProcurementService } from 'src/app/services/procurement/procurement.service';

@Component({
  selector: 'app-procurement',
  templateUrl: './procurement.component.html',
  styleUrls: ['./procurement.component.scss']
})
export class ProcurementComponent implements OnInit, OnDestroy {
  procurement: Procurement = {} as Procurement;
  pastProcurement: Procurement[] = [];
  validateProcurementList: Procurement[] = [];
  subscrition: Subscription[] = [];
  constructor(private procurementService: ProcurementService, private fileService: FileService, private router: Router) {
  
  }

  ngOnInit(): void {
    this.subscrition.push(this.getValidateProcurement(), this.getPastProcurement());
      this.getPastProcurement();
  }

  ngOnDestroy(): void {
    this.subscrition.forEach((subscrition) => {
      subscrition.unsubscribe();
    })
  }

  getValidateProcurement(): Subscription {
    return this.procurementService.getValidateProcurement().subscribe({
      next: (procurements) => {
        this.validateProcurementList = procurements;
        this.validateProcurementList.forEach((procurement) => {
          procurement.lineProcurementList.forEach((line) => {
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
        })
      },
      error: (e) => console.error(LOG_MESSAGES.procurementRetrieval.error, e)
    })

  }


  getPastProcurement(): Subscription {
    return this.procurementService.getPastProcurement().subscribe({
      next: (procurements) => {
        this.pastProcurement = procurements;
        this.pastProcurement.forEach((procurement) => {
          procurement.lineProcurementList.forEach((line) => {
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
        })
      },
      error: (e) => console.error(LOG_MESSAGES.procurementRetrieval.error, e)
    })
  }

  

  goToProcurement(procurementId: number) {
    this.router.navigate(['/procurement-detail', procurementId]);
  }
}

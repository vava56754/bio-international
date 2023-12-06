import { DatePipe } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { LOG_MESSAGES } from 'src/app/core/constants/log-messages';
import { Procurement } from 'src/app/models/procurement/procurement.model';
import { FileService } from 'src/app/services/file/file.service';
import { ProcurementService } from 'src/app/services/procurement/procurement.service';

@Component({
  selector: 'app-procurement-view',
  templateUrl: './procurement-view.component.html',
  styleUrls: ['./procurement-view.component.scss']
})
export class ProcurementViewComponent implements OnInit, OnDestroy{
  procurement: Procurement = {} as Procurement;
  id: number = 0;
  subscription: Subscription = new Subscription();
  constructor(private router: Router, private route: ActivatedRoute, private procurementService: ProcurementService, private fileService: FileService){};

  ngOnInit(): void {
    this.route.params.subscribe(param => {
      this.id = Number.parseInt(this.route.snapshot.params['procurementId']);
      this.subscription.add(this.getProcurement(this.id));
      
    })
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  getProcurement(procurementId: number) {
    return this.procurementService.getProcurement(procurementId).subscribe({
      next: (procurement) => {
        this.procurement = procurement;
        this.procurement.lineProcurementList.forEach((line)=> {
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

  goToProductDetail(productId: number): void {
    this.router.navigate(['/product', productId]);
  }
}

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminProcurementDetailComponent } from './admin-procurement-detail.component';

describe('AdminProcurementDetailComponent', () => {
  let component: AdminProcurementDetailComponent;
  let fixture: ComponentFixture<AdminProcurementDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminProcurementDetailComponent]
    });
    fixture = TestBed.createComponent(AdminProcurementDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

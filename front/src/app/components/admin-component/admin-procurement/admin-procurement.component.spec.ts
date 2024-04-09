import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminProcurementComponent } from './admin-procurement.component';

describe('AdminProcurementComponent', () => {
  let component: AdminProcurementComponent;
  let fixture: ComponentFixture<AdminProcurementComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminProcurementComponent]
    });
    fixture = TestBed.createComponent(AdminProcurementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

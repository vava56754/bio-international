import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminHouseComponent } from './admin-house.component';

describe('AdminHouseComponent', () => {
  let component: AdminHouseComponent;
  let fixture: ComponentFixture<AdminHouseComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminHouseComponent]
    });
    fixture = TestBed.createComponent(AdminHouseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

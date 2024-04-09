import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcurementViewComponent } from './procurement-view.component';

describe('ProcurementViewComponent', () => {
  let component: ProcurementViewComponent;
  let fixture: ComponentFixture<ProcurementViewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProcurementViewComponent]
    });
    fixture = TestBed.createComponent(ProcurementViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

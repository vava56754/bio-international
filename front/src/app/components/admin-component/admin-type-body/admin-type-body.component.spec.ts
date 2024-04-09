import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminTypeBodyComponent } from './admin-type-body.component';

describe('AdminTypeBodyComponent', () => {
  let component: AdminTypeBodyComponent;
  let fixture: ComponentFixture<AdminTypeBodyComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminTypeBodyComponent]
    });
    fixture = TestBed.createComponent(AdminTypeBodyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

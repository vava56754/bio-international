import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ForWhoComponent } from './for-who.component';

describe('ForWhoComponent', () => {
  let component: ForWhoComponent;
  let fixture: ComponentFixture<ForWhoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ForWhoComponent]
    });
    fixture = TestBed.createComponent(ForWhoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PictureContentComponent } from './picture-content.component';

describe('PictureContentComponent', () => {
  let component: PictureContentComponent;
  let fixture: ComponentFixture<PictureContentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PictureContentComponent]
    });
    fixture = TestBed.createComponent(PictureContentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PirateLabelComponent } from './pirate-label.component';

describe('PirateLabelComponent', () => {
  let component: PirateLabelComponent;
  let fixture: ComponentFixture<PirateLabelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PirateLabelComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PirateLabelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

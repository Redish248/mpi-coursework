import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewShipProfileComponent } from './view-ship-profile.component';

describe('ViewShipProfileComponent', () => {
  let component: ViewShipProfileComponent;
  let fixture: ComponentFixture<ViewShipProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewShipProfileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewShipProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

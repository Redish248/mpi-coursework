import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewCrewProfileComponent } from './view-crew-profile.component';

describe('ViewCrewProfileComponent', () => {
  let component: ViewCrewProfileComponent;
  let fixture: ComponentFixture<ViewCrewProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewCrewProfileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewCrewProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

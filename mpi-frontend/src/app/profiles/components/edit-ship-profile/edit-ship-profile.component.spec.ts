import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditShipProfileComponent } from './edit-ship-profile.component';

describe('EditShipProfileComponent', () => {
  let component: EditShipProfileComponent;
  let fixture: ComponentFixture<EditShipProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditShipProfileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditShipProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

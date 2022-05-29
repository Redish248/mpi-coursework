import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditCrewProfileComponent } from './edit-crew-profile.component';

describe('EditCrewProfileComponent', () => {
  let component: EditCrewProfileComponent;
  let fixture: ComponentFixture<EditCrewProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditCrewProfileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditCrewProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotFoungPageComponent } from './not-foung-page.component';

describe('NotFoungPageComponent', () => {
  let component: NotFoungPageComponent;
  let fixture: ComponentFixture<NotFoungPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NotFoungPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NotFoungPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

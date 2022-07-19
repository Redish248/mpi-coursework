import {ComponentFixture, TestBed} from '@angular/core/testing';

import {TripRatingComponent} from './rating.component';

describe('TripRatingComponent', () => {
    let component: TripRatingComponent;
    let fixture: ComponentFixture<TripRatingComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [TripRatingComponent]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(TripRatingComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});

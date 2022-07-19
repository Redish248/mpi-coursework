import {ComponentFixture, TestBed} from '@angular/core/testing';

import {TravelerHistoryComponent} from './traveler-history.component';

describe('TravelerHistoryComponent', () => {
    let component: TravelerHistoryComponent;
    let fixture: ComponentFixture<TravelerHistoryComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [TravelerHistoryComponent]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(TravelerHistoryComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
